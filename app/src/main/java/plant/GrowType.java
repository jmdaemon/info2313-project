package plant;

public enum GrowType {
  NONE("none"),
  SEEDING("seeding"),
  CUTTING("cutting"),
  DIVISION("division"),
  LAYERING("layering"),
  GRAFTING("grafting"),
  BUDDING("budding");

  String value;
  GrowType(String value) { this.value = value; }

  @Override
  public String toString() { return this.value; }

  public static GrowType fromString(String text) {
    for (GrowType e : GrowType.values()) 
      if (e.toString().equalsIgnoreCase(text)) 
        return e;
    return null;
  }
}
