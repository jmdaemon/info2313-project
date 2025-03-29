package plant;

import java.util.Date;
import java.util.List;

/**
  * name              : Plant name
  * alt_names         : Alternative names for the plant
  * pot_time          : Best growing season for the plant
  * price             : Price of the plant denominated in $CAD
  * lifespan          : Lifespan of the plant in days
  * grow_method       : Growing methods available to the plant
  * grow_instructions : Instructions on how to cultivate the plant
  */
public class PlantInfo {
  public String name;
  public List<String> alt_names;

  public Season pot_time;
  public Date pot_date;

  public double price;
  public int lifespan;

  public GrowType grow_method;
  public String grow_instructions;

  public PlantType plant_type;

  public PlantInfo(
      final String name,
      final List<String> alt_names,
      final Season pot_time,
      final Date pot_date,
      final double price,
      final int lifespan,
      final GrowType grow_method,
      final String grow_instructions,
      final PlantType plant_type
      ) {
    this.name = name;
    this.alt_names = alt_names;
    this.pot_time = pot_time;
    this.pot_date = pot_date;
    this.price = price;
    this.lifespan = lifespan;
    this.grow_method = grow_method;
    this.grow_instructions = grow_instructions;
    this.plant_type = plant_type;
  }
}
