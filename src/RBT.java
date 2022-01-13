import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RBT {

  //class to generate a Node for the Tree
  private class Node {
    private Node left;
    private Node right;
    private boolean color; //true = red, false = black
    private int value; //value of node in tree

    // constructor for a node, value and color must be set
    public Node(int value, boolean color) {
      this.value = value;
      this.color = color;
    }
  }

  private static final boolean RED = true;
  private static final boolean BLACK = false;
  private Node root;
  private static int counter = 0; // counter for filewriter method to count up output names

  public int get(int value) {
    Node node = root;

    while (node != null) {
      if (value < node.value) {
        node = node.left;
      } else if (value > node.value) {
        node = node.right;
      } else if (value == node.value) {
        return node.value;
      }
    }
    return Integer.MIN_VALUE;
  }

  private boolean isRed(Node node) {
    if (node == null) {
      return false;
    }
    return node.color;
  }

  private Node rotateLeft(Node h) {
    Node x = h.right;
    h.right = x.left;
    x.left = h;
    return x;
  }

  private Node rotateRight(Node h) {
    Node x = h.left;
    h.left = x.right;
    x.right = h;
    return x;
  }

  private Node insert(Node node, int value, boolean isRed) {

    if (node == null) {
      return new Node(value, RED); // RED was isRed before
    }

    if (isRed(node.left) && isRed(node.right)) {
      node.color = RED;
      node.left.color = BLACK;
      node.right.color = BLACK;
    }


    if (value < node.value) {
      node.left = insert(node.left, value, BLACK); // /*BLACK was
      // isRed
      // before*/

      if (isRed(node) && isRed(node.left) && isRed) {
        node = rotateRight(node);
      }

      if (isRed(node.left) && isRed(node.left.left)) {
        node = rotateRight(node);
        node.color = BLACK;
        node.right.color = RED;
      }

    } else {
      node.right = insert(node.right, value, RED);

      if (isRed(node) && isRed(node.right) && !isRed) {
        node = rotateLeft(node);
      }

      if (isRed(node.right) && isRed(node.right.right)) {
        node = rotateLeft(node);
        node.color = BLACK;
        node.left.color = RED;
      }
    }
    return node;
  }

  public void insert(int value) {
    this.root = insert(this.root, value, BLACK);
    this.root.color = BLACK;
  }


  public void printDotGraph() throws IOException {

    String output = "digraph g0 {\nnode [height=.1, style=filled];\n";
    output += printDotNode(root);
    output += "}";
    writeDotFile(output);
  }

  private String printDotNode(Node node) {
    String output = "";

    if (node != null) {
      output += node.value + "[label=\"" + node.value + "\", color=black, fillcolor=";
      output += isRed(node) ? "red" : "black";
      output += ", fontcolor=";
      output += isRed(node) ? "black" : "white";
      output += ", shape=circle, width=.6 ];\n";
      if (node.left != null) {
        output += printDotNode(node.left);
        output += node.value + " -> " + node.left.value + ";\n";
      } else {
        output += "nil_l_" + node.value + "[ width=.3, fontsize=7, label=\"NIL\", color=black, fontcolor=white, shape=record ];\n";
        output += node.value + " -> nil_l_" + node.value + ";\n";
      }

      if (node.right != null) {
        output += printDotNode(node.right);
        output += node.value + " -> " +  node.right.value + ";\n";
      } else {
        output += "nil_r_" + node.value + "[ width=.3, fontsize=7, label=\"NIL\", color=black, fontcolor=white, shape=record ];\n";
        output += node.value + " -> nil_r_" + node.value + ";\n";
      }
    } else {
      output += "nil_node_root[width=.3, fontsize=7, label=\"NIL\", color=black, fontcolor=white, shape=record ];\n";
    }

    return output;
  }


  static void writeDotFile(String data) throws IOException {
    File outputFile = new File("output" + counter + ".dot");
    counter++;

    //Datei im Ordner erstellen
    outputFile.createNewFile();

    FileWriter writer = new FileWriter(outputFile);

    writer.write(data);
    writer.flush();
    writer.close();
  }


}