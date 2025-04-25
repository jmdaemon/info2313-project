package ui.components;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import plant.AbstractPlant;

/**
  * Show plant details and information
  * TODO: 
  * - Clean up interface a bit
  * - Include picture of plant
  */
public class PlantDetails {

  final static private List<String> titles = List.of(
    "Name",
    "Other Names",
    "Potting Season",
    "Potting Date",
    "Price $(CAD)",
    "Lifespan (Days)"
  );
  private List<Label> lbl_titles;

  final static private List<String> plant_titles = List.of(
    "Staking",
    "Pruning",
    "Fertilizing",
    "Watering"
  );
  private List<Label> lbl_plant_titles;

  // private HBox hb;
  private VBox vb_details;
  private GridPane gp_details;

  private VBox vb;

  private Label hdr_details;
  private Label hdr_instructions;

  private Label lbl_name;
  private Label lbl_alt_names;
  private Label lbl_pot_time;
  private Label lbl_pot_date;
  private Label lbl_price;
  private Label lbl_lifespan;
  private List<Label> lbl_details;

  private Label lbl_stake;
  private Label lbl_prune;
  private Label lbl_fertilize;
  private Label lbl_water;
  private List<Label> lbl_plant_details;

  // private List<Label> all_labels;
  // Grow Method
  // Grow Instructions
  
  // Longer
  // Staking
  // Pruning
  // Fertilizing
  // Watering

  public ObjectProperty<AbstractPlant> plant;

  // TODO:
  // Add Image of plant
  public PlantDetails() {

    // Prepare Titles & Empty Details

    this.plant = new SimpleObjectProperty<AbstractPlant>();

    this.lbl_titles = new ArrayList<Label>();
    this.lbl_details = new ArrayList<Label>();
    this.lbl_plant_titles = new ArrayList<Label>();
    this.lbl_plant_details = new ArrayList<Label>();
    // this.all_labels = new ArrayList<Label>();

    this.vb_details = new VBox();
    this.vb_details.setSpacing(12);

    this.gp_details = new GridPane();
    this.gp_details.setPadding(new Insets(12, 12, 12, 12));
    this.gp_details.setVgap(12); 
    this.gp_details.setHgap(12);

    Font font = new Font(15);

    Font heading = new Font(24);

    this.hdr_details = new Label("Details");
    this.hdr_details.setAlignment(Pos.CENTER);
    this.hdr_details.setFont(heading);

    this.hdr_instructions = new Label("Instructions");
    this.hdr_instructions.setFont(heading);

    // Create Titles
    for (String title : this.titles) {
      Label lbl = new Label(title);
      lbl.setFont(font);
      this.lbl_titles.add(lbl);
    }
      // this.lbl_titles.add(new Label(title));
    

    /*
    this.lbl_name = new Label("");
    this.lbl_alt_names = new Label("");
    this.lbl_pot_time = new Label("");
    this.lbl_pot_date = new Label("");
    this.lbl_price = new Label("");
    this.lbl_lifespan = new Label("");
    this.lbl_details.addAll(List.of(
        lbl_name, lbl_alt_names, lbl_pot_time,
        lbl_pot_date, lbl_price, lbl_lifespan));
        */
    
      // Label lbl = new Label(title);
      // lbl.setFont(font);
      // this.lbl_titles.add(lbl);
    // }

    // Create Empty Details
    for (int i = 0; i < this.lbl_titles.size(); i++) {
      Label lbl = new Label("N/A");
      lbl.setFont(font);
      this.lbl_details.add(lbl);
    }

    this.vb = new VBox();
    this.vb.setSpacing(12);
    this.vb.setPadding(new Insets(12));

    // Create Plant Titles
    for (String title : this.plant_titles) {
      Label lbl = new Label(title);
      lbl.setFont(font);
      this.lbl_plant_titles.add(lbl);
    }
    
    // Create Empty Plant Details
    for (int i = 0; i < this.lbl_plant_titles.size(); i++) {
      Label lbl = new Label("N/A");
      lbl.setFont(font);
      this.lbl_plant_details.add(lbl);
    }

    // Listeners
    this.plant.addListener((e, before, after) -> {

      // Set the details of the plants
      if (after != null) {
        this.lbl_details.get(0).setText(after.info.name);
        this.lbl_details.get(1).setText(after.info.alt_names.toString());
        this.lbl_details.get(2).setText(after.info.pot_time.toString());
        this.lbl_details.get(3).setText(after.info.pot_date.toString());
        this.lbl_details.get(4).setText(String.valueOf(after.info.price));
        this.lbl_details.get(5).setText(String.valueOf(after.info.lifespan));

        this.lbl_plant_details.get(0).setText(after.staking());
        this.lbl_plant_details.get(1).setText(after.pruning());
        this.lbl_plant_details.get(2).setText(after.fertilizing());
        this.lbl_plant_details.get(3).setText(after.watering());
      }
    });
    
    // Packing

    // GridPane Col: 1
    this.gp_details.add(this.hdr_details, 0, 0, 2,  1);
    for (int i = 0; i < this.lbl_titles.size(); i++)
      this.gp_details.add(this.lbl_titles.get(i), 0, i + 1);
      // this.gp_details.addRow(i, this.lbl_titles.get(i));
    
    // GridPane Col: 2
    for (int i = 0; i < this.lbl_details.size(); i++)
      this.gp_details.add(this.lbl_details.get(i), 1, i + 1);
    
    this.vb.getChildren().addAll(this.hdr_instructions);
    for (int i = 0; i < this.lbl_plant_titles.size(); i++) {
      this.vb.getChildren().addAll(
          this.lbl_plant_titles.get(i),
          this.lbl_plant_details.get(i)
      );
    }

    this.vb_details.getChildren().addAll(this.gp_details, this.vb);
  }

  // API

  public Parent asParent() {
    return this.vb_details;
  }
}
