package app;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
  * options: Enum options mapped to integer choices
  * choices: Integer choices mapped to enum options
  * descriptions:  Enum options mapped to String descriptions
  * menu: Integer choices as text mapped to string descriptions
  */
public class Menu <T> {
  private Map<Integer, T> choices;
  private Map<T, Integer> options;
  private Map<T, String> descriptions;
  private Map<String, String> menu;

  // private Map<T, Integer> choices;
  // private Map<T, String> descriptions;
  // private Map<String, String> menu;

  // private List<T> options;
  // private List<Integer> choices;
  // private List<String> descriptions;

  public Menu(
      final List<T> options,
      final List<Integer> choices,
      final List<String> descriptions
      ) {

    // this.options = options;
    // this.choices = choices;
    // this.descriptions = descriptions;

    final List<String> choices_text = 
      choices.stream().map(i -> i.toString()).collect(Collectors.toList());
    
    // this.choices = zipToMap(options, choices);

    this.choices = zipToMap(choices, options);
    this.options = zipToMap(options, choices);
    this.descriptions = zipToMap(options, descriptions);
    this.menu = zipToMap(choices_text, descriptions);
  }
  
  // Internal 
  private static <K, V> Map<K, V> zipToMap(List<K> keys, List<V> values) {
    return IntStream
      .range(0, keys.size()).boxed()
      .collect(Collectors.toMap(keys::get, values::get));
  }
  
  // API
  public Map<String, String> getMenu() {
    return this.menu;
  }

  public Map<Integer, T> getChoices() {
    return this.choices;
  }

  public Optional<T> getChoice(final int c) {
    if (this.choices.containsKey(c)) {
      return Optional.of(this.choices.get(c));
    } 
    return Optional.empty();
  }
  
  public Optional<Integer> toChoice(final T opt) {
    if (this.options.containsKey(opt)) {
      return Optional.of(this.options.get(opt));
    }
    return Optional.empty();
  }

  public Optional<String> getDesc(final T opt) {
    if (this.descriptions.containsKey(opt)) {
      return Optional.of(this.descriptions.get(opt));
    }
    return Optional.empty();
  }
  
  // Show User Menu
  // public static void showMenu(final String title, final Map<String, String> options, final boolean indent) {

  public void showMenu(final String title, final boolean indent) {
    System.out.println(title);
    final String prefix = setIndent(indent);

    // for (Map.Entry<String, String> entry : options.entrySet()) {
    for (Map.Entry<String, String> entry : this.menu.entrySet()) {
      final String key = entry.getKey();
      final String val = entry.getValue();
      System.out.printf("%s%s) %s\n", prefix, key, val);
    }
  }

  public static void showOpts(final String title, final List<Integer> choices, final List<String> options, final boolean indent) {
    System.out.println(title);
    final String prefix = setIndent(indent);

    // for (Map.Entry<String, String> entry : options.entrySet()) {
    for (int i = 0; i < choices.size(); i++) {
      final String key = String.valueOf(choices.get(i));
      final String val = options.get(i);
      System.out.printf("%s%s) %s\n", prefix, key, val);
    }
  }

  public static String setIndent(final boolean indent) {
    String prefix = "";
    if (indent) 
      prefix += "\t";
    return prefix;
  }
}
