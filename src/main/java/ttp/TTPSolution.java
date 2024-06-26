package ttp;

import java.util.Arrays;

public class TTPSolution {

  private int[] tour;
  private int[] pickingPlan;

  public long fp;
  public double ft;
  public double ob;
  public long wend;

  // time accumulator
  public double[] timeAcc;
  // time record
  public double[] timeRec;
  // weight accumulator
  public long[] weightAcc;
  // weight record at each iteration
  public long[] weightRec;
  // tour mapper
  public int[] mapCI;

  private void initSolution(int[] tour, int[] pickingPlan) {
    this.tour = tour;
    this.pickingPlan = pickingPlan;

    // records
    this.timeAcc = new double[this.tour.length];
    this.timeRec = new double[this.tour.length];
    this.weightAcc = new long[this.tour.length];
    this.weightRec = new long[this.tour.length];
    this.mapCI = new int[this.tour.length];
  }

  public TTPSolution() {

  }

  public TTPSolution(int[] tour, int[] pickingPlan) {
    initSolution(tour, pickingPlan);
  }

  public TTPSolution(int m, int n) {

    this.tour = new int[m];
    this.pickingPlan = new int[n];

    // records
    timeAcc = new double[tour.length];
    timeRec = new double[tour.length];
    weightAcc = new long[tour.length];
    weightRec = new long[tour.length];
    mapCI = new int[tour.length];
  }

  public TTPSolution(TTPSolution s2) {

    this.tour = Arrays.copyOf(s2.tour, s2.tour.length);
    this.pickingPlan = Arrays.copyOf(s2.pickingPlan, s2.pickingPlan.length);

    this.fp = s2.fp;
    this.ft = s2.ft;
    this.ob = s2.ob;
    this.wend = s2.wend;

    this.timeAcc = Arrays.copyOf(s2.timeAcc, s2.timeAcc.length);
    this.timeRec = Arrays.copyOf(s2.timeRec, s2.timeRec.length);
    this.weightAcc = Arrays.copyOf(s2.weightAcc, s2.weightAcc.length);
    this.weightRec = Arrays.copyOf(s2.weightRec, s2.weightRec.length);
    this.mapCI = Arrays.copyOf(s2.mapCI, s2.mapCI.length);
  }

  @Override
  public String toString() {
    // the tour
    String s = "tsp tour    : (";
    for (int i = 0; i < tour.length; i++) {
      s += tour[i] + " ";
    }
    s = s.substring(0, s.length() - 1) + ")\n";

    // the picking plan
    s += "picking plan: (";
    for (int i = 0; i < pickingPlan.length; i++) {
      int pp = pickingPlan[i];
      s += pp + " ";
    }
    s = s.substring(0, s.length() - 1) + ")";

    return s;
  }

  @Override
  public TTPSolution clone() {
    return new TTPSolution(this);
  }

  @Override
  public boolean equals(Object o2) {

    TTPSolution s2 = (TTPSolution) o2;

    for (int i = 0; i < this.tour.length; i++) {
      if (this.tour[i] != s2.tour[i])
        return false;
    }
    for (int i = 0; i < this.pickingPlan.length; i++) {
      if (this.pickingPlan[i] != s2.pickingPlan[i])
        return false;
    }

    return true;
  }

  // getters
  public int[] getTour() {
    return tour;
  }

  public int[] getPickingPlan() {
    return pickingPlan;
  }

  // setters
  public void setTour(int[] tour) {
    this.tour = tour;
  }

  public void setPickingPlan(int[] pickingPlan) {
    this.pickingPlan = pickingPlan;
  }

  public void printStats() {

    System.out.println("============");
    System.out.println(" STATISTICS ");
    System.out.println("============");
    System.out.println("objective   : " + this.ob);
    System.out.println("final time  : " + this.ft);
    System.out.println("final weight: " + this.wend);
    System.out.println("final profit: " + this.fp);

    int cmpItems = 0;
    int nbItems = this.pickingPlan.length;
    for (int x : this.pickingPlan)
      if (x != 0)
        cmpItems++;
    System.out.println("percent inserted: " + cmpItems + "/" + nbItems + "(" +
        String.format("%.2f", (cmpItems * 100.0) / nbItems) + "%)");
    System.out.println("============");
  }
}
