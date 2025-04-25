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
import javafx.stage.Stage;
import plant.AbstractPlant;
import plant.PlantManager;

import ui.components.PlantItemListing;
import ui.components.PlantItemPage;
import ui.interfaces.Component;
import ui.interfaces.Navigator;

public class Gallery implements Component, Navigator {
  final static String ERR_NAV_ELEM_NOT_FOUND = "Error: Navigator element not found.";
  
  // Properties
  // TODO: Update alongside changes in Editor
  private ObjectProperty<PlantManager> pm;
  private ObservableList<PlantItemListing> ol_listings;
  private ObservableList<PlantItemPage> ol_pages;

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

    this.ol_listings = FXCollections.observableArrayList();
    this.ol_pages = FXCollections.observableArrayList();

    // Controls
    this.tp = new TilePane();
    this.vb = new VBox();
    this.tb = new ToolBar();

    this.btn_editor = new Button("Editor");
    this.btn_quit = new Button("Quit");
    
    Font font = new Font(15);

    this.btn_editor.setFont(font);
    this.btn_quit.setFont(font);

    // Listeners

    // Enable users to quit the window
    this.btn_quit.setOnMouseClicked(_event -> {
      Stage stage = (Stage) this.btn_quit.getScene().getWindow();
      stage.close();
    });

    // Packing

    for (AbstractPlant plant : pm.getPlants()) {
      PlantModel model = new PlantModel(plant);
      PlantItemListing listing = new PlantItemListing(model);
      PlantItemPage page = new PlantItemPage(model);
      //
      // page.setResource(this.getClass().getResource(PLANT_PAGE_TMPL));

      // For every listing, include a listener to change root
      ol_listings.add(listing);
      ol_pages.add(page);
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
      // Enable users to view plant pages
      case "ol-plant-listing" -> {
          for (int i = 0; i < this.ol_listings.size(); i++) {
            PlantItemPage page = this.ol_pages.get(i);
            PlantItemListing listing = this.ol_listings.get(i);

            // Navigate to plant pages
            listing.setNavigateEvent(root, page.asParent());
            
            // Navigate back to home gallery
            page.setNavigateEvent("btn-back", root, next);
            // page.setNavigateEvent("btn-back", root, );
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
