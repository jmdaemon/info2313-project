package plant;

public interface Plant {
  /* Retrieve the plant methods/instructions */
  abstract String staking();
  abstract String pruning();
  default String fertilizing() {
    return "STUB: Fertilizing";
  }
  default String watering() {
    return "STUB: Watering";
  }
}
