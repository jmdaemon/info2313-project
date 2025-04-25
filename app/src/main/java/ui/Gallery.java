package ui;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import plant.AbstractPlant;
import plant.PlantManager;

import ui.components.PlantItemListing;
import ui.interfaces.Component;
import ui.interfaces.Navigator;

public class Gallery implements Component, Navigator {
  final static String ERR_NAV_ELEM_NOT_FOUND = "Error: Navigator element not found.";
  
  // Properties
  // TODO: Update alongside changes in Editor
  private ObjectProperty<PlantManager> pm;
  private ObservableList<PlantItemListing> ol_plants;

  // Widgets
  private VBox vb;
  private TilePane tp;

  private ToolBar tb;
  private Button btn_editor;
  private Button btn_quit;


  public Gallery() {
    // Data
    // TODO: Create & pass this in main
    PlantManager pm = GUI.createPlantManagerFixture();
    this.ol_plants = FXCollections.observableArrayList();

    // Controls
    this.tp = new TilePane();
    this.vb = new VBox();
    this.tb = new ToolBar();

    this.btn_editor = new Button("Editor");
    this.btn_quit = new Button("Quit");
    
    Font font = new Font(15);

    this.btn_editor.setFont(font);
    this.btn_quit.setFont(font);

    // Packing



    // this.hb = new HBox();
    
    // this.hb.getChildren().addAll(this.btn_editor);
    // this.tp.getChildren().addAll(this.hb);

    // Create Plant Gallery
    for (AbstractPlant plant : pm.getPlants()) {
      // Setup plant controllers
      PlantModel model = new PlantModel(plant);
      PlantItemListing listing = new PlantItemListing(model);

      // Add to our tile pane
      ol_plants.add(listing);
      // this.ol_plants.add(listing);
      tp.getChildren().addAll(listing.asParent());
    }
    
    this.tb.getItems().addAll(
        this.btn_quit,
        this.btn_editor
    );

    this.vb.getChildren().addAll(this.tb, this.tp);
  }

  // Internal

  // API

  // Set all plant listings to navigate to detail view
  @Override
  public void setNavigateEvent(final String elem, Scene root, Parent next) {
    switch(elem) {
      case "ol-plant-listing" -> {
        for (PlantItemListing listing: ol_plants) {
          listing.setNavigateEvent(root, next);
        }
      }
      case "btn-edit" -> {
        this.btn_editor.setOnMouseClicked(_event -> {
          root.setRoot(next);
        });
      }
      default -> System.out.println(ERR_NAV_ELEM_NOT_FOUND);
    }
  }

  @Override
  public Parent asParent() { return this.vb; }
}
