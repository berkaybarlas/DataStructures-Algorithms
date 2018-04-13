package code;

import java.util.ArrayList;
import java.util.List;

public class HashBasedWordCO {

  HashMapDH<String, HashCounter<String>> coMat;
  HashSet<String> words;
  int windowSize;

  public HashBasedWordCO() {
    this(5);

  }

  public HashBasedWordCO(int winSize) {
    windowSize = winSize;
  }

  public List<String> splitSentences(String text) {
    HashSet<Character> lineEnders = new HashSet<Character>();
    lineEnders.put('.');
    lineEnders.put('?');
    lineEnders.put('!');
    return splitSentences(text, lineEnders);
  }

  public List<String> splitSentences(String text, HashSet<Character> lineEnders) {
    ArrayList<String> sentences = new ArrayList<String>();
    int i, preI = 0, n = text.length();
    for (i = 0; i < n; i++) {
      char c = text.charAt(i);
      if (lineEnders.contains(c)) {
        if (i > 0 && i < n - 1) {

          if (Character.isDigit(text.charAt(i - 1)) && Character.isDigit(text.charAt(i + 1))) {
            continue;
          } else {
            sentences.add(text.substring(preI, i).trim().replaceAll("[^A-Za-z0-9 ]+", "").toLowerCase());
            preI = i + 1;
          }
        }
      }
    }
    if (preI < i)
      sentences.add(text.substring(preI, i).trim().replaceAll("[^A-Za-z0-9 ]+", "").toLowerCase());
    return sentences;
  }

  // Returns the set of unique words, apart from the given ignored ones, given in
  // the text with word length constraints
  public HashSet<String> getUniqueWords(String text, int minWordLen, String[] ignoreList) {
    HashSet<String> uWords = new HashSet<String>();
    List<String> sentences = splitSentences(text);
    
    /*
     * ADD CODE HERE IF NEEDED
     * 
     */

    for (String sentence : sentences) {
      for (String word : sentence.split("\\s+")) {
        // TODO: Fill here
        // Make sure to NOT include any ignored words and words that are shorter than
        // the minWordLen
      }
    }
    return uWords;
  }

  public HashSet<String> getUniqueWords(String text) {
    String[] ignore = { "and", "the", "or", "am", "are", "is", "was", "were", "a", "an", "be" };
    return getUniqueWords(text, 2, ignore);
  }

  public void fillCoMat(String text) {
    fillCoMat(text, null);
  }

  /*
   * Co-occurence definition is based on how close each word is to each other in a
   * given sentence Sentences are split automatically for you in this assignment
   * so you just need to count how many times two words appear within the same
   * window The windows are formed by putting their center to a word and taking w
   * number of words on each side. For example give [A B C D E F], a window with w
   * = 1 centered on C is, [B C D]. If the edges of the window are at the limit,
   * we just truncate. E.g. for A, the same sized winde is [A B] and for F it is
   * [E F]
   * 
   * The below function should calculate the co-occurence values by filling the
   * coMat object. coMat is a hashmap of String and HashCounters. The counters count
   * the number of times the words co-occur together with the String key
   * representing the main word. Note that coMAt needs to be symmetric
   * 
   * For example the sentence "once twice thrice thrice twice thrice" with w = 1 should yield:
   * once->  [once->0,twice->1,thrice->0]
   * twice-> [once->1,twice->0,thrice->3]
   * thrice->[once->0,twice->3,thrice->2] 
   * 
   */
  public void fillCoMat(String text, String[] keyWords) {
    if (text == null)
      return;
    coMat = new HashMapDH<String, HashCounter<String>>();

    if (keyWords != null) {
      if (keyWords.length != 0) {
        words = new HashSet<String>();
        for (String word : keyWords)
          words.put(word);
      }
    } else
      words = getUniqueWords(text);
    
    /*
     * ADD CODE HERE IF NEEDED
     * 
     */

    // Further bit of help for setting up
    for (String word : words.keySet())
      coMat.put(word, new HashCounter<String>());
    List<String> sentences = splitSentences(text);

    // TODO: Implement co-occurence calculations
  }

  public int getCoOccurrenceValue(String word1, String word2) {
    if (coMat.get(word1) == null)
      return 0;
    else
      return coMat.get(word1).getCount(word2);
  }

  public void printMatrix() {
    if (coMat == null)
      System.out.format("Empty co-occurrence matrix!");
    System.out.format("%10s", " ");
    for (String word : words.keySet()) {
      System.out.format("%10s", word);// .print(word + " ");//
    }
    System.out.println("");

    for (String word1 : words.keySet()) {
      System.out.format("%10s", word1);// print(word1+":");
      for (String word2 : words.keySet()) {
        String tmp = "%10s";// "%" + word2.length() + "s ";
        System.out.format(tmp, Integer.toString(coMat.get(word1).getCount(word2)));// print(coMatrix[index1][index2].toString()
                                                                                   // + " ");
      }
      System.out.println("");
    }
  }

}