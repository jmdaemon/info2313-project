package plant;

public enum Season {
  NONE("none"),
  WINTER("winter"),
  SPRING("spring"),
  SUMMER("summer"), 
  FALL("fall");

  String value;
  Season(String value) { this.value = value; }

  @Override
  public String toString() { return this.value; }

  public static Season fromString(String text) {
    for (Season e : Season.values()) 
      if (e.toString().equalsIgnoreCase(text)) 
        return e;
    return null;
  }
}
