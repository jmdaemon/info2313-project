package plant;

public enum PlantType {
  NONE("none"),
  TREE("tree"),
  HERB("herb"),
  CREEPER("creeper");

  String value;
  PlantType(String value) { this.value = value; }

  @Override
  public String toString() { return this.value; }

  public static PlantType fromString(String text) {
    for (PlantType e : PlantType.values()) 
      if (e.toString().equalsIgnoreCase(text)) 
        return e;
    return null;
  }
}
