package solver;

import ttp.TTP1Instance;
import ttp.TTPSolution;
import utils.Deb;
import utils.RandGen;

/**
 * CS2SA algorithm
 *
 * A CoSolver implementation that uses 2-opt
 * for the TSP component and SA for the KP
 * component
 *
 * Created by kyu on 4/7/15.
 */
public class CS2SA extends LocalSearch {

  public double T_abs; // absolute temperature
  public double T0; // initial temperature
  public double alpha; // cooling rate
  public double trialFactor; // number of trials (per temperature)

  public CS2SA() {
    super();
    // use default config
    SAConfig();
  }

  public CS2SA(TTP1Instance ttp) {
    super(ttp);
    // use default config
    SAConfig();
  }

  // SA params config
  // default config
  void SAConfig() {

    int nbItems = ttp.getNbItems();

    T_abs = 1;
    // T0 = 100.0;
    // alpha = 0.95;
    alpha = 0.9578;
    T0 = 98;

    trialFactor = generateTFLinFit(nbItems);
  }

  @Override
  public TTPSolution search() {
    // copy initial solution into improved solution
    TTPSolution sol = s0.clone();
    return sol;
  }

  public static double generateTFLinFit(int xi) {
    int i = -1;

    int[] x = new int[] { 1, 130, 496, 991, 3038, 18512, 75556, 169046, 338090 };
    double[] y = new double[] { 57872, 13896, 700, 350, 16, 1, 0.16, 0.0493, 0.03 };
    int n = y.length;
    for (int k = 0; k < n - 1; k++) {
      if (x[k] <= xi && xi < x[k + 1]) {
        i = k;
        break;
      }
    }
    if (xi <= x[0]) {
      return 57872.0;
    }
    if (xi >= x[n - 1]) {
      return 0.03;
    }

    double m = (y[i] - y[i + 1]) / (x[i] - x[i + 1]);
    double b = y[i] - m * x[i];

    double yi = m * xi + b;

    return yi;
  }

}
