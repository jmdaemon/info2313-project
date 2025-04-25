package ui;

import java.net.URL;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class PlantView {
  final static String PLANT_DEFAULT_IMG = "plant.png";
  // Properties
  public StringProperty currency;

  // Data
  private PlantModel model;

  // Controls
  private GridPane gp;
  private ColumnConstraints cc1;
  private ColumnConstraints cc2;

  private ImageView iv; 
  private Label lbl_plant_name;
  private Label lbl_plant_price;
  private Button btn_plant_view;

  public PlantView(final PlantModel model) {
    // Data
    this.model = model;

    // Controls
    this.gp = new GridPane();
    this.gp.setPadding(new Insets(12));
    this.gp.setVgap(12); 
    this.gp.setHgap(12);

    this.cc1 = new ColumnConstraints(100);
    this.cc1.setHalignment(HPos.LEFT);

    this.cc2 = new ColumnConstraints(100);
    this.cc2.setHalignment(HPos.RIGHT);
    this.gp.getColumnConstraints().addAll(this.cc1, this.cc2);
    
    this.lbl_plant_name = new Label("Name");
    this.lbl_plant_price = new Label("Price");
    this.btn_plant_view = new Button("View");
    
    Font font = new Font(15);
    this.lbl_plant_name.setFont(font);
    this.lbl_plant_price.setFont(font);
    this.btn_plant_view.setFont(font);
    
    this.iv = new ImageView();
    this.iv.setImage(new Image(PLANT_DEFAULT_IMG, 200, 200, true, false));

    // Listeners

    // Format gallery labels
    this.currency = new SimpleStringProperty("$CAD ");
    this.lbl_plant_name.textProperty().bind(this.model.name);
    this.lbl_plant_price.textProperty().bind(this.currency.concat(this.model.price.asString()));
    // this.iv.imageProperty().bindBidirectional(this.model.picture); 

    // Packing
    this.gp.add(this.iv, 0, 0, 2 ,1);
    this.gp.add(this.lbl_plant_name, 0, 1, 1, 1);
    this.gp.add(this.lbl_plant_price, 0, 2, 1, 1);
    this.gp.add(this.btn_plant_view, 1, 2, 1, 1);
  }

  // Internal

  // Set an element to change the scene root
  public void setNavigateEvent(Scene root, Parent next) {
    this.btn_plant_view.setOnMouseClicked(_event -> {
      root.setRoot(next);
    });
  }

  // @Override
  public void setResource(final URL url) {
    String path = "file://" + url.getPath();
    this.iv.setImage(new Image(path));
  }

  // API
  public Parent asParent() {
    return this.gp;
  }
}
