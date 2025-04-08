package plant;

public enum GrowType {
  Seeding("Seeding"),
  Cutting("Cutting"),
  Division("Division"),
  Layering("Layering"),
  Grafting("Grafting"),
  Budding("Budding");

  String value;
  GrowType(String value) { this.value = value; }
}
