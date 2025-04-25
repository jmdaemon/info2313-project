package ui.components;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;

public class PlantStatusBar {

  private HBox hb;
  private ToolBar tb;
  private Label lbl_status;

  public PlantStatusBar() {
    this.hb = new HBox();
    this.tb = new ToolBar();
    this.lbl_status = new Label("");

    this.hb.setHgrow((Node) this.tb, Priority.ALWAYS);
    
    // Packing
    this.tb.getItems().addAll(this.lbl_status);

    this.hb.getChildren().addAll(this.tb);
  }

  // Internal

  // API
  public void showStatus(final String status, long millis) {
    this.lbl_status.setText(status);

    // Sleep for some time
    Thread thread = new Thread(() -> {
      try {
        Thread.sleep(millis);

        // Clear status after number of seconds
        Platform.runLater(() -> {
          this.lbl_status.setText("");
        });

      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    thread.start();
  }

  // Set the font size of the label
  public void setFont(final Font font) {
    this.lbl_status.setFont(font);
  }
  
  public Parent asParent() {
    return this.hb;
  }
}
