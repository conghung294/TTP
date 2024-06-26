package solver;

import ea.Population;
import ttp.TTP1Instance;

public abstract class Evolution extends SearchHeuristic {

  public Evolution() {
    super();
  }

  public Evolution(TTP1Instance ttp) {
    super(ttp);
  }

  public static final int MAX_GEN = 10000;
  public static final int MAX_IDLE_STEPS = 1000;

  // GA params
  public static final double MUTATION_RATE = .2;
  public static final double MUTATION_STRENGTH_PP = .1;

  public static final double SELECTION_RATE = .75;
  public static final int POP_SIZE = 40;
  public static final int TOURNAMENT_SIZE = 6;

  // MA params
  public static final double LS_RATE = .2;

  protected Population pop;

}
