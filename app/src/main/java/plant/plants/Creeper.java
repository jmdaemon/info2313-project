package plant.plants;

import plant.AbstractPlant;
import plant.PlantInfo;

public class Creeper extends AbstractPlant {
  private String color;

  public Creeper(final PlantInfo plant, final String color) {
    super(plant);
    this.color = color;
  }

  public String getColor() { return this.color; }
  public void setColor(final String color) { this.color = color; }
  
  @Override public String staking() {
    return "Install trellises/arbors 20-30 cm away from the walls allowing for good air circulation";
  }

  @Override public String pruning() {
    return "Cut overgrown or unwanted stems";
  }
}
