//Node for the Red-Black-Tree
class RedBlackNode {
    RedBlackNode leftChild, rightChild;
    int element; //Value of Node
    int color; //color = 0 -> red, color = 1 -> black

    //constructor to set the value of a node having no left and right child  
    public RedBlackNode(int element) {
        this(element, null, null);
    }

    //constructor to set value of element, leftChild, rightChild and color  
    public RedBlackNode(int element, RedBlackNode leftChild, RedBlackNode rightChild) {
        this.element = element;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        color = 1;
    }
}

//create class CreateRedBlackTree for creating red-black tree  
class CreateRedBlackTree {
    private static RedBlackNode nullNode;   //define null node
    private RedBlackNode current;   //define current node   
    private RedBlackNode parent;    //define parent node   
    private RedBlackNode root;   // define header node
    private RedBlackNode grand; //define grand node  
    private RedBlackNode great; //define great node  

    //constants RED and BLACK for nodecolors
    static final int RED   = 0;
    static final int BLACK = 1;


    //set attributes of the nullNode even before the main method is executed
    static
    {
        nullNode = new RedBlackNode(0);
        nullNode.leftChild = nullNode;
        nullNode.rightChild = nullNode;
    }


    // Constructor for creating root node
    public CreateRedBlackTree(int root) {
        this.root = new RedBlackNode(root);
        this.root.leftChild = nullNode;
        this.root.rightChild = nullNode;
    }


    //method for adding a new node in the red black tree
    public void insertNewNode(int newElement) {
        current = parent = grand = root;      //set header value to current, parent, and grand node
        nullNode.element = newElement;          //set newElement to the element of the null node

        //repeat statements until the element of the current node will not equal to the value of the newElement  
        while (current.element != newElement) {
            great = grand;
            grand = parent;
            parent = current;

            //if the value of the newElement is lesser than the current node element, the current node will point to the current left child else point to the current right child.  
            current = newElement < current.element ? current.leftChild : current.rightChild;

            // Check whether both the children are RED or NOT. If both the children are RED change them by using handleColors() method          
            if (current.leftChild.color == RED && current.rightChild.color == RED)
                handleColors( newElement );
        }

        // insertion of the new node will fail if already present in the tree
        if (current != nullNode)
            return;

        //create a node having no left and right child and pass it to the current node   
        current = new RedBlackNode(newElement, nullNode, nullNode);

        //connect the current node with the parent    
        if (newElement < parent.element)
            parent.leftChild = current;
        else
            parent.rightChild = current;
        handleColors( newElement );
    }

    //create handleColors() method to maintain the colors of Red-black tree nodes  
    private void handleColors(int newElement)
    {
        // flip the colors of the node  
        current.color = RED;    //make current node RED  
        current.leftChild.color = BLACK;    //make leftChild BLACK  
        current.rightChild.color = BLACK;   //make rightChild BLACK  

        //check the color of the parent node  
        if (parent.color == RED)
        {
            // perform rotation in case when the color of parent node is RED  
            grand.color = RED;

            if (newElement < grand.element && grand.element != newElement && newElement < parent.element)
                parent = performRotation( newElement, grand );  // Start dbl rotate  
            current = performRotation(newElement, great );
            current.color = BLACK;
        }

        // change the color of the root node with BLACK  
        root.rightChild.color = BLACK;
    }

    //create performRotation() method to perform dbl rotation  
    private RedBlackNode performRotation(int newElement, RedBlackNode parent)
    {
        //check whether the value of the newElement is lesser than the element of the parent node or not  
        if(newElement < parent.element)
            //if true, perform the rotation with the left child and right child based on condition and set return value to the left child of the parent node  
            return parent.leftChild = newElement < parent.leftChild.element ? rotationWithLeftChild(parent.leftChild) : rotationWithRightChild(parent.leftChild) ;
        else
            //if false, perform the rotation with the left child and right child based on condition and set return value to the right child of the parent node  
            return parent.rightChild = newElement < parent.rightChild.element ? rotationWithLeftChild(parent.rightChild) : rotationWithRightChild(parent.rightChild);
    }

    //create rotationWithLeftChild() method  for rotating binary tree node with left child   
    private RedBlackNode rotationWithLeftChild(RedBlackNode node2)
    {
        RedBlackNode node1 = node2.leftChild;
        node2.leftChild = node1.rightChild;
        node1.rightChild = node2;
        return node1;
    }

    // create rotationWithRightChild() method for rotating binary tree node with right child  
    private RedBlackNode rotationWithRightChild(RedBlackNode node1)
    {
        RedBlackNode node2 = node1.rightChild;
        node1.rightChild = node2.leftChild;
        node2.leftChild = node1.leftChild;
        return node2;
    }

    // create nodesInTree() method for getting total number of nodes in a tree  
    public int nodesInTree()
    {
        return nodesInTree(root.rightChild);
    }
    private int nodesInTree(RedBlackNode node)
    {
        if (node == nullNode)
            return 0;
        else
        {
            int size = 1;
            size = size + nodesInTree(node.leftChild);
            size = size + nodesInTree(node.rightChild);
            return size;
        }
    }
    // create searchNode() method to get desired node from the Red-Black tree  
    public boolean searchNode(int value)
    {
        return searchNode(root.rightChild, value);
    }
    private boolean searchNode(RedBlackNode node, int value)
    {
        boolean check = false;
        while ((node != nullNode) && check != true)
        {
            int nodeValue = node.element;
            if (value < nodeValue)
                node = node.leftChild;
            else if (value > nodeValue)
                node = node.rightChild;
            else
            {
                check = true;
                break;
            }
            check = searchNode(node, value);
        }
        return check;
    }

    //create preorderTraversal() method to perform inorder traversal  
    public void preorderTraversal()
    {
        preorderTraversal(root.rightChild);
    }
    private void preorderTraversal(RedBlackNode node)
    {
        if (node != nullNode)
        {
            char c = 'R';
            if (node.color == 1)
                c = 'B';
            System.out.print(node.element +""+c+" ");
            preorderTraversal(node.leftChild);
            preorderTraversal(node.rightChild);
        }
    }

    //create inorderTraversal() method to perform inorder traversal   
    public void inorderTraversal()
    {
        inorderTraversal(root.rightChild);
    }
    private void inorderTraversal(RedBlackNode node)
    {
        if (node != nullNode)
        {
            inorderTraversal(node.leftChild);
            char c = 'R';
            if (node.color == 1)
                c = 'B';
            System.out.print(node.element +""+c+" ");
            inorderTraversal(node.rightChild);
        }
    }

    //create postorderTraversal() method to perform inorder traversal   
    public void postorderTraversal()
    {
        postorderTraversal(root.rightChild);
    }
    private void postorderTraversal(RedBlackNode node)
    {
        if (node != nullNode)
        {
            postorderTraversal(node.leftChild);
            postorderTraversal(node.rightChild);
            char c = 'R';
            if (node.color == 1)
                c = 'B';
            System.out.print(node.element +""+c+" ");
        }
    }
}

//create class RedBlackTreeExample having main() method  

/*
class RedBlackTreeExample
{
    //main() method start  
    public static void main(String[] args)
    {
        //create instance of the Scanner class  
        Scanner scan = new Scanner(System.in);

        //Create instance of the createRedBlackTree class and pass minimum integer value to the constructor to make it header node  
        CreateRedBlackTree obj = new CreateRedBlackTree(Integer.MIN_VALUE);
        char choice;

        //create switch case for performing the operation in Red Black Tree  
        do
        {
            System.out.println("\nSelect an operation:\n");
            System.out.println("1. Insert a node ");
            System.out.println("2. Search a node");
            System.out.println("3. Get total number of nodes in Red Black Tree");
            System.out.println("4. Is Red Black Tree empty?");
            System.out.println("5. Remove all nodes from Red Black Tree");
            System.out.println("6. Display Red Black Tree in Post order");
            System.out.println("7. Display Red Black Tree in Pre order");
            System.out.println("8. Display Red Black Tree in In order");

            //get choice from user  
            int ch = scan.nextInt();
            switch (ch)
            {
                case 1 :
                    System.out.println("Please enter an element to insert in Red Black Tree");
                    obj.insertNewNode( scan.nextInt() );
                    break;
                case 2 :
                    System.out.println("Enter integer element to search");
                    System.out.println(obj.searchNode( scan.nextInt() ));
                    break;
                case 3 :
                    System.out.println(obj.nodesInTree());
                    break;
                case 4 :
                    System.out.println(obj.checkEmpty());
                    break;
                case 5 :
                    obj.removeAll();
                    System.out.println("\nTree Cleared successfully");
                    break;
                case 6 :
                    System.out.println("\nDisplay Red Black Tree in Post order");
                    obj.postorderTraversal();
                    break;
                case 7 :
                    System.out.println("\nDisplay Red Black Tree in Pre order");
                    obj.preorderTraversal();
                    break;
                case 8 :
                    System.out.println("\nDisplay Red Black Tree in In order");
                    obj.inorderTraversal();
                    break;
                default :
                    System.out.println("\n ");
                    break;
            }
            System.out.println("\nPress 'y' or 'Y' to continue \n");
            choice = scan.next().charAt(0);
        } while (choice == 'Y'|| choice == 'y');
    }
}
*/