package code;

/*
 * ASSIGNMENT 2
 * AUTHOR:  Berkay Barlas
 * Class : ArrayDeque
 *
 * You are not allowed to use Java containers!
 * You must implement the Array Deque yourself
 *
 * MODIFY 
 * 
 **/

import given.iDeque;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import given.Util;


//BARIS: May need to give them some pointers on how an arraydeque works
public class ArrayDeque<E> implements iDeque<E> {

  private E[] A;
  private int size = 0;
	
	public ArrayDeque() {
		this(1000);
	}
	
	public ArrayDeque(int initialCapacity) {
	   if(initialCapacity < 1)
	      throw new IllegalArgumentException();
		A = createNewArrayWithSize(initialCapacity);
	}
	
	// This is given to you for your convenience since creating arrays of generics is not straightforward in Java
	@SuppressWarnings({"unchecked" })
  private E[] createNewArrayWithSize(int size) {
	  return (E[]) new Object[size];
	}
	
	//Bonus: Modify this such that the dequeue prints from front to back!
	// Hint, after you implement the iterator, use that!
  public String toString() {
    if(isEmpty()) return "";
    StringBuilder sb = new StringBuilder(1000);
    sb.append("[");
    //Iterator<E> iter = Arrays.asList(A).iterator();
    Iterator<E> iter = iterator();
    while(iter.hasNext()) {
      E e = iter.next();
      if(e == null)
        continue;
      sb.append(e);
      if(!iter.hasNext())
        sb.append("]");
      else
        sb.append(", ");
    }
    return sb.toString();
  }

  private void doubleSize(int Capacity){
// create new a array with double size
    E[] temp = createNewArrayWithSize(2*Capacity);
    for(int i=0; i<size();i++){
      temp[i] = A[i];
    }
    A=temp;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size()==0;
  }

  @Override
  public void addFront(E o) {
    if(size() == A.length){
      doubleSize(A.length);
    }
    for(int i=size(); i>0; i--){
      A[i] = A[i-1];
    }

    A[0] = o;
    size++;
  }

  @Override
  public E removeFront() {
    if(isEmpty()) return null;
    E temp = A[0];
    for(int i=0; i<size(); i++){
      A[i] = A[i+1];
    }
    size--;
    return temp;
  }

  @Override
  public E front() {
    if(isEmpty()) return null;
    return A[0];
  }

  @Override
  public void addBehind(E o) {
    if(size() == A.length){
      doubleSize(A.length);
    }
    A[size()] = o;
    size++;
  }

  @Override
  public E removeBehind() {
    if(isEmpty()) return null;
    size--;
    E temp = A[size()];
    A[size()] = null;
    return temp;
  }

  @Override
  public E behind() {
    if(isEmpty()) return null;
    return A[size()-1];
  }

  @Override
  public void clear() {
    while (!isEmpty()){
      this.removeBehind();
    }
    
  }

  @Override
  public Iterator<E> iterator() {
    return new ArrayDequeIterator();
  }
  private final class ArrayDequeIterator implements Iterator<E> {
    int i = 0;

    @Override
    public boolean hasNext() {
      return i<size();
    }

    @Override
    public E next() throws NoSuchElementException {
      if (i==size()) throw new NoSuchElementException("No Element Left");
      i++;
      return A[i-1];
    }        
  }
}
