package code;

import given.AbstractArraySort;

/*
 * Implement the heap-sort algorithm here. You can look at the slides for the pseudo-code.
 * Make sure to use the swap and compare functions given in the AbstractArraySort!
 *
 */

public class HeapSort<K extends Comparable<K>> extends AbstractArraySort<K> {

  // Add any fields here

  public HeapSort() {
    name = "Heapsort";

    // Initialize anything else here
  }

  @Override
  public void sort(K[] inputArray) {
    // TODO: Implement the heap-sort algorithm
   heapSort(inputArray);
  }

  private void heapSort(K[] inputArray){
    int lastIndex = inputArray.length-1;
    heapify(inputArray);
    while(lastIndex>0){
      swap(inputArray,0,lastIndex);
      lastIndex--;
      downHeap(inputArray,0,lastIndex);
    }
  }

  // Public since we are going to check its output!
  public void heapify(K[] inputArray) {
    // TODO: Heapify the array. See the slides for an O(n) version which uses
    int length = inputArray.length;
    int mid = (inputArray.length)/2;
    for(int i = mid-1; i>=0; i--){
      downheap(inputArray,i);
    }
  }
  // The below methods are given given as suggestion. You do not need to use them.
  // Feel free to add more methods
  protected void downHeap(K[] inputArray, int i,int lastIndex) {
    // TODO: Implement the downheap method to help with the algorithm
    int currentIndex = i;
    while(leftChild(currentIndex)<=lastIndex && inputArray[leftChild(currentIndex)]!=null) {
      int lChildIndex = leftChild(currentIndex);
      int rChildIndex = rightChild(currentIndex);
      int biggest = lChildIndex;
      if (rChildIndex <= lastIndex  ) {
        if (compare(inputArray[lChildIndex], inputArray[rChildIndex]) < 0) {
          biggest = rChildIndex;
        }
      }
      if (compare(inputArray[biggest], inputArray[currentIndex]) > 0) {
        swap(inputArray, biggest, currentIndex);
        currentIndex = biggest;
      }else {break;}
    }
  }
  protected void downheap(K[] inputArray, int i) {
    downHeap(inputArray,i,inputArray.length-1);
  }
  private int rightChild(int i){return 2*i+2;}
  private int leftChild(int i){return 2*i+1;}
}
