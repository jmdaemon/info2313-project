package ui;

import java.util.Date;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

// TODO: Deal with the import bloat later on
import plant.GrowType;
import plant.PlantInfo;
import plant.PlantManager;
import plant.PlantType;
import plant.Season;
import plant.plants.Creeper;
import plant.plants.Herb;
import plant.plants.Tree;


// Plant
// - Listing
// - Details Page
//
// Main Gallery
//
// Admin Panel

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
  private final static int WIN_MIN_WIDTH = 640;
  private final static int WIN_MIN_HEIGHT = 480;

  // Panels
  private Gallery gallery;
  private ItemDetailsView detail_view;
  // private Scene root;
  private Scene scene;

  @Override
  public void start(Stage stage) {
    // Create our different panels/windows
    this.gallery = new Gallery(new GalleryController());
    this.detail_view = new ItemDetailsView();

    // Scene scene = new Scene(this.gallery.asParent(), WIN_MIN_WIDTH, WIN_MIN_HEIGHT);
    // scene = new Scene(this.gallery.asParent(), WIN_MIN_WIDTH, WIN_MIN_HEIGHT);
    // this.scene = new Scene(this.detail_view.asParent(), WIN_MIN_WIDTH, WIN_MIN_HEIGHT);
    this.scene = new Scene(this.gallery.asParent(), WIN_MIN_WIDTH, WIN_MIN_HEIGHT);

    // Link the button widgets in each panel to switch the root
    this.gallery.setNavigateEvent(this.scene, this.detail_view.asParent());
    this.detail_view.setNavigateEvent(this.scene, this.gallery.asParent());

    // Show our scene
    stage.setScene(scene);
    stage.show();
  }

  public void run(String[] args) {
    System.out.println("Show Graphical User Interface");
    launch();
  }

  // TEST FIXTURE DATA
  public static PlantManager createPlantManagerFixture() {
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
    
    PlantManager pm = new PlantManager();
    pm.add(apple_tree);
    pm.add(basil);
    pm.add(money_plant);
    return pm;
  }
}
