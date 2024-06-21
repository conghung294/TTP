package ea;

import solver.TTPHeuristic;
import ttp.TTP1Instance;
import ttp.TTPSolution;

import utils.Deb;
import utils.GraphHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by kyu on 11/2/15.
 */
public class Initialization extends TTPHeuristic {

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

  /**
   * random tour
   * + 1 kick using LK
   */
  public int[] rlinkern() {

    int nbCities = ttp.getNbCities();
    int[] tour = new int[nbCities];

    String fileName = ttp.getTspName().replaceAll("-.+", "");

    // number of LK kicks
    int nbKicks = 1;
    if (nbCities > 100)
      nbKicks = 10;
    if (nbCities > 1000)
      nbKicks = 80;
    if (nbCities > 10000)
      nbKicks = 500;
    if (nbCities > 30000)
      nbKicks = 800;
    // ...

    // Deb.echo("nb LK kicks: "+nbKicks);
    // if (Thread.currentThread().isInterrupted()) return null;

    try {
      // execute linkern program
      String[] cmd = { "./bins/linkern/rlinkern.sh", fileName, "" + nbKicks };
      Runtime runtime = Runtime.getRuntime();
      Process proc = runtime.exec(cmd);

      proc.waitFor();

      // read output tour
      File tourFile = new File("./bins/linkern/" + fileName + ".tour");
      BufferedReader br = new BufferedReader(new FileReader(tourFile));
      String line;
      br.readLine(); // skip first line
      int i = 0;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split("\\s+");
        tour[i++] = 1 + Integer.parseInt(parts[0]);
      }
      tourFile.delete();
      br.close();

    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return tour;
  }

}
