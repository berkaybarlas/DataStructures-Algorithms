package code;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import given.AbstractHashMap;
import given.HashEntry;

/*
 * The file should contain the implementation of a hashmap with:
 * - Separate Chaining for collision handling
 * - Multiply-Add-Divide (MAD) for compression: (a*k+b) mod p
 * - Java's own linked lists for the secondary containers
 * - Resizing (to double its size) and rehashing when the load factor gets above a threshold
 *   Note that for this type of hashmap, load factor can be higher than 1
 * 
 * Some helper functions are provided to you. We suggest that you go over them.
 * 
 * You are not allowed to use any existing java data structures other than for the buckets (which have been 
 * created for you) and the keyset method
 */

public class HashMapSC<Key, Value> extends AbstractHashMap<Key, Value> {

  // The underlying array to hold hash entry Lists
  private LinkedList<HashEntry<Key, Value>>[] buckets;

  // Note that the Linkedlists are still not initialized!
  @SuppressWarnings("unchecked")
  protected void resizeBuckets(int newSize) {
    // Update the capacity
    N = nextPrime(newSize);
    buckets = (LinkedList<HashEntry<Key, Value>>[]) Array.newInstance(LinkedList.class, N);
  }

  /*
   * ADD MORE FIELDS IF NEEDED
   * 
   */

  // The threshold of the load factor for resizing
  protected float criticalLoadFactor;

  /*
   * ADD A NESTED CLASS IF NEEDED
   * 
   */

  public int hashValue(Key key, int iter) {
    return hashValue(key);
  }

  public int hashValue(Key key) {
    int k = Math.abs(key.hashCode());
    return Math.abs((k * a + b) % P) % N;
  }

  // Default constructor
  public HashMapSC() {
    this(101);
  }

  public HashMapSC(int initSize) {
    // High criticalAlpha for representing average items in a secondary container
    this(initSize, 10f);
  }

  public HashMapSC(int initSize, float criticalAlpha) {
    N = initSize;
    criticalLoadFactor = criticalAlpha;
    resizeBuckets(N);
    
    // Set up the MAD compression and secondary hash parameters
    updateHashParams();
    n =0 ;
    /*
     * ADD MORE CODE IF NEEDED
     * 
     */
  }

  /*
   * ADD MORE METHODS IF NEEDED
   * 
   */
 private HashEntry<Key, Value> getEntry(Key k){
   if(k == null){
     return null;
   }
   int hashVal = hashValue(k);
   if(buckets[hashVal]!=null) {
     ListIterator iterator = buckets[hashVal].listIterator();
     /*
      *think for other ways to do that
      */
     while(iterator.hasNext()){
       Object next = iterator.next();
       HashEntry<Key, Value> element = (next instanceof HashEntry ? (HashEntry<Key, Value>) next : null);
       if(element != null && element.getKey()!=null && element.getKey().equals(k)){
         return element;
       }
     }
   }
   return null;
 }
  @Override
  public Value get(Key k) {
    // TODO Auto-generated method stub
    if(k == null){
      return null;
    }
    HashEntry<Key, Value> entry = getEntry(k);
    if(entry!=null){
      return entry.getValue();
    }
    return null;
  }

  @Override
  public Value put(Key k, Value v) {
    // TODO Auto-generated method stub
    // Do not forget to resize if needed!
    // Note that the linked lists are not initialized!
    if(k == null){
      return null;
    }
    HashEntry<Key, Value> entry = getEntry(k);
    if(entry!=null){
      Value oldVal = entry.getValue();
      getEntry(k).setValue(v);
      return  oldVal;
    }
    n++;
    checkAndResize();
    int hashVal = hashValue(k);
    if(buckets[hashVal]==null){
      buckets[hashVal] = new LinkedList<>();
    }
    buckets[hashVal].add(new HashEntry(k,v));
    return null;
  }
  @Override
  public Value remove(Key k) {
    if(k == null){
      return null;
    }
    HashEntry<Key, Value> entry = getEntry(k);
    if(entry!=null){
      n--;
      buckets[hashValue(k)].remove(entry);
      return entry.getValue();
    }

    return null;
  }

  @Override
  public Iterable<Key> keySet() {
    // TODO Auto-generated method stub
    List<Key> iter = new LinkedList<>();
    for(int i=0; i<N; i++){
      LinkedList<HashEntry<Key,Value>> bucketList = buckets[i];
      if(bucketList!=null ){
        for(HashEntry<Key,Value> element : bucketList ){
          if(element.getKey()!=null)
          iter.add(element.getKey());
        }
      }
    }
    return iter;
  }

  /*
  check again
   */



  /**
   * checkAndResize checks whether the current load factor is greater than the
   * specified critical load factor. If it is, the table size should be increased
   * to 2*N and recreate the hash table for the keys (rehashing). Do not forget to
   * re-calculate the hash parameters and do not forget to re-populate the new
   * array!
   */
  protected void checkAndResize() {
    if (loadFactor() > criticalLoadFactor) {
      // TODO: Fill this yourself
      int oldN = N;
      LinkedList<HashEntry<Key, Value>>[] oldBucket = buckets;
      resizeBuckets(2*N);
      updateHashParams();
      n=0;
      for(int i =0 ;i<oldN; i++){
        LinkedList<HashEntry<Key,Value>> bucketList = oldBucket[i];
        if(bucketList!=null ){
          for(HashEntry<Key,Value> element : bucketList ){
            put(element.getKey(),element.getValue());
          }
        }
      }
    }
  }
}
