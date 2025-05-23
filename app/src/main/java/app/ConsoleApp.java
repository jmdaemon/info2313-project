package app;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import plant.AbstractPlant;
import plant.GrowType;
import plant.PlantInfo;
import plant.PlantManager;
import plant.PlantType;
import plant.Season;
import plant.plants.Creeper;
import plant.plants.Herb;
import plant.plants.Tree;
import utils.Menu;
import utils.Prompt;

public class ConsoleApp {
  final static String FP_PLANT_DATA = "plant.psv";

  final static String TITLE_OPTIONS = "Options:";

  // Main Functions
  public static void addPlants(final PlantManager pm, final Scanner reader, final boolean indent) {
    String name = Prompt.promptLine("Plant Name: ", reader);
    String names = Prompt.promptLine("Other Names: ", reader);

    // Prompt Choices
    String input = Prompt.promptChoiceUnchecked("Potting Season: ", reader, indent,
        s -> Season.fromString(s) != Season.NONE);
    Season season = Season.fromString(input);
    
    // Try to parse a date
    String maybe_pot_date = null;
    while(true) {
      System.out.print("Potting Date: ");
      maybe_pot_date = reader.nextLine();
      if (!maybe_pot_date.isEmpty())
        break;
    }
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate pot_date = LocalDate.parse(maybe_pot_date, fmt);

    double price = Prompt.promptDouble("Price: ", reader);

    int lifespan = Prompt.promptInt("Lifespan: ", reader);

    reader.nextLine(); // Skip line

    input = Prompt.promptChoiceUnchecked("Grow Method: ", reader, indent,
          gt -> GrowType.fromString(gt) != GrowType.NONE);
    GrowType grow_method = GrowType.fromString(input);
    
    String grow_instructions = Prompt.promptMultiLine("Special Growing Instructions: ", "END", reader);

    // Get Plant Type
    PlantType plant_type = getPlantType("Plant Type: ", reader);

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
      case TREE -> {
        double height = Prompt.promptDouble("Height: ", reader);
        plant = new Tree(info, height);
      }
      case HERB -> {
        String taste = Prompt.promptWord("Taste: ", reader);
        plant = new Herb(info, taste);
      }
      case CREEPER -> {
        String color = Prompt.promptWord("Color: ", reader);
        plant = new Creeper(info, color);
      }
    }
    
    pm.add(plant);
  }

  public static void deletePlants(final PlantManager pm, final Scanner reader, final boolean indent) {
    System.out.println("# Delete tree plant:");
    
    int choice = selectPlant(pm, reader, indent);
    if (choice == -1) 
      return;
    else
      pm.del(choice - 1);
    System.out.println("Deleted plant.");
  }

  public static void editPlants(final PlantManager pm, final Scanner reader, final boolean indent) {
    System.out.println("# Update plant price:");
    
    // Get Plant
    int choice = selectPlant(pm, reader, indent);
    if (choice == -1) 
      return;

    // Get Price
    double price = -1;
    do {
      System.out.print("Enter a new price: ");
      price = reader.nextDouble();
    } while (price <= 0.0);

    pm.update(choice - 1, price);
    System.out.println("Plant price updated.");
  }

  public static void searchPlants(final PlantManager pm, final Scanner reader) {
    System.out.println("# Search for plants by type:");

    PlantType plant_type = getPlantType("Select a plant type:", reader);

    if (plant_type == null)
      return;

    // Filter all plants
    List<AbstractPlant> results = pm.search(plant_type);
    for (AbstractPlant plant : results) 
      showPlantDetails(plant);
  }

  public static void showPlants(final PlantManager pm) {
    System.out.println("# Showing all plants:");
    for (AbstractPlant plant : pm.getPlants()) 
      showPlantDetails(plant);
  }

  public static void savePlants(final PlantManager pm, final Scanner reader) {
    System.out.println("# Saving all plants:");
    pm.save(FP_PLANT_DATA, reader);
    System.out.printf("Plant data saved to: \'%s\'\n", FP_PLANT_DATA);
  }

  public static void loadPlants(final PlantManager pm, final Scanner reader) {
    System.out.println("# Loading plant data:");
    pm.load(FP_PLANT_DATA, reader);
    System.out.println("# Plants Loadded:");
    for (AbstractPlant p : pm.getPlants())
      System.out.println(p.info.name);
  }
  

  // Helper Functions

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

  public static int selectPlant(final PlantManager pm, final Scanner reader, final boolean indent) {
    final List<AbstractPlant> plants = pm.getPlants();
    if (plants.size() <= 1) {
      System.out.println("No plants to delete!");
      return -1;
      // break;
    }

    // Choose a plant
    List<String> options = plants.stream().map(p -> p.info.name).collect(Collectors.toList());
    return Prompt.promptChoice("Select a plant:", options, reader, indent);
  }

  public static PlantType getPlantType(final String prompt, final Scanner reader) {
    List<Integer> valid_choices = List.of(1, 2, 3, 9);
    List<String> valid_options = List.of("TREE", "HERB", "CREEPER", "BACK");
    int choice = -1;
    do {
      // System.out.println("");
      System.out.print(prompt);
      System.out.println();
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
    return ptype;
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
        case ADD -> { addPlants(pm, reader, indent); }
        case DEL -> { deletePlants(pm, reader, indent); }
        case EDIT -> { editPlants(pm, reader, indent); }
        case SEARCH -> { searchPlants(pm, reader); }
        case SHOW -> { showPlants(pm); }
        case SAVE -> { savePlants(pm, reader); }
        case LOAD -> { loadPlants(pm, reader); }
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
