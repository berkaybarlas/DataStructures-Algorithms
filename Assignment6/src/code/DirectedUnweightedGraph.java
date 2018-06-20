package code;

public class DirectedUnweightedGraph<V> extends BaseGraph<V> {
  
  /*
   * YOU CAN ADD ANY FIELDS AND ADDITIONAL METHODS AS YOU LIKE
   * 
   */
  public DirectedUnweightedGraph(){
    super(true);
  }
  @Override
  public String toString() {
    String tmp = "Directed Unweighted Graph";
    return tmp;
  }

}
