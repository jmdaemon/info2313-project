package plant;

import java.util.Date;
import java.util.List;

import plant.plants.Creeper;
import plant.plants.Herb;
import plant.plants.Tree;

public class PlantDemoSystem {
  public static void showPlantDetails(final AbstractPlant p) {
    System.out.println(p.info.name);
    System.out.println("=".repeat(16));
    System.out.println("Growing: " + p.info.grow_instructions);
    System.out.println("Staking: " + p.staking());
    System.out.println("Pruning: " + p.pruning());
    System.out.println("-".repeat(16));
  }

  public static void main(String[] args) {
    Tree appleTree = new Tree(
      new PlantInfo(
        "Apple Tree",
        List.of("Common Apple"),
        Season.SPRING,
        new Date(),
        50,
        7300, 
        GrowType.Grafting,
        "Plant in full sun with well-drained, fertile soil. Ensure good air circulation to prevent disease",
        PlantType.TREE
      ),
      120.0
    );

    Herb basil = new Herb(
      new PlantInfo(
        "Basil",
        List.of("Sweet Basil"),
        Season.SPRING,
        new Date(),
        5,
        150, 
        GrowType.Seeding, // Or Cutting
        "Sow seeds indoors 6-8 weeks before the last frost. Transplant outdoors in full sun after the danger of frost has passed",
        PlantType.HERB
        ),
      "Aromatic"
    );

    Creeper money_plant = new Creeper (
      new PlantInfo(
        "Money Plant",
        List.of("Devils Ivy"),
        Season.SPRING,
        new Date(),
        15,
        1825, 
        GrowType.Cutting,
        "Thrives in indirect sunlight. Can be grown in soil or water",
        PlantType.CREEPER
      ),
      "Red"
    );
    
    showPlantDetails(appleTree);
    showPlantDetails(basil);
    showPlantDetails(money_plant);
  }
}
