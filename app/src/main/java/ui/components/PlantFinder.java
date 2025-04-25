package ui.components;

import java.util.List;
import java.util.stream.Stream;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableRow;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import plant.AbstractPlant;
import plant.PlantManager;
import plant.PlantType;

import utils.Library;

/**
  * Widget that search plants by various options
  */
public class PlantFinder {
  
  private VBox vb;
  private Label lbl_search;

  private GridPane gp_options;
  private ColumnConstraints cc1;
  private ColumnConstraints cc2;

  private Label lbl_options;
  private ComboBox<String> cb_plant_type;

  private Button btn_find;
  private ListView<String> lv_results;

  // Properties
  public ObjectProperty<PlantManager> pm;
  public ObjectProperty<AbstractPlant> plant;
  
  public PlantFinder() {

    // Properties
    this.pm = new SimpleObjectProperty<PlantManager>();
    this.plant = new SimpleObjectProperty<AbstractPlant>();

    // Controls
    this.pm = new SimpleObjectProperty<PlantManager>();
    this.plant = new SimpleObjectProperty<AbstractPlant>();

    this.vb = new VBox();
    this.vb.setSpacing(12);
    this.vb.setPadding(new Insets(12));

    Font font = new Font(15);
    Font h1 = new Font(24);
    Font h2 = new Font(18);

    this.lbl_search = new Label("Search");
    this.lbl_search.setFont(h1);
    
    this.gp_options = new GridPane();
    // this.gp_options.setPadding(new Insets(12));
    this.gp_options.setVgap(12); 
    this.gp_options.setHgap(12);
    
    this.cc1 = new ColumnConstraints(150);
    // this.cc1.setHalignment(HPos.CENTER);
    this.cc1.setHalignment(HPos.LEFT);
    this.cc2 = new ColumnConstraints(150);
    this.cc2.setHalignment(HPos.CENTER);

    this.gp_options.getColumnConstraints().addAll(this.cc1, this.cc2);

    this.lbl_options = new Label("Options");
    this.lbl_options.setFont(h2);

    // TODO: Create plant type combo box?
    this.cb_plant_type = new ComboBox<String>();
    this.cb_plant_type.setPrefWidth(300);
    this.cb_plant_type.setPromptText("Select a plant type.");
    this.cb_plant_type.getItems().add("");
    this.cb_plant_type.getItems().addAll(
        Library.enumToLabels(Stream.of(PlantType.values()).map(s -> s.toString()).toList())
    );

    this.btn_find = new Button("Find");
    this.btn_find.setFont(font);
    
    this.lv_results = new ListView<String>();
    this.lv_results.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    // Listeners

    // Show results in list view
    this.btn_find.setOnMouseClicked((event) -> {
      this.search();
    });

    // Update our seleced plant
    this.lv_results.getSelectionModel().selectedIndexProperty().addListener((e, before, after) -> {
      // Select new plants only if they're valid choices
      if (after.intValue() > -1)
        this.plant.set(this.pm.get().getPlants().get(after.intValue()));
    });

    // Deselect plants
    this.vb.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {

      Node source = evt.getPickResult().getIntersectedNode();

      // Search node hierarchy until ListCell or Scene Root found
      while (source != null && !(source instanceof ListCell))
        source = source.getParent();

      // Clear selection on click anywhere but on a selected cell
      if (source == null || (source instanceof ListCell && ((ListCell) source).isEmpty())) {
        this.lv_results.getSelectionModel().clearSelection();
        this.plant.set(null);
      }
    });

    // Packing

    // this.gp_options.add(this.lbl_options, 0, 0, 2, 1);
    // this.gp_options.add(this.cb_plant_type, 0, 1);
    this.gp_options.add(this.lbl_options, 0, 0, 1, 1);
    this.gp_options.add(this.cb_plant_type, 0, 1);

    this.vb.getChildren().addAll(
        this.lbl_search,
        this.gp_options,
        this.btn_find,
        this.lv_results
    );
  }

  // Internal
  private void search() {
    // Get Plant Type
    String input = this.cb_plant_type.getSelectionModel().getSelectedItem();

    // Show all results by default
    if (input == "") {
      showAll();
      return;
    }

    // Filter results
    final PlantType plant_type = PlantType.fromString(input);

    List<AbstractPlant> results = this.pm.get().search(plant_type);

    this.lv_results.getItems().clear();

    // If there are any new results
    if (results.size() > 0) {
      List<String> plants = results.stream().map(p -> p.info.name).toList();
      this.lv_results.getItems().addAll(plants);
    }
  }
  
  // API

  // In-case we want to filter on types immediately
  public void setFilterImmediate(final boolean on) {
    if (on) {
      // Add the listener on the combo box
      this.cb_plant_type.getSelectionModel().selectedItemProperty().addListener((e, before, after) -> {
        this.search();
      });
    } else {
      // Remove listener on combo box
      this.cb_plant_type.getSelectionModel().selectedItemProperty().removeListener((e, before, after) -> {});
    }
  }

  // Refresh the results
  public void refresh() {
    this.search();
  }

  // Show available plants when none is selected
  public void showAll() {
    this.lv_results.getItems().clear();
    List<AbstractPlant> results = this.pm.get().getPlants();
    List<String> plants = results.stream().map(p -> p.info.name).toList();
    this.lv_results.getItems().addAll(plants);
  }

  public Parent asParent() {
    return this.vb;
  }

  // Expose plant inter
}
