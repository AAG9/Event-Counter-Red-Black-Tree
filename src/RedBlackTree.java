/**
 * Created by ameya on 3/18/16.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class RedBlackTree {

    private int inRangeCount;
    private Node previous;
    private Node next;

    public static Node root;     // root of the BST

    private static final boolean RED   = true;
    private static final boolean BLACK = false;

    // RBT helper node class
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
    }

    /***************************************************************************
     *  Red-black tree creation.
     ***************************************************************************/


    /**
     * Creates Red Black Tree from sorted array. Each node is colored black
     * @param arr
     * @param start
     * @param end
     * @return the root of the RBT
     */

    public Node createRBT(String[] arr, int start, int end){

        //Base Case
        if (start > end) {
            return null;
        }

        // Get the middle element and make it root
        int mid = (start + end) / 2;

        String[] input = arr[mid].split(" ");
        int id = Integer.parseInt(input[0]);
        int count = Integer.parseInt(input[1]);
        Node node = new Node(id,count,BLACK);

        // Recursively construct the left subtree and make it left child of root
        node.left = createRBT(arr, start, mid - 1);

        // Recursively construct the right subtree and make it right child of root
        node.right = createRBT(arr, mid + 1, end);

        return node;
    }


    /**
     * Returns the deepest nodes.
     * @param root
     * @return the list of deepest nodes in the RBT
     */

    public static List findDeepestNodes(Node root)
    {
        Object[] levelNodes = new Object[2];
        levelNodes[0] = 0;
        levelNodes[1] = new ArrayList();
        findDeepestNodes(root, 1, levelNodes);
        return (List) levelNodes[1];
    }


    /**
     * Finds the deepest nodes in Red-black tree.
     * @param root
     * @param level
     * @param levelNodes
     * @return the list of deepest nodes in the RBT
     */

    private static void findDeepestNodes(Node root, int level,
                                         Object[] levelNodes)
    {
        if (root == null)
            return;
        if((Integer)levelNodes[0]<=level)
        {
            if((Integer)levelNodes[0] < level)
                ((List)levelNodes[1]).clear();
            levelNodes[0]=level;
            ((List)levelNodes[1]).add(root);
        }
        findDeepestNodes(root.left, level+1, levelNodes);
        findDeepestNodes(root.right, level+1, levelNodes);
    }


    /**
     * Change the color of the deepest nodes to RED to satisfy the condition of the Red Black Tree
     * @param deepNodes the lis of deepNodes
     */

    public void changeColorToRed(List<Node> deepNodes){
        for(Node node : deepNodes)
            node.nodeColor=RED;
    }


    /***************************************************************************
     *  Red-black tree insertion.
     ***************************************************************************/


    /**
     * calls the insert method to insert a node
     * @param id the eventID
     * @param count the event count
     */

    public void insert(int id, int count) {
        root = insert(root, id, count);
        root.nodeColor = BLACK;
    }


    /**
     * Insert the eventID and count in the tree rooted at node
     * @param id
     * @param count
     * @param node the root of the RBT
     * @return the root of the RBT
     */

    private Node insert(Node node, int id, int count) {
        if (node == null) return new Node(id, count, RED);

        if(id < node.id)
            node.left  = insert(node.left,  id, count);
        else if (id > node.id)
            node.right = insert(node.right, id, count);
        else
            node.count   = count;

        // fix-up any right-leaning links
        if (isRed(node.right) && !isRed(node.left))
            node = rotateLeft(node);
        if (isRed(node.left)  &&  isRed(node.left.left))
            node = rotateRight(node);
        if (isRed(node.left)  &&  isRed(node.right))
            colorsFlip(node);

        return node;
    }


    /***************************************************************************
     *  Node helper methods.
     ***************************************************************************/


    /**
     * Verifies if node is RED or BLACK
     * @param node
     * @return true if parameterised node is RED and false otherwise
     */

    private boolean isRed(Node node) {
        if (node == null) return false;
        return node.nodeColor == RED;
    }


    /**
     * Checks whether RBT is empty
     * @return true if this symbol table is empty and false otherwise
     */

    public boolean isEmpty() {
        return root == null;
    }


    /***************************************************************************
     *  Red-black tree helper methods.
     ***************************************************************************/


    /**
     * make a left-leaning link lean to the right
     * @param h
     * @return x
     */

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.nodeColor = x.right.nodeColor;
        x.right.nodeColor = RED;
        return x;
    }


    /**
     * make a right-leaning link lean to the left
     * @param h
     * @return x
     */

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.nodeColor = x.left.nodeColor;
        x.left.nodeColor = RED;
        return x;
    }

    /**
     * Flip the colors of the nodes
     * @param h
     */

    private void colorsFlip(Node h) {
        h.nodeColor = !h.nodeColor;
        h.left.nodeColor = !h.left.nodeColor;
        h.right.nodeColor = !h.right.nodeColor;
    }


    /**
     * Assuming that h is red and both h.left and h.left.left are black, make h.left or one of its children red.
     * @param h
     */

    private Node moveRedLeft(Node h) {
        colorsFlip(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            colorsFlip(h);
        }
        return h;
    }


    /**
     * Assuming that h is red and both h.right and h.right.left are black, make h.right or one of its children red.
     * @param h
     * @return h
     */

    private Node moveRedRight(Node h) {
        colorsFlip(h);
        if (isRed(h.left.left)) {
            h = rotateRight(h);
            colorsFlip(h);
        }
        return h;
    }


    /**
     * restore red-black tree invariant.
     * @param h
     * @return h
     */

    private Node balance(Node h) {
        // assert (h != null);

        if (isRed(h.right))                      h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))     colorsFlip(h);
        return h;
    }


    /***************************************************************************
     *  Red-black tree deletion.
     ***************************************************************************/


    /**
     * the smallest key in subtree rooted at node; null if no such key
     * @param node
     */

    private Node min(Node node) {
        if (node.left == null)
            return node;
        else
            return min(node.left);
    }


    /**
     * delete the eventID and count with the minimum key rooted at h
     * @param h
     */

    private Node deleteMin(Node h) {
        if (h.left == null)
            return null;

        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = deleteMin(h.left);
        return balance(h);
    }


    /**
     * Removes the specified eventid and its associated count from RBT
     * (if the key is in this symbol table)
     * @param  id
     */

    public void delete(int id) {

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.nodeColor = RED;

        root = delete(root, id);
        if (!isEmpty()) root.nodeColor = BLACK;
    }


    /**
     * delete the key-value pair with the given key rooted at h
     * (if the key is in this symbol table)
     * @param  id
     * @param h
     */

    private Node delete(Node h, int id) {

        if (id < h.id)  {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, id);
        }
        else {
            if (isRed(h.left))
                h = rotateRight(h);
            if (id==h.id && (h.right == null))
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (id == h.id) {
                Node x = min(h.right);
                h.id = x.id;
                h.count = x.count;
                h.right = deleteMin(h.right);
            }
            else h.right = delete(h.right, id);
        }
        return balance(h);
    }


    /***************************************************************************
     *  Methods for main project commands
     ***************************************************************************/


    /**
     * Returns the count of theID. If not present, return 0.
     * @param id
     * @return the count associated with the given event id if the id is in the symbol table
     *     and 0 if the key is not in the symbol table
     */

    public int count(int id) {
        return count(root, id);
    }


    /**
     * value associated with the given event id in tree rooted at x; 0 if no such event id
     * @param id
     * @param x
     * @return the count associated with the given event id if the id is in the symbol table
     *     and 0 if the key is not in the symbol table
     */

    private int count(Node x, int id) {
        while (x != null) {

            if      (id < x.id) x = x.left;
            else if (id > x.id) x = x.right;
            else              return x.count;
        }
        return 0;
    }


    /**
     * Increase the count of the event theID by m. If theID is not present, insert it. Print the count
     * of theID after the addition.
     * @param id
     * @param increment
     * @return the increment
     */

    public int increase(int id, int increment) {
        Node x = root;
        while (x != null) {
            if      (id < x.id) x = x.left;
            else if (id > x.id) x = x.right;
            else {
                x.count = x.count + increment;
                return x.count;
            }
        }
        insert(id, increment);
        return increment;
    }


    /**
     * Decrease the count of theID by m. If theID’s count becomes less than or equal to 0, remove
     * theID from the counter. Print the count of theID after the deletion, or 0 if theID is
     * removed or not present.
     * @param id
     * @param decrement
     * @return the decrement
     */

    public int reduce(int id, int decrement){
        Node x = root;
        while (x != null) {
            if      (id < x.id) x = x.left;
            else if (id > x.id) x = x.right;
            else {
                x.count = x.count - decrement;
                if(x.count>0) return x.count;
                else{
                    delete(x.id);
                    return 0;
                }
            }
        }
        return 0;
    }


    /*  */

    /**
     * The functions returns the total of count events which is in the given range [id1..id2].
     * The function assumes than id1 < id2
     * @param id1
     * @param id2
     * @return total count
     */

    public int inRange(int id1, int id2){
        inRangeCount = 0;
        return inRange(root,id1,id2);
    }


    /**
     * Return the total count for IDs between ID1 and
     * ID2 inclusively. Note, ID1 ≤ ID2
     * @param id1
     * @param id2
     * @param node
     * @return total count
     */

    public int inRange(Node node, int id1, int id2) {

        int count=0;
        /* base case */
        if (node == null) {
            return 0;
        }

        /* Since the desired o/p is sorted, recurse for left subtree first
         If node.id is greater than id1, then only we can get o/p count
         in left subtree */
        if (id1 < node.id) {
            inRange(node.left, id1, id2);
        }

        /* if root's data lies in range, then prints root's data */
        if (id1 <= node.id && id2 >= node.id) {
            inRangeCount += node.count;
        }

        /* If node.id is smaller than id2, then only we can get o/p count
         in right subtree */
        if (id2 > node.id) {
            inRange(node.right, id1, id2);
        }

        return inRangeCount;
    }

    /**Print the ID and the count of the event with
     * the lowest ID that is greater that theID. Print
     * “0 0”, if there is no next ID.
     * @param id
     */

    public void findNext(int id){
        next = null;
        next(root,id);
        if(next == null)
            System.out.println("0 0");
        else
            System.out.println(next.id +" "+next.count);
    }


    /**Print the ID and the count of the event with
     * the lowest ID that is greater that theID. Print
     * “0 0”, if there is no next ID.
     * @param id
     * @param node
     */

    private void next(Node node, int id){
        // Base case
        if (node== null)  return ;

        // If key is present at root
        if (node.id == id)
        {
            // the minimum value in right subtree is successor
            if (node.right != null)
            {
                Node temp = node.right ;
                while (temp.left != null)
                    temp = temp.left ;
                next = temp;
            }

        }

        // If key is smaller than root's key, go to left subtree
        else if (node.id > id)
        {
            next = node;
            next(node.left, id) ;
        }
        else // go to right subtree
        {
            next(node.right,id) ;
        }
    }

    /**Print the ID and the count of the event with O(log n)
     * the greatest key that is less that theID. Print
     * “0 0”, if there is no previous ID.
     * @param id
     */

    public void findPrevious(int id){
        previous = null;
        previous(root,id);
        if(previous == null)
            System.out.println("0 0");
        else
            System.out.println(previous.id +" "+previous.count);
    }


    /**Print the ID and the count of the event with O(log n)
     * the greatest key that is less that theID. Print
     * “0 0”, if there is no previous ID.
     * @param id
     */

    private void previous(Node node, int id){
        // Base case
        if (node== null)  return ;

        // If key is present at root
        if (node.id == id)
        {
            // the maximum value in left subtree is predecessor
            if (node.left != null)
            {
                Node temp = node.left;
                while (temp.right != null)
                    temp = temp.right;
                previous = temp ;
            }

        }

        // If key is smaller than root's key, go to left subtree
        else if (node.id > id)
        {
            previous(node.left, id) ;
        }
        else // go to right subtree
        {
            previous = node ;
            previous(node.right,id) ;
        }

    }



    /***************************************************************************
     *  Red Black Tree test methods.
     ***************************************************************************/


    /* Test methods to print the Red Black Tree in order
     */

    private void inOrder(Node node){

        if(node!= null) {
            inOrder(node.left);
            System.out.print(node.id + " ");
            System.out.print(node.count + " ");
            System.out.print(node.nodeColor + " ");
            inOrder(node.right);
        }
    }


    private void printInOrder(){
        inOrder(root);
    }

    /* Test method to find the root of the RBT
    * @return root of RBT
    */

    private boolean rootdata(){
        return root.nodeColor;
    }

    /**
     * Unit tests the RedBlackTree class.
     */
   /* public static void main(String[] args) {
        RedBlackTree st = new RedBlackTree();

        st.insert(3,2);
        st.insert(6,4);
        st.insert(8,3);

        st.readInputFile();

        //st.printRoot();

        System.out.println(st.increase(350,100));

        st.printRoot();

        System.out.println(st.reduce(350,50));

        st.printRoot();

        System.out.println();
        System.out.println(st.count(350));

        System.out.println("Inrangest ids:" +st.inRange(200,299));
    }*/
}
