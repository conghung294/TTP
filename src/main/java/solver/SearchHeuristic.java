package solver;

import ttp.TTP1Instance;

public abstract class SearchHeuristic {

  protected int maxIterTSKP = Integer.MAX_VALUE;
  protected int maxIterKRP = Integer.MAX_VALUE;

  protected TTP1Instance ttp;
  protected String name;

  public SearchHeuristic() {
    this.name = this.getClass().getSimpleName();

  }

  public SearchHeuristic(TTP1Instance ttp) {
    this();
    this.ttp = ttp;
  }

  // heuristic name
  public void setName(String logfile) {
    this.name = logfile;
  }

  public String getName() {
    return name;
  }

  // TTP access
  public void setTTP(TTP1Instance ttp) {
    this.ttp = ttp;
  }

  public TTP1Instance getTTP() {
    return ttp;
  }
}
