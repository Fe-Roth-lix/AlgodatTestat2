import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Main {
  public static void main(String[] args) {
    System.out.println(generateRandomNumber());

    int[] values = new int[15];
    for (int i = 0; i < 15; i++) {
      values[i] = generateRandomNumber();
    }

    System.out.println(Arrays.toString(values));
  }

  public static void writeDotFile(String data) throws IOException {
    File outputFile = new File("output.dot");

    //Datei im Ordner erstellen
    outputFile.createNewFile();

    FileWriter writer = new FileWriter(outputFile);

    writer.write(data);
    writer.flush();
    writer.close();

  }

  public static String prepareDataForFilewriter() {
    String data = "digraph G { \n";
    String tab = "\t";
    return data + "\n}";
  }


  /**
   * Generates a Random Number between 1-100.
   *
   * @return random int between 1-100
   */
  public static int generateRandomNumber() {
    int number;
    number = (int) (Math.random() * (100 - 1 + 1) + 1);
    return number;
  }
}
