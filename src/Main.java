import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
    RBT tree = new RBT();

    //generates an random array with length 15 and numbers between 1-100
    int[] values = generateRandomArray();
    //empty tree export
    tree.printDotGraph();
    for (int i = 0; i < 15; i++) {
      tree.insert(values[i]);
      tree.printDotGraph();
    }

  }


  //Generates an Array with random numbers between 1-100, with length 15, without duplicates.
  public static int[] generateRandomArray() {
    int[] values = new int[15];
    for (int i = 0; i < 15; i++) {
      values[i] = (int) (Math.random() * (100 - 1 + 1) + 1);
    }
    if (checkDuplicate(values)) {
      return generateRandomArray();
    } else {
      return values;
    }
  }

  public static boolean checkDuplicate(int[] arr){

    for (int i = 0; i < arr.length; i++) {
      int temp = arr[i];
      for (int j = 0; j < arr.length; j++) {
        if (temp == arr[j] && i != j) {
          return true;
        }
      }
    }
    return false;
  }

}
