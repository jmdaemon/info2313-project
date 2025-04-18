package ui;

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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import plant.AbstractPlant;
import plant.PlantManager;

import ui.components.PlantFinder;
import ui.components.PlantForm;

public class Editor {
  // Data
  // private PlantManager pm;
  private ObjectProperty<PlantManager> pm;
  private ObservableList<PlantModel> plants;

  private ObjectProperty<AbstractPlant> plant;
  private BooleanProperty plantSelected;

  // private SimpleListProperty<ObservableList<AbstractPlant>> data;

  // Widgets
  private PlantFinder plant_finder;

  // Components
  private PlantForm form;
  private Stage form_window;
  private Scene form_dialog;

  // private class SearchPane {
  //   private GridPane gp;

  // Widget: Search

  
    

  // }

  // Button Bar
  private HBox hb;
  private Button btn_add;
  private Button btn_del;
  private Button btn_edit;
  private Button btn_load;
  private Button btn_save;
  private Button btn_back;

  private List<Label> titles;
  private HBox hb_info;

  // Table View
  private ChoiceBox<String> cb_plant;
  private List<TableColumn<PlantModel, String>> tbl_cols;
  private TableView<PlantModel> tbl_plant_info;

  // Paragraph View
  private List<Label> plant_details;
  /*
  private Label lbl_hdr_grow;
  private Label lbl_hdr_grow;
  private Label lbl_hdr_fert;
  private Label lbl_hdr_water;
  private Label lbl_hdr_stake;
  private Label lbl_hdr_prune;
  */

  private VBox vb;

  public Editor() {
    this.pm = new SimpleObjectProperty<PlantManager>();
    this.pm.set(GUI.createPlantManagerFixture());

    this.plant = new SimpleObjectProperty<AbstractPlant>();

    this.plantSelected = new SimpleBooleanProperty();
    this.plantSelected.bind(this.plant.isNull());

    this.plant_finder = new PlantFinder();
    this.plant_finder.pm.bind(this.pm);
    this.plant_finder.plant.bindBidirectional(this.plant);
    this.plant_finder.showAll();
    // this.plant_finder.plant.bind(this.plant);

    // PlantManager test = this.pm.get();
    // System.out.println(test.getPlants().get(0).info.name);
    // this.plant_finder = new PlantFinder(this.pm, this.plant);

    // this.pm = GUI.createPlantManagerFixture();

    // Button Bar
    this.btn_add = new Button("Add");
    this.btn_del = new Button("Delete");
    this.btn_edit = new Button("Edit");
    this.btn_load = new Button("Load");
    this.btn_save = new Button("Save");
    this.btn_back = new Button("Back");

    // Disable buttons requiring selections
    this.btn_del.setDisable(true);
    this.btn_edit.setDisable(true);

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
    this.hb = new HBox();
    this.vb = new VBox();
    this.vb.setSpacing(12);
    this.hb.setSpacing(12);

    this.hb.getChildren().addAll(
        this.btn_back,
        this.btn_add,
        this.btn_del,
        this.btn_edit,
        this.btn_load,
        this.btn_save);
    
    // this.vb.getChildren().addAll(this.hb, this.cb_plant, this.tbl_plant_info);
    this.vb.getChildren().addAll(this.hb, this.plant_finder.asParent());
    
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
    this.btn_add.setOnMouseClicked(event -> {
      this.form = new PlantForm();
      this.plant.bind(this.form.data);

      // Watch for new plant form changes
      this.plant.addListener((e) -> {
        AbstractPlant plant = this.plant.get();
        System.out.println(plant.info.name);
        // this.pm.add(plant);
        this.pm.get().add(plant);
      });

      setupDialog(this.form.asParent(), event, "Add a new plant");
    });

    // Enable buttons
    this.plant.addListener((obs, before, after) -> {
      boolean enabled = (after == null);
      this.btn_del.setDisable(enabled);
      this.btn_edit.setDisable(enabled);
    });

    // DELETE Plants
    this.btn_del.setOnMouseClicked(event -> {
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
    this.btn_edit.setOnMouseClicked(event -> {
      if (this.plant.get() != null) {

        int index = this.pm.get().indexOf(this.plant.get());

        if (index < 0) {
          System.out.println("No plants were found");
          return;
        }

        this.form = new PlantForm(this.plant.get());
        this.plant.bind(this.form.data);

        // Watch for new plant edits
        this.plant.addListener((e) -> {
          AbstractPlant plant = this.plant.get();
          System.out.println(plant.info.name);
          // this.pm.add(plant);
          this.pm.get().updatePlant(index, plant);
        });


        setupDialog(this.form.asParent(), event, "Edit an existing plant");
      }
    });

  }

  // private void setupDialog(Scene root, Parent dialog, Event event, final String title) {
  // private void setupDialog(Parent dialog, Event event, final String title) {
  private void setupDialog(Parent dialog, Event event, final String title) {
    // Stage stage = new Stage();

    // Scene root = new Scene(dialog);
    // root.setRoot(this.asParent());

    // Scene root = new Scene(dialog);
    // stage.setScene(root);
    // stage.initOwner(((Node) event.getSource()).getScene().getWindow());
    // stage.initOwner(((Node) event.getSource()).getScene().getWindow());
    // stage.setScene(new Scene(dialog));
    // stage.initOwner(root);
    this.form_dialog = new Scene(dialog);
    this.form_window = new Stage();
    this.form_window.setScene(this.form_dialog);
    // this.form_window.setTitle("Edit an existing plant");
    this.form_window.setTitle(title);
    this.form_window.initModality(Modality.WINDOW_MODAL);
    this.form_window.show();
    /*
    Stage stage = 
    stage.setScene(new Scene(dialog));
    stage.setTitle("Edit an existing plant");
    stage.initModality(Modality.WINDOW_MODAL);
    stage.showAndWait();
    */
    // stage.show();
  }


  // API

  // Set an element to change the scene root
  public void setNavigateEvent(final String elem, Scene root, Parent next) {
    switch(elem) {
      case "btn-back" -> {
        this.btn_back.setOnMouseClicked(_event -> {
          root.setRoot(next);
        });
      }
      // case "btn-add", "btn-edit" -> {
      // }
    }
  }

  /*
  public void setDialog(final String elem, Scene root) {
    switch(elem) {
      case "btn-add", "btn-edit" -> {
        setupListeners();
      }
    }
  }
  */

  public Parent asParent() {
    return this.vb;
  }
}
