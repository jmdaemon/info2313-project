package ui;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// User Interface

// Core:
// 
// Show a gallery with plant picture thumbnails & plant names/prices 
// - User clicks on plant, navigates to a plant panel which shows
//   - Bigger picture of the plant
//   - Bold Title Name
//   - Bold Title Price ($CAD)
//   - Further information about the plant below
// - Every panel has navigation buttons:
//   - Forward/Backward

// Admin Panels to modify plant price/information
//
// Extra:
// 
// About Dialog Window which shows credits & information

// We're going to have a graphical user interface that is simmilar
// to online e-commerce stores, with a picture of the plant, and
// its details listed.
// 
// We will also offer admin panels for managing prices & plant information
// for the store owner/shop keeper

public class GUI extends Application {
  @Override
  public void start(Stage stage) {
    // Create initial plant interface
    TilePane tp = new TilePane();

    VBox vb = new VBox();
    vb.setPrefWidth(100);
    vb.setPrefHeight(200);

    ImageView iv = new ImageView("file:/home/jmd/Media/Pictures/Avatars/default-user.png");

    HBox hb1 = new HBox();
    HBox hb2 = new HBox();
    Label lbl_plant_name = new Label("Plant Name");
    Label lbl_plant_price = new Label("Plant Price");
    Button btn_plant_view = new Button("View");

    Separator sep_plant = new Separator(Orientation.HORIZONTAL);
    
    // Packing
    hb1.getChildren().addAll(lbl_plant_name);
    hb2.getChildren().addAll(lbl_plant_price, sep_plant, btn_plant_view);
    vb.getChildren().addAll(
        iv,
        hb1,
        hb2
    );

    tp.getChildren().addAll(vb);

    Scene scene = new Scene(tp, 640, 480);

    // String javaVersion = System.getProperty("java.version");
    // String javafxVersion = System.getProperty("javafx.version");
    // Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
    // Scene scene = new Scene(new StackPane(l), 640, 480);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    System.out.println("Show Graphical User Interface");
    launch();
  }
}
