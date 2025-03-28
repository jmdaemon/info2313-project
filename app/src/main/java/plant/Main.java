package plant;

import java.util.Date;

public class Main {
    public static void main(String[] args) 
    {
        PlantInfo appleTreeInfo = new PlantInfo("Apple Tree", "Common Apple", 
        Season.SPRING, new Date(), 50, 7300, 
        "Grafted", 
        " Plant in full sun with well-drained, fertile soil. Ensure good air circulation to prevent disease");

        PlantInfo basilInfo = new PlantInfo("Basil", "Sweet Basil", 
        Season.SPRING, new Date(), 5, 150, 
        "Seed or Cutting", 
        "Sow seeds indoors 6-8 weeks before the last frost. Transplant outdoors in full sun after the danger of frost has passed");
    
        PlantInfo moneyPlantInfo = new PlantInfo("moneyPlant", "Devils Ivy", 
        Season.SPRING, new Date(), 15, 1825, 
        "Cutting", 
        "Thrives in indirect sunlight. Can be grown in soil or water");

        Tree appleTree = new Tree(appleTreeInfo, 120);
        Herb basil = new Herb(basilInfo, "Aromatic");
        Creepers moneyPlant = new Creepers(moneyPlantInfo, "Red");

        appleTree.Prune();
        basil.Prune();
        moneyPlant.Prune();
    }
}
