package ea;

import ttp.TTP1Instance;

import ttp.TTPSolution;
import utils.Deb;
import utils.RandGen;

import java.util.Random;
import java.util.TreeSet;

/**
 * Created by kyu on 12/6/15.
 */
public class Mutation {

  // mutate using double bridge
  public static int[] doubleBridge(int[] sol) {

    int j, k, l;
    int n = sol.length;
    int[] newsol = new int[n];

    j = RandGen.randInt(n / 4, n / 2 - 1);
    k = RandGen.randInt(n / 2, 3 * n / 4 - 1);
    l = RandGen.randInt(3 * n / 4, n - 2);

    // construct mutated solution
    newsol[0] = sol[0];
    int u = 1;

    // part A
    for (int i = k + 1; i <= l; i++, u++) {
      newsol[u] = sol[i];
    }
    // part B
    for (int i = j + 1; i <= k; i++, u++) {
      newsol[u] = sol[i];
    }
    // part C
    for (int i = 1; i <= j; i++, u++) {
      newsol[u] = sol[i];
    }
    // part D
    for (int i = l + 1; i < n; i++, u++) {
      newsol[u] = sol[i];
    }

    return newsol;
  }

  public static TTPSolution randomFlips(TTPSolution sol, double strength, TTP1Instance ttp) {
    TTPSolution copy = sol.clone();
    int[] pp = sol.getPickingPlan();
    int[] A = ttp.getAvailability();
    int nbBits = (int) strength * pp.length;
    int r;
    for (int i = 0; i < nbBits; i++) {
      r = RandGen.randInt(0, pp.length - 1);
      pp[r] = pp[r] != 0 ? 0 : A[r];
    }
    ttp.objective(sol);
    if (sol.wend < 0) {
      Deb.echo("BIG PROBLEM ! STOP MUTATION, RECOVER SAVED COPY...");
      sol = copy;
    }
    return sol;
  }

}
