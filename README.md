# Event-Counter-Red-Black-Tree
Language/Compiler Used:
Developer Environment : Intellij IDEA IDE Java
Version : java version "1.8.0_66"
Java(TM) SE Runtime Environment (build 1.8.0_66-b18)
Java HotSpot(TM) 64-Bit Server VM (build 25.66-b18, mixed mode)

Steps to run the project:
1)make clean
Cleans all the previous .class files
2)make
to compile all .java files

3)java bbst test_100.txt < commands.txt
java bbst test_100.txt < commands.txt > out_100.txt // to store output in out_100.txt

To input the file: test_100000000.txt Provide following arguments :(Need to allocate 8 gb heap size)

java -Xmx8g RedBlackBST test_100000000.txt < commands.txt


Project Structure :
The project contains two java files :bbst.java and RBTree.java.
Bbst.java : contains Main driver program to accept the test input file and create instances of node which are stored in sorted form in array

public void readInputFile(String fileName)

This method is called to create the Red-black tree from the input file. This method also takes commands as input and executes them case by case.


Testfile is of form :
ID1 count1
ID2 count2
...
IDn countn
Assume that IDi < IDi+1 where IDi and counti are positive integers

Commands accepted by program are of the form:
increase 350 100
reduce 350 50
count 350
inrange 300 1000
next 360
quit is used to exit from the program

RedBlackTree.java

1. Node Class to create Red-black tree:

	private class Node {
	    private int id;           // eventID
	    private int count;         // count
	    private Node left, right;  // left and right subtrees links
	    private boolean nodeColor;     // color of the node

	    public Node(int id, int count, boolean color) {
	        this.id = id;
	        this.count = count;
	        this.nodeColor = color;
    }

The node class has attributes event ID, count, link to left and right nodes and the node color which represents Black or Red color.
2. Red-black Tree Methods
1. Red-black Tree creation(O(n))
public Node createRBT(String[] arr, int start, int end)
The createRBT method accepts as an argument, the user input and then converts into an array. The 'start' and 'end' arguments represent the start index and end index of the input array 'arr'. The createRBT method first creates a Binary Search Tree from a sorted input array of event IDs. The event IDs are represented as nodes all colored black. To convert Black BST into a valid RBT following method finds the deepest nodes of the created Black BST to color them Red.
public static List findDeepestNodes(Node root)
private static void findDeepestNodes(Node root, int level, Object[] levelNodes)
The first method in the above two listed method invokes the second method to return the list of deepest nodes.
The second method finds the deepest nodes which do  not have any children nodes.
public void changeColorToRed(List<Node> deepNodes)
The above method takes the list of deepest nodes as an input and changes the color of each nodes to Red one by one.

2. Red-black Tree insertion(O(log n))
public void insert(int id, int count)
The above method invokes the following method which inserts a node in the RBT in the O(log n). This method is called when Increase(int id, int increment) does not find an event ID in the created RBT to increase the event count.
private Node insert(Node node, int id, int count)
This method recursively traverse through the RBT and inserts a node at a position where the RBT is still a Binary Search Tree and To verify the color of the node, it invokes following method.
private boolean isRed(Node node)
The above method checks if the node passed as an input is a Red node or not. It returns true  if the node is a Red otherwise false.
private Node rotateRight(Node h)
The above method makes a left-leaning link which is subrooted at node h lean to the right
private Node rotateLeft(Node h)
The above method makes a right-leaning link which is subrooted at node h lean to the right
private void colorsFlip(Node h)
The above method inverts the color of the node h to Red from Black upon invocation.

3. Red-black Tree deletion

public void delete(int id) 

The above method is called if reduce operation in the main program methods becomes less than or equal to 0; i.e. If the event count of a particular event Id reduces to less than or equal to 0. The delete method is called. This method then invokes the delete method.

private Node delete(Node h, int id)

Delete the event id and count pair with the given key rooted at h (if the key is in this symbol table)

private Node moveRedLeft(Node h)

Assuming that h is red and both h.left and h.left.left are black, make h.left or one of its children red.


private Node moveRedRight(Node h)

Assuming that h is red and both h.right and h.right.left are black, make h.right or one of its children red.

private Node balance(Node h)

restore red-black tree invariant.

4. Increase(theID, m)(O(log n))

public int increase(int id, int increment)

The above method is called when user needs to increase the event count of a particular event id. This method recursively traverse to the event id and increase the event count. If the inputted event id is not found, it is inserted into to RBT using insert method mentioned above.

5. Reduce(theID,m)(O(log n))

public int reduce(int id, int decrement)

The above method is called when user needs to decrease the event count of a particular event id. This method recursively traverse to the event id and decrease the event count. If after reduction the event count becomes less than or equal to 0, it is deleted from the RBT using delete method mentioned above.

6. Count(theID)(O(log n))

public int count(int id) 

private int count(Node x, int id)

Count is performed with the help of Search logic of a Binary search tree. We first look for the node with theID and if it is present, we print the output to the standard output window or print 0 if not found. The function prototypes involved are given below:
7. InRange(ID1, ID2)(O(log n + s))

public int inRange(int Id1, int id2)

private int inRange(Node node, int id1, int id2)

With the use of a recursive call and keep looking in the left and right subtrees to check for nodes that satisfy the conditions id1 <= id of node >= id2. We add the count to a counter when we come across such a node. This process is supported with the help of 2 functions whose prototypes are as given below. inRange just invokes the call by verifying the arguments whereas 2nd inRange actually computes the InRange Count.


8. Next(theID)(O(log n))

public void findNext(int id)

private void next(Node node, int id)

We find the inorder successor to find the next node greater than theID.The algorithm for finding the successor is basically if right subtree is not null find smallest node in right subtree or start from the root and recursively look for the next ID greater than theID. FindNext invokes the 'next' method which actually finds the successor.

9. Previous(theID)(O(log n))
public void findPrevious(int id)

private void previous(Node node, int id)

Finding the previous node is similar to finding the inorder successor. Difference in the algorithm is that we find the biggest node in the left subtree if its note empty otherwise we keep looking recursively in either of the trees.

Results :
Tested the program on following input files :
test_100, test_1000000, test_10000000, test_10000000
For the input with more than 10^8 data ,the memory allocated to heap had to be increased so that it did not run out of memory.(java -Xmx8g ).
All the methods as implemented in given time complexity.

References :

1. Introduction to Algorithms 3rdEdition
2. Algorithms, 4th Edition by Robert Sedgewick - Princeton University

