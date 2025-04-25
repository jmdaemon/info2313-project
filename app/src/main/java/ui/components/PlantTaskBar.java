package ui.components;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import plant.AbstractPlant;
import plant.PlantManager;

public class PlantTaskBar {

  private HBox hb;
  private ToolBar tb;
  public Separator sep;

  public Button btn_add;
  public Button btn_del;
  public Button btn_edit;
  public Button btn_load;
  public Button btn_save;
  public Button btn_back;

  public List<Button> all_btns;

  public PlantTaskBar() {
    // Controls
    this.tb = new ToolBar();

    this.hb = new HBox();
    this.hb.setHgrow((Node) this.tb, Priority.ALWAYS);

    this.all_btns = new ArrayList<Button>();

    this.sep = new Separator();

    this.btn_add = new Button("Add");
    this.btn_del = new Button("Delete");
    this.btn_edit = new Button("Edit");
    this.btn_load = new Button("Import");
    this.btn_save = new Button("Export");
    this.btn_back = new Button("Back");

    this.all_btns.addAll(List.of(
        btn_back,
        btn_load, 
        btn_save,
        btn_add,
        btn_del,
        btn_edit
    ));

    // Listeners

    // Packing
    this.tb.getItems().addAll(
        this.btn_back, this.sep,
        this.btn_load, 
        this.btn_save,
        this.btn_add,
        this.btn_del,
        this.btn_edit
    );

    this.hb.getChildren().addAll(this.tb);
  }

  // Internal

  // API

  // Set the font size of all buttons
  public void setFont(final Font font) {
    for (Button btn : this.all_btns)
      btn.setFont(font);
  }
  
  public Parent asParent() {
    return this.hb;
  }
}
