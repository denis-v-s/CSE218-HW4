package application;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileWriter {
  public static void writeToFile(String text) {
    Path dir = Paths.get("").toAbsolutePath().resolve("data/");
    File file = new File(dir.resolve("output.txt").toString());
    try (PrintWriter writer = new PrintWriter(file)) {
      writer.println(text);
    } catch (Exception e) {
      System.out.println("error: " + e.getMessage());
    }
  }
}
