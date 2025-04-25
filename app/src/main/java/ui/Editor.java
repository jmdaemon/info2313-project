package ui;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
import ui.components.PlantTaskBar;

public class Editor {
  // Data
  // private PlantManager pm;
  private ObjectProperty<PlantManager> pm;
  private ObservableList<PlantModel> plants;

  private ObjectProperty<AbstractPlant> plant;
  // private SimpleListProperty<ObservableList<AbstractPlant>> data;

  // Widgets
  private PlantTaskBar plant_taskbar;
  private PlantFinder plant_finder;
  private PlantDetails plant_details;

  // Components

  private List<Label> titles;
  private HBox hb_info;

  // Table View
  private ChoiceBox<String> cb_plant;
  private List<TableColumn<PlantModel, String>> tbl_cols;
  private TableView<PlantModel> tbl_plant_info;

  // Paragraph View
  // private List<Label> plant_details;
  /*
  private Label lbl_hdr_grow;
  private Label lbl_hdr_grow;
  private Label lbl_hdr_fert;
  private Label lbl_hdr_water;
  private Label lbl_hdr_stake;
  private Label lbl_hdr_prune;
  */

  private VBox vb;
  private BorderPane bp;

  public Editor() {
    this.pm = new SimpleObjectProperty<PlantManager>();
    this.pm.set(GUI.createPlantManagerFixture());

    this.plant = new SimpleObjectProperty<AbstractPlant>();

    // Task Bar
    this.plant_taskbar = new PlantTaskBar();
    this.plant_taskbar.setFont(new Font(15));


    this.plant_finder = new PlantFinder();
    this.plant_finder.pm.bind(this.pm);
    this.plant_finder.plant.bindBidirectional(this.plant);
    this.plant_finder.showAll();

    this.plant_details = new PlantDetails();
    this.plant_details.plant.bind(this.plant);

    // this.plant_finder.plant.bind(this.plant);

    // PlantManager test = this.pm.get();
    // System.out.println(test.getPlants().get(0).info.name);
    // this.plant_finder = new PlantFinder(this.pm, this.plant);

    // this.pm = GUI.createPlantManagerFixture();

    // Task Bar
    // Disable buttons requiring selections
    this.plant_taskbar.btn_del.setDisable(true);
    this.plant_taskbar.btn_edit.setDisable(true);


    this.hb_info = new HBox();

    // Table View
    this.cb_plant = new ChoiceBox<String>();
    this.tbl_plant_info = new TableView<PlantModel>();
    

    // Paragraph View


    
    // Properties

    // this.titles.addAll(List.of(
    //     new Label("Growing Method"),
    //     new Label("Growing Instructions"),
    //     new Label("Fertilizing Method"),
    //     new Label("Watering Method"),
    //     new Label("Staking Method"),
    //     new Label("Pruning Method")
    // ));

    // this.hb_info.getChildren().addAll(
    // );

    // this.hb_info.getChildren()
    //   .addAll(new Label());
    

    // Table
    this.tbl_plant_info.setPlaceholder(new Label("No plants available to display."));

    // Table Columns
    this.tbl_cols = Arrays.asList(
        new TableColumn<>("Name"),
        new TableColumn<>("Other Names"),
        new TableColumn<>("Potting Time"),
        new TableColumn<>("Potting Season"),
        new TableColumn<>("Price ($CAD)"),
        new TableColumn<>("Lifespan (Days)")
        // new TableColumn<>("Lifespan (Days)"),
        // TODO: We can move these to like a
        // different ui element later if they are too big
        /*
        new TableColumn<>("Growing Method"),
        new TableColumn<>("Growing Instructions"),
        new TableColumn<>("Fertilizing Method"),
        new TableColumn<>("Watering Method"),
        new TableColumn<>("Staking Method"),
        new TableColumn<>("Pruning Method")
        */
    );

    // this.lbl_grow = new Label("Growing Method");

    /*
    for (TableColumn<PlantModel, String> col : this.tbl_cols) {
      col.setCellValueFactory(new PropertyValueFactory<>("name"));
      this.tbl_plant_info.getColumns().add(col);
    }
    */

    // Table Items
    // for (AbstractPlant plant : this.pm.getPlants()) {
    for (AbstractPlant plant : this.pm.get().getPlants()) {
      PlantModel model = new PlantModel(plant);
      this.cb_plant.getItems().add(plant.info.name);
      this.tbl_plant_info.getItems().add(model);
    }

    this.pm.get();

    // this.plant_details.addAll(
        // new Label("Growing Method"),
        // new Label(plant.)
        // );

        // new TableColumn<>("Growing Method"),
        // new TableColumn<>("Growing Instructions"),
        // new TableColumn<>("Fertilizing Method"),
        // new TableColumn<>("Watering Method"),
        // new TableColumn<>("Staking Method"),
        // new TableColumn<>("Pruning Method")
    
    
    
    // Packing
    // this.hb = new HBox();
    // this.hb.setSpacing(12);

    this.vb = new VBox();
    this.vb.setSpacing(12);

    this.bp = new BorderPane();

    // this.hb.getChildren().addAll(
    //     this.btn_back,
    //     this.btn_load,
    //     this.btn_save,
    //     this.btn_add,
    //     this.btn_del,
    //     this.btn_edit
    //     );
    
    // this.vb.getChildren().addAll(this.hb, this.cb_plant, this.tbl_plant_info);

    // this.vb.getChildren().addAll(
    //     this.hb,
    //     this.plant_finder.asParent(),
    //     this.plant_details.asParent()
    //     );
    // this.bp.setTop(this.hb);
    this.bp.setTop(this.plant_taskbar.asParent());
    this.bp.setCenter(this.plant_details.asParent());
    this.bp.setLeft(this.plant_finder.asParent());
    
    // Hook up Listeners

    // Selection ChoiceBox
    this.cb_plant.setOnAction((event) -> {
      // Set labels
      final int selected = this.cb_plant.getSelectionModel().getSelectedIndex();
      
      // final AbstractPlant plant = this.pm.getPlants().get(selected);
      final AbstractPlant plant = this.pm.get().getPlants().get(selected);

      // final int selectedIndex = this.cb_plant.getSelectionModel().getSelectedIndex();
      // final Object selectedItem = this.cb_plant.getSelectionModel().getSelectedItem();

      // System.out.println("Selection made: [" + selectedIndex + "] " + selectedItem);
      // System.out.println("   ChoiceBox.getValue(): " + this.cb_plant.getValue());
    });

    // TableView


    
    setupListeners();
  }
  // Internal
  // private void setupListeners(Scene root) {
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
        this.plant_finder.showAll();
        System.out.println("Successfully imported plants from: " + fp.toString());
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
        System.out.println(fp.toString());
        this.pm.get().write(fp.toString(), true);
        System.out.println("Successfully exported plants to: " + fp.toString());
      } else {
        // Init plants file
        this.pm.get().write(fp.toString(), false);
      }
    });
  }

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
      // case "btn-add", "btn-edit" -> {
      // }
    }
  }

  public Parent asParent() {
    return this.bp;
    // return this.vb;
  }
}
