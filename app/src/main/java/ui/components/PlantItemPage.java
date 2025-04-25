package ui.components;

import java.net.URL;

// import java.net.URI;

import javafx.concurrent.Worker;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Priority;
// import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import ui.PlantFFI;
import ui.PlantModel;
import ui.interfaces.Component;
import ui.interfaces.Navigator;
import ui.interfaces.Resource;

public class PlantItemPage implements Component, Navigator, Resource {
  // DATA
  
  private PlantModel model;
  
  // final static String PLANT_PAGE_TMPL = "index.html";
  final static String PLANT_PAGE_TMPL = "/plant-details-page.html";

  // Controls
  private VBox vb;

  private ToolBar tb;
  private Button btn_back;

  private WebView page;

  public PlantItemPage(final PlantModel model) {

    this.vb = new VBox();
    this.tb = new ToolBar();
    this.btn_back = new Button("Back");

    Font font = new Font(15);
    this.btn_back.setFont(font);

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
          jsobj.setMember("plant_ffi", new PlantFFI(this.model));
        }
    });
    this.vb.setVgrow((Node) this.page, Priority.ALWAYS);

    // this.page.getEngine().load(this.getClass().getResource(PLANT_PAGE_TMPL).toString());
    // this.page.getEngine().load(PLANT_PAGE_TMPL);
    // this.page.getEngine().load(PLANT_PAGE_TMPL);
    this.setResource(this.getClass().getResource(PLANT_PAGE_TMPL));

    // Packing
    this.tb.getItems().addAll(this.btn_back);
    this.vb.getChildren().addAll(this.tb, this.page);
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
      this.btn_back.setOnMouseClicked(_event -> {
        root.setRoot(next);
      });
    }
  }

  @Override
  public Parent asParent() {
    return this.vb;
  }
}
