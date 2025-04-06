package ui;

import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import plant.AbstractPlant;

public class PlantListing {
  // Data
  private AbstractPlant plant;

  // Widgets
  private Label lbl_plant_name = new Label("Plant Name");
  private Label lbl_plant_price = new Label("Plant Price");
  private Button btn_plant_view = new Button("View");
  private Separator sep_plant = new Separator(Orientation.HORIZONTAL);
  private ImageView iv = new ImageView("file:/home/jmd/Media/Pictures/Avatars/default-user.png");

  private HBox hb1 = new HBox();
  private HBox hb2 = new HBox();
  public VBox vb = new VBox();

  PlantListing(final AbstractPlant plant) {
    this.plant = plant;
  }
  
  // @Override
  public void start(Stage stage) {
    // TODO Set Widget Properties 

    vb.setPrefWidth(100);
    vb.setPrefHeight(200);

    // Packing
    hb1.getChildren().addAll(lbl_plant_name);
    hb2.getChildren().addAll(lbl_plant_price, sep_plant, btn_plant_view);
    vb.getChildren().addAll(
        iv,
        hb1,
        hb2
    );
  }
}
