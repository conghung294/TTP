package solver;

import ttp.TTP1Instance;
import ttp.TTPSolution;
import utils.*;

import java.util.HashSet;

public class LocalSearch extends SearchHeuristic {
  public TTPSolution search() {
    // copy initial solution into improved solution
    TTPSolution sol = s0.clone();
    return sol;
  }

  protected TTPSolution s0;
  protected HashSet<Integer>[] candidates;

  public TTPSolution getS0() {
    return s0;
  }

  public boolean firstfit;

  public LocalSearch() {
    super();
  }

  public LocalSearch(TTP1Instance ttp) {
    super(ttp);
    if (!System.getProperty("os.name").contains("Windows")) {
      candidates = GraphHelper.delaunay(ttp);

    }
  }

  @Override
  public void setTTP(TTP1Instance ttp) {
    super.setTTP(ttp);
    if (!System.getProperty("os.name").contains("Windows")) {
      candidates = GraphHelper.delaunay(ttp);
    }
  }

  public void firstfit() {
    firstfit = true;
  }

  public void bestfit() {
    firstfit = false;
  }

  public TTPSolution insertT2(TTPSolution sol) {

    // TTP data
    int nbCities = ttp.getNbCities();
    int nbItems = ttp.getNbItems();
    int[] A = ttp.getAvailability();
    double maxSpeed = ttp.getMaxSpeed();
    double minSpeed = ttp.getMinSpeed();
    long capacity = ttp.getCapacity();
    double C = (maxSpeed - minSpeed) / capacity;
    double R = ttp.getRent();

    // initial solution data
    int[] tour = sol.getTour();
    int[] pickingPlan = sol.getPickingPlan();

    // neighbor solution
    int origBF;
    int i, k, itr;

    // distances of all tour cities (city -> end)
    long[] L = new long[nbCities];
    // current weight
    long wCurr;
    // time approximations
    double t1, t2;

    // store `distance to end` of each tour city
    L[nbCities - 1] = ttp.distFor(tour[nbCities - 1] - 1, 0);
    for (i = nbCities - 2; i >= 0; i--) {
      L[i] = L[i + 1] + ttp.distFor(tour[i + 1] - 1, tour[i] - 1);
    }

    // sort item according to score
    Double[] scores = new Double[nbItems];
    int[] insertedItems = new int[nbItems];

    for (k = 0; k < nbItems; k++) {
      // index where Bit-Flip happened
      origBF = sol.mapCI[A[k] - 1];
      // calculate time approximations
      t1 = L[origBF] * (1 / (maxSpeed - C * ttp.weightOf(k)) - 1 / maxSpeed);
      // affect score to item
      scores[k] = (ttp.profitOf(k) - R * t1) / ttp.weightOf(k);
      // empty the knapsack
      pickingPlan[k] = 0;
    }

    // evaluate solution after emptying knapsack
    ttp.objective(sol);

    // sort items according to score
    Quicksort<Double> qs = new Quicksort<>(scores);
    qs.sort();
    int[] sortedItems = qs.getIndices();

    // loop & insert items
    int nbInserts = 0;
    wCurr = 0;

    for (itr = 0; itr < nbItems; itr++) {

      k = sortedItems[itr];

      // check if new weight doesn't exceed knapsack capacity
      if (wCurr + ttp.weightOf(k) > capacity) {
        continue;
      }

      // index where Bit-Flip happened
      origBF = sol.mapCI[A[k] - 1];

      /* insert item if it has a potential gain */
      // time approximations t2 (worst-case time)
      t2 = L[origBF] * (1 / (maxSpeed - C * (wCurr + ttp.weightOf(k))) - 1 / (maxSpeed - C * wCurr));
      if (ttp.profitOf(k) > R * t2) {
        pickingPlan[k] = A[k];
        wCurr += ttp.weightOf(k);
        insertedItems[nbInserts++] = k;
      }
    } // END FOR k

    // evaluate solution & update vectors
    ttp.objective(sol);

    return sol;
  }

}
