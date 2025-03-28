package plant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import data.Data;
import utils.Library;

public class PlantManager {
  private List<Plant> plants;

  public List<Plant> getPlants() { return this.plants; }
  public void setPlants(List<Plant> plants) { this.plants = plants; }

  public void add(final Plant plant) {
    this.plants.add(plant);
  }

  // Select a plant to delete
  // TODO: Prompt user for confirmation
  public void del(final int plant_index) {
    this.plants.remove(plant_index);
  }

  // Update a single plant in the list
  public void update(final int plant_index, final double plant_price) {
    Plant p = this.plants.get(plant_index);
    // TODO: Set plant price
    this.plants.set(plant_index, p);
  }
  
  // Search for a plant according to type
  public List<Plant> search(final PlantType plant_type) {
    return this.filter(p -> p.plant_type != plant_type);
  }

  // Save the plants in the list to disk
  public void save(String fp) {
    File data = new File(fp);
    if (data.exists()) {
      // TODO: Check if the user wants to overwrite the data in the file
      // For now we just error out
      System.out.println(Data.WARN_FILE_OVERWRITE);
      System.exit(Data.ERROR_FAILURE);
    } else {
      // If the file does not exist yet, create the file
      // data.createNewFile();
    }

    // TODO: Access plant list and serialize information to disk

    // Save data to disk
    // write(fp);
    String[] lines = new String[5];
    Library.write_buf(fp, lines, Data.ERROR_FILE_WRITE);
  }

  // Read plants from disk to list
  // Assume csv file
  public void read(String fp) {
    File data = new File(fp);
    if (!data.exists()) {
      System.out.println(Data.ERROR_FILE_NEXIST);
      System.exit(Data.ERROR_FAILURE);
    }

    // TODO: Read plant data from disk into memory
    this.plants = parse(fp);
  }

  // Filter plants according to a type
  // public List<Plant> filter(PlantType plant_type) {
  public List<Plant> filter(Predicate<Plant> pred) {
    List<Plant> results = new ArrayList<Plant>(this.plants); // Clone plants
    results.removeIf(pred);
    return results;
  }

  // Parse data into plant data
  private List<Plant> parse(String fp) {
    List<Plant> loaded = new ArrayList<Plant>();

    // Read data to memory
    List<String> lines = Library.read_buf(fp, Data.ERROR_FILE_READ);

    // Parse data
    for (String line : lines) {
      // Parse each line into plant data
      // TODO: We don't know what the format will look
      // like so we don't use this data right now
      // Plant plant = new Plant();
      // loaded.add(plant);
    }

    return loaded;
  }
}
