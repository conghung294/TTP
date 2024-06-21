package ea;

import solver.SearchHeuristic;
import ttp.TTPSolution;

public class Population extends SearchHeuristic {

  // population of solutions
  public TTPSolution[] sol;

  // create population
  public Population(int popSize) {
    sol = new TTPSolution[popSize];
  }

  @Override
  public String toString() {
    String str = "";
    for (TTPSolution t : sol) {
      str += t + "\n";
    }
    return str;
  }

  // fittest solution
  public TTPSolution fittest() {
    TTPSolution fittest = sol[0];
    for (int i = 1; i < sol.length; i++) {
      if (sol[i].ob > fittest.ob) {
        fittest = sol[i];
      }
    }
    return fittest;
  }

}
