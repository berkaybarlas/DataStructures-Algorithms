package code;

import given.AbstractArraySort;

import java.lang.reflect.Array;
import java.util.Random;

/*
 * Your sorting algorithm for the sorting spree!
 * You do not need to use the swap and compare methods of AbstractArraySort here
 * Only the speed of the code and the correctness of the output will be checked
 *
 * We suggest that you use a hybrid algorithm!
 *
 */

public class ContestEntrySort<K extends Comparable<K>> extends AbstractArraySort<K> {

    public ContestEntrySort() {
        // Change the name with your ID!
        name = "54512";

        //Initialize anything else here
    }

    @Override
    public void sort(K[] inputArray) {
        if(inputArray == null) {
            System.out.println("Null array");
            return;
        }
        if(inputArray.length < 1) {
            System.out.println("Empty array");
            return;
        }

        boolean isSorted = isSortedArray(inputArray,0,inputArray.length-1);
        if(!isSorted) {
            boolean isReverse = isReverseSorted(inputArray, 0, inputArray.length - 1);
            if (!isReverse) {
                boolean isInteger = (inputArray[0] instanceof Integer);
                if (!isInteger) {
                    //timSort(inputArray, 0, inputArray.length - 1);
                    introSort(inputArray,0,inputArray.length-1);
                }else{
                    CountingSort(inputArray);
                }
            }else{
                reverseToNormal(inputArray,0,inputArray.length-1);
            }
        }
    }
    private void reverseToNormal(K[] array, int lo, int hi){
        int mid = lo + (hi-lo)/2;
        for(int i = lo; i <= mid; i++)
        {
            swap(array,i,hi-i+lo);
        }
    }
    private boolean isSortedArray(K[] array, int lo, int hi) {
        for(int i = lo; i < hi; i++)
        {
            if(array[i].compareTo(array[i+1]) > 0)
            { return false; }
        }
        return true;
    }
    private boolean isReverseSorted(K[] array, int lo, int hi) {
        for(int i = lo; i < hi; i++)
        {
            if(array[i].compareTo(array[i+1]) < 0)
            { return false; }
        }
        return true;
    }


    int RUN =32 ;
    private void timSort(K[] inputArray, int lo, int hi){
        //insert
        //mergesort
        int size = hi - lo + 1;
        for(int i =lo; i<lo+size; i+=RUN){
            insertionSort(inputArray,i,Math.min(i+RUN-1,hi));
        }
        for(int i = lo + RUN; i < lo + size; i = 2*i){
            for(int l = lo; l < lo + size; l+=2*i ){
                int mid = l + i - 1 ;
                int r = Math.min((l + 2*i - 1), (lo+size-1));
                merge(inputArray,l,mid,r);
            }
        }
    }

    private void introSort(K[] inputArray, int lo, int hi){
        int dept = (int)( 2*Math.log(hi-lo+1));
        quickSortC(inputArray,lo,hi,dept);
    }

    private void insertionSort(K[] inputArray, int lo, int hi)
    {
        int currentIndex;
        for(int i=lo; i<=hi; i++){
            currentIndex = i;
            while(currentIndex>lo){
                if(compare(inputArray[currentIndex],inputArray[currentIndex-1]) < 0 ){
                    swap(inputArray,currentIndex,currentIndex-1);
                    currentIndex--;
                }else{
                    break;
                }
            }
        }
    }

    private void merge(K[] inputArray, int lo, int mid, int hi) {
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
    //
//quicksort
//
    private class indexPair {
        public int p1, p2;
        indexPair(int pos1, int pos2)
        { p1 = pos1; p2 = pos2; }
        public String toString()
        {
            return "(" + Integer.toString(p1) + ", " + Integer.toString(p2) + ")";
        }
    }

    int insSortLim = 32;
    private void quickSortC(K[] inputArray,int lo, int hi,int depth){
        if(lo<hi){
            if((hi-lo)<=insSortLim){
                insertionSort(inputArray,lo,hi);
            }else if(true){
                int p = pickPivot(inputArray,lo,hi);
                ContestEntrySort.indexPair indexPairs = partition(inputArray,lo,hi,p);
                quickSortC(inputArray,lo, indexPairs.p1,depth-1);
                quickSortC(inputArray,indexPairs.p2, hi,depth-1);
///*
            }else{
                heapSort(inputArray,lo,hi);
            }//*/
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

    private int pickPivot(K[] inputArray, int lo, int hi)
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

    //heapsort

    private void heapSort(K[] inputArray,int lo,int hi){
        int lastIndex = hi;
        heapify(inputArray,lo,hi);
        while(lastIndex>lo){
            swap(inputArray,lo,lastIndex);
            lastIndex--;
            downHeap(inputArray,lo,lastIndex);
        }
    }
    // Public since we are going to check its output!
    private void heapify(K[] inputArray,int lo, int hi) {
        int length = hi-lo+1;
        int mid = lo+length/2;
        for(int i = mid-1; i>=lo; i--){
            downHeap(inputArray,i,hi);
        }
    }
    private void downHeap(K[] inputArray, int i,int lastIndex) {
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
    private int rightChild(int i){return 2*i+2;}
    private int leftChild(int i){return 2*i+1;}

    private void CountingSort(K[] inputArray) {
        K min = inputArray[0];
        K max = inputArray[0];
        for(int i = 0; i < inputArray.length; i++){
            if(compare(min,inputArray[i])>0){
                min = inputArray[i];
            }else if(compare(max,inputArray[i])<0){
                max = inputArray[i];
            }
        }
        Integer minInt = (Integer) min;
        Integer maxInt = (Integer) max;
        int k = maxInt - minInt +1;
        int[] counts = new int[k];

        for(int i = 0 ; i<inputArray.length;i++){
            counts[(Integer)inputArray[i] - minInt]++;
        }

        int count = 0 ;
        for(int i = 0 ; i<k;i++){
            for(int  j = 0 ; j<counts[i];j++){
                inputArray[count] = (K) (Integer)(minInt + i);
                count++;
            }
        }
    }

}
