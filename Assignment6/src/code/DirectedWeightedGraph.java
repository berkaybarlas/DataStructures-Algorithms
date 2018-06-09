package code;

public class DirectedWeightedGraph<V> extends BaseGraph<V>  {

  /*
   * YOU CAN ADD ANY FIELDS AND ADDITIONAL METHODS AS YOU LIKE
   * 
   */
  
  @Override
  public String toString() {
    String tmp = "Directed Weighted Graph";
    return tmp;
  }

  @Override
  public void insertVertex(V v) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public V removeVertex(V v) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean areAdjacent(V v1, V v2) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void insertEdge(V source, V target) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void insertEdge(V source, V target, float weight) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean removeEdge(V source, V target) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public float getEdgeWeight(V source, V target) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int numVertices() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public Iterable<V> vertices() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int numEdges() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public boolean isDirected() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isWeighted() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public int outDegree(V v) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int inDegree(V v) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public Iterable<V> outgoingNeighbors(V v) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Iterable<V> incomingNeighbors(V v) {
    // TODO Auto-generated method stub
    return null;
  }
}
