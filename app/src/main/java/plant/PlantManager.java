package plant;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import data.Data;
import plant.plants.Creeper;
import plant.plants.Herb;
import plant.plants.Tree;
import utils.Library;

public class PlantManager {
  private List<AbstractPlant> plants;

  //  API: //
  // <===> //

  public PlantManager() {
    this.plants = new ArrayList<AbstractPlant>();
  }

  public List<AbstractPlant> getPlants() { return this.plants; }
  public void setPlants(List<AbstractPlant> plants) { this.plants = plants; }

  public void add(final AbstractPlant plant) {
    this.plants.add(plant);
  }

  // Select a plant to delete
  // TODO: Prompt user for confirmation
  public void del(final int plant_index) {
    this.plants.remove(plant_index);
  }

  // Update a single plant 
  public void updatePlant(final int plant_index, final AbstractPlant plant) {
    this.plants.set(plant_index, plant);
  }

  // Update a single plant's price
  public void update(final int plant_index, final double plant_price) {
    AbstractPlant p = this.plants.get(plant_index);
    p.info.price = plant_price;
    this.plants.set(plant_index, p);
  }

  // Find index of plant in array
  public int indexOf(final AbstractPlant search) {
    for (int i = 0; i < this.plants.size(); i++)
      if (this.plants.get(i) == search)
        return i;
      // if (this.plants.get(i).equals(search))
    return -1;
  }
  
  // Search for a plant according to type
  public List<AbstractPlant> search(final PlantType plant_type) {
    return this.filter(p -> p.info.plant_type != plant_type);
  }

  // There are two methods available for users to call
  // For the gui we will use write and read directly with separate prompting
  // For the cli we will use load & save

  // Save the plants in the list to disk
  // Users must prompt outside of this
  public void write(final String fp, final boolean overwrite) {
    File data = new File(fp);

    if (data.exists() && !overwrite) {
      System.out.println(Data.WARN_FILE_OVERWRITE);
      System.exit(Data.ERROR_FAILURE);
    } else {
      try {
        data.delete();
        data.createNewFile();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    // Serialize plant data and save to disk
    // Format plants
    String[] lines = new String[this.plants.size()];
    for (int i = 0; i < this.plants.size(); i++) {
      lines[i] = this.format(this.plants.get(i)) + "\n";
    }

    // Save data to disk
    Library.write_buf(fp, lines, Data.ERROR_FILE_WRITE);
  }

  // Prompt user to overwrite file
  public void save(final String fp, final Scanner reader) {
    System.out.print("Save plant data? [yes/no]: ");
    String choice = reader.next();
    reader.nextLine(); // Skip newline char

    if (choice.toLowerCase().equals("yes")) {
      // Check for overwrites
      File data = new File(fp);
      if (data.exists()) {
        System.out.println("Plant save data already exists.");
        System.out.print("Overwrite plant save data? [yes/no]: ");
        choice = reader.next();
        reader.nextLine(); // Skip newline char
      }

      if (choice.toLowerCase().equals("yes")) {
        write(fp, true);
      }
    }
  }

  // Load the plants from disk

  // Read plants from disk to list
  // Assume csv file
  public void read(String fp, boolean init) {
    File data = new File(fp);

    if (!data.exists() && !init) {
      System.out.println(Data.ERROR_FILE_NEXIST);
      System.exit(Data.ERROR_FAILURE);
    } else {
      // Create initial data file
      try {
        data.createNewFile();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    // Read plant data from disk into memory
    this.plants = parse(fp);
  }

  public void load(final String fp, final Scanner reader) {
    System.out.print("Load plant data? [yes/no]: ");
    String choice = reader.next();
    reader.nextLine(); // Skip newline char

    if (choice.toLowerCase().equals("yes")) {
      // Check for overwrites
      File data = new File(fp);
      if (!data.exists()) {

        System.out.println("No plant save data currently exists.");
        System.out.print("Create new plant data? [yes/no]: ");
        System.out.println();
        choice = reader.next();
        reader.nextLine(); // Skip newline char
      }

      if (choice.toLowerCase().equals("yes")) {
        read(fp, true);
      }
    }
  }

  // Filter plants according to a type
  public List<AbstractPlant> filter(Predicate<AbstractPlant> pred) {
    List<AbstractPlant> results = new ArrayList<AbstractPlant>(this.plants); // Clone plants
    results.removeIf(pred);
    return results;
  }

  // Internal //
  // <======> //

  private String format(AbstractPlant plant) {
    String line = String.format(
      "%s|%s|%s|%s|%s|%d|%s|%s|%s",
      plant.info.name,
      plant.info.alt_names,
      plant.info.pot_time.toString().toUpperCase(),
      plant.info.pot_date.toString(),
      plant.info.price,
      plant.info.lifespan,
      plant.info.grow_method.toString().toUpperCase(),
      plant.info.grow_instructions,
      plant.info.plant_type.toString().toUpperCase()
    );
    
    switch(plant.info.plant_type) {
      case TREE -> {
        Tree tree = (Tree) plant;
        line += "|" + String.valueOf(tree.getHeight());
      }
      case HERB -> {
        Herb herb = (Herb) plant;
        line += "|" + String.valueOf(herb.getTaste());
      }
      case CREEPER -> {
        Creeper creeper = (Creeper) plant;
        line += "|" + String.valueOf(creeper.getColor());
      }
    }
      
    return line;
  }

  // Parse data into plant data
  private List<AbstractPlant> parse(String fp) {
    List<AbstractPlant> loaded = new ArrayList<AbstractPlant>();

    // Read data to memory
    List<String> lines = Library.read_buf(fp, Data.ERROR_FILE_READ);
    
    // Parse data
    for (String line : lines) {
      // Ignore blank lines
      if (line.isBlank())
        continue;

      List<String> tokens = Collections.list(new StringTokenizer(line, "|")).stream()
        .map(token -> (String) token)
        .collect(Collectors.toList());
      
      final String name = tokens.get(0);
      final List<String> names = List.of(tokens.get(1).split(","));
      final Season pot_time = Season.fromString(tokens.get(2));

      DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      final LocalDate pot_date = LocalDate.parse(tokens.get(3), fmt);

      final double price = Double.valueOf(tokens.get(4));
      final int lifespan = Integer.valueOf(tokens.get(5));
      final GrowType grow_method = GrowType.fromString(tokens.get(6));
      final String grow_instructions = tokens.get(7);
      final PlantType plant_type = PlantType.fromString(tokens.get(8).toUpperCase());

      final PlantInfo plant_info = new PlantInfo(name, names, pot_time, pot_date,
          price, lifespan, grow_method,
          grow_instructions, plant_type);

      AbstractPlant plant = null;
      switch(plant_type) {
        case TREE -> {
          final double height = Double.valueOf(tokens.get(9));
          plant = new Tree(plant_info, height);
        }
        case HERB -> {
          final String taste = tokens.get(9);
          plant = new Herb(plant_info, taste);
        }
        case CREEPER -> {
          final String color = tokens.get(9);
          plant = new Creeper(plant_info, color);
        }
        default -> {
          // TODO: ERROR OUT
        }
      }
      loaded.add(plant);
    }

    return loaded;
  }
}
