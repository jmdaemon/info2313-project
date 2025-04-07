package ui;

import java.util.List;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import plant.AbstractPlant;
import plant.PlantManager;

public class Gallery implements Component, Navigator {
  final static String ERR_NAV_ELEM_NOT_FOUND = "Error: Navigator element not found.";
  // MVC
  private GalleryController controller;

  // Widgets
  private ObservableList<PlantView> ol_plants;

  private TilePane tp;

  public Gallery(final GalleryController controller) {
    this.controller = controller;

    this.ol_plants = FXCollections.observableArrayList();

    this.tp = new TilePane();

    // Create Plant Gallery
    // TODO: Create & pass this in main
    PlantManager pm = GUI.createPlantManagerFixture();

    for (AbstractPlant plant : pm.getPlants()) {
      // Setup plant controllers
      PlantModel model = new PlantModel(plant);
      PlantController control = new PlantController(model);
      PlantView listing = new PlantView(control, model);

      // Add to our tile pane
      this.ol_plants.add(listing);
      tp.getChildren().addAll(listing.asParent());
    }
  }

  // Internal

  // API

  // Set all plant listings to navigate to detail view
  @Override
  public void setNavigateEvent(final String elem, Scene root, Parent next) {
    if (elem.equals("ol-plant-listing")) {
      for (PlantView listing: this.ol_plants) {
        listing.setNavigateEvent(root, next);
      }
    } else {
      System.out.println(ERR_NAV_ELEM_NOT_FOUND);
    }
  }

  @Override
  public Parent asParent() { return this.tp; }
  
}
