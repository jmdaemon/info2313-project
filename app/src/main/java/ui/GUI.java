package ui;

import java.time.LocalDate;
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
import ui.components.PlantItemPage;

// Features

// Plant Form
// - Fill in details for plant info
// Editor
// - Select plant, and edit details
// Search
// - Search plant by type, and view results
// Load/Save
// - (OPT) Select file to load/save
// - Show loaded/saved output. (Saved to file: PATH, Loaded plants: {})

// Widgets:

// Selection:
// - Editor -> Provide a combo box?
// - Searching: -> Widget with combo box to search for type


// Editor:
// - Perform Ops (Add, Del, Edit
// - 


// TODO:
// - Admin Panel
//   - Add (new) plant 
//   - Del plant 
//   - Update plant price
// - Gallery HUB Panel
//   - Nav Panel to Admin Page
//   - Search Plants
//   - Import/Export Plants

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
  // private final static int WIN_MIN_WIDTH = 1600;
  // private final static int WIN_MIN_HEIGHT = 900;

  final static String PLANT_PAGE_TMPL = "/plant-details-page.html";

  // Panels
  private Gallery gallery;
  // private PlantItemPage detail_view;
  private Editor editor;

  // private Scene root;
  private Scene scene;

  @Override
  public void start(Stage stage) {
    // Create our different panels/windows
    this.gallery = new Gallery();
    // this.detail_view = new PlantItemPage();
    this.editor = new Editor();

    // Scene scene = new Scene(this.gallery.asParent(), WIN_MIN_WIDTH, WIN_MIN_HEIGHT);
    // scene = new Scene(this.gallery.asParent(), WIN_MIN_WIDTH, WIN_MIN_HEIGHT);
    // this.scene = new Scene(this.detail_view.asParent(), WIN_MIN_WIDTH, WIN_MIN_HEIGHT);
    this.scene = new Scene(this.gallery.asParent(), WIN_MIN_WIDTH, WIN_MIN_HEIGHT);

    // Link the button widgets in each panel to switch the root
    // TODO: Update navigateEvent
    // this.gallery.setNavigateEvent("ol-plant-listing", this.scene, this.detail_view.asParent());

    // this.gallery.setNavigateEvent("ol-plant-listing", this.scene, null);
    this.gallery.setNavigateEvent("ol-plant-listing", this.scene, this.gallery.asParent());
    this.gallery.setNavigateEvent("btn-edit", this.scene, this.editor.asParent());

    // this.detail_view.setNavigateEvent("btn-back", this.scene, this.gallery.asParent());
    this.editor.setNavigateEvent("btn-back", this.scene, this.gallery.asParent());

    // Set resource on button widgets
    // this.detail_view.setResource(this.getClass().getResource(PLANT_PAGE_TMPL));

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
        LocalDate.now(),
        50,
        7300, 
        GrowType.GRAFTING,
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
        LocalDate.now(),
        5,
        150, 
        GrowType.SEEDING, // Or Cutting
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
        LocalDate.now(),
        15,
        1825, 
        GrowType.CUTTING,
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
