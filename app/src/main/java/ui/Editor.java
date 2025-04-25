package ui;

import java.io.File;
import java.nio.file.Paths;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

import plant.AbstractPlant;
import plant.PlantManager;

import ui.components.PlantDetails;
import ui.components.PlantFinder;
import ui.components.PlantForm;
import ui.components.PlantStatusBar;
import ui.components.PlantTaskBar;

public class Editor {
  // Properties
  private ObjectProperty<PlantManager> pm;
  private ObjectProperty<AbstractPlant> plant;

  // Widgets
  private PlantTaskBar plant_taskbar;
  private PlantStatusBar plant_statusbar;
  private PlantFinder plant_finder;
  private PlantDetails plant_details;

  // Components
  private BorderPane bp;

  private Separator sep_left;

  private HBox hb_left;
  private VBox vb;

  public Editor() {

    // Properties
    this.pm = new SimpleObjectProperty<PlantManager>();
    this.plant = new SimpleObjectProperty<AbstractPlant>();

    this.pm.set(GUI.createPlantManagerFixture());

    // Components

    // Task Bar
    this.plant_taskbar = new PlantTaskBar();
    this.plant_taskbar.setFont(new Font(15));

    // Status Bar
    this.plant_statusbar = new PlantStatusBar();
    this.plant_statusbar.setFont(new Font(15));

    // Plant Finder
    this.plant_finder = new PlantFinder();
    this.plant_finder.pm.bind(this.pm);
    this.plant_finder.plant.bindBidirectional(this.plant);
    this.plant_finder.showAll();

    // Plant Details
    this.plant_details = new PlantDetails();
    this.plant_details.plant.bind(this.plant);

    // Controls

    // Disable buttons requiring selections
    this.plant_taskbar.btn_del.setDisable(true);
    this.plant_taskbar.btn_edit.setDisable(true);

    // Packing

    this.hb_left = new HBox();
    this.sep_left = new Separator(Orientation.VERTICAL);


    this.vb = new VBox();
    this.vb.setSpacing(12);

    this.bp = new BorderPane();

    this.hb_left.getChildren().addAll(
        this.plant_finder.asParent(),
        this.sep_left
    );

    this.bp.setTop(this.plant_taskbar.asParent());
    this.bp.setCenter(this.plant_details.asParent());

    this.bp.setLeft(this.hb_left);
    this.bp.setBottom(this.plant_statusbar.asParent());
    
    // Listeners
    setupListeners();
  }

  // Internal
  private void setupListeners() {

    // ADD Plants
    this.plant_taskbar.btn_add.setOnMouseClicked(event -> {
      PlantForm form = new PlantForm();
      this.plant.bind(form.data);

      // Watch for new plant form changes
      this.plant.addListener((e) -> {
        AbstractPlant plant = this.plant.get();
        System.out.println(plant.info.name);
        this.pm.get().add(plant);
      });

      Stage window = setupDialog(form.asParent(), event, "Add a new plant");
      window.show();
    });

    // Enable buttons
    this.plant.addListener((obs, before, after) -> {
      boolean enabled = (after == null);
      this.plant_taskbar.btn_del.setDisable(enabled);
      this.plant_taskbar.btn_edit.setDisable(enabled);
    });

    // DELETE Plants
    this.plant_taskbar.btn_del.setOnMouseClicked(event -> {
      if (this.plant.get() != null) {

        // Get the index of the plant
        int index = this.pm.get().indexOf(this.plant.get());

        if (index < 0) {
          System.out.println("No plants were found");
          return;
        }
        // Delete the plant
        this.pm.get().del(index);
        System.out.println("Deleted plant: " + this.plant.get().info.name);
        System.out.println("Plant List Size: " + this.pm.get().getPlants().size());

        // Deselect the plant 
        this.plant.set(null);

        this.plant_finder.refresh();
      }
    });
    
    // EDIT Plants
    this.plant_taskbar.btn_edit.setOnMouseClicked(event -> {
      if (this.plant.get() != null) {

        int index = this.pm.get().indexOf(this.plant.get());

        if (index < 0) {
          System.out.println("No plants were found");
          return;
        }

        PlantForm form = new PlantForm(this.plant.get());
        this.plant.bind(form.data);

        // Watch for new plant edits
        this.plant.addListener((e) -> {
          AbstractPlant plant = this.plant.get();
          System.out.println(plant.info.name);
          this.pm.get().updatePlant(index, plant);
        });

        Stage window = setupDialog(form.asParent(), event, "Edit an existing plant");
        window.show();
      }
    });

    // IMPORT Plants
    this.plant_taskbar.btn_load.setOnMouseClicked(event -> {

      FileChooser fchooser = setupFileChooser("IMPORT"); 
      Stage stage = new Stage();
      File fp = fchooser.showOpenDialog(stage);
      
      if (fp.exists()) {
        System.out.println(fp.toString());
        this.pm.get().read(fp.toString(), false);
        
        // Show the new loaded plants
        final String status = "Successfully imported plants from: " + fp.toString();

        System.out.println(status);
        this.plant_statusbar.showStatus(status, 3000);
        this.plant_finder.showAll();
      } else {
        // Init plants file
        this.pm.get().read(fp.toString(), true);
      }
    });

    // EXPORT Plants
    this.plant_taskbar.btn_save.setOnMouseClicked(event -> {

      FileChooser fchooser = setupFileChooser("EXPORT");
      Stage stage = new Stage();
      File fp = fchooser.showSaveDialog(stage);
      
      // Write plants to disk
      if (fp.exists()) {
        final String status = "Successfully exported plants to: " + fp.toString();
        System.out.println(status);
        this.plant_statusbar.showStatus(status, 3000);
        this.pm.get().write(fp.toString(), true);
      } else {
        // Init plants file
        this.pm.get().write(fp.toString(), false);
      }
    });
  }

  /** Set up a file chooser to save or load files */
  private FileChooser setupFileChooser(final String type) {
    // Open file chooser to default plants.psv dir
    String default_dir = Paths.get("").toAbsolutePath().toString();
    FileChooser fchooser = new FileChooser();
    fchooser.setInitialDirectory(new File(default_dir));
    switch(type) {
      case "EXPORT" -> {
        fchooser.setTitle("Save plants to: ");
        fchooser.setInitialFileName("plants.psv");
      }
      case "IMPORT" -> {
        fchooser.setTitle("Select PSV File");
        fchooser.setSelectedExtensionFilter(new ExtensionFilter("Pipe Separated Value File", "psv"));
      }
      default -> {
        System.out.println("WARNING: No valid chooser type selected. Default file chooser will be used.");
      }
    }
    return fchooser;
  }

  /** Set up a dialog window with a title */
  private Stage setupDialog(Parent dialog, Event event, final String title) {
    Scene scene = new Scene(dialog);
    Stage window = new Stage();
    
    window.setScene(scene);
    window.setTitle(title);
    window.initModality(Modality.WINDOW_MODAL);
    return window;
  }

  // API

  // Set an element to change the scene root
  public void setNavigateEvent(final String elem, Scene root, Parent next) {
    switch(elem) {
      case "btn-back" -> {
        this.plant_taskbar.btn_back.setOnMouseClicked(_event -> {
          root.setRoot(next);
        });
      }
    }
  }

  public Parent asParent() {
    return this.bp;
  }
}
