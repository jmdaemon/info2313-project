package utils;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Library {

  // Write data buffered line-by-line to disk
  public static void write_buf(String fp, String[] lines, String error) {
    try {
      FileWriter fwriter = new FileWriter(fp);
      for (String line : lines)
        fwriter.append(line);
      fwriter.close();
    } catch (Exception e) {
      System.out.println(error);
      e.printStackTrace();
    }
  }

  // Read data buffered line-by-line from disk
  public static List<String> read_buf(String fp, String error) {
    // Attempt to buffer read the file line by line
    List<String> lines = new ArrayList<String>();
    try {
      File data = new File(fp);
      Scanner freader = new Scanner(data);
      while (freader.hasNextLine()) 
        lines.add(freader.nextLine());
      freader.close();
    } catch (Exception e) {
      System.out.println(error);
      e.printStackTrace();
    }
    return lines;
  }
}

