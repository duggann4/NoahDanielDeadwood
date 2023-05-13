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
 *  public Role()
 *      Creates a new Role object with non-null but empty field values
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
    private Player player;
    private String name;
    private String flavorLine;
    private int rankRequired;
    private boolean roleOpen;
    private boolean onCard;

    public Role() {
        // Called exclusively from parsers
        player = null;
        name = "";
        flavorLine = "";
        rankRequired = 0;
        roleOpen = true;
        onCard = false;
    }

    public void setBasic(String name, int rank){
        this.name = name;
        this.rankRequired = rank;
    }

    public void setFlavor(String flavor){
        this.flavorLine = flavor;
    }

    public void setOnCard(boolean onCard) {
        this.onCard = onCard;
    }

    public boolean takeRole(Player player){
        if(roleOpen && player.getRank() >= rankRequired){
            this.player = player;
            roleOpen = false;
            return true;
        } else {
            return false;
        }
    }

    public void freeRole() {
        roleOpen = true;
    }

    public boolean checkOpen() {
        return roleOpen;
    }

    public boolean checkOnCard() {
        return onCard;
    }

    public String getName() {
        return name;
    }

    public Player getPlayer() {
        return player;
    }

    public int getRank() {
        return rankRequired;
    }

    public String toString(){
        return name + ", \"" + flavorLine + "\" requires rank " + rankRequired;
    }
}
