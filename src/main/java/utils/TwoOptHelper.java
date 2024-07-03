package utils;

public class TwoOptHelper {

  /**
   * get tour value using 2opt technique
   * 
   * @param k    element index
   * @param tour TSP tour
   * @param i    beginning
   * @param j    end
   * @return the k'th element
   */
  public static int get2optValue(int k, int[] tour, int i, int j) {

    if (k >= i && k <= j) {
      return tour[j - k + i];
    }
    return tour[k];
  }

  /**
   * get tour value using 2opt technique
   */
  public static long get2optValue(int k, long[] vect, int i, int j) {

    if (k >= i && k <= j) {
      return vect[j - k + i];
    }
    return vect[k];
  }

  public static void do2opt(int[] tour, int i, int j) {

    int tmp, N = (j - i + 1) / 2;
    for (int k = i; k < i + N; k++) {
      tmp = tour[k];
      tour[k] = tour[j - k + i];
      tour[j - k + i] = tmp;
    }
  }

}
