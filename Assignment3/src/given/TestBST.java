package given;

import java.util.Comparator;
import java.util.List;

import code.BinarySearchTree;
import code.BinaryTreeNode;

public class TestBST {

  public static <T> void printList(List<T> l) {
    StringBuilder sb = new StringBuilder(1000);
    sb.append("[");
    int i;
    for (i = 0; i < l.size() - 1; i++)
      sb.append(l.get(i) + ", ");
    sb.append(l.get(i) + "]");
    System.out.println(sb.toString());
  }

  public static <E extends Entry<K, ?>, K> boolean isSorted(List<E> l, Comparator<K> C) {
    for (int i = 0; i < l.size() - 1; i++) {
      if (C.compare(l.get(i).getKey(), l.get(i + 1).getKey()) > 0) {
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) {
    BinarySearchTree<String, Integer> bt = new BinarySearchTree<String, Integer>();
    BinarySearchTree<Integer, String> test = new BinarySearchTree<Integer, String>();
    bt.setComparator(new DefaultComparator<String>());
    test.setComparator(new DefaultComparator<>(true));
    if(!bt.isEmpty())
      System.out.println("Tree should initially be empty!");

    bt.put("Hello", 50);
    bt.put("B", 4);
    bt.put("A", 2);
    bt.put("D", 54);
    bt.put("F", 9);
    bt.put("C", 58);
    bt.put("E", 12);
    bt.put("H", 5);
    bt.put("I", 36);
    bt.put("J", 96);
    bt.put("K", 60);
    bt.put("L", 28);
    bt.put("M", 44);
    test.put(3,"Mehmet");
    test.put(8,"Ercan");
    test.put(11,"Can");

    System.out.println("Testing BST methods");

    List<BinaryTreeNode<String, Integer>> l;
    
    if (bt.size() != 13) {
      System.out.println("Tree size does not return the correct one.");
    }
    if(bt.height()!=6)
      System.out.println("Tree height is not correct." + bt.height() );
    l = bt.getNodesInOrder();
    if (!isSorted(l, bt.getComparator()))
      System.out.println("In order sequence of a BST should be sorted!");
    printList(l);
    
    if(!bt.getParent(bt.getNode("E")).getKey().equals("F"))
      System.out.println("Either getNode or getParent is not working " + bt.getParent(bt.getNode("E")).getKey());
    
    if(!bt.getLeftChild(bt.getNode("B")).getKey().equals("A"))
      System.out.println("Either getNode or getLeftChild is not working " + bt.getLeftChild(bt.getNode("B")).getKey());
    
    if(!bt.getRightChild(bt.getNode("B")).getKey().equals("D"))
      System.out.println("Either getNode or getRightChild is not working " + bt.getRightChild(bt.getNode("B")).getKey());
    
    if(!bt.isInternal(bt.getNode("M")))
      System.out.println("Either getNode or isInternal is not working " + bt.getNode("M").getValue() );

    if(!bt.sibling(bt.getNode("F")).getKey().equals("C"))
      System.out.println("Either getNode or sibling is not working");
    
    if(!bt.isLeftChild(bt.getNode("B")))
      System.out.println("Either getNode or isLeftChild is not working " + bt.isLeftChild(bt.getNode("B")));
    
    if(!bt.isRightChild(bt.getNode("L")))
      System.out.println("Either getNode or isRightChild is not working" + bt.isRightChild(bt.getNode("L")));

    bt.remove("A");
    bt.remove("D");
    System.out.println("parent node of key k: F " + bt.getNode("F").getParentNode().getKey());

    if(!bt.getRoot().getKey().equals("Hello"))
      System.out.println("Wrong root.");
    bt.remove("Hello");
    l = bt.getNodesInOrder();
    printList(l);
    if(!bt.getRoot().getKey().equals("I"))
      System.out.println("Wrong root. " +bt.getRoot().getKey() );

    bt.remove("M");
    bt.remove("K");
    if (bt.size() != 8) {
      System.out.println("Tree size does not return the correct one.");
    }
    if (bt.remove("Z") != null)
      System.out.println("Trying to delete something that does not exist should return null");
    if (bt.size() != 8) {
      System.out.println("Tree size does not return the correct one.");
    }
    if(bt.height()!=5)
      System.out.println("Tree height is not correct. But this could be due to removal decision");
    l = bt.getNodesInOrder();
    if (!isSorted(l, bt.getComparator()))
      System.out.println("In order sequence of a BST should be sorted!");
    printList(l);
    
    if(bt.isEmpty())
      System.out.println("Tree should not be empty!");
    
    System.out.println("Ceiling test. Match it with the comments");
    System.out.println(bt.ceiling("A")); //B
    System.out.println(bt.ceiling("C")); //C
    System.out.println(bt.ceiling("D")); //E
    System.out.println(bt.ceiling("I")); //I
    System.out.println(bt.ceiling("K")); //L
    System.out.println(bt.ceiling("L")); //L
    System.out.println(bt.ceiling("M")); //null
    System.out.println("Floor test. Match it with the comments");
    System.out.println(bt.floor("A")); //null
    System.out.println(bt.floor("C")); //C
    System.out.println(bt.floor("D")); //C check
    System.out.println(bt.floor("I")); //I
    System.out.println(bt.floor("K")); //J
    System.out.println(bt.floor("L")); //L
    System.out.println(bt.floor("M")); //L
    
    System.out.println();
    System.out.println("This is an incomplete test!");
  }
}
