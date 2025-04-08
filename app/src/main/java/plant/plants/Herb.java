package plant.plants;

import plant.AbstractPlant;
import plant.PlantInfo;

public class Herb extends AbstractPlant {
  private String taste;

  public Herb(final PlantInfo plant, String taste) {
    super(plant);
    this.taste = taste;
  }

  public String getTaste() { return this.taste; }
  public void setTaste(final String taste) { this.taste = taste; }

  @Override public String pruning() {
    return "Snip off the top sets of the leaves/stems";
  }
  
  @Override public String staking() {
    return "Not required";
  }
  
}
