import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
    RBT tree = new RBT();

    int[] values = generateRandomArray();
    tree.printDotGraph();
    for (int i = 0; i < 15; i++) {
      tree.insert(values[i]);
      tree.printDotGraph();
    }
  }

  /**
   * Generates an Array with random numbers between 1-100, with length 15.
   *
   * @return random int between 1-100
   */
  public static int[] generateRandomArray() {
    int[] values = new int[15];
    for (int i = 0; i < 15; i++) {
      values[i] = (int) (Math.random() * (100 - 1 + 1) + 1);
    }
    return values;
  }
}
