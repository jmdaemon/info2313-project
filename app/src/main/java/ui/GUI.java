package ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import plant.AbstractPlant;
import plant.GrowType;
import plant.PlantInfo;
import plant.PlantManager;
import plant.PlantType;
import plant.Season;
import plant.plants.Creeper;
import plant.plants.Herb;
import plant.plants.Tree;

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
  // Data
  private PlantManager pm;
  // private PlantManager pm = new PlantManager();

    // this.pm = new PlantManager();

  private final static int WIN_MIN_WIDTH = 640;
  private final static int WIN_MIN_HEIGHT = 480;

  // Widgets
  private List<PlantListing> plants;
  private TilePane tp = new TilePane();

  // Set Properties & Create UI 
  @Override
  public void start(Stage stage) {
    setup();
    // For all plants, create a plant listing elem
    for (AbstractPlant plant : this.pm.getPlants()) {
      PlantListing listing = new PlantListing(plant);
      this.plants.add(listing);
      tp.getChildren().addAll(listing.vb);
    }

    Scene scene = new Scene(tp, WIN_MIN_WIDTH, WIN_MIN_HEIGHT);

    stage.setScene(scene);
    stage.show();
  }
  
  public void setup() {
    // Create fixtures
    Tree apple_tree = new Tree(
      new PlantInfo(
        "Apple Tree",
        List.of("Common Apple"),
        Season.SPRING,
        new Date(),
        50,
        7300, 
        GrowType.Grafting,
        "Plant in full sun with well-drained, fertile soil. Ensure good air circulation to prevent disease",
        PlantType.TREE
      ),
      120.0
    );

    Herb basil = new Herb(
      new PlantInfo(
        "Basil",
        List.of("Sweet Basil"),
        Season.SPRING,
        new Date(),
        5,
        150, 
        GrowType.Seeding, // Or Cutting
        "Sow seeds indoors 6-8 weeks before the last frost. Transplant outdoors in full sun after the danger of frost has passed",
        PlantType.HERB
        ),
      "Aromatic"
    );

    Creeper money_plant = new Creeper (
      new PlantInfo(
        "Money Plant",
        List.of("Devils Ivy"),
        Season.SPRING,
        new Date(),
        15,
        1825, 
        GrowType.Cutting,
        "Thrives in indirect sunlight. Can be grown in soil or water",
        PlantType.CREEPER
      ),
      "Red"
    );
    // this.pm = new PlantManager();
    this.pm.add(apple_tree);
    this.pm.add(basil);
    this.pm.add(money_plant);

    this.plants = new ArrayList<PlantListing>();
  }

  public void run(String[] args) {
    System.out.println("Show Graphical User Interface");
    // setup();
    launch();
  }
}
