package plant;

public abstract class AbstractPlant implements Plant {
  public PlantInfo info;

  public AbstractPlant(final PlantInfo info) { this.info = info; }
}
