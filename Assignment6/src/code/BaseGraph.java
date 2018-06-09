package code;

import given.iGraph;

import java.util.HashMap;

class inVertex<V>{
    private V element;
    private HashMap<Vertex<V>,Edge<V>> outgoing,incoming;

    public inVertex(V element,boolean isDirected){
      this.element = element;
      outgoing = new HashMap<>();
      if(isDirected){
          incoming = new HashMap<>();
      }else{ incoming = outgoing; }
    }
    public V getElement(){ return element;}
    public void setPosition(){}
    public Map<Vertex<V>,Edge<E>>
}
class Edge<E>{

}
class Vertex<V>{

}
public abstract class BaseGraph<V> implements iGraph<V>{

/*
 * Fill as you like!
 *   
 */
  

}
