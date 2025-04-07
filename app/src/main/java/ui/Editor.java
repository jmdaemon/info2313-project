package ui;

import java.util.Arrays;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import plant.AbstractPlant;
import plant.PlantManager;

public class Editor {
  // Data
  private PlantManager pm;
  private ObservableList<PlantModel> plants;

  // Widgets

  // Button Bar
  private HBox hb;
  private Button btn_add;
  private Button btn_del;
  private Button btn_edit;
  private Button btn_load;
  private Button btn_save;
  private Button btn_back;

  private ChoiceBox<String> cb_plant;
  private List<TableColumn<PlantModel, String>> tbl_cols;
  private TableView<PlantModel> tbl_plant_info;

  private VBox vb;

  public Editor() {
    this.pm = GUI.createPlantManagerFixture();

    this.hb = new HBox();
    this.btn_add = new Button();
    this.btn_del = new Button();
    this.btn_edit = new Button();
    this.btn_load = new Button();
    this.btn_save = new Button();
    this.btn_back = new Button();

    this.cb_plant = new ChoiceBox<String>();
    this.tbl_plant_info = new TableView<PlantModel>();

    this.vb = new VBox();
    
    // Setup
    // Table Columns
    this.tbl_cols = Arrays.asList(
        new TableColumn<>("Name"),
        new TableColumn<>("Other Names"),
        new TableColumn<>("Potting Time"),
        new TableColumn<>("Potting Season"),
        new TableColumn<>("Price ($CAD)"),
        new TableColumn<>("Lifespan (Days)"),
        // TODO: We can move these to like a
        // different ui element later if they are too big
        new TableColumn<>("Growing Method"),
        new TableColumn<>("Growing Instructions"),
        new TableColumn<>("Fertilizing Method"),
        new TableColumn<>("Watering Method"),
        new TableColumn<>("Staking Method"),
        new TableColumn<>("Pruning Method")
    );

    for (TableColumn<PlantModel, String> col : this.tbl_cols) {
      this.tbl_plant_info.getColumns().add(col);
    }

    // Table Items
    for (AbstractPlant plant : this.pm.getPlants()) {
      PlantModel model = new PlantModel(plant);
      this.cb_plant.getItems().add(plant.info.name);
      this.tbl_plant_info.getItems().add(model);
    }
    
    // Packing
    this.hb.getChildren().addAll(
        this.btn_back,
        this.btn_add,
        this.btn_del,
        this.btn_edit,
        this.btn_load,
        this.btn_save);
    
    this.vb.getChildren().addAll(this.hb, this.cb_plant, this.tbl_plant_info);
    
    // Hook up Listeners

    // Selection ChoiceBox
    this.cb_plant.setOnAction((event) -> {
      final int selectedIndex = this.cb_plant.getSelectionModel().getSelectedIndex();
      final Object selectedItem = this.cb_plant.getSelectionModel().getSelectedItem();

      System.out.println("Selection made: [" + selectedIndex + "] " + selectedItem);
      System.out.println("   ChoiceBox.getValue(): " + this.cb_plant.getValue());
    });

    // TableView

  }

  // API

  // Set an element to change the scene root
  public void setNavigateEvent(Scene root, Parent next) {
    this.btn_back.setOnMouseClicked(_event -> {
      root.setRoot(next);
    });
  }

  public Parent asParent() {
    return this.vb;
  }
}
