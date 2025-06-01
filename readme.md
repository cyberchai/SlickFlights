# SLICK FLIGHTS README

Authors: Chaira Kai Harder and Sirohi Kumar

## goal

- in this game, the user is tasked with navigating from one airport to another airport via the cheapest route possible
- we used a weighted graph as our structure. for more, see the "data structures" section of this README. 

## phases

### data structures
- to contain all of the relevant information, we created an airport object which contained a variety of information about airports (names, locations, unique codes, etc). 
- our main data structure was a weighted graph
  - this was implemented using a nested map, where the outside keys were airport objects and each key-value was its own map, which represented the various airports that key had a route to
  - the values for these internal maps were the costs for each route. thus, each node was an airport, the edges were the routes, and the weight of each edge is the cost of each route
  - for this graph structure, we had to create several methods, including methods to randomly select airports in this non-iterable structure, add routes between two given airports, and more
- we then created a variety of other classes using different data structures
  - we coded djikstra's shortest path algorithm using a series of 2D arrays and sets. this was especially difficult because the traditional implementation of this algorithm for a weighted graph would work for a weighted graph that uses airport objects.
    - to allow future expansion to an infinite number of airports, we created several flexible methods that could take a map of any size, convert it to a 2D array and then find the shortest route between two nodes.
  - in order to use djikstra's algorithm we coded a sorting algorithm for sets of type airport, which used linked lists to complete a merge sort
    - this required arraydeques
  - we also used sets, arrays, arraylists, and linkedlists throughout our classes and methods. 

### world building

- again, we designed this to simulate an airport, with the user being able to navigate across the country through its airports. 
- the goal would be to get from one randomly-selected airport to another, and the user can choose to limit themselves or add constraints

### navigation

- navigation between the nodes of the graph involve two steps: 
	- determining if there's a path between two nodes
	- "moving" to the next node
- this is where our idea to next our maps came from -- it was imperative each "route", or node--node edge have their own numeric value
- early in development, we simply had a map where the keys were Airport objects and the values were lists of Airport objects the key would have a "route" (edge) with. 
- however, list values don't have their own inherent attributes, which meant while we had edges, they weren't weighted
- in order to keep this essential aspect, we ended up having to change our main data structure a week into the project, after a lot of struggling at the conceptual level. 

## next steps/expansion

- other modes for adding airports (pulling in information from a database -- possibly from real world flight information)
- time and difficulty complexity
	- making flights available based on the "time" you've landed
	- the goal is to get the "cheapest" flight, or the shortest flight path (time elapsed), or most direct (fewest stops)
- controlling which airports get flights to each other based on size and proximity
	
		/**
		 * MODES:
		 * 0 - fewest number of routes taken
		 * 1 - least amount of time totaled between all routes
		 * 2 - lowest cumulative weight of edges
		 */
		game_modes.add("I'm in a rush! I need the most direct flight to "); // 0
		game_modes.add("Get me the quickest flight to "); // 1
		game_modes.add("I'm strapped for cash! Help me find the cheapest flight to "); // 2


- developing an "optimal path" for the user to compare their path to:

		/**
		 * YOUR PATH WAS:
		 * ↪ [BDL] Bangor Intl Airport
		 * ↪ [JFK] John F Kennedy International Airport
		 * ↪ [YYZ] Toronto International Airport
		 * ↪ [LAX] Los Angeles International Airport
		 */
		map.optimalPath(Airport BDL, Airport LAX);
		
		/**
		 * THE MOST DIRECT PATH IS:
		 * ↪ [BDL] Bangor Intl Airport
		 * ↪ [DCA] Reagan International Airport
		 * ↪ [LAX] Los Angeles International Airport
		 */


### graphics

- giving the user limited "undos", allowing them to move backwards through the route without price accumulating
- developing a map for the user to click through, rather than keeping it text based
	- allowing the user to visualize the connections between airports rather than having to 
	

## checklist (needs point values), separate into checklist.txt

#### developing the world

YES__  Airport object is created with all necessary traits (name, IATA code, location)

YES__ integrate a text file (.csv) that can be read in with flight data

YES__ randomly generate route costs

YES__ nested map is created with accessible cost, airport, and destination values


#### navigation

YES__ random start and destination points are selected

YES__ allow the user to opt in and out of repeated rounds of the game 

YES__ take user input and translate it to a "move" between nodes

YES__ recognize when the round "ends" because the user finished the route

#### ending the game

YES__ the cost of the route can be calculated

YES__ the length of the route can be calculated


## reflections

### sirohi

- as far as this project goes, i was really engaged with the concept of our game, i thought it was really fun as well as highly expandable and something that could be developed on
- i thought it was a fun mandate, but think we could have benefitted from a bit more in class learning about graphics, which i recognize isn't part of class, but is something fun
- maybe as part of an office hours style lesson. not sure. 
- the project itself was sufficiently challenging, especially because we had to develop so many of our own classes and methods
- additionally, our data structure ended up being highly abstract which posed its own difficulties, but i felt like it came to make a lot of sense after working on it for so long
- i'd be interested in hearing how someone not involved in development/coding finds interpreting the various methods and classes 

### chaira

Like Sirohi, I was very engaged in the concept/idea of the game, in part because I hope to expand and use parts of it for a real life application like for finding the cheapest optimal route from one location to another. I'm an international student from the west coast (Vancouver/Kelowna BC Canada), so looking for the cheapest routes on sites like kayak.com is something I do frequently and is actually how I came up with the idea for the game. 
I feel that this project challenged us and gave us adequate experience using and traversing differnet data structures such as graphs and hashmaps. Almost the entirety of this project was pair programmed as well, which proved to be an effective and productive learning experience for both Sirohi and I.
