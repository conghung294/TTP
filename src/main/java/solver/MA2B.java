package solver;

import ea.*;
import ttp.TTP1Instance;
import ttp.TTPSolution;
import utils.Quicksort;

public class MA2B extends Evolution {

  public MA2B() {
    super();
  }

  public MA2B(TTP1Instance ttp) {
    super(ttp);
  }

  public TTPSolution search() {

    // determine GA params
    // number of selected individuals
    int selectSize = (int) (SELECTION_RATE * POP_SIZE);
    int nbCities = ttp.getNbCities();
    int nbItems = ttp.getNbItems();

    // generate initial population
    // to construct initial solutions
    Constructive construct = new Constructive(ttp);
    Initialization init = new Initialization(ttp);

    // current population & offspring
    pop = new Population(Evolution.POP_SIZE);
    Population offpop = new Population(selectSize);
    int offpopSize;

    // initialize one using LK
    pop.sol[0] = new TTPSolution(
        construct.randomTour(),
        construct.zerosPickingPlan());

    for (int i = 1; i < POP_SIZE; i++) {
      pop.sol[i] = new TTPSolution(
          init.randomTour(),
          construct.zerosPickingPlan());
      // sleep for 2 ms to randomize
      try {
        Thread.sleep(2);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }

    // use local search
    LocalSearch ls = new LocalSearch(ttp);
    // reduce LS time
    ls.maxIterTSKP = 50;
    ls.maxIterKRP = 50;
    ls.firstfit();
    // apply LS for all
    for (int i = 0; i < POP_SIZE; i++) {
      ttp.objective(pop.sol[i]);

      // initialize pp
      pop.sol[i] = ls.insertT2(pop.sol[i]);
      // simple bit-flip on KRP
      if (nbItems < 100000)
        pop.sol[i] = ls.lsBitFlip(pop.sol[i]);

    }

    // start EA search
    double bestSoFar = Double.MIN_VALUE;
    int nbGen = 0;
    int nbIdleSteps = 0;
    // max iteration in LS
    ls.maxIterTSKP = 10;
    ls.maxIterKRP = 10;
    do {

      nbGen++;
      nbIdleSteps++;

      // get & sort fitness, use indices
      Double[] fits = new Double[POP_SIZE];
      for (int i = 0; i < POP_SIZE; i++) {
        fits[i] = pop.sol[i].ob;
      }
      Quicksort<Double> qs = new Quicksort<>(fits);
      qs.sort();
      int[] idx = qs.getIndices();

      // get fittest
      TTPSolution fittest = pop.sol[idx[0]];
      // if improvement is made
      if (fittest.ob > bestSoFar) {
        bestSoFar = fittest.ob;
        nbIdleSteps = 0;
      }

      // stop execution if interrupted (runtime limit: 600sec)
      if (Thread.currentThread().isInterrupted() || pop.sol[idx[POP_SIZE - 1]].ob == fittest.ob)
        return fittest;

      int j = POP_SIZE - 1;
      offpopSize = 0;

      // Genetic evolution
      for (int i = 0; i < selectSize; i++) {

        /* Select parents */
        TTPSolution[] p = Selection.tournament(pop);

        /* Crossover parents */
        TTPSolution c = Cross.crossover(p[0], p[1], ttp);
        ttp.objective(c);

        /* Apply local search */
        double lsp = Math.random();
        if (lsp < LS_RATE) {

          c = ls.fast2opt(c);
          c = ls.lsBitFlip(c);
        }

        /* add to offspring population */
        // check if already in offpop
        boolean identical = false;
        for (int k = 0; k < POP_SIZE; k++) {
          // either tour or picking plan is identical
          if (pop.sol[k].ob == c.ob) {
            identical = true;
            break;
          }
          if (k < offpopSize && offpop.sol[k].ob == c.ob) {
            identical = true;
            break;
          }
        }

        // if not existent
        if (!identical) {

          // add to offspring population
          offpop.sol[offpopSize++] = c;
        }
        // use mutation to eliminate premature convergence
        else {

          int[] x;
          x = Mutation.doubleBridge(c.getTour());
          x = Mutation.doubleBridge(x);
          c.setTour(x);

          // create new pick plan
          c = ls.insertT2(c);
          c = ls.fast2opt(c);
          c = ls.lsBitFlip(c);

          offpop.sol[offpopSize++] = c;

        }
      }

      // Add offspring to population

      for (int i = 0; i < offpopSize; i++) {
        TTPSolution c = offpop.sol[i];
        // replace worst solutions
        pop.sol[idx[j--]] = c;
      }

      // stop when no improvements
    } while (nbGen < MAX_GEN && nbIdleSteps < MAX_IDLE_STEPS);

    // apply LS for all

    ls.maxIterTSKP = 300;
    ls.maxIterKRP = 300;
    for (int i = 0; i < POP_SIZE; i++) {

      // 2-opt heuristic on TSKP
      pop.sol[i] = ls.fast2opt(pop.sol[i]);
      // simple bit-flip on KRP
      pop.sol[i] = ls.lsBitFlip(pop.sol[i]);

    }

    return pop.fittest();
  }
}
