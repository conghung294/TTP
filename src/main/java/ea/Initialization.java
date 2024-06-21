package ea;

import solver.SearchHeuristic;
import ttp.TTP1Instance;
import ttp.TTPSolution;

import utils.GraphHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Initialization extends SearchHeuristic {

  protected HashSet<Integer>[] candidates;

  public Initialization() {
    // generate Delaunay triangulation
    candidates = GraphHelper.delaunayKNN(ttp, 5);
  }

  public Initialization(TTP1Instance ttp) {
    this.ttp = ttp;
    // generate Delaunay triangulation
    candidates = GraphHelper.delaunayKNN(ttp, 5);
  }

  // random initialization procedure
  public int[] randomTour() {

    int nbCities = ttp.getNbCities();
    int[] tour = new int[nbCities];

    for (int i = 0; i < nbCities; i++)
      tour[i] = i + 1;

    int s1, s2, tmp;
    Random generator = new Random();
    for (int i = 1; i < 10000; i++) {
      do {
        s1 = generator.nextInt(nbCities);
        s2 = generator.nextInt(nbCities);
      } while (s1 == s2 || s1 == 0 || s2 == 0);
      // swap cities
      tmp = tour[s1];
      tour[s1] = tour[s2];
      tour[s2] = tmp;
    }
    return tour;
  }

}
