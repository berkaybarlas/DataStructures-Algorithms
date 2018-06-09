package code;

import given.AbstractArraySort;

import java.lang.reflect.Array;

/*
 * Implement the merge-sort algorithm here. You can look at the slides for the pseudo-codes.
 * Make sure to use the swap and compare functions given in the AbstractArraySort!
 * 
 * You may need to create an Array of K (Hint: the auxiliary array). 
 * Look at the previous assignments on how we did this!
 * 
 */

public class MergeSort<K extends Comparable<K>> extends AbstractArraySort<K> {

  // Add any fields here

  public MergeSort() {
    name = "Mergesort";

  }

  @Override
  public void sort(K[] inputArray) {
    // TODO: Implement the merge-sort algorithm
    mergeSort(inputArray,0,inputArray.length-1);
  }
  public void mergeSort(K[] inputArray,int lo, int hi) {
    // TODO: Implement the merge-sort algorithm
    int size = hi - lo;
    int mid = lo+(size)/2;
    if(size>0){
      mergeSort(inputArray,lo,mid);
      mergeSort(inputArray,mid+1,hi);
      merge(inputArray,lo,mid,hi);
    }
  }

  // Public since we are going to check its output!
  public void merge(K[] inputArray, int lo, int mid, int hi) {
    // TODO: Implement the merging algorithm

    K[] tempA = (K[]) Array.newInstance(inputArray.getClass().getComponentType(), hi-lo+1);
    System.arraycopy(inputArray, lo, tempA, 0, hi-lo+1);

    int i = lo;
    int j = mid + 1;
    int m  = lo;
    while (m <= hi){
      if(j>hi || (i <= mid && compare(tempA[i-lo],tempA[j-lo])<=0 ) ){
        inputArray[m] = tempA[i-lo];
        m++;
        i++;
      }else{
        inputArray[m] = tempA[j-lo];
        m++;
        j++;
      }
    }
  }

  // Feel free to add more methods
}
