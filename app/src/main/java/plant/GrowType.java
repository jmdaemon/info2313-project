package plant;

public enum GrowType {
  None("none"),
  Seeding("seeding"),
  Cutting("cutting"),
  Division("division"),
  Layering("layering"),
  Grafting("grafting"),
  Budding("budding");
  /*
  Seeding("Seeding"),
  Cutting("Cutting"),
  Division("Division"),
  Layering("Layering"),
  Grafting("Grafting"),
  Budding("Budding");
  */

  String value;
  GrowType(String value) { this.value = value; }

  public static GrowType toGrowType(String grow_method) {
    switch(grow_method) {
      case "seeding" -> { return Seeding; }
      case "cutting" -> { return Division; }
      case "layering" -> { return Layering; }
      case "grafting" -> { return Grafting; }
      case "budding" -> { return Budding; }
    }
    return None;
  }
}
