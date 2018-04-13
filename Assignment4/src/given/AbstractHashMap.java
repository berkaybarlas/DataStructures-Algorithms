package given;

import java.util.Random;

// The abstract base class for the hashmap. 
// Some of the fields and methods are provided to help you in your implementation

abstract public class AbstractHashMap<Key, Value> implements iMap<Key, Value>, iPrintable<Key> {

  // Current size of the hashmap
  protected int n;

  // Current capacity of the hashmap
  protected int N;
  
  // The parameters for the Multiply-Add-Divide (MAD) hash compression
  protected int a, b;

  // The prime number to be used in MAD
  protected int P;
  
  // Random generator seed specified to be able to get the same values for everyone
  // This will be used to calculate the parameters of the MAD compression
  Random rgen = new Random(202);

  public int size() {
    return n;
  }

  public int capacity() {
    return N;
  }

  public float loadFactor() {
    return (float) n / N;
  }

  public boolean isEmpty() {
    return n == 0;
  }
  
  /*
   * Calculates the hash value of a key given the probe iteration number. 
   * The iteration does not matter for separate chaining! 
   * 
   * Made public to make debugging and testing easier, it should normally be protected or private
   * 
   */
  abstract public int hashValue(Key key, int iter);
  
  /*
   * Do not forget to overwrite this for separate chaining
   * 
   */
  public int hashValue(Key key) {
    return hashValue(key, 0);
  }
  
  /*
   * Returns the smallest prime larger than n
   * 
   */
  protected static int nextPrime(int n) {
    boolean isPrime = false;

    int start = 2;

    while (!isPrime) {
      n += 1;
      int m = (int) Math.ceil(Math.sqrt(n));

      isPrime = true;
      for (int i = start; i <= m; i++) {
        if (n % i == 0) {
          isPrime = false;
          break;
        }
      }
    }
    return n;
  }
  
  /* 
   * Multiply-Add-Divide (MAD) compression will be used in both of the hashmaps
   * 
   */
  protected void updateHashParams() {
    P = nextPrime(N);
    a = rgen.nextInt(N - 1) + 1; // +1 to never have 0!
    b = rgen.nextInt(N);
  }
}
