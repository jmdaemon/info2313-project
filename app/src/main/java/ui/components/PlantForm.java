package ui.components;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import plant.AbstractPlant;
import plant.GrowType;
import plant.PlantInfo;
import plant.PlantType;
import plant.Season;
import plant.plants.Creeper;
import plant.plants.Herb;
import plant.plants.Tree;

import ui.interfaces.Component;

import utils.Library;

public class PlantForm implements Component {
  private GridPane gp;

  private ColumnConstraints cc1;
  private ColumnConstraints cc2;

  // C1
  private Label lbl_name;
  private Label lbl_names;
  private Label lbl_plant_type;
  private Label lbl_pot_season;
  private Label lbl_pot_date;
  private Label lbl_price;
  private Label lbl_lifespan;
  private Label lbl_grow_method;
  private Label lbl_grow_instructions;
  private List<Label> all_labels;

  // C2
  private TextField tf_name;
  private TextField tf_names;
  private ComboBox<String> cb_plant_type;
  private ComboBox<String> cb_pot_season;
  private DatePicker dp_pot_date;
  private TextField tf_price;
  private TextField tf_lifespan;
  private ComboBox<String> cb_grow_method;
  private TextArea ta_grow_instructions;
  private Button btn_submit;

  // Specific Plant Control Types
  // Additional fields to read user input for specific plant types
  private Label lbl_plant_extra;
  private TextField tf_plant_extra;

  // public ObservableList<AbstractPlant> data;
  public ObjectProperty<AbstractPlant> data;

  // Add
  public PlantForm() {
    this.createLabels();
    this.createControls();
    this.btn_submit.setText("Submit");
    this.packControls();
    this.setupListeners();
  }

  // Edit:
  public PlantForm(AbstractPlant plant) {
    this.createLabels();
    this.createControls();

    this.btn_submit.setText("Done");

    // Fill controls with information from our plant
    this.fillLabels(plant.info);

    // Set labels & fill extra plant details
    this.setExtraPlantControls(plant.info.plant_type.toString());

    // Fill labels for extra plant details
    switch(plant.info.plant_type) {
      case TREE -> {
        Tree tree = (Tree) plant;
        final double height = tree.getHeight();
        this.tf_plant_extra.setText(String.valueOf(height));
      }
      case HERB -> {
        Herb herb = (Herb) plant;
        final String taste = herb.getTaste();
        this.tf_plant_extra.setText(taste);
      }
      case CREEPER -> {
        Creeper creeper = (Creeper) plant;
        final String color = creeper.getColor();
        this.tf_plant_extra.setText(color);
      }
    }
    
    // Packing
    this.packControls();
    this.setupListeners();
  }

  // Internal
  private void createLabels() {
    this.lbl_name = new Label("Plant Name");
    this.lbl_names = new Label("Other Names");
    this.lbl_plant_type = new Label("Plant Type");
    this.lbl_pot_season = new Label("Pot Season");
    this.lbl_pot_date = new Label("Pot Date");
    this.lbl_price = new Label("Price $(CAD)");
    this.lbl_lifespan = new Label("Lifespan (Days)");
    this.lbl_grow_method = new Label("Growing Method"); 
    this.lbl_grow_instructions = new Label("Growing Instructions"); 
    this.lbl_plant_extra = new Label("");

    this.all_labels = new ArrayList<Label>();
    all_labels.addAll(List.of(
          lbl_name, lbl_names, lbl_plant_type, lbl_pot_season,
          lbl_pot_date, lbl_price, lbl_price, lbl_lifespan,
          lbl_grow_method, lbl_grow_instructions));

    Font font = new Font(15);
    for (Label lbl : this.all_labels) {
      lbl.setFont(font);
    }
  }

  // Fill labels with plant info
  private void fillLabels(final PlantInfo info) {
    this.tf_name.setText(info.name);
    this.tf_names.setText(info.alt_names.stream().collect(Collectors.joining(",")));
    this.cb_plant_type.getSelectionModel().select(info.plant_type.toString());;
    this.cb_pot_season.getSelectionModel().select(info.pot_time.toString());
    this.dp_pot_date.setValue(info.pot_date);
    this.tf_price.setText(String.valueOf(info.price));
    this.tf_lifespan.setText(String.valueOf(info.lifespan));
    this.cb_pot_season.getSelectionModel().select(info.grow_method.toString());
    this.ta_grow_instructions.setText(info.grow_instructions);
  }

  private void createControls() {
    this.tf_name = new TextField();
    this.tf_names = new TextField();
    this.cb_plant_type = new ComboBox<String>();
    this.cb_pot_season = new ComboBox<String>();
    this.dp_pot_date = new DatePicker(LocalDate.now());
    this.tf_price = new TextField();
    this.tf_lifespan = new TextField();
    this.cb_grow_method = new ComboBox<String>();
    this.ta_grow_instructions = new TextArea();
    this.btn_submit = new Button();

    this.tf_name.setPromptText("e.g Apple Tree");
    this.tf_names.setPromptText("e.g Apple Common, Common Apple Tree");
    this.tf_price.setPromptText("e.g 50.0");
    this.tf_lifespan.setPromptText("e.g 7200");

    // this.cb_plant_type.setPromptText("Choose a plant type.");
    this.cb_plant_type.setPromptText("Select a plant type.");

    this.cb_plant_type.getItems().addAll(
        // "Tree", "Herb", "Creeper"
        Library.enumToLabels(Stream.of(PlantType.values()).map(s -> s.toString()).toList())
    );

    this.cb_pot_season.setPromptText("Select the best season for planting.");
    this.cb_pot_season.getItems().addAll(
      // "Winter", "Spring", "Summer", "Fall"
        Library.enumToLabels(Stream.of(Season.values()).map(s -> s.toString()).toList())
    );

    this.cb_grow_method.setPromptText("Select a method of growing");
    this.cb_grow_method.getItems().addAll(
      // "Seeding", "Cutting", "Layering", "Grafting", "Budding"
        Library.enumToLabels(Stream.of(GrowType.values()).map(s -> s.toString()).toList())
    );

    this.dp_pot_date.setPromptText("Select the best date for planting.");
    this.ta_grow_instructions.setPromptText("Note any extra special growing instructions here.");

    this.tf_plant_extra = new TextField();
    this.data = new SimpleObjectProperty<AbstractPlant>();
    // this.data = FXCollections.observableArrayList();
    // this.data = new ObjectProperty<AbstractPlant>();

  }

  private void packControls() {
    this.gp = new GridPane();
    this.gp.setPadding(new Insets(12, 12, 12, 12));
    this.gp.setVgap(12); 
    this.gp.setHgap(12);

    this.cc1 = new ColumnConstraints(150);
    this.cc1.setHalignment(HPos.RIGHT);

    this.cc2 = new ColumnConstraints(100);
    this.cc2.setHalignment(HPos.LEFT);
    
    this.gp.addRow(0, this.lbl_name, this.tf_name);
    this.gp.addRow(1, this.lbl_names, this.tf_names);
    this.gp.addRow(2, this.lbl_plant_type, this.cb_plant_type);
    this.gp.addRow(3, this.lbl_pot_season , this.cb_pot_season);
    this.gp.addRow(4, this.lbl_pot_date, this.dp_pot_date);
    this.gp.addRow(5, this.lbl_price, this.tf_price);
    this.gp.addRow(6, this.lbl_lifespan, this.tf_lifespan);
    this.gp.addRow(7, this.lbl_grow_method, this.cb_grow_method);
    this.gp.addRow(8, this.lbl_grow_instructions, this.ta_grow_instructions);
    this.gp.addRow(9, this.lbl_plant_extra, this.tf_plant_extra);
    this.gp.addRow(10, this.btn_submit);

    this.btn_submit.setAlignment(Pos.CENTER_RIGHT);
  }

  private void setupListeners() {
    // Accept extra plant data
    this.cb_plant_type
      .getSelectionModel()
      .selectedItemProperty()
      .addListener((opts, before, after) -> {
        setExtraPlantControls(after);
    });

    // Read the form info, check validity, and set plant data in parent
    this.btn_submit.setOnMouseClicked(event -> {
      // Get plant data
      final AbstractPlant plant = getPlant();

      // Notify subscribers of our plant data
      this.data.set(plant);
      // this.data.add(plant);

      // Close the dialog window
      Stage stage = (Stage) this.btn_submit.getScene().getWindow();
      stage.close();
    });
  }

  private AbstractPlant getPlant() {
    // Check for validity
    // TODO: Check for '|' character & filter it out
    final String name = this.tf_name.getText();
    final List<String> names = List.of(this.tf_names.getText().split(","));

    final PlantType p_type = PlantType.fromString(this.cb_plant_type.getSelectionModel().getSelectedItem());
    final Season pot_time = Season.fromString(this.cb_pot_season.getSelectionModel().getSelectedItem());
    final GrowType grow_method = GrowType.fromString(this.cb_grow_method.getSelectionModel().getSelectedItem());

    final LocalDate pot_date = this.dp_pot_date.getValue();

    final double price = Double.valueOf(this.tf_price.getText());
    final int lifespan = Integer.valueOf(this.tf_lifespan.getText());
    final String grow_instructions = this.ta_grow_instructions.getText();

    // Create plant
    final PlantInfo plant_info = new PlantInfo(
        name, names, pot_time, pot_date,
        price, lifespan,
        grow_method, grow_instructions, p_type);

    AbstractPlant plant = null;
    switch(p_type) {
      case TREE: {
        final double height = Double.valueOf(this.tf_plant_extra.getText());
        plant = new Tree(plant_info, height);
      }
      case HERB: {
        final String taste = this.tf_plant_extra.getText();
        plant = new Herb(plant_info, taste);
      }
      case CREEPER: {
        final String color = this.tf_plant_extra.getText();
        plant = new Herb(plant_info, color);
      }
      default: {}
    }
    return plant;
  }

  private void setExtraPlantControls(String plant_type) {
    final PlantType plant = PlantType.fromString(plant_type);
    switch(plant) {
      case TREE: {
        this.lbl_plant_extra.setText("Tree Height");
        this.tf_plant_extra.setPromptText("e.g 120.0");
        break;
      }
      case HERB: {
        this.lbl_plant_extra.setText("Herb Taste");
        this.tf_plant_extra.setPromptText("e.g Minty");
        break;
      }
      case CREEPER: {
        this.lbl_plant_extra.setText("Creeper Color");
        this.tf_plant_extra.setPromptText("e.g Green");
        break;
      }
      default: { } // Ignore empty cases
    }
  }

  // API
  @Override
  public Parent asParent() {
    return this.gp;
  }
}
