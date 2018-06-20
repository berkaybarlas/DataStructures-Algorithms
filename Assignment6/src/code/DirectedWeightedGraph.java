package code;

public class DirectedWeightedGraph<V> extends BaseGraph<V>  {

  /*
   * YOU CAN ADD ANY FIELDS AND ADDITIONAL METHODS AS YOU LIKE
   * 
   */
  public DirectedWeightedGraph(){
    super(true,true);
  }

  @Override
  public String toString() {
    String tmp = "Directed Weighted Graph";
    return tmp;
  }

}
