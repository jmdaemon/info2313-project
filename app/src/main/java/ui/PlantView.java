package ui;

import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlantView {
  // Widgets
  private Label lbl_plant_name;
  private Label lbl_plant_price;
  private Button btn_plant_view;
  private Separator sep_plant;
  private ImageView iv; 

  private HBox hb1;
  private HBox hb2;
  private VBox vb;
  
  // MVC
  private PlantController controller;
  private PlantModel model;

  public PlantView(final PlantController controller, final PlantModel model) {
    // MVC
    this.controller = controller;
    this.model = model;

    // Widgets
    this.lbl_plant_name = new Label("Plant Name");
    this.lbl_plant_price = new Label("Plant Price");
    this.btn_plant_view = new Button("View");
    this.sep_plant = new Separator(Orientation.HORIZONTAL);
    this.iv = new ImageView();

    // Containers
    this.hb1 = new HBox();
    this.hb2 = new HBox();
    this.vb = new VBox();

    // Properties

    // TODO: Use FXML later to spruce up the look of the project

    // Set dynamic binding on our plant info
    this.lbl_plant_name.textProperty().bind(this.model.name);
    this.lbl_plant_price.textProperty().bind(this.model.price.asString());
    this.iv.imageProperty().bindBidirectional(this.model.picture);
    this.iv.setImage(new Image("file:/home/jmd/Media/Pictures/Avatars/default-user.png"));

    vb.setPrefWidth(100);
    vb.setPrefHeight(200);

    // Layout Packing
    hb1.getChildren().addAll(lbl_plant_name);
    hb2.getChildren().addAll(lbl_plant_price, sep_plant, btn_plant_view);
    vb.getChildren().addAll(
        iv,
        hb1,
        hb2
    );
  }

  // Internal

  // API
  public Parent asParent() {
    return this.vb;
  }
}
