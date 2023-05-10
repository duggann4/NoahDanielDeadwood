package src;

/**
 * Title: Role
 * Author: Noah Duggan Erickson
 * CSCI 345
 * Spring 2023
 * 
 * DESCRIPTION:
 *  A "container" for information pertaining to
 *      roles, both on and off-card.
 * 
 * CONSTRUCTORS:
 *  public Scene()
 *      Creates a new Scene object with non-null but empty field values
 *      Author: Noah Duggan Erickson
 * 
 * METHODS:
 *  public void setBasic(String name, int rank)
 *      Overrides the existing values for name and rank, respectively
 *      Author: Noah Duggan Erickson
 *      Parameters:
 *          title - the new name of the role
 *          budget - the new rank required to take the role
 * 
 *  public void setFlavor(String flavor)
 *      Overrides the existing values for flavorText
 *      Author: Noah Duggan Erickson
 *      Parameters:
 *          flavor - the new flavor text of the role
 * 
 *  public boolean takeRole(int pRank)
 *      Checks whether a player with rank pRank can take
 *          the role, and if so, the role becomes taken.
 *      Author: Noah Duggan Erickson
 *      Parameters:
 *          pRank - the rank of the player trying to take the role
 *      Returns:
 *          true if the player was successful in taking
 *              the role, false otherwise
 * 
 *  public String getName()
 *      Returns the name of the role
 *      Author: Noah Duggan Erickson
 *      Returns:
 *          this.name
 * 
 *  public String toString()
 *      Returns a string representation of the role
 *      Author: Noah Duggan Erickson
 *      Returns:
 *          string representation of this role
 *      Overrides:
 *          toString in class Object
 * 
 * INHERITED METHODS:
 *  Standard java.lang.Object inheritance
 */

public class Role {
    private String name;
    private String flavorLine;
    private int rankRequired;
    private boolean roleOpen;

    public Role(){
        // Called exclusively from parsers
        name = "";
        flavorLine = "";
        rankRequired = 0;
        roleOpen = true;
    }

    public void setBasic(String name, int rank){
        this.name = name;
        this.rankRequired = rank;
    }

    public void setFlavor(String flavor){
        this.flavorLine = flavor;
    }

    public boolean takeRole(int pRank){
        if(roleOpen && pRank >= rankRequired){
            roleOpen = false;
            return true;
        } else {
            return false;
        }
    }

    public String getName() {
        return name;
    }

    public String toString(){
        return name + ", \"" + flavorLine + "\" requires rank " + rankRequired;
    }
}
