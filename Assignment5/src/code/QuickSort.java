package code;

import given.AbstractArraySort;

import java.util.Random;

/*
 * Implement the quick-sort algorithm here. You can look at the slides for the pseudo-codes.
 * Make sure to use the swap and compare functions given in the AbstractArraySort!
 *
 */

public class QuickSort<K extends Comparable<K>> extends AbstractArraySort<K> {
  //Add any fields here

  public QuickSort()
  {
    name = "Quicksort";

  //Initialize anything else here
  }

  //useful if we want to return a pair of indices from the partition function.
  //You do not need to use this if you are just returning and integer from the partition
  public class indexPair {
    public int p1, p2;

    indexPair(int pos1, int pos2)
    { p1 = pos1;
      p2 = pos2; }
    public String toString()
    {
      return "(" + Integer.toString(p1) + ", " + Integer.toString(p2) + ")";
    }
  }


  public indexPair partition(K[] inputArray, int lo, int hi, int p)
  {
    int e = lo; int u = lo; int g = hi+1;
    K pivot = inputArray[p];
    while(u<g){
      int compareVal = compare(inputArray[u],pivot);
      if(compareVal<0) {
        swap(inputArray, u, e);
        e++;
        u++;
      }
      else if (compareVal==0){ u++;}
      else{
        g--;
        swap(inputArray,u,g);
      }
    }
    return new indexPair(e,g);
  }

  //The below methods are given given as suggestion. You do not need to use them.
  // Feel free to add more methods
  protected int pickPivot(K[] inputArray, int lo, int hi)
  {
    //TODO: Pick a pivot selection method and implement it
    int middle = (lo+hi)/2;
    int smaller;
    int bigger;
    Random rgen = new Random();
    int index = lo;
    int index1 = hi;
    // (int) (lo + Math.random()*(hi-lo));
    //(lo + rgen.nextInt(hi-lo));

    if(compare(inputArray[index],inputArray[index1])<=0){
      smaller = index;
      bigger = index1;
    }else{smaller = index1;bigger = index;}

    if(compare(inputArray[middle],inputArray[smaller])<=0) {
      middle = smaller;
    }else if(compare(inputArray[middle],inputArray[bigger])>0){
      middle = bigger;
    }
    return middle;
  }
  @Override
  public void sort(K[] inputArray)
  {
    //TODO:: Implement the quicksort algorithm here
    quickSort(inputArray,0, inputArray.length-1);
  }

  private void quickSort(K[] inputArray,int lo, int hi){
    if(lo<hi){
      int p = pickPivot(inputArray,lo,hi);
      indexPair indexPairs = partition(inputArray,lo,hi,p);

      quickSort(inputArray,lo, indexPairs.p1);
      quickSort(inputArray,indexPairs.p2, hi);

    }
  }
}
