package plant;

public enum PlantType {
  NONE("none"),
  TREE("tree"),
  HERB("herb"),
  CREEPER("creeper");

  String value;
  PlantType(String value) { this.value = value; }
  
  // @Override
  // public String toString() {
  //   switch(this) {
  //     case NONE: return "none";
  //     case TREE: return "tree";
  //     case HERB: return "herb";
  //     case CREEPER: return "creeper";
  //     default: throw new IllegalArgumentException();
  //   }
  // }
}
