package src;

/**
 * Title: Trailer
 * Author: Noah Duggan Erickson
 * CSCI 345
 * Spring 2023
 * 
 * DESCRIPTION:
 *  A singleton implementation of Area
 *      used to represent the Trailer area of the board
 * 
 * CONSTRUCTORS:
 *  public Trailer()
 *      Creates a new Trailer object with non-null but empty field values
 *      Author: Noah Duggan Erickson
 * 
 * METHODS:
 *  public String getName()
 *      Returns "Trailer"
 *      Author: Noah Duggan Erickson
 *      Returns:
 *          "Trailer"
 *      Specified by:
 *          getName in interface Area
 * 
 *  public ArrayList<String> getNeighbors()
 *      Returns the names of all neighboring areas
 *      Author: Noah Duggan Erickson
 *      Returns:
 *          ArrayList<String> with the names of all neighboring areas
 *      Specified by:
 *          getNeighbors in interface Area
 * 
 *  public boolean equals(Area a)
 *      Returns whether a refers to the Trailers
 *      Author: Noah Duggan Erickson
 *      Parameters:
 *          a - the object to compare against
 *      Returns:
 *          true if a refers to the Trailers
 *      Overrides:
 *          equals in class Object
 *      Specified by:
 *          equals in interface Area
 * 
 *  public String toString()
 *      Returns "Trailer"
 *      Author: Noah Duggan Erickson
 *      Returns:
 *          "Trailer"
 *      Overrides:
 *          toString in class Object
 *      Specified by:
 *          toString in interface Area
 * 
 *  public void addNeighbor(String name)
 *      Adds the name of another Area object
 *          to the internal collection of neighbors
 *      Author: Noah Duggan Erickson
 *      Parameters:
 *          name - the new Area.name to add as a neighbor
 * 
 *  public static Trailer getInstance()
 *      Artefact of the singleton design
 *      Author: Noah Duggan Erickson
 *      Returns:
 *          the Trailer object
 * 
 * INHERITED METHODS:
 *  Standard java.lang.Object inheritance
 */

import java.util.ArrayList;

public class Trailer extends GUIElement implements Area {
    private ArrayList<String> neighbors;
    private static Trailer instance = new Trailer();
    private Trailer() {
        neighbors = new ArrayList<String>();
    }
    public String getName() {
        return "trailer";
    }
    public ArrayList<String> getNeighbors() {
        return instance.neighbors;
    }
    public boolean equals(Area a) {
        return a.getName().equals("trailer");
    }
    public String toString() {
        return "trailer";
    }
    public void addNeighbor(String name) {
        instance.neighbors.add(name);
    }
    public static Trailer getInstance() {
        return instance;
    }
}
