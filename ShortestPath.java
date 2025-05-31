
/**
 * 
 * A class to calculate the shortest path (cheapest route) between two airports using
 * Dijkstra's single source shortest path algorithm.
 * 
 * adapted from : https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/
 */
import java.io.*;
import java.lang.*;
import java.util.*;

class ShortestPath {
  // A utility function to find the vertex with minimum
  // distance value, from the set of vertices not yet
  // included in shortest path tree

  static ArrayList<Airport> airportIndexes = new ArrayList<Airport>();

  // necessary:
  static Graph map = createGraph("testairports.txt");

  static Object[] keysog = map.keySet();

  static int[][] allPrices = new int[keysog.length][keysog.length];

  static final int V = map.size();

  static LinkedList<Airport> airportKeys = mergeSort(keysog);

  static Object[] keys = airportKeys.toArray();

  // MAIN METHOD
  public static void main(String[] args) {
    checkAlgo();

  } // end of main

  /**
   * Calculates minimum distance
   * 
   * @param dist
   * @param sptSet
   * @return min_index
   */
  public static int minDistance(int dist[], Boolean sptSet[]) {
    // Initialize min value
    int min = Integer.MAX_VALUE, min_index = -1;

    for (int v = 0; v < V; v++)
      if (sptSet[v] == false && dist[v] <= min) {
        min = dist[v];
        min_index = v;
      }

    return min_index;
  }

  /**
   * Print solution without returning a cost value. A utility function to print
   * the constructed distance array.
   * 
   * @param dist
   */
  public static void printSolutionAll(int dist[]) {
    System.out.println(
        "Airport \t\t Cheapest Cost from Source Airport");
    for (int i = 0; i < V; i++) {
      // System.out.println(i + " \t\t " + dist[i]);
      // System.out.println(airportIndexes.get(i) + " \t\t " + dist[i]);
      int diff = 70 - airportIndexes.get(i).toString().length();
      String spaces = "";
      if (diff < 1) {
        spaces = "\t";
      } else {
        for (int j = 0; j < diff; j++) {
          spaces = spaces + " ";
        }
      }
      System.out.println(airportIndexes.get(i) + spaces + dist[i]);
      // displayAirportCostAnalysis(airportIndexes.get(i), dist[i]);
    }
  }

  /**
   * Prints solution and returns cost value.
   * Printing cheapest route
   * 
   * @param dist
   * @param d
   * @return cost
   */
  public static int printSolution(int dist[], int dest) {
    System.out.println("Airport \t\t\t\t Cheapest Cost from Source Airport");
    for (int i = 0; i < V; i++) {
      if (i == dest) {
        int diff = 70 - airportIndexes.get(i).toString().length();
        String spaces = "";
        if (diff < 1) {
          spaces = "\t";
        } else {
          for (int j = 0; j < diff; j++) {
            spaces = spaces + " ";
          }
        }
        System.out.println(airportIndexes.get(i) + spaces + dist[i]);
        return dist[i];
      }
      // displayAirportCostAnalysis(airportIndexes.get(i), dist[i]);
    }
    return 0;
  }

  /**
   * Cheapest route algorithm that implements Dijkstra's
   * single source shortest path algorithm for a graph represented
   * using adjacency matrix representation.
   * 
   * @param graph
   * @param src
   * @param dest
   * @return cost
   */
  public static int dijkstra(int graph[][], int src, int dest) {
    int dist[] = new int[V]; // The output array.
                             // dist[i] will hold
    // the shortest distance from src to i

    // sptSet[i] will true if vertex i is included in
    // shortest path tree or shortest distance from src
    // to i is finalized
    Boolean sptSet[] = new Boolean[V];

    // Initialize all distances as INFINITE and stpSet[]
    // as false
    for (int i = 0; i < V; i++) {
      dist[i] = Integer.MAX_VALUE;
      sptSet[i] = false;
    }

    // Distance of source vertex from itself is always 0
    dist[src] = 0;

    // Find shortest path for all vertices
    for (int count = 0; count < V - 1; count++) {
      // Pick the minimum distance vertex from the set
      // of vertices not yet processed. u is always
      // equal to src in first iteration.
      int u = minDistance(dist, sptSet);

      // Mark the picked vertex as processed
      sptSet[u] = true;

      // Update dist value of the adjacent vertices of
      // the picked vertex.
      for (int v = 0; v < V; v++)

        // Update dist[v] only if is not in sptSet,
        // there is an edge from u to v, and total
        // weight of path from src to v through u is
        // smaller than current value of dist[v]
        if (!sptSet[v] && graph[u][v] != 0
            && dist[u] != Integer.MAX_VALUE
            && dist[u] + graph[u][v] < dist[v])
          dist[v] = dist[u] + graph[u][v];
    }

    // print the constructed distance array
    int cost = printSolution(dist, dest);
    return cost;
  }

  /**
   * Long code from Main -- just holding it inside of a method.
   */
  public static void checkAlgo() {
    Airport source = getRandomAirport(map);
    Airport destination = getRandomAirport(map);

    System.out.println("Source: " + source);
    System.out.println("Destination: " + destination);

    System.out.println("SORTED KEYS OF TYPE AIRPORT");
    // for(Airport a : airportKeys) {
    // System.out.println(a.toString());
    // }

    for (Object b : keys) {
      System.out.println(b.toString());
    }

    // this generates a 2d array of the costs
    for (int i = 0; i < keys.length; i++) {
      Airport current = (Airport) keys[i];
      for (int j = 0; j < keys.length; j++) {
        if (i == j || map.get(current).get((Airport) keys[j]) == null) {
          allPrices[i][j] = 0;
        } else {
          allPrices[i][j] = map.get(current).get((Airport) keys[j]);
        }
      }
    }

    // prints out the items in keys
    int o = 0;
    for (Object k : keys) {
      System.out.println(o + k.toString());
      o++;
    }

    // finds the shortest distance from each airport for every source
    for (int i = 0; i < allPrices.length; i++) {
      Airport current = (Airport) keys[i];

    }

    System.out.println(Arrays.deepToString(allPrices));

    System.out.println("\n\n" + source);
    System.out.println(destination);
  }

  /**
   * MergeSort - sorts a list of objects (assumed to be airports)
   * 
   * @param arr
   */
  public static LinkedList<Airport> mergeSort(Object[] arr) {

    ArrayDeque<LinkedList<Airport>> queue = new ArrayDeque<LinkedList<Airport>>();

    // add each element in arr to its own arraylist, and tehn a to a queue
    for (Object object : arr) {
      Airport airport = (Airport) object;
      LinkedList<Airport> airportList = new LinkedList<Airport>();
      airportList.add(airport);
      queue.add(airportList);
    }

    while (queue.size() > 1) {
      // GET TWO LISTS TO MERGE
      LinkedList<Airport> li1 = queue.removeLast();
      LinkedList<Airport> li2 = queue.removeLast();

      LinkedList<Airport> merged = new LinkedList<Airport>();

      // MERGE
      while (li1.size() > 0 && li2.size() > 0) {
        // if li1 comes first, add l1
        if (li1.getFirst().getIATA().compareTo(li2.getFirst().getIATA()) < 0) {
          merged.add(li1.removeFirst());
        } else { // otherwise add l2
          merged.add(li2.removeFirst());
        }

      }

      while (li1.size() > 0) {
        merged.add(li1.removeFirst());
      }
      while (li2.size() > 0) {
        merged.add(li2.removeFirst());
      }

      // MERGE FINISHED

      // queue.push(merged);
      queue.add(merged);

    }

    // return the sorted result here
    return queue.remove();
  } // end of mergesort

  // other class methods:

  /**
   * !!!Not used in game. Was used to build and test shortest path algorithm in
   * this file.!!!
   * Pulls data from filename and creates graph from given information in data
   * frame.
   * 
   * @param filename
   * @return graph
   */
  public static Graph createGraph(String filename) {
    Graph map = new Graph();
    Scanner file = null;
    boolean default_map = false;
    try {
      file = new Scanner(new File(filename));
    } catch (FileNotFoundException e) {
      System.err.println("Cannot locate file.");
      System.out.println("Default map created.");
      // System.exit(-1);
      Airport lax = new Airport("LAX", "Los Angeles International Airport", "Los Angeles, CA", "United States", 'L');
      Airport jfk = new Airport("JFK", "John F Kennedy Airport", "New York City, NY", "United States", 'L');
      Airport bdl = new Airport("BDL", "Bradley Intl Airport", "Hartford, CT", "United States", 'M');
      Airport bgr = new Airport("BGR", "Bangor Intl. Airport", "Bangor, ME", "United States", 'S');

      map.addAirport(lax);
      map.addAirport(jfk);
      map.addAirport(bdl);
      map.addAirport(bgr);
      map.addRoute(lax, jfk);
      map.addRoute(lax, bdl);
      map.addRoute(bdl, jfk);
      map.addRoute(bdl, bgr);
      map.addRoute(lax, bgr);
      map.addRoute(jfk, bgr);

      default_map = true;
    }

    if (!default_map) {
      ArrayList<Airport> keys = new ArrayList<Airport>();
      while (file.hasNextLine()) {
        String[] line = file.nextLine().split(",");
        // System.out.println(line);
        // List<E> lineArr = new ArrayList<>(9); // why don't my generic arrays and
        // arraylists work <-- ask at TA hours
        if (line.length == 5) {
          Airport a1 = new Airport(line[0], line[1], line[2], line[3], line[4].charAt(0));
          keys.add(a1);
          // we need to figure out how we want to add connections.
          // options - create a method to do it manually
          // or within graph.addRoute we create the airports there... complicated here.
          map.addAirport(a1);
          airportIndexes.add(a1); // adding to indexes
        } else {
          System.out.println("Incomplete data for airport: " + line[1] + ". Airport omitted.");
          continue;
        }
      }

      file.close();

      // assigning/connecting routes... ideally we would have another method that
      // randomizes routes and
      // doesn't just connect evrything, but it works for now because the prices are
      // different each round.
      for (Airport i : keys) {
        for (Airport j : keys) {
          if (!i.equals(j)) {
            map.addRoute(i, j);
          }
        }
      }

    }
    return map;

  } // end file read method

  /**
   * !!!Not used in game. Was used to build and test shortest path algorithm in
   * this file.!!!
   * Returns a random airport
   * 
   * @return Airport
   */
  public static Airport getRandomAirport(Graph map) {
    Object[] airports = map.keySet();
    Random generator = new Random();
    Airport randomAirport = (Airport) airports[generator.nextInt(airports.length)];
    return randomAirport; // @FLAG@
  }

}
