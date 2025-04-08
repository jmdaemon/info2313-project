package plant;

public enum Season {
  // NONE("none"),
  WINTER("winter"),
  SPRING("spring"),
  SUMMER("summer"), 
  FALL("fall");
  // ALL,

  String value;
  Season(String value) { this.value = value; }

  // public Season toSeason(String season) {
  //   switch(season) {
  //     case "winter" -> { return WINTER; }
  //     case "spring" -> { return SPRING; }
  //     case "summer" -> { return SUMMER; }
  //     case "fall" -> { return FALL; }
  //     default -> { return NONE; }
  //   }
  // }
}
