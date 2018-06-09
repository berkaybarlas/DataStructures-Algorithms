package code;

import given.AbstractArraySort;

/*
 * Implement the c algorithm here. You can look at the slides for the pseudo-codes.
 * You do not have to use swap or compare from the AbstractArraySort here
 * 
 * You may need to cast any K to Integer
 * 
 */

public class CountingSort<K extends Comparable<K>> extends AbstractArraySort<K> {

  //Add any fields here
  
  public CountingSort()
  {
    name = "Countingsort";
  }
  
  @Override
  public void sort(K[] inputArray) {

    if(inputArray == null)
    {
      System.out.println("Null array");
      return;
    }
    if(inputArray.length < 1)
    {
      System.out.println("Empty array");
      return;
    }   
    
    if(!(inputArray[0] instanceof Integer)) {
      System.out.println("Can only sort integers!");
      return;
    }

    countingSort(inputArray);
    //TODO:: Implement the counting-sort algorithm here
  }


  private void countingSort(K[] inputArray) {
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
