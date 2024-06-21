package solver;

import java.util.ArrayList;
import java.util.Collections;
import ttp.TTP1Instance;

public class Constructive extends SearchHeuristic {

  public Constructive() {
    super();
  }

  public Constructive(TTP1Instance ttp) {
    super(ttp);
  }

  public int[] randomTour() {

    int[] tour = new int[ttp.getNbCities()];

    ArrayList<Integer> li = new ArrayList<>(tour.length - 1);
    for (int i = 0; i < tour.length - 1; i++) {
      li.add(i, i + 2);
    }
    Collections.shuffle(li);

    tour[0] = 1;
    for (int i = 1; i < tour.length; i++) {
      tour[i] = li.get(i - 1);
    }
    return tour;
  }

  public int[] zerosPickingPlan() {

    // picking plan
    int[] pp = new int[ttp.getNbItems()];

    for (int i = 0; i < pp.length; i++) {
      pp[i] = 0;
    }
    return pp;
  }

}
