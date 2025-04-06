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

public class ItemDetailsView {
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
          jsobj.setMember("item_page", new ItemPage());
        }
    });

    // this.page.getEngine().load(this.getClass().getResource(PLANT_PAGE_TMPL).toString());
    // this.page.getEngine().load(PLANT_PAGE_TMPL);

    this.btn_gallery = new Button("Back");

    this.vb = new VBox();

    vb.getChildren().addAll(this.btn_gallery, page);
  }
  
  // API
  
  // Set an element to change the scene root
  // public void setResource(Scene root) {
  // public void setResource(String rsrc) {
  public void setResource(URL url) {
    // this.page.getEngine().load(root.getClass().getResource("/" + PLANT_PAGE_TMPL).toExternalForm());
    // this.page.getEngine().load(root.getClass().getResource("/" + PLANT_PAGE_TMPL).toExternalForm());
    // this.page.getEngine().load(root.getClass().getResource(PLANT_PAGE_TMPL).toExternalForm());

    // URL url = Thread.currentThread().getContextClassLoader().getResource("demo/index.html");
    // URL url = Thread.currentThread().getContextClassLoader().getResource("demo/index.html");
    // URL url = Thread.currentThread().getContextClassLoader().getResource("demo/index.html");
    this.page.getEngine().load("file://" + url.getPath());
  }

  // Set an element to change the scene root
  public void setNavigateEvent(Scene root, Parent next) {
    this.btn_gallery.setOnMouseClicked(_event -> {
      root.setRoot(next);
    });
  }

  public Parent asParent() {
    return this.vb;
  }
}
