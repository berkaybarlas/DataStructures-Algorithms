package code;

import java.util.*;

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
    List<V> DFSlist = new LinkedList<>();
    Vertex<V> v = G.findVertex(startVertex);
    Stack<Vertex<V>> S = new Stack<>();
    S.push(v);
    while(!S.empty()){
      Vertex<V> u = S.pop();
      if(!u.isVisited()){
        u.visit();
        DFSlist.add(u.getElement());
        for(V w : iterableToSortedIterable(u.adjacent() )){
          Vertex<V> vertexW = G.findVertex(w);
          if(vertexW!=null && !vertexW.isVisited())
            S.push(vertexW);
        }
      }
    }
    return DFSlist;
  }
  
  /*
   * Runs breadth first search on the given graph G and
   * returns a list of vertices in the visited order, 
   * starting from the startvertex.
   * 
   */
  public List<V> BFS(BaseGraph<V> G, V startVertex) {
    usageCheck = false;
    List<V> BFSlist = new LinkedList<>();
    Vertex<V> v = G.findVertex(startVertex);
    Deque<Vertex<V>> S = new ArrayDeque<>();
    S.addLast(v);
    while(!S.isEmpty()){
      Vertex<V> u = S.removeFirst();
      if(!u.isVisited()){
        u.visit();
        BFSlist.add(u.getElement());
        for(V w : iterableToSortedIterable(u.adjacent() )){
          Vertex<V> vertexW = G.findVertex(w);
          if(vertexW!=null && !vertexW.isVisited())
            S.addLast(vertexW);
        }
      }
    }
    return BFSlist;
  }
  
  /*
   * Runs Dijkstras single source all-destinations shortest path 
   * algorithm on the given graph G and returns a map of vertices
   * and their associated minimum costs, starting from the startvertex.
   * 
   */
  public HashMap<V,Float> Dijkstras(BaseGraph<V> G, V startVertex) {
    usageCheck = false;
    HashMap<V,Float> dijkstrasMap = new HashMap<>();
    Vertex<V> v = G.findVertex(startVertex);
    v.cost = 0;
    Queue<Vertex<V>> S = new PriorityQueue<>();
    S.add(v);
    while(!S.isEmpty()){
      Vertex<V> u = S.poll();
      if(!u.isVisited()){
        u.visit();
        dijkstrasMap.put(u.getElement(),u.cost);
        for(V w : iterableToSortedIterable(u.adjacent())){
          Vertex<V> vertexW = G.findVertex(w);
          if(vertexW!=null && !vertexW.isVisited() && vertexW.cost> u.cost + G.getEdgeWeight(u.element,w)){
            vertexW.cost = u.cost + G.getEdgeWeight(u.element,w);
            vertexW.parent = u;
            S.add(vertexW);
          }
        }
      }
    }
    return dijkstrasMap;
  }
  
  /*
   *  Returns true if the given graph is cyclic, false otherwise
   */
  public boolean isCyclic(BaseGraph<V> G) {
    //Vertex<V> v = G.getVertices().getFirst();
    //if(v == null) return false;
    //if(!G.isDirected()) return true;
    for(Vertex<V> v : G.getVertices()){
      for(Vertex<V> v1 : G.getVertices()){
        v1.unVisit();
      }
      Stack<Vertex<V>> S = new Stack<>();
      S.push(v);
      while(!S.empty()){
        Vertex<V> u = S.pop();
        if(!u.isVisited()){
          u.visit();
          for(V w : iterableToSortedIterable(u.adjacent() )){
            Vertex<V> vertexW = G.findVertex(w);
            if(vertexW!=null && vertexW.isVisited() && (u.parent ==null || !u.parent.equals(vertexW))) {
              if (!S.contains(vertexW)) return true;
            }
            if(vertexW!=null && !vertexW.isVisited()) {
              vertexW.parent = u;
              S.push(vertexW);
            }
          }
        }
      }
    }

    return false;
  }

}
