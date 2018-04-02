package code;

/* 
 * ASSIGNMENT 2
 * AUTHOR:  Berkay Barlas
 * Class : LLDeque
 *
 * You are not allowed to use Java containers!
 * You must implement the linked list yourself
 * Note that it should be a doubly linked list
 *
 * MODIFY 
 * 
 * */

import given.iDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import given.Util;

//If you have been following the class, it should be obvious by now how to implement a Deque wth a doubly linked list
public class LLDeque<E> implements iDeque<E> {
  
  //Use sentinel nodes. See slides if needed
  private Node<E> header;
  private Node<E> trailer;
  private int size = 0;

  public LLDeque() {
    //Remember how we initialized the sentinel nodes
    header  = new Node<E>(null, null, header);
    trailer = new Node<E>(null, trailer, header);
    header.next = trailer;

   }

  // The nested node class, provided for your convenience. Feel free to modify
  private class Node<T> {
    private T element;
    private Node<T> next;
    private Node<T> prev;

    public Node(T d, Node<T> n, Node<T> p) {
      element = d;
      next = n;
      prev = p;
    }


    public T getElement() throws IllegalStateException {
      if(next== null)
        throw new IllegalStateException("This node is deleted.");
      return element;
    }

    public Node<T> getNext() {
      return next;
    }

    public Node<T> getPrev() {
      return prev;
    }

    public void setElement(T element) {
      this.element = element;
    }

    public void setNext(Node<T> next) {
      this.next = next;
    }

    public void setPrev(Node<T> prev) {
      this.prev = prev;
    }

  }
  

  
  public String toString() {
      if(isEmpty()) return "";
    StringBuilder sb = new StringBuilder(1000);
    sb.append("[");
    Node<E> tmp = header.next;
    while(tmp.next != trailer) {
      sb.append(tmp.element.toString());
      sb.append(", ");
      tmp = tmp.next;
    }
    sb.append(tmp.element.toString());
    sb.append("]");
    return sb.toString();
  }
  
  // ADD METHODS IF NEEDED
  // Below are the interface methods to be overriden

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size()==0;
  }
// might be wrong
  @Override
  public void addFront(E o) {
    //Node<> temp = header.next;
    header.getNext().setPrev(new Node<E>(o,header.getNext(),header));
    header.setNext(header.getNext().getPrev());
    size++;
  }

  @Override
  public E removeFront() {
    if(isEmpty())
      return null;
    Node<E> node = header.getNext();
    E temp = node.getElement();
    header.setNext(node.getNext());
    node.getNext().setPrev(header);

    node.setNext(null);
    node.setElement(null);
    node.setPrev(null);
    size--;
    return temp;
  }

  @Override
  public E front() {
    if(isEmpty())
      return null;
    return header.getNext().getElement();
  }

  // might be wrong
  @Override
  public void addBehind(E o) {
    trailer.getPrev().setNext(new Node<E>(o,trailer,trailer.getPrev()));
    trailer.setPrev(trailer.getPrev().getNext());
    size++;
  }

  @Override
  public E removeBehind() {
    if(isEmpty())
      return null;
    Node<E> node = trailer.getPrev();
    E temp = node.getElement();
    trailer.setPrev(node.getPrev());
    node.getPrev().setNext(trailer);

    node.setNext(null);
    node.setElement(null);
    node.setPrev(null);
    size--;
    return temp;
  }

  @Override
  public E behind() {
    if(isEmpty())
      return null;
    return trailer.getPrev().getElement();
  }

  @Override
  public void clear() {
    while (!isEmpty()){
      this.removeBehind();
    }
    
  }

  @Override
  public Iterator<E> iterator() {
    return new LLDequeIterator();
  }

  private final class LLDequeIterator implements Iterator<E> {
      Node<E> node = header;
    /**
     * 
     * ADD A CONSTRUCTOR IF NEEDED
     * Note that you can freely access everything about the outer class!
     * 
     **/

    @Override
    public boolean hasNext() {
       return node.getNext()!=trailer;
    }

    @Override
    public E next() throws NoSuchElementException {
        if (node.getNext()==trailer) throw new NoSuchElementException("No Element Left");
        node = node.getNext();
      return node.getElement();
    }        
  }
  
}
