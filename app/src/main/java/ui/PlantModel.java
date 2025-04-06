package ui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import plant.AbstractPlant;

public class PlantModel {
  // Data
  private AbstractPlant plant;

  // Properties
  public StringProperty name;
  public DoubleProperty price;

  public ObjectProperty<Image> picture;

  // We're going to use the original AbstractPlantmodel as much as possible,
  // but update certain fields when we need to, such as price.
  public PlantModel(final AbstractPlant plant) {
    this.plant = plant;
    this.name = new SimpleStringProperty();
    this.name.set(this.plant.info.name);

    this.price = new SimpleDoubleProperty();
    this.price.set(this.plant.info.price);

    this.picture = new SimpleObjectProperty<Image>();
  }
}
