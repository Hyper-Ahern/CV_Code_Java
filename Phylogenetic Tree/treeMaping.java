/** Name: Treebuilder.java
 *  Author: James A'Hern
 *  Purpose: This class builds a phylogenetic tree from the four basic building
 *           blocks of life. It uses a general tree to do this.
 *  Desc: This algorithm creates a tree class and the corresponding methods to
 *        create trees. Then it takes in scanner data, sorts it in an arraylist,
 *        uses that arraylist to create a new tree for every data, and finally
 *        it puts the resulting trees in the proper order based on being or not
 *        being a descendant of any of the others. It then prints the tree out
 *        in a formatted fashion to result in a genetic tree of descendants in
 *        alphabetical order.
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class TreeBuilder {

  public static void main(String [] args) {
    Scanner in = new Scanner(System.in);
    ArrayList <String> list = new ArrayList<>();
    Tree rootTree = new Tree();
    rootTree.setData("*");
    String check = in.next();

    // Populating an arraylist with scanner data then sorting it
    while ( ! (check.equals("done"))) {
      list.add(check);
      check = in.next();
    }

    Collections.sort(list);

    /* Creating a new tree for each piece of data recieved from the scanner,
     * then it is calling the insert function to place it in the tree properly
     */
    for (int i = 0; i < list.size(); i++) {
      String sequence = list.get(i);
      Tree t = new Tree();
      t.setData(sequence);
      insert(t, rootTree);
    }

    // Calling the print function to print the tree in pre-order format
    print(rootTree, 0);
  }

  /* This method takes in a new node and the original root tree. It then checks
   * to see if the root tree is empty. If so it puts the new tree in. If not
   * it calls the insertTree method.
   */
  public static void insert(Tree t, Tree rootTree){
    if (rootTree.childrenSize() == 0) {
      rootTree.setChild(t);
      t.setParent(rootTree);
    } else {
      insertTree(t,rootTree);
    }
  }

  /* This function takes in two parameters: a new tree and the root node. It
   * then iterates through the root node's children looking for a child with
   * data that matches it's own. If it finds it, it recursively calls itself
   * to pass in that matching node as the new "root tree" to find where the
   * new tree belongs in terms of depth in the tree. Once it finds where it is
   * supposed to be, it sets the parent and the child accordingly.
   */
  public static void insertTree(Tree t, Tree rootTree) {
    if (rootTree.childrenSize() == 0) {
      rootTree.setChild(t);
      t.setParent(rootTree);
      return;
    }

    // If current node matches start of one being examined call insertTree()
    for (int i = 0; i < rootTree.childrenSize(); i++) {
      if (t.getData().startsWith(rootTree.getChildAt(i).getData())) {
        insertTree(t, rootTree.getChildAt(i));
        return;
      }
    }

    t.setParent(rootTree);
    rootTree.setChild(t);
  }

  /* This method takes in the root node and a depth then recursively calls
   * itself to pre-order print each tree node in the appropriate "tree-looking"
   * format. It calls the depthPrinter function to continuously track depth.
   */
  public static void print(Tree rootTree, int depth) {
    depthPrinter(depth);
    System.out.println(rootTree.getData());
    for (Tree i : rootTree.getChildren()) {
      print(i, depth + 1);
    }
  }

  /* This method keeps track of the current node's depth and works with the
   * print method to print the proper amounts of spaces, lines, and dashes to
   * get it to the required format.
   */
  public static void depthPrinter(int depth) {
    if ( ! (depth == 0)) {
      for (int j = 0; j < depth-1; j++) {
        System.out.print("| ");
      }

      System.out.print("|-");
    }
  }

  /* This class allows trees to be built and stored in arraylists. It keeps
   * track of all of a node's (which is a tre itself) children and it's parent.
   */
  public static class Tree {

    private String data;
    private Tree parent;
    private ArrayList<Tree> children = new ArrayList<>();

    public Tree() {
      parent = null;
      data = null;
    }

    // Adds a new child to the node
    public void setChild(Tree t) {
      this.children.add(t);
    }

    // Sets the data of the current node calling it
    public void setData(String s) {
      this.data = s;
    }

    // Returns the data of the current node calling this method
    public String getData() {
      return this.data;
    }

    // Returns the "i-th" child of the node
    public Tree getChildAt(int child) {
      return this.children.get(child);
    }

    // Returns the entire arraylist of children of the node
    public ArrayList<Tree> getChildren() {
      return children;
    }

    // Retruns the number of children this node has in int form
    public int childrenSize() {
      if (this.children.isEmpty()) {
        return 0;
      } else {
        return this.children.size();
      }
    }

    // Sets the parent to the appropriate node
    public void setParent(Tree t) {
      this.parent = t;
    }
  }
}
