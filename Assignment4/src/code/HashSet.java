package code;

import given.AbstractHashMap;
import given.iPrintable;
import given.iSet;

/*
 * A set class implemented with hashing. Note that there is no "value" here 
 * 
 * You are free to implement this however you want. Two potential ideas:
 * 
 * - Use a hashmap you have implemented with a dummy value class that does not take too much space
 * OR
 * - Re-implement the methods but tailor/optimize them for set operations
 * 
 * You are not allowed to use any existing java data structures
 * 
 */

public class HashSet<Key> implements iSet<Key>, iPrintable<Key>{

  //private HashSet<Key>[] buckets;
  AbstractHashMap<Key,Integer> A;
  int size = 0;
  // A default public constructor is mandatory!
  public HashSet() {
   A = new HashMapDH<>();

  }
  /*
   * 
   * Add whatever you want!
   * 
   */

  @Override
  public int size() {
    return A.size();
  }

  @Override
  public boolean isEmpty() {
    return size()==0;
  }

  @Override
  public boolean contains(Key k) {
    return A.get(k)!=null;
  }

  @Override
  public boolean put(Key k) {
    //if(!contains(k)){
      //size++;
    //}
    return A.put(k,1)!=null;
  }

  @Override
  public boolean remove(Key k) {
    //if(contains(k)){
      //size--;
    //}
    return A.remove(k)!=null;
  }

  @Override
  public Iterable<Key> keySet() {
    // TODO Auto-generated method stub
    return A.keySet();
  }

  @Override
  public Object get(Key key) {
    // Do not touch
    return null;
  }

}
