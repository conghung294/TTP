package solver;

import ea.Population;
import ttp.TTP1Instance;

public abstract class Params extends SearchHeuristic {

  public Params() {
    super();
  }

  public Params(TTP1Instance ttp) {
    super(ttp);
  }

  public static final int MAX_GEN = 100000;
  public static final int MAX_IDLE_STEPS = 10000;

  // GA params
  public static final double MUTATION_RATE = .2;
  public static final double MUTATION_STRENGTH_PP = .1;

  public static final double SELECTION_RATE = .75;
  public static final int POP_SIZE = 100;
  public static final int TOURNAMENT_SIZE = 6;

  // MA params
  public static final double LS_RATE = .2;

  protected Population pop;

}
