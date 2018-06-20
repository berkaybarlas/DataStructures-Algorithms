package code;

import given.iGraph;
import sun.awt.image.ImageWatched;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

class Vertex<V> implements Comparable<Vertex<V>>{

    protected V element;
    private boolean visited;
    public float cost = Float.MAX_VALUE;
    public Vertex<V> parent;
    public V getElement(){return element;}
    private HashMap<V,Edge> outgoing,incoming;

    public Vertex(V element,boolean isDirected){
      this.element = element;
      visited = false;
      outgoing = new HashMap<>();
      if(isDirected){
          incoming = new HashMap<>();
      }else{ incoming = outgoing; }
    }
    public HashMap<V,Edge> getOutgoing(){return outgoing;}
    public HashMap<V,Edge> getIncoming(){return incoming;}
    public Iterable<V> adjacent(){
        List<V> adjacentList = new LinkedList<>();
        for(V v : this.getOutgoing().keySet())
            adjacentList.add(v);
        return adjacentList;
    }
    public boolean isVisited(){return visited;}
    public void unVisit(){visited = false;}
    public void visit(){visited=true;}


    @Override
    public int compareTo(Vertex<V> o) {
        if(cost>o.cost) return 1;
        if(cost<o.cost) return -1;
        return 0;
    }
}
class Edge{
    protected float element;
    public float getElement(){ return element;}
    private Vertex[] endPoints;
    public Edge(Vertex u, Vertex v, float e){
        this.element = e;
        endPoints = new Vertex[]{u,v};
    }
    public Vertex[] getEndpoints(){return endPoints;}

}


public abstract class BaseGraph<V> implements iGraph<V>{

    private boolean isDirected ;
    private boolean isWeighted ;
    private LinkedList<Vertex<V>> vertices = new LinkedList<>();
    private LinkedList<Edge> edges = new LinkedList<>();

    public BaseGraph(boolean directed){isDirected = directed; isWeighted=false;}
    public BaseGraph(boolean directed, boolean weighted){isDirected = directed; isWeighted=weighted;}

    public LinkedList<Vertex<V>> getVertices(){return vertices;}
    public Edge getEdge(V source, V target){
        Vertex<V> vertex = findVertex(source);
        if(vertex==null)return null;
        return vertex.getOutgoing().get(target);
    }

    public Vertex<V> findVertex(V v){
        for(Vertex<V> vertex: vertices){
            if(vertex.getElement().equals(v))
                return vertex;
        }
        return null;
    }

    public Vertex<V> opposite(V v, Edge e){
        Vertex[] endPoints = e.getEndpoints();
        if(endPoints[0].getElement().equals(v))
            return endPoints[1];
        else if(endPoints[1].getElement().equals(v))
            return endPoints[0];
        else throw new IllegalArgumentException("V is not incident to this edge");
    }

    @Override
    public void insertVertex(V v) {
        Vertex<V> searchVertex = findVertex(v);
        if(searchVertex!=null) return;

        Vertex<V> vertex = new Vertex<>(v,isDirected);
        vertices.add(vertex);
    }
//*******problematic
    @Override
    public V removeVertex(V v) {
        Vertex<V> vertex = findVertex(v);
        if (vertex == null) return null;
        //if (!isDirected()) {
            Iterator<Edge> iter = vertex.getIncoming().values().iterator();
            while (iter.hasNext()) {
                Edge e = iter.next();
                justRemoveEdge(opposite(v, e).getElement(), v);
                //e.getEndpoints()[0].getOutgoing().remove(e.getEndpoints()[1].getElement());
                //e.getEndpoints()[0].getIncoming().remove(e.getEndpoints()[1].getElement());
                e.getEndpoints()[1].getIncoming().remove(e.getEndpoints()[0].getElement());
                e.getEndpoints()[1].getOutgoing().remove(e.getEndpoints()[0].getElement());
                //targetV.getIncoming().remove(source);
            }
            Iterator<Edge> iter2 = vertex.getOutgoing().values().iterator();
            while (iter2.hasNext()) {
                Edge e = iter2.next();
                justRemoveEdge(v, opposite(v, e).getElement());
                //e.getEndpoints()[0].getOutgoing().remove(e.getEndpoints()[1].getElement());
                e.getEndpoints()[1].getIncoming().remove(e.getEndpoints()[0].getElement());
                e.getEndpoints()[1].getOutgoing().remove(e.getEndpoints()[0].getElement());
            }
            vertices.remove(vertex);
            return vertex.getElement();

    }
    @Override
    public boolean areAdjacent(V v1, V v2) {
        Vertex<V> vertex = findVertex(v1);
        if(vertex==null) return false;
        return vertex.getIncoming().containsKey(v2) || vertex.getOutgoing().containsKey(v2);
    }

    @Override
    public void insertEdge(V source, V target) {
        insertEdge(source, target, 1);
    }

    @Override
    public void insertEdge(V source, V target, float weight) {
        if(!isWeighted()) weight = 1;
        if(getEdge(source, target)==null){
            Vertex<V> sourceV = findVertex(source);
            Vertex<V> targetV = findVertex(target);

            if(sourceV==null) {insertVertex(source); sourceV = findVertex(source);}
            if(targetV==null){ insertVertex(target); targetV = findVertex(target);}

            Edge e = new Edge(sourceV,targetV,weight);
            edges.add(e);

            sourceV.getOutgoing().put(target,e);
            targetV.getIncoming().put(source,e);
        }else{
            getEdge(source, target).element = weight;
        }
    }
//-------------------------
    @Override
    public boolean removeEdge(V source, V target) {
        Vertex<V> inVertex = findVertex(source);
        Vertex<V> outVertex = findVertex(target);
        if(inVertex==null) {insertVertex(source); inVertex = findVertex(source);}
        if(outVertex==null){insertVertex(target); outVertex = findVertex(target);}

        Edge e = getEdge(source,target);
        if(e==null) return false;
        edges.remove(e);

        inVertex.getOutgoing().remove(target);
        outVertex.getIncoming().remove(source);
        return true;

    }
    public boolean justRemoveEdge(V source, V target) {
        Vertex<V> sourceV = findVertex(source);
        Vertex<V> targetV = findVertex(target);
        if(sourceV==null) {return false;}
        if(targetV==null){return false;}
        //sourceV.getOutgoing().remove(target);
        //targetV.getIncoming().remove(source);
        Edge e = getEdge(source,target);
        if(e==null) return false;
        edges.remove(e);

        return true;

    }


    @Override
    public float getEdgeWeight(V source, V target) {
        Edge e = getEdge(source,target);
        if(e==null){
            if(isWeighted()) return Float.MAX_VALUE;
            else return 0;
        }
        return getEdge(source,target).getElement();
    }

    @Override
    public int numVertices() {
        return vertices.size();
    }

    @Override
    public Iterable<V> vertices() {
        List<V> list = new LinkedList<>();
        for(Vertex<V> v : vertices){
            list.add(v.getElement());
        }
        return list;
    }

    @Override
    public int numEdges() {
        return edges.size();
    }

    @Override
    public boolean isDirected() {
        return isDirected;
    }

    @Override
    public boolean isWeighted() {
        return isWeighted;
    }

    @Override
    public int outDegree(V v) {
        Vertex<V> vertex = findVertex(v);
        if(vertex==null) return -1;
        return vertex.getOutgoing().size();
    }

    @Override
    public int inDegree(V v) {
        Vertex<V> vertex = findVertex(v);
        if(vertex==null) return -1;
        return vertex.getIncoming().size();
    }

    @Override
    public Iterable<V> outgoingNeighbors(V v) {
        Vertex<V> vertex = findVertex(v);
        if(vertex==null || vertex.getOutgoing()==null) return null;
        return vertex.getOutgoing().keySet();
    }

    @Override
    public Iterable<V> incomingNeighbors(V v) {
        Vertex<V> vertex = findVertex(v);
        if(vertex==null || vertex.getIncoming()==null) return null;
        return vertex.getIncoming().keySet();
    }

}
