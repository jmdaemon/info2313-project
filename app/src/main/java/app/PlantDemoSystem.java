package app;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import plant.AbstractPlant;
import plant.GrowType;
import plant.PlantInfo;
import plant.PlantManager;
import plant.PlantType;
import plant.Season;
import plant.plants.Creeper;
import plant.plants.Herb;
import plant.plants.Tree;

public class PlantDemoSystem {
  final static String FP_PLANT_DATA = "plant.psv";

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
        LocalDate.now(),
        50,
        7300, 
        GrowType.GRAFTING,
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
        LocalDate.now(),
        5,
        150, 
        GrowType.SEEDING, // Or Cutting
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
        LocalDate.now(),
        15,
        1825, 
        GrowType.CUTTING,
        "Thrives in indirect sunlight. Can be grown in soil or water",
        PlantType.CREEPER
      ),
      "Red"
    );

    PlantManager pm = new PlantManager();

    pm.add(appleTree);
    pm.add(basil);
    pm.add(money_plant);
    
    // Show all plants
    System.out.println("# Showing all plants:");
    for (AbstractPlant plant : pm.getPlants()) 
      showPlantDetails(plant);

    // Save Plants
    Scanner reader = new Scanner(System.in);

    pm.save(FP_PLANT_DATA, reader);
    pm.load(FP_PLANT_DATA, reader);

    // Show all plants
    System.out.println("# After loading plant data:");
    for (AbstractPlant plant : pm.getPlants()) 
      showPlantDetails(plant);

    // Filter plants to show only tree
    System.out.println("# Filter for tree plants only:");
    List<AbstractPlant> results = pm.search(PlantType.TREE);
    for (AbstractPlant plant : results) 
      showPlantDetails(plant);

    // Delete tree & show plants
    System.out.println("# Delete tree plant:");
    pm.del(0);

    // Now show the plants
    for (AbstractPlant plant : pm.getPlants()) 
      showPlantDetails(plant);

    reader.close();
  }
}
