
/**
 * Graph Map class.
 * Class to create map object to hold game airports.
 * @author Chaira Harder and Sirohi Kumar
 *
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Collection.*;

public class Graph {

  // airport, adj airports (destination of route) with price as weight
  private Map<Airport, Map<Airport, Integer>> adjAirports;

  /**
   * Constructor for a Map of airports object
   */
  public Graph() {
    adjAirports = new HashMap<Airport, Map<Airport, Integer>>();
  }

  /**
   * Gets an airport based on IATA code.
   * 
   * @return an airport object based on a given IATA value
   */
  public Airport getAirport(String IATA) {

    // loop through airports in adjAirports keys
    for (Airport airport : adjAirports.keySet()) {

      // if the key's IATA matches the IATA of the user input
      if (airport.getAirport(IATA) != null) {
        // then return that airport object
        return airport;
      }
    }
    return null;
  }

  /**
   * Get map object of a specific airport
   * 
   * @param airport
   * @return adjAirportMap
   */
  public Map<Airport, Integer> get(Airport airport) {
    return adjAirports.get(airport);
  }

  /**
   * Add airport given an airport object
   * 
   * @param airport (Airport object)
   */
  public void addAirport(Airport airport) {
    adjAirports.putIfAbsent(airport, new HashMap<Airport, Integer>());
  }

  /**
   * Method to add airport to taking all parameters
   * 
   * @param IATA
   */
  public void addAirport(String IATA, String airport_name, String city, String country, char enplanement) {
    adjAirports.putIfAbsent(new Airport(IATA, airport_name, city, country, enplanement),
        new HashMap<Airport, Integer>());
  }

  /**
   * Method to remove airport from map
   * 
   * @param Airport
   */
  public void removeAirport(Airport airport) {
    adjAirports.values().stream().forEach(e -> e.remove(airport));
    adjAirports.remove(airport);
  }

  /**
   * Method to add route or path between two NEW airports.
   * 
   * @param IATA1
   * @param IATA2
   */
  public void addRoute(Airport source, Airport destination) {
    int price = (int) (Math.random() * 1500) + 60;
    adjAirports.get(source).put(destination, price);
    adjAirports.get(destination).put(source, price);
  }

  /**
   * Removes route between two airports.
   * 
   * @param a1
   * @param a2
   */
  public void removeRoute2(Airport source, Airport destination) {
    adjAirports.get(source).remove(destination);
    adjAirports.get(destination).remove(source);
  }

  // check this method bc i forgot exactly how we mean to use it
  /**
   * Method to get adjacent or connected airports for a specified airport.
   * 
   * @param airport
   * @return a list of adjacent airports
   */
  public Map<Airport, Integer> getAdjVertices(Airport airport) {
    return adjAirports.get(airport);
  }

  /**
   * Returns a random airport
   * 
   * @return Airport
   */
  public Airport getRandomAirport() {
    Object[] airports = adjAirports.keySet().toArray();
    Random generator = new Random();
    Airport randomAirport = (Airport) airports[generator.nextInt(airports.length)];
    return randomAirport; // @FLAG@
  }

  /**
   * Gets size of the adjacent airports list.
   * 
   * @return size
   */
  public int size() {
    return adjAirports.size();
  }

  /**
   * We KNOW we should just implement HashMap but it is a little too late for
   * that. So:
   * This gets the keySet of adjacent airports.
   * 
   * @return keySet of airport's map
   */
  public Object[] keySet() {
    return adjAirports.keySet().toArray();
  }

  /**
   * Prints all destinations for a given airport object
   */
  public void printDestinations(Airport source) {
    Map<Airport, Integer> sourceMap = adjAirports.get(source);
    Object[] destinations = sourceMap.keySet().toArray();

    for (Object i : destinations) {
      Airport airport = (Airport) i;
      System.out.println("\t> " + airport.getIATA() + " in (" + airport.getCity() +
          ") costs $" + sourceMap.get(airport));
    }

  }

  /*
   * Prints map representation by showing all flights and their corresponding
   * routes.
   */
  public String toString() {
    // return adjAirports.toString();
    String mapStr = "";

    for (Airport i : adjAirports.keySet()) {
      mapStr = mapStr + ("from: " + i + " to: " + adjAirports.get(i) + "\n");
    }

    return mapStr;

  }

}
