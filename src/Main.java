import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    System.out.println(prepareDataForFilewriter());
    System.out.println(generateRandomNumber());
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
    return data + "\n}" ;
  }

  public static int generateRandomNumber() {
    int number;
    number = (int) (Math.random() * (100 - 1 + 1) + 1);
    return number;
  }
}
