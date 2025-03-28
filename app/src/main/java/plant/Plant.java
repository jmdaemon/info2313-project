package plant;

public class Plant implements IPlant {
  public PlantType plant_type;
  protected PlantInfo info;    

  public Plant(PlantInfo info) {
    this.info = info;
  }
  
  @Override
  public void Fertilize() {
    System.out.println("Using fertilizer on " + info.name);
  }
  
  @Override
  public void Water() {
      System.out.println("Watering " + info.name);
  }

  @Override
  public void Prune() {
      System.out.println("Prune " + info.name);
  }

  @Override
  public void Stake() {
      System.out.println("Stake " + info.name);
  }
}
