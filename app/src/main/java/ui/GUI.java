package ui;

import java.time.LocalDate;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import plant.GrowType;
import plant.PlantInfo;
import plant.PlantManager;
import plant.PlantType;
import plant.Season;
import plant.plants.Creeper;
import plant.plants.Herb;
import plant.plants.Tree;

public class GUI extends Application {
  private final static int WIN_MIN_WIDTH = 640;
  private final static int WIN_MIN_HEIGHT = 480;

  final static String PLANT_PAGE_TMPL = "/plant-details-page.html";

  // Panels
  private Gallery gallery;
  private Editor editor;

  private Scene root;

  @Override
  public void start(Stage stage) {
    // Create our different panels/windows
    this.gallery = new Gallery();
    this.editor = new Editor();

    this.root = new Scene(this.gallery.asParent(), WIN_MIN_WIDTH, WIN_MIN_HEIGHT);
    stage.setScene(root);

    // Hookup navigation elements
    this.gallery.setNavigateEvent("ol-plant-listing", this.root, this.gallery.asParent());
    this.gallery.setNavigateEvent("btn-edit", this.root, this.editor.asParent());
    this.editor.setNavigateEvent("btn-back", this.root, this.gallery.asParent());

    // Show our scene
    stage.show();
  }

  public void run(String[] args) {
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
