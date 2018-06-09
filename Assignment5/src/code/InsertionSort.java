package code;

import given.AbstractArraySort;

public class InsertionSort<K extends Comparable<K>> extends AbstractArraySort<K> {

  public InsertionSort()
  {
    name = "Insertionsort";
  }
  
  @Override
  public void sort(K[] inputArray) 
  {
    int currentIndex = 0 ;
    for( int i=0; i< inputArray.length; i++){
      currentIndex = i;
      while(currentIndex>0){
        if(compare(inputArray[currentIndex],inputArray[currentIndex-1]) < 0 ){
          swap(inputArray,currentIndex,currentIndex-1);
          currentIndex--;
        }else{
          break;
        }
      }
    }

  }
}

