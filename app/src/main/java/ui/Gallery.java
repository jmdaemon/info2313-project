package ui;

import java.util.List;
import java.util.ArrayList;

import javafx.scene.Parent;
import javafx.scene.layout.TilePane;
import plant.AbstractPlant;
import plant.PlantManager;

public class Gallery {
  // MVC
  private GalleryController controller;

  // Widgets
  private List<PlantView> plants;

  private TilePane tp;

  // public Gallery(final PlantModel model, ) {
  public Gallery(final GalleryController controller) {
    this.controller = controller;
    this.plants = new ArrayList<PlantView>();

    this.tp = new TilePane();

    // Create Plant Gallery
    PlantManager pm = GUI.createPlantManagerFixture();

    for (AbstractPlant plant : pm.getPlants()) {
      PlantModel model = new PlantModel(plant);
      PlantController control = new PlantController(model);

      PlantView listing = new PlantView(control, model);
      this.plants.add(listing);
      // Add to our tile pane
      tp.getChildren().addAll(listing.asParent());
    }

  }

  // Internal

  // TODO: Implement navigation to plant details page

  // API
  public Parent asParent() {
    return this.tp;
  }
}
