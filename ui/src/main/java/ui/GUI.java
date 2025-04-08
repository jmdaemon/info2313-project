package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

// User Interface

// We're going to have a graphical user interface that is simmilar
// to online e-commerce stores, with a picture of the plant, and
// its details listed.
// 
// We will also offer admin panels for managing prices & plant information
// for the store owner/shop keeper

public class GUI extends Application {

    @Override
    public void start(Stage stage) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        Scene scene = new Scene(new StackPane(l), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("Show Graphical User Interface");
        launch();
    }

}
