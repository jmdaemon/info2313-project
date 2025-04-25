package ui;

/**
  * FFI for exposing plant details to JavaScript 
  *
  * PlantFFI is a JavaScript wrapper for AbstractPlant and PlantInfo
  */
public class PlantFFI {
  
  private PlantModel model;

  public PlantFFI(final PlantModel model) {
    this.model = model;
  }

  public String getName() { return this.model.name.get(); }
  public String getPriceCAD() {
    return "$CAD " + this.model.price.get();
  }
}
