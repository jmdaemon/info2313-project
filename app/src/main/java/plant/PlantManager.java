package plant;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class PlantManager {
  public static short ERROR_FAILURE = 1;
  public static String WARN_FILE_OVERWRITE = "Warning: File already exists and will be overwritten.";

  public static String ERROR_FILE_NEXIST = "Error: File does not exist.";
  public static String ERROR_FILE_WRITE = "Error: File could not be written to.";
  public static String ERROR_FILE_READ = "Error: File could not be read from.";

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
      System.out.println(WARN_FILE_OVERWRITE);
      System.exit(ERROR_FAILURE);
    } else {
      // If the file does not exist yet, create the file
      // data.createNewFile();
    }

    // TODO: Access plant list and serialize information to disk

    // Save data to disk
    // write(fp);
  }

  // Read plants from disk to list
  // Assume csv file
  public void read(String fp) {
    File data = new File(fp);
    if (!data.exists()) {
      System.out.println(ERROR_FILE_NEXIST);
      System.exit(ERROR_FAILURE);
    }

    // TODO: Read plant data from disk into memory
    this.plants = parse(data);
  }

  // Filter plants according to a type
  // public List<Plant> filter(PlantType plant_type) {
  public List<Plant> filter(Predicate<Plant> pred) {
    List<Plant> results = new ArrayList<Plant>(this.plants); // Clone plants
    results.removeIf(pred);
    return results;
  }

  // Parse data into plant data
  private List<Plant> parse(File data) {
    List<Plant> loaded = new ArrayList<Plant>();

    // Attempt to buffer read the file line by line
    try {
      Scanner freader = new Scanner(data);
      while (freader.hasNextLine()) {
        // TODO: We don't know what the format will look
        // like so we don't use this data right now
        String line = freader.nextLine();

        // Parse each line into plant data
        // Plant plant = new Plant();
        // loaded.add(plant);
      }
      freader.close();
      
    } catch (Exception e) {
      System.out.println(ERROR_FILE_READ);
      e.printStackTrace();
    }

    return loaded;
  }

  // Helper Functions:

  // Write buffered data line-by-line to disk
  private static void write(String fp, String[] lines) {
    try {
      FileWriter fwriter = new FileWriter(fp);
      for (String line : lines)
        fwriter.append(line);
      fwriter.close();
    } catch (Exception e) {
      System.out.println(ERROR_FILE_WRITE);
      e.printStackTrace();
    }
  }
}
