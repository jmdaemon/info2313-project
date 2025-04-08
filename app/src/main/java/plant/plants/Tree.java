package plant.plants;

import plant.AbstractPlant;
import plant.PlantInfo;

public class Tree extends AbstractPlant {
  private double height;

  public Tree(final PlantInfo plant, final double height) {
    super(plant); 
    this.height = height;
  }

  public double getHeight() { return this.height; }
  public void setHeight(final double height) { this.height = height; }

  @Override
  public String pruning() {
    return "Cut out dead or deseased branches, without leaving stubs";
  }

  @Override
  public String staking() {
    return "Place 2 metal/wood stakes of 5 ft near the branches."
      + "Tie up stakes with the branch of a tree for wind security";
  }
}
