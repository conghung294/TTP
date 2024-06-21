package solver;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import ttp.TTP1Instance;
import utils.ConfigHelper;

public class Constructive extends SearchHeuristic {

  public Constructive() {
    super();
  }

  public Constructive(TTP1Instance ttp) {
    super(ttp);
  }

  /**
   * generate random tour
   * 
   * @return
   */
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

  /**
   * use Lin-Kernighan TSP tour
   * uses hardcoded tours
   */
  public int[] linkernTour() {
    int nbCities = ttp.getNbCities();
    int[] tour = new int[nbCities];

    String fileName = ttp.getTspName();
    String dirName = ConfigHelper.getProperty("lktours");
    fileName += ".linkern.tour";

    File file = new File(dirName + "/" + fileName);
    BufferedReader br = null;

    try {
      br = new BufferedReader(new FileReader(file));
      String line;

      // scan tour
      while ((line = br.readLine()) != null) {

        if (line.startsWith("TOUR_SECTION")) {

          for (int j = 0; j < nbCities; j++) {
            line = br.readLine();
            tour[j] = Integer.parseInt(line);
          }
        }
      } // end while

      br.close();
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return tour;
  }

  /**
   * empty knapsack
   * 
   * @return picking plan filled with zeros
   */
  public int[] zerosPickingPlan() {

    // picking plan
    int[] pp = new int[ttp.getNbItems()];

    for (int i = 0; i < pp.length; i++) {
      pp[i] = 0;
    }
    return pp;
  }

}
