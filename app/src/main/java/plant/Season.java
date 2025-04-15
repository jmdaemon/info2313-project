package plant;

public enum Season {
  NONE("none"),
  WINTER("winter"),
  SPRING("spring"),
  SUMMER("summer"), 
  FALL("fall");

  // NONE("none"),

  // WINTER("winter"),
  // SPRING("spring"),
  // SUMMER("summer"), 
  // FALL("fall");

  // ALL,

  String value;
  Season(String value) { this.value = value; }

  public static Season toSeason(String season) {
    switch(season) {
      case "winter" -> { return Season.WINTER; }
      case "spring" -> { return Season.SPRING; }
      case "summer" -> { return Season.SUMMER; }
      case "fall" -> { return Season.FALL; }
    }
    return NONE;
    // return WINTER;
      // default -> { return NONE; }
  }
  // }
}
