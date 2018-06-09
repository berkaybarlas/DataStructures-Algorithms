package code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/*
 * The class that will hold your graph algorithm implementations
 * Implement:
 * - Depth first search
 * - Breadth first search
 * - Dijkstra's single-source all-destinations shortest path algorithm
 * 
 * Feel free to add any addition methods and fields as you like
 */
public class GraphAlgorithms<V extends Comparable<V>> {
  
  /*
   * YOU CAN ADD ANY FIELDS AND ADDITIONAL METHODS AS YOU LIKE
   * 
   */
  
  public static boolean usageCheck = false;
  
  /*
   * WARNING: MUST USE THIS FUNCTION TO SORT THE 
   * NEIGHBORS (the adjacent call in the pseudocodes)
   * FOR DFS AND BFS
   * 
   * THIS IS DONE TO MAKE AUTOGRADING EASIER
   */
  public Iterable<V> iterableToSortedIterable(Iterable<V> inIterable) {
    usageCheck = true;
    List<V> sorted = new ArrayList<>();
    for (V i : inIterable) {
      sorted.add(i);
    }
    Collections.sort(sorted);
    return sorted;
  }
  
  /*
   * Runs depth first search on the given graph G and
   * returns a list of vertices in the visited order, 
   * starting from the startvertex.
   * 
   */
  public List<V> DFS(BaseGraph<V> G, V startVertex) {
    usageCheck = false;
    //TODO
    return null;
  }
  
  /*
   * Runs breadth first search on the given graph G and
   * returns a list of vertices in the visited order, 
   * starting from the startvertex.
   * 
   */
  public List<V> BFS(BaseGraph<V> G, V startVertex) {
    usageCheck = false;
    //TODO
    return null;
  }
  
  /*
   * Runs Dijkstras single source all-destinations shortest path 
   * algorithm on the given graph G and returns a map of vertices
   * and their associated minimum costs, starting from the startvertex.
   * 
   */
  public HashMap<V,Float> Dijkstras(BaseGraph<V> G, V startVertex) {
    usageCheck = false;
    //TODO
    return null;
  }
  
  /*
   *  Returns true if the given graph is cyclic, false otherwise
   */
  public boolean isCyclic(BaseGraph<V> G) {
    //TODO
    return false;
  }

}
