package ttp;

import java.io.*;
import java.util.ArrayList;
import utils.CityCoordinates;

public abstract class TTPInstance {

  protected String name;
  protected String directory;
  protected String tspName;

  protected String knapsackDataType;
  protected int nbCities;
  protected int nbItems;
  protected long capacity;
  protected double minSpeed;
  protected double maxSpeed;
  protected String edgeWeightType;
  protected CityCoordinates[] coordinates;
  protected long[][] dist = null;
  protected int[] availability;
  protected int[] profits;
  protected int[] weights;

  protected File ttpFile;

  // item clusters per city
  protected ArrayList<Integer>[] clusters;

  public void setDist(long[][] dist) {
    this.dist = dist;
  }

  public String getTspName() {
    return tspName;
  }

  public String getDirectory() {
    return directory;
  }

  public String getName() {
    return name;
  }

  public long[][] getDist() {
    return dist;
  }

  public int[] getAvailability() {
    return availability;
  }

  public int[] getWeights() {
    return weights;
  }

  public int[] getProfits() {
    return profits;
  }

  public int getNbCities() {
    return nbCities;
  }

  public int getNbItems() {
    return nbItems;
  }

  public double getMaxSpeed() {
    return maxSpeed;
  }

  public double getMinSpeed() {
    return minSpeed;
  }

  public long getCapacity() {
    return capacity;
  }

  public ArrayList<Integer>[] getClusters() {
    return clusters;
  }

  public CityCoordinates[] getCoordinates() {
    return coordinates;
  }

  public int profitOf(int i) {
    return this.profits[i];
  }

  public int weightOf(int i) {
    return this.weights[i];
  }

  // used to simulate the distance matrix
  public long distFor(int i, int j) {
    if (dist == null) {
      return (long) Math.ceil(this.coordinates[i].distanceEuclid(this.coordinates[j]));
    }
    return dist[i][j];
  }

  /**
   * organize items per city
   */
  public void clusterItems() {

    clusters = new ArrayList[nbCities];
    int i;

    // init cluster arrays
    for (i = 0; i < nbCities; i++) {
      clusters[i] = new ArrayList<>();
    }
    // fill clusters
    for (i = 0; i < nbItems; i++) {
      clusters[availability[i] - 1].add(i);
    }
  }

}
