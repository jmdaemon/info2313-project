package utils;

import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class Prompt {
  
  public static int promptChoice(final String prompt, final List<String> options, final Scanner reader, final boolean indent) {
    final int min = 1;
    final int max = options.size();

    int choice = -1;
    do {
      System.out.print(prompt);
      System.out.println();
      for (int i = 0, opt = 0; i < max; i++, opt = i + 1) 
        System.out.printf("%s%d) %s\n", Menu.setIndent(indent), opt, options.get(i));
        
      choice = reader.nextInt();
    } while ((choice >= min) && (choice <= max));
    return choice;
  }

  public static String promptChoiceUnchecked(
      final String prompt,
      final Scanner reader,
      final boolean indent,
      final Predicate<String> pred
    ) {
    String input = "";
    do {
      System.out.print(prompt);
      input = reader.nextLine();
    } while(input.isEmpty() || !pred.test(input));
    return input;
  }

  public static String promptWord(final String prompt, final Scanner reader) {
    String input = "";
    do {
      System.out.print(prompt);
      input = reader.nextLine();
    } while(input.isEmpty());
    return input;
  }

  public static String promptLine(final String prompt, final Scanner reader) {
    String input = "";
    do {
      System.out.print(prompt);
      input = reader.next();
      reader.nextLine(); // Flush line
    } while(input.isEmpty());
    return input;
  }

  public static String promptMultiLine(final String prompt, final String end, final Scanner reader) {
    String input = "";
    String line = "";
    System.out.print(prompt);
    do {
      line = reader.nextLine();
      if (line.equals(end))
        break;
      input += line;
    } while (!line.equals(end));
    return input;
  }

  public static double promptDouble(final String prompt, final Scanner reader) {
    double input = -1.0;
    do {
      System.out.print(prompt);
      input = reader.nextDouble();
    } while (input <= 0.0);
    return input;
  }

  public static int promptInt(final String prompt, final Scanner reader) {
    int input = -1;
    do {
      System.out.print(prompt);
      input = reader.nextInt();
    } while (input <= 0);
    return input;
  }
}
