package given;

/*
 * Copyright 2018 Baris Akgun
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted 
 * provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this list of 
 * conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of 
 * conditions and the following disclaimer in the documentation and/or other materials provided 
 * with the distribution.
 * 
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to 
 * endorse or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR 
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER 
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * Most importantly, this software is provided for educational purposes and should not be used for
 * anything else.
 * 
 * AUTHORS: Baris Akgun, Ali Tugrul Balci
 *
 * DO NOT MODIFY 
 * 
 * */

import code.*;
import java.util.ArrayList;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * A benchmarking/sorting program.
 * We are going to use something like this to benchmark your grade for the bonus part.
 * There will be additional tests with other data types
 * 
 * @author baakgun
 *
 */

public class SortBenchmark {
  
  public static void report(String sortCase, AbstractArraySort<Integer> alg, Integer[] intArray) throws IOException{
    int n = intArray.length;
    BufferedWriter w = new BufferedWriter(new FileWriter(alg+sortCase+n+".txt"));
    w.write(alg.getSwaps()+"\n");
    w.write(alg.getCompares()+"\n");
    for(int i = 0; i < n; i++){
      w.write(intArray[i]+"\n");
    }
    w.flush();
    w.close();
  }
  
  //Instead of printing we should just compare them 
  public static void report(String sortCase, AbstractArraySort<Integer> alg) throws IOException{
    BufferedWriter w = new BufferedWriter(new FileWriter(alg+"_"+sortCase+".txt"));
    w.write(alg.getSwaps()+"\n");
    w.write(alg.getCompares()+"\n");
    w.flush();
    w.close();
  }
  
  /**
   * Benchmarking function
   * 
   * @param sortAlg: sorting algorithm to benchmark
   * @param array:   data to sort
   * @param output:  whether to print output or not
   * @return
   */
  public static long benchMark(AbstractArraySort<Integer> sortAlg, Integer[] array, boolean output)
  {
    long timeA, timeB;
    if(output)
      System.out.println(sortAlg);
    timeA = System.currentTimeMillis();
    sortAlg.sort(array);
    timeB = System.currentTimeMillis();
    boolean sorted = false;
    if(output)
    {
      sorted = AbstractArraySort.isSorted(array,0,array.length-1);
      System.out.println("Issorted: " + Boolean.toString(sorted));
      System.out.println("Sorting took: " + (timeB - timeA) + " miliseconds");
      System.out.println();
    }
    assert sorted;
    return timeB - timeA;
  }
  
  public static void main(String[] args)
  {
    /* WARNING: Do not run anything else on your machine while you are benchmarking! */
    
    benchmarkLog log = new benchmarkLog();
    
    /* benchmarking parameters */
    
    // Number of elements to test. Once you feel comfortable, uncomment the remaining 2
    int dataLengths[] = {10000, 100000};//,100000};// 1000000, 10000000};
    
    // Type of data to test, look at the pdf for descriptions
    String runTypes[] = {"uniform", "randomizedDuplicates", "constant", "sorted" , "sortedReverse", "staggered", "sortedChunks", "ladder"};
    
    // Number of trials per test. The higher this value, the better the results assuming consistent OS scheduling
    int runsPerTrial = 10; //100

    /* Initializing the algorithms and filling in the "algorithm array" */
    QuickSort<Integer> qSortInt = new QuickSort<Integer>();
    InsertionSort<Integer> iSortInt = new InsertionSort<Integer>();
    MergeSort<Integer> mSortInt = new MergeSort<Integer>();
    HeapSort<Integer> hSortInt = new HeapSort<Integer>();
    JavaArraySort<Integer> jSortInt = new JavaArraySort<Integer>();
    CountingSort<Integer> coSortInt = new CountingSort<Integer>();
    ContestEntrySort<Integer> ceSortInt = new ContestEntrySort<Integer>();
    
    
    ArrayList<AbstractArraySort<Integer>> algs = new ArrayList<AbstractArraySort<Integer>>();
    
    algs.add(mSortInt);
    algs.add(hSortInt);
    algs.add(qSortInt);
    algs.add(iSortInt);
    algs.add(jSortInt);
    algs.add(coSortInt);
    algs.add(ceSortInt);

    int c = 100;
    Integer[] orig = DataGenerator.randomIntRange(0, c, c);
    Integer[] randIntegers = new Integer[c];

    /* Running all of them once to get better results. Run once and ignore results */
    for(AbstractArraySort<Integer> alg : algs)  
    {
      System.arraycopy(orig, 0, randIntegers, 0, c);
      benchMark(alg, randIntegers, false);
    }
    
    /* Let the benchmarking begin */
    
    // 4 nested loops follow? 1) data length, 2) data type, 3) iteration, 4) algorithm. 
    for(int n : dataLengths)
    {
      randIntegers = new Integer[n]; 
      for(String type : runTypes)
      {
        for(int i = 0; i < runsPerTrial; i++)
        {
          // re-sample the data for each iteration
          orig = DataGenerator.generateIntegers(n, type);
          
          for(AbstractArraySort<Integer> alg : algs)  
          {
            if(alg.name.equals("Insertionsort") && n > 10000)
            {
              //System.out.println("Skipping insertion sort due to size of data!");
              continue;
            }
            
            // To get more info per run, you could pass true instead of false for the last element
            alg.initTest();
            System.arraycopy(orig, 0, randIntegers, 0, n);
            long result = benchMark(alg, randIntegers, false);
            if(type == "random"){
              try{
                //report(type, alg, randIntegers);
                report(type, alg);
              }
              catch (IOException e){
                System.out.println("Cannot write to a file.");
              }
            }
            log.add(alg.toString(), type, n, result);
          }
        }
      }
    }
    
    /* Getting the mean results. */
    // For the interested student: Sort the results for each case and display it as such! You can use the methods of the benchmarkLog class and even add your own. 
    for(int n : dataLengths)
    {
      for(String type : runTypes)
      {
        for(AbstractArraySort<Integer> alg : algs)
        {
          log.updateMean(alg.toString(), type, n);
          double meanResult = log.getMean(alg.toString(), type, n);
          if(meanResult != -1)
            System.out.println("Run length: " + n + " data type: " + type + ", algorithm: " + alg + " mean: " +  meanResult);
        }
        System.out.println();
      }
    }
  }
}
