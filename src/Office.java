package src;

/**
 * Title: Office
 * Author: Noah Duggan Erickson
 * CSCI 345
 * Spring 2023
 * 
 * DESCRIPTION:
 *  A singleton implementation of Area
 *      used to represent the Office area of the board
 * 
 * CONSTRUCTORS:
 *  public Trailer()
 *      Creates a new Trailer object with non-null but empty field values
 *      Author: Noah Duggan Erickson
 * 
 * METHODS:
 *  public String getName()
 *      Returns "Office"
 *      Author: Noah Duggan Erickson
 *      Returns:
 *          "Office"
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
 *      Returns whether a refers to the Office
 *      Author: Noah Duggan Erickson
 *      Parameters:
 *          a - the object to compare against
 *      Returns:
 *          true if a refers to the Office
 *      Overrides:
 *          equals in class Object
 *      Specified by:
 *          equals in interface Area
 * 
 *  public String toString()
 *      Returns "Office"
 *      Author: Noah Duggan Erickson
 *      Returns:
 *          "Office"
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
 *          the Office object
 * 
 *  public ArrayList<Upgrade> getUpgrades()
 *      Returns a list of all possible upgrades
 *      Author: Noah Duggan Erickson
 *      Returns:
 *          ArrayList<String> with all possible upgrades
 * 
 *  public void addUpgrade(String line){
 *      Adds an upgrade to the list of possible
 *          upgrades as a line from the xml file
 *      Author: Noah Duggan Erickson
 *      Parameters:
 *          line - line of the board.xml file
 *              to parse into an upgrade object
 * 
 * INHERITED METHODS:
 *  Standard java.lang.Object inheritance
 */

import java.util.ArrayList;

public class Office implements Area{
    private ArrayList<String> neighbors;
    private ArrayList<Upgrade> upgrades;
    private static Office instance = new Office();
    private Office(){
        neighbors = new ArrayList<String>();
        upgrades = new ArrayList<Upgrade>();
    }
    public String getName(){
        return "office";
    }
    public ArrayList<String> getNeighbors(){
        return instance.neighbors;
    }
    public ArrayList<Upgrade> getUpgrades(){
        return instance.upgrades;
    }
    public boolean equals(Area a){
        return a.getName().equals("office");
    }
    public void addNeighbor(String name){
        instance.neighbors.add(name);
    }
    public void addUpgrade(String line){
        String[] attrs = line.split("\"");
        instance.upgrades.add(new Upgrade(Integer.parseInt(attrs[1]), attrs[3], Integer.parseInt(attrs[5])));
    }
    public boolean validateUpgrade(int rank, int amount) {
        if (amount >= upgrades.get(rank).getAmt()) {
            return true;
        } else {
            return false;
        }
    }

    public static Office getInstance(){
        return instance;
    }
}