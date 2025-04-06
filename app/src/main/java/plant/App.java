package plant;
import ui.GUI;

public class App {
    public static void main(String[] args) {
      System.out.println("Initial Project");
      PlantDemoSystem.main(args);

      GUI app = new GUI();
      app.run(args);
    }
}
