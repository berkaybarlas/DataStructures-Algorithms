package code;

public class UndirectedWeightedGraph<V> extends BaseGraph<V> {

  /*
   * YOU CAN ADD ANY FIELDS AND ADDITIONAL METHODS AS YOU LIKE
   * 
   */
  public UndirectedWeightedGraph(){
    super(false,true);
  }

  @Override
  public String toString() {
    String tmp = "Undirected Weighted Graph";
    return tmp;
  }

}
