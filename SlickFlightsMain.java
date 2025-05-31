import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Main class for text-based Slick Flights. [Run this class/file]
 * 
 * @author Chaira Harder and Sirohi Kumar
 *
 */

public class SlickFlightsMain {

  /**
   * Class fields.
   */
  private static Graph map = createGraph("testairports.txt");

  private static Object[] keysog = map.keySet();

  private static int[][] allPrices = new int[keysog.length][keysog.length];

  private static final int V = map.size();

  private static LinkedList<Airport> airportKeys = ShortestPath.mergeSort(keysog);

  private static Object[] keys = airportKeys.toArray();

  /**
   * MAIN METHOD FOR SLICK FLIGHTS
   * 
   * @param args (SYSTEM INPUT)
   */
  public static void main(String[] args) {

    // FILLING IN 2D COST ARRAY
    generateAllCosts();

    // CREATING SCANNER FOR INPUT USER
    Scanner input = new Scanner(System.in);

    System.out.println("Welcome to Slick Flights\u2122\nStart Game? ");
    String user_resp = input.nextLine();

    // @FLAG@ change path in replit
    // Graph map =
    // createGraph("/Users/chairaharder/eclipse-workspace/DataStructures/src/testairports.txt");
    // REPLIT VERSION: (uncomment below) (comment out the above)
    // Graph map = createGraph("testairports.txt");
    // OTHER PEOPLE'S COMPUTER VERSION: (comment out both above)
    // Graph map = createGraph("PUT YOUR TESTAIRPORTS FILE PATH HERE"); // <- one
    // way to find the path: view below
    // Graph map = createGraph("testairports.txt");

    /**
     * ON A MAC OS SYSTEM:
     * HOLD THE OPTION BUTTON ON YOUR KEYBOARD WHILE
     * RIGHT CLICK ON THE FILE CALLED 'testairports.txt' ON YOUR COMPUTER
     * CLICK 'Copy testairoprts.txt as pathname'
     * PASTE THAT INTO THE CODE ABOVE IN createGraph() in between the ""
     */

    // SETTING A STARTING AND DESTINATION LOCATION -- DISTINGUISHED
    Airport start_loc = map.getRandomAirport();
    Airport destination = start_loc;
    while (start_loc.equals(destination)) {
      destination = map.getRandomAirport();
    }
    Airport current_loc = start_loc;
    // newAirports -- generate new airports each round?
    boolean newAirports = false;

    // OUTER GAME LOOP
    while (validate(user_resp)) {

      // GENERATE NEW AIRPORT SOURCE AND DESTINATION PAIR IF USER SPECIFIES
      if (newAirports) {
        start_loc = map.getRandomAirport();
        destination = start_loc;
        while (start_loc.equals(destination)) {
          destination = map.getRandomAirport();
        }
      }
      current_loc = start_loc;

      // route -- USER PICKED AIRPORTS MAKE UP ROUTE ARARYLIST
      ArrayList<Airport> route = new ArrayList<Airport>();
      route.add(start_loc);

      // DISPLAY ROUND OBJECTIVE.
      System.out.println("\tYOUR CURRENT LOCATION IS BASED OUT OF " + start_loc + ".\n");
      System.out.println("LOCAL CLIENT: I need help finding the cheapest flight(s) to " + destination.getCity()
          + "! I need to fly into " + destination + ".");
      System.out.println("\n\tTASK: FIND THE CHEAPEST ROUTE FROM " + start_loc + " TO " + destination + ".");

      // REPEATS UNTIL DESTINATION IS FOUND OR GAME IS QUIT.
      while (!current_loc.equals(destination)) {
        // PRINT ALL DESTINATIONS FROM CURRENT LOCATION
        System.out.println("\n" + current_loc + " HAS THE FOLLOWING FLIGHTS: ");
        map.printDestinations(current_loc);

        // SELECT AIRPORT TO MOVE TO
        System.out.println("\nPLEASE SELECT ONE OF THE FLIGHTS ABOVE BY TYPING IN THE THREE DIGIT IATA CODE: ");
        user_resp = input.nextLine();

        // CHECK FOR QUIT
        if (user_resp.equals("quit")) {
          System.out.println("Exiting game");
          return;
        }

        // VALIDATES USER INPUT -- MAKES SURE USER PICKS A VALID ROUTE
        while (map.getAirport(user_resp.toUpperCase()) == null
            || map.getAirport(user_resp.toUpperCase()).equals(current_loc)) {
          System.out.println("Sorry, that IATA code was invalid."
              + "\nPLEASE SELECT ONE OF THE FLIGHTS ABOVE BY TYPING IN THE THREE DIGIT IATA CODE: ");
          user_resp = input.nextLine();

          // CHECK FOR QUIT
          if (user_resp.equals("quit")) {
            System.out.println("Exiting game");
            return;
          }
        }

        // IF USER TRIES TO MOVE TO A PREVIOUSLY VISITED AIRPORT, CHECK AND ASK USER TO
        // CONFIRM THIS MOVE
        if (route.contains(map.getAirport(user_resp.toUpperCase()))) {
          System.out.println(
              "Are you sure you want to visit this airport? You have been here already. You will still be charged.");
          String yn = input.nextLine();

          // CONFIRM USER WANTS TO REVISIT AN AIRPORT AND ADD THE AIRPORT TO THE ROUTE
          // AGAIN
          if (validate(yn)) {
            current_loc = map.getAirport(user_resp.toUpperCase());
            route.add(current_loc);
          }
        } else { // CASE: USER SELECTS AND VISITS AN AIRPORT THEY HAVE NOT PREVIOUSLY SELECTED
          current_loc = map.getAirport(user_resp.toUpperCase());
          route.add(current_loc);

        }

        // UPDATE USER WITH UPDATED LOCATION AND REMIND USER OF DESTINATION
        displayLocation(current_loc);
        displayDestination(destination);
      }
      // DISPLAY WHEN USER COMPLETES ROUTE
      System.out.println(" 🥳 ROUTE COMPLETED 🥳 !");

      // DISPLAY ROUND ANALYTICS: TELL THE USER WHERE THEY WENT AND THE ROUTE THEY
      // TOOK
      displayRoute(route, map);
      // CALCULATES >> USER'S << SELECTED CALCULATED COST (TOTAL OF ALL FLIGHTS
      // SELECTED)
      Integer user_cost = calculateCost(route, map);

      // SHOWING SOLUTION
      System.out.println("This route could be completed for : [view figure on line below]");
      // CALCULATE THE CHEAPEST COST USING DIJKSTRA'S SHORTEST PATH ALGORITHM
      int bestCost = calculateCheapestCost(map, start_loc, destination);
      // DISPLAY WIN OR LOSS BASED ON PRICE DIFFERENCE
      if (user_cost - bestCost <= 0) {
        System.out.println("\n 🎉 Congratulations! You found the best and cheapest route! 🎉 \n");
      } else {
        System.out.println("\nYOU LOST BY A DIFFERENCE OF $" + (user_cost - bestCost) + "\n");
      }

      // ASK USER IF NEW ROUND TO BE PLAYED
      System.out.println("\nTry again (new round)?");
      user_resp = input.nextLine();

      // IF YES, THEN USER DECIDES IF THEY WANT A NEW PAIR OF AIRPORTS OR TRY AGAIN
      // WITH THE PREVIOUS PAIR
      if (validate(user_resp)) {
        System.out.println("Would you like to play with a new set of airports?");
        String airportresponse = input.nextLine();
        newAirports = validate(airportresponse);
      }
    }

    // WHEN GAME QUITS OR ROUND ENDS
    System.out.println("Thank you for playing!");

    // CLOSING SCANNER OBJECT
    input.close();
  }

  /**
   * Display current location
   * 
   * @param curLoc
   */
  public static void displayLocation(Airport curLoc) {
    System.out.println("\tYOUR CURRENT LOCATION IS NOW " + curLoc + ".");
  }

  /**
   * Display current destination. Used to remind user of destination airport.
   * 
   * @param dest
   */
  public static void displayDestination(Airport dest) {
    System.out.println("\tYOU ARE TRYING TO GET TO " + dest + ".\n");
  }

  /**
   * Validate a "yes" from a user response
   * 
   * @param s
   * @return True/False
   */
  public static boolean validate(String s) {
    return Arrays.asList("yes", "sure", "ok", "ye", "y", "yeah", "yup", "yse", "ya", "start", "okay", "k")
        .contains(s.toLowerCase());
  }

  /**
   * Given a route (list) and map object, calculates the cost of trip.
   * 
   * @param route
   * @param map
   * @return cost
   */
  public static Integer calculateCost(ArrayList<Airport> route, Graph map) {
    Integer cost = 0;
    Integer length = route.size();

    // conditions for if the list is too short
    if (length == 0 || length == 1) {
      System.out.println("This route is too short for us to calculate ):");
      return -1;
    }

    for (int i = 0; i < length - 1; i++) {
      Airport current = route.get(i);
      Airport next = route.get(i + 1);

      cost += map.get(current).get(next);
    }

    Airport start = route.get(0);
    Airport destination = route.get(length - 1);

    System.out.println(
        "\nYour chosen route from \n\t" + start.toString() + " --> " + destination.toString() + " \n\tcost $" + cost);
    return cost;
  }

  /**
   * Displays route of airports selected by user during current round.
   * 
   * @param route
   * @param map
   */
  public static void displayRoute(ArrayList<Airport> route, Graph map) {
    System.out.println("\tstarted at " + route.get(0).toString());
    for (int i = 0; i < route.size() - 1; i++) {
      Airport current = route.get(i);
      Airport next = route.get(i + 1);

      Integer cost = map.get(current).get(next);
      if (i == route.size() - 2)
        System.out.println("\tlast flight, to " + next.toString() + " cost $" + cost);
      else
        System.out.println("\t↪ going to " + next.toString() + " cost $" + cost);

    }
    System.out.println("\nYou went to " + route.size() + " airports on this journey");

  }

  /**
   * Calculates the cheapest cost from one source airport to the final destination
   * using Dijkstra's algorithm.
   * 
   * @param map
   * @param source
   * @param destination
   * @return cost
   */
  public static int calculateCheapestCost(Graph map, Airport source, Airport destination) {

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

    // CALCULATING THE CHEAPEST PATH
    int src = 0;
    int dest = 0;
    for (int i = 0; i < allPrices.length; i++) {
      Airport current = (Airport) keys[i];
      if (current.equals(source)) {
        src = i;
        break;
      } else if (current.equals(destination)) {
        dest = i;
      }
    }
    return ShortestPath.dijkstra(allPrices, src, dest);
  }

  /**
   * Generates a 2D array of all costs from all routes.
   */
  public static void generateAllCosts() {
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
  }

  /**
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

}
