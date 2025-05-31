/**
 * Airport class with hashmap implementation.
 * 
 * @author Chaira Harder and Sirohi Kumar
 *
 */

public class Airport {

  // CLASS FIELDS
  String IATA, airport_name, city, country;
  char enplanement_size;

  /**
   * Generic constructor.
   */
  public Airport() {
    this.IATA = null;
    this.airport_name = null;
    this.city = null;
    this.country = null;
    this.enplanement_size = '-';
  }

  /**
   * Primary Airport constructor.
   * 
   * @param IATA
   * @param airport_name
   * @param city
   * @param country
   * @param enplanement_size
   */
  public Airport(String IATA, String airport_name, String city, String country, char enplanement_size) {
    this.IATA = IATA;
    this.airport_name = airport_name;
    this.city = city;
    this.country = country;
    this.enplanement_size = enplanement_size;
  }

  // GETTERS AND SETTERS

  /**
   * Returns IATA code
   * 
   * @param IATA
   * @return airport
   */
  public Airport getAirport(String IATA) {
    return this.IATA.equals(IATA) ? this : null;
  }

  /**
   * Gets IATA of this airport.
   * 
   * @return IATA
   */
  public String getIATA() {
    return this.IATA;
  }

  /**
   * Gets airport name of this airport.
   * 
   * @return airport name
   */
  public String getAirportName() {
    return this.airport_name;
  }

  /**
   * Gets city of this airport.
   * 
   * @return city
   */
  public String getCity() {
    return city;
  }

  /**
   * Set IATA code
   * 
   * @param iata
   */
  public void setIATA(String iata) {
    this.IATA = iata;
  }

  /**
   * Set airport name
   * 
   * @param airport name
   */
  public void setAirportName(String apn) {
    this.airport_name = apn;
  }

  /**
   * Set airport city
   * 
   * @param city
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * Set airport country
   * 
   * @param country
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * Set enplanement size
   * 
   * @param eps
   */
  public void setEnplanement(char eps) {
    this.enplanement_size = eps;
  }

  /**
   * Equals method for airport comparison.
   * 
   * @param comp
   * @return true/false
   */
  public boolean equals(Airport comp) {
    return this.toString().equals(comp.toString());
  }

  /**
   * toString airport string representation method
   */
  public String toString() {
    // return this.getIATA() + " - " + this.get"";
    return "[" + this.IATA + "] " + this.airport_name;
  }

}
