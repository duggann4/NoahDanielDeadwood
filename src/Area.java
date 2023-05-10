package src;

/**
 * Title: interface Area
 * Author: Noah Duggan Erickson
 * CSCI 345
 * Spring 2023
 * 
 * DESCRIPTION:
 *  An interface used for subclasses to
 *      represent a physical location on
 *      the board that players can occupy
 * METHODS:
 *  public String getName()
 *      Returns the name of the area
 * 
 *  public ArrayList<String> getNeighbors()
 *      Returns an ArrayList containing the
 *          names of all areas that this
 *          area is adjacent to.
 * 
 *  public boolean equals(Area a)
 *      Tests whether or not two areas
 *          refer to the same area
 *      Overrides:
 *          equals in class Object
 * 
 *  public String toString()
 *      Returns a string representation
 *          of the area
 *      Overrides:
 *          toString in class Object
 */

import java.util.ArrayList;

public interface Area {
    public String getName();
    public ArrayList<String> getNeighbors();

    // Override from java.lang.Object
    //
    public boolean equals(Area a);
    public String toString();
}