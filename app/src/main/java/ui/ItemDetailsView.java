package ui;

import java.net.URL;

// import java.net.URI;

import javafx.concurrent.Worker;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
// import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import ui.interfaces.Component;
import ui.interfaces.Navigator;
import ui.interfaces.Resource;

public class ItemDetailsView implements Component, Navigator, Resource {
  // DATA
  private PlantModel model;
  
  // final static String PLANT_PAGE_TMPL = "index.html";
  // final static String PLANT_PAGE_TMPL = "plant-details-page.html";

  // Widgets
  private VBox vb;
  private Button btn_gallery;

  private WebView page;

  // Show all the details required for our plant

  public ItemDetailsView() {

    // Create a new web page object, inject our Java library code, and load it
    this.page = new WebView();

    // Test Page
    // this.page.getEngine().load("http://google.com");

    this.page
      .getEngine()
      .getLoadWorker()
      .stateProperty()
      .addListener((obs, before, after) -> {
        // Load our library
        if (after == Worker.State.SUCCEEDED) {
          JSObject jsobj = (JSObject) this.page.getEngine().executeScript("window");
          jsobj.setMember("item_page", new PlantFFI());
        }
    });

    // this.page.getEngine().load(this.getClass().getResource(PLANT_PAGE_TMPL).toString());
    // this.page.getEngine().load(PLANT_PAGE_TMPL);

    this.btn_gallery = new Button("Back");

    this.vb = new VBox();
    // this.vb.setPrefWidth(1920);
    // this.vb.setPrefHeight(1080);

    vb.getChildren().addAll(this.btn_gallery, page);
  }
  
  // API
  
  @Override
  public void setResource(final URL url) {
    this.page.getEngine().load("file://" + url.getPath());
  }

  // Set an element to change the scene root
  @Override
  public void setNavigateEvent(final String elem, Scene root, Parent next) {
    if (elem.equals("btn-back")) {
      this.btn_gallery.setOnMouseClicked(_event -> {
        root.setRoot(next);
      });
    }
  }

  @Override
  public Parent asParent() {
    return this.vb;
  }
}
