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

  private Node root;
  private static int counter = 0; // counter for filewriter method to count up output names
  private static final boolean RED = true;
  private static final boolean BLACK = false;

  private boolean isRed(Node node) {
    //if leaf is a nil leaf, the color has to be black
    if (node == null) {
      return false;
    }
    return node.color;
  }

  private Node rotateRight(Node oldTopNode) {
    Node newTopNode = oldTopNode.left;
    oldTopNode.left = newTopNode.right;
    newTopNode.right = oldTopNode;
    return newTopNode;
  }

  private Node rotateLeft(Node oldTopNode) {
    Node newTopNode = oldTopNode.right;
    oldTopNode.right = newTopNode.left;
    newTopNode.left = oldTopNode;
    return newTopNode;
  }

  private Node internalInsert(Node node, int value, boolean isRed) {
    //condition for first insertion into tree
    if (node == null) {
      return new Node(value, RED);
    }

    //if both child-nodes are red, the parent node is set to red, children get black
    if (isRed(node.left) && isRed(node.right)) {
      node.color = RED;
      node.left.color = BLACK;
      node.right.color = BLACK;
    }

    //decision if new node gets inserted on the left or right side of parent node (BST)
    if (value < node.value) { //insertion on left side of parent node
      node.left = internalInsert(node.left, value, BLACK); //recursive call of internalInsert until nil leaf is hit

      if (isRed(node) && isRed(node.left) && isRed) { //condition for right-rotation
        node = rotateRight(node);
      }

      if (isRed(node.left) && isRed(node.left.left)) { //(fall 3 folie 12)
        node = rotateRight(node);
        node.color = BLACK;
        node.right.color = RED;
      }
    } else { //insertion on right side of parent node
      node.right = internalInsert(node.right, value, RED);

      if (isRed(node) && isRed(node.right) && !isRed) { //condition for left-rotation
        node = rotateLeft(node);
      }

      if (isRed(node.right) && isRed(node.right.right)) { //(fall 3 folie 12)
        node = rotateLeft(node);
        node.color = BLACK;
        node.left.color = RED;
      }
    }
    return node;
  }

  //gets called by Main.java to perform the internal insertion of the node into the tree
  public void insert(int value) {
    this.root = internalInsert(this.root, value, false);

    //root always has to be black (rule 2)
    this.root.color = BLACK;
  }

  public void printDotGraph() throws IOException {
    //preparing content of dot file for export
    String output = "digraph g0 {\nnode [height=.1, style=filled];\n";
    output += printDotNode(root); //calling conversion magic for converting tree into dot file
    output += "}";
    writeDotFile(output);
  }

  private String printDotNode(Node node) {
    String output = "";

    //building output string in dot format
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

    //create file in folder
    outputFile.createNewFile();
    FileWriter writer = new FileWriter(outputFile);

    writer.write(data);
    writer.flush();
    writer.close();
  }
}
