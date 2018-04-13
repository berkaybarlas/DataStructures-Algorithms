package given;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import code.*;

public class Test {

  public static <Key> void print(iPrintable<Key> T) {
    System.out.println("Printing an item of size: " + T.size());
    for (Key k : T.keySet()) {
      if (T.get(k) != null)
        System.out.println(" <Key: " + k + " --- Value: " + T.get(k) + ">");
      else
        System.out.println(" <Key: " + k + ">");
    }
    System.out.println();
  }

  public static void TestHM() {
    String[] arr;
    String keywords = "";
    try {
      File keyfile = new File("keywords.txt");
      keywords = new String(Files.readAllBytes(keyfile.toPath()), StandardCharsets.UTF_8);
    } catch (Exception e) {
      System.out.println("Error Reading keywords.txt");
      System.exit(0);
    }
    arr = keywords.toString().replaceAll("[^A-Za-z0-9 ]+", "").toLowerCase().split("\\s+");
    float criticalAlpha = 0.6f;
    int initCap = arr.length / 2;
    // Test HashMap (0-LinProbHashMap, 1-OpenAddressingHashMap)
    AbstractHashMap<String, Integer> hm = null;
    for (int h = 0; h < 2; h++) {

      if (h == 0) {
        // Test open addressing
        hm = new HashMapDH<String, Integer>(initCap, criticalAlpha);
      } else if (h == 1) {
        // Test separate chaining
        hm = new HashMapSC<String, Integer>(initCap, criticalAlpha);
      }

      initCap = hm.capacity();

      // Test put implementation
      for (int i = 0; i < arr.length; i++) {
        if (hm.put(arr[i], i) != null) {
          System.out.println("put return value is expected to be null.");
        }
      }
      if (hm.put(arr[0], 0) != 0) {
        System.out.println("put return value is expected to be 0.");
      }

      // Test cAlpha implementation
      if (hm.capacity() == initCap) {
        System.out.println("Capaity is not increased as per criticalAplha.");
      }

      // Test get implementation
      for (int i = 0; i < arr.length; i++) {
        if (hm.get(arr[i]) != i) {
          System.out.println("Wrong Value returned.");
        }
      }
      if (hm.get("comp202Alpha") != null) {
        System.out.println("Return should be null because accessing value that do not exist.");
      }

      print(hm);
    }
  }

  public static void TestCounter() {
    String S1 = "once twice thrice thrice twice thrice";
    String S2 = "Today is a lovely day";
    String S3 = "The dog and the cat were outside the store";
    String S4 = "How much wood would a woodchuck chuck if a woodchuck could chuck wood?";
    String S5 = "Güzel ile güzel güzel yemek yemek güzeldir";

    String[] allS = { S1, S2, S3, S4, S5 };

    HashCounter<String> myCounter;
    for (int h = 0; h < 2; h++) {
      for (String S : allS) {
        System.out.println("The sentence: ");
        System.out.println(S);
        System.out.println("The counter:");
        String[] record = S.toString().replaceAll("[^A-Za-z0-9ü ]+", "").toLowerCase().split("\\s+");
        if (h == 0)
          myCounter = new HashCounter<String>(new HashMapDH<String, Integer>());
        else
          myCounter = new HashCounter<String>(new HashMapSC<String, Integer>());
        myCounter.countAll(record);
        print(myCounter);
      }
    }
  }

  public static void TestSet() {
    HashSet<String> hs = new HashSet<String>();
    String[] stuff = { "Comp202", "hello", "Hello", "spring break", "a", "Z" };
    for (String s : stuff) {
      hs.put(s);
    }

    for (String s : stuff) {
      if (!hs.contains(s)) {
        System.out.println("The hashset should contain " + s);
      }
    }

    if (hs.contains("comp202")) {
      System.out.println("The hashset should not contain comp202");
    }

    if (hs.contains("hellO")) {
      System.out.println("The hashset should not contain hellO");
    }

    if (hs.contains("springbreak")) {
      System.out.println("The hashset should not contain hellO");
    }

    hs.remove("Hello");
    print(hs);

    for (String s : stuff) {
      hs.remove(s);
      if (hs.contains(s)) {
        System.out.println("The hashset should not contain " + s + " after removal");
      }
    }

    print(hs);
  }

  public static void TestCoOccurrence() {
    File dir = new File("News");
    File[] files = dir.listFiles();
    String[] fnames = new String[files.length];
    HashBasedWordCO[] matrices = new HashBasedWordCO[files.length];
    for (int i = 0; i < files.length; i++) {
      fnames[i] = files[i].getName();
      try {
        matrices[i] = loadDoc(files[i]);
      } catch (Exception e) {
        System.out.println("IOException at loadDoc file : " + fnames[i]);
        e.printStackTrace();
        System.exit(0);
      }
      // System.out.println(fnames[i]+" : ");
      // matrices[i].printMatrix();
    }

    // Check co-occurance value between two keywords
    
    //Below should change
    if (matrices[0].getCoOccurrenceValue("dollar", "bid") != 3) {
      System.out.println("Wrong co-occurance value: " + matrices[0].getCoOccurrenceValue("dollar", "bid"));
    }
    if (matrices[0].getCoOccurrenceValue("dollar", "stock") != 2) {
      System.out.println("Wrong co-occurance value");
    }
    if (matrices[1].getCoOccurrenceValue("dollar", "bid") != 0) {
      System.out.println("Wrong co-occurance value");
    }
    matrices[0].printMatrix();
  }

  public static HashBasedWordCO loadDoc(File doc) throws IOException {
    String contents = new String(Files.readAllBytes(doc.toPath()), StandardCharsets.UTF_8);
    File keyfile = new File("keywords.txt");
    String keywords = new String(Files.readAllBytes(keyfile.toPath()), StandardCharsets.UTF_8);
    HashBasedWordCO matrix = new HashBasedWordCO();
    matrix.fillCoMat(contents, keywords.toLowerCase().split("\\s+"));
    return matrix;
  }
  
  public static void TestCoOccurrenceEZ() {
    String S1 = "once twice thrice thrice twice thrice";
    
    System.out.println("Text: " + S1);
    HashBasedWordCO tmp = new HashBasedWordCO(1);
    tmp.fillCoMat(S1);
    tmp.printMatrix();
    System.out.println();
    
    String S2 = "To be or not to be. That is the question";
    System.out.println("Text: " + S2);
    tmp = new HashBasedWordCO(2);
    tmp.fillCoMat(S2);
    tmp.printMatrix();
  }

  public static void main(String[] args) {
    System.out.println("Testing hashmaps: ");
    TestHM();
    System.out.println("Testing counters: ");
    TestCounter();
    System.out.println("Testing set: ");
    TestSet();
    System.out.println("Testing co-occurence: ");
    TestCoOccurrenceEZ();
    TestCoOccurrence();
  }

}
