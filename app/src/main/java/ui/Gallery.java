package ui;

import java.util.List;
import java.util.ArrayList;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import plant.AbstractPlant;
import plant.PlantManager;

public class Gallery {
  // MVC
  private GalleryController controller;

  // Widgets
  private List<PlantView> plants;

  private TilePane tp;

  public Gallery(final GalleryController controller) {
    this.controller = controller;
    this.plants = new ArrayList<PlantView>();

    this.tp = new TilePane();

    // Create Plant Gallery
    PlantManager pm = GUI.createPlantManagerFixture();

    for (AbstractPlant plant : pm.getPlants()) {
      // Setup plant controllers
      PlantModel model = new PlantModel(plant);
      PlantController control = new PlantController(model);
      PlantView listing = new PlantView(control, model);

      // Add to our tile pane
      this.plants.add(listing);
      tp.getChildren().addAll(listing.asParent());
    }
  }

  // Internal

  // API
  // Set an element to change the scene root
  public void setNavigateEvent(Scene root, Parent next) {
    for (PlantView listing: this.plants) {
      listing.setNavigateEvent(root, next);
    }
  }

  public Parent asParent() {
    return this.tp;
  }
}
