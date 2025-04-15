package app;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import plant.AbstractPlant;
import plant.GrowType;
import plant.PlantInfo;
import plant.PlantManager;
import plant.PlantType;
import plant.Season;
import plant.plants.Creeper;
import plant.plants.Herb;
import plant.plants.Tree;

public class App {
  final static String FP_PLANT_DATA = "plant.psv";

  final static String TITLE_OPTIONS = "Options:";

  public static void showPlantDetails(final AbstractPlant p) {
    final String price = String.format("$%.2f CAD", p.info.price);
    System.out.printf("%-16s %-16s\n", p.info.name, price);
    System.out.println("=".repeat(16));
    System.out.println("Growing: " + p.info.grow_instructions);
    System.out.println("Staking: " + p.staking());
    System.out.println("Pruning: " + p.pruning());
    System.out.println(">".repeat(16));
    System.out.println("Watering    : " + p.watering());
    System.out.println("Fertilizing : " + p.fertilizing());
    System.out.println("-".repeat(16));
    System.out.println();
  }

  public static void createPlant(Scanner reader) {
    // Show plant creation menu
    // Get user input & do basic error validation on it
  }




  /*
  public static void showPlants(final PlantManager pm) {
    // System.out.println("==> Showing all plants:");
    System.out.println("# Showing all plants:");
    for (AbstractPlant plant : pm.getPlants()) 
      showPlantDetails(plant);
  }

  public static void savePlants(final PlantManager pm, final Scanner reader) {
    System.out.println("# Saving all plants:");
    pm.save(FP_PLANT_DATA, reader);
  }

  public static void loadPlants(final PlantManager pm, final Scanner reader) {
    System.out.println("# After loading plant data:");
    for (AbstractPlant plant : pm.getPlants()) 
      showPlantDetails(plant);
  }
  */

    // public static int promptRange(int min, int max, String prompt, Scanner reader) {
    //   int input = 0;
    //   do {
    //     // prompt the user again
    //     System.out.print(prompt);
    //     input = reader.nextInt();

    //     // if (domain.in_range(input))
    //     if (Domain.in_range(input, min, max))
    //       System.out.println("Error: Invalid User Input.");
    //   // } while (Domain.in_range(input));
    //   } while (Domain.in_range(input, min, max));
    //   return input;
    // }

    public static int selectPlant(final PlantManager pm, final Scanner reader, final boolean indent) {
      final List<AbstractPlant> plants = pm.getPlants();
      if (plants.size() <= 1) {
        System.out.println("No plants to delete!");
        return -1;
        // break;
      }

      // Choose a plant
      final int PLANT_MIN = 1;
      final int PLANT_MAX = plants.size();
      int choice = -1;
      while (true) {
        System.out.println("Select a plant:");
        for (int i = 0; i < plants.size(); i++) {
          int opt = i + 1;
          System.out.printf("%s%d) %s\n", Menu.setIndent(indent), opt, plants.get(i).info.name);
        }
        
        choice = reader.nextInt();
        
        if ((choice >= PLANT_MIN) && (choice <= PLANT_MAX))
          break;
      }
      return choice;
  }
  
  public static void main(String[] args) {
    Scanner reader = new Scanner(System.in);
    
    final List<MenuOptions> opts = List.of(
        MenuOptions.ADD,
        MenuOptions.DEL,
        MenuOptions.EDIT,
        MenuOptions.SEARCH,
        MenuOptions.SHOW,
        MenuOptions.SAVE,
        MenuOptions.LOAD,
        MenuOptions.QUIT
    );
    final List<Integer> choices = List.of(1, 2, 3, 4, 5, 6, 7, 9);
    final List<String> descriptions = List.of(
        "Add a plant",
        "Remove a plant",
        "Update plant price",
        "Search for plants by type",
        "Show all plants",
        "Save plants to file",
        "Load plants from file",
        "Quit"
    );
    final Menu<MenuOptions> menu = new Menu<MenuOptions>(opts, choices, descriptions);

    PlantManager pm = new PlantManager();

    // Settings
    final boolean indent = true;

    do {
      menu.showMenu(TITLE_OPTIONS, true);

      // Validate user input
      Optional<MenuOptions> input = null;
      do {
        System.out.print("PMS> ");
        int choice = reader.nextInt();
        input = menu.getChoice(choice);
      } while (input.isEmpty());

      MenuOptions opt = input.get();
      switch(opt) {
        case ADD -> {
          // TODO: Get user input to add new plant

          // Prompt for plant name
          System.out.println("Plant Name: ");
          String name = reader.next();
          reader.nextLine(); // Flush line

          System.out.println("Other Names: ");
          String names = reader.next();
          reader.nextLine(); // Flush line

          String maybe_season = null;
          while(true) {
            System.out.println("Potting Season: ");
            maybe_season = reader.nextLine();
            if (!input.isEmpty() && (Season.toSeason(maybe_season) != Season.NONE))
              break;
          }
          Season season = Season.toSeason(maybe_season);

          // Try to parse a date
          String maybe_pot_date = null;
          while(true) {
            System.out.println("Potting Date: ");
            maybe_pot_date = reader.nextLine();
            if (!input.isEmpty())
              break;
          }
          DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
          LocalDate pot_date = LocalDate.parse(maybe_pot_date, fmt);

          double price = -1.0;
          while(true) {
            System.out.println("Price: ");
            price = reader.nextDouble();
            if (price <= 0)
              break;
          }

          int lifespan = -1;
          while(true) {
            System.out.println("Lifespan : ");
            lifespan = reader.nextInt();
            if (lifespan <= 0)
              break;
          }

          String maybe_grow_method = null;
          while(true) {
            System.out.println("Grow Method: ");
            maybe_grow_method  = reader.nextLine();
            if (!input.isEmpty() && (GrowType.toGrowType(maybe_grow_method) != GrowType.None))
              break;
          }
          GrowType grow_method = GrowType.toGrowType(maybe_grow_method);

          String grow_instructions = "";
          System.out.println("Special Growing Instructions: ");
          String line = null;
          while (!line.equals("END")) {
            line = reader.nextLine();
            grow_instructions += line;
          }

          // Get Plant Type
          PlantType plant_type = PlantType.TREE;

          PlantInfo info = new PlantInfo(
              name,
              List.of(names.split(",")),
              season,
              pot_date,
              price,
              lifespan,
              grow_method,
              grow_instructions,
              plant_type
          );
          
        // Prompt for extra data based on plant type
          AbstractPlant plant = null;
          switch(plant_type) {
            case TREE -> { plant = new Tree(info, 120.0); }
            case HERB -> { plant = new Herb(info, "Aromatic"); }
            case CREEPER -> { plant = new Creeper(info, "Red"); }
          }
          
          pm.add(plant);
        }
        case DEL -> {
          System.out.println("# Delete tree plant:");
          
          int choice = selectPlant(pm, reader, indent);
          if (choice == -1) 
            break;
          else
            pm.del(choice - 1);
          System.out.println("Deleted plant.");
        }
        case EDIT -> {
          System.out.println("# Update plant price:");
          
          // Get Plant
          int choice = selectPlant(pm, reader, indent);
          if (choice == -1) 
            break;

          // Get Price
          double price = -1;
          do {
            System.out.print("Enter a new price: ");
            price = reader.nextDouble();
          } while (price <= 0.0);
          pm.update(choice - 1, price);
          System.out.println("Plant price updated.");
        }
        case SEARCH -> {
          System.out.println("# Search for plants by type:");

          // Get Plant
          // Allow only valid options
          List<Integer> valid_choices = List.of(1, 2, 3, 9);
          List<String> valid_options = List.of("TREE", "HERB", "CREEPER", "BACK");
          int choice = -1;
          do {
            System.out.println("Select a plant type:");
            Menu.showOpts(TITLE_OPTIONS, valid_choices, valid_options, true);
            
            choice = reader.nextInt();
          } while (!valid_choices.contains(choice));
          
          // Get plant type
          PlantType ptype = null;
          switch (choice) {
            case 1 -> { ptype = PlantType.TREE; }
            case 2 -> { ptype = PlantType.HERB; }
            case 3 -> { ptype = PlantType.CREEPER; }
            case 9 -> { break; }
          }

          // Exit early
          if (choice == 9)
            break;

          // Filter all plants
          List<AbstractPlant> results = pm.search(ptype);
          for (AbstractPlant plant : results) 
            showPlantDetails(plant);
        }
        case SHOW -> {
          System.out.println("# Showing all plants:");
          for (AbstractPlant plant : pm.getPlants()) 
            showPlantDetails(plant);
        }
        case SAVE -> {
          System.out.println("# Saving all plants:");
          pm.save(FP_PLANT_DATA, reader);
          System.out.printf("Plant data saved to: \'%s\'\n", FP_PLANT_DATA);
        }
        case LOAD -> {
          System.out.println("# Loading plant data:");
          pm.load(FP_PLANT_DATA, reader);
          System.out.println("# Plants Loadded:");
          for (AbstractPlant p : pm.getPlants())
            System.out.println(p.info.name);
        }
        case QUIT -> {
          opt = MenuOptions.QUIT;
          break;
        }
      }

      // Quit Program
      if (opt == MenuOptions.QUIT)
        break;
    } while (true);

    reader.close();
  }
}
