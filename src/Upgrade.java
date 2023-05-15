package src;

/**
 * Title: Role
 * Author: Noah Duggan Erickson
 * CSCI 345
 * Spring 2023
 * 
 * DESCRIPTION:
 *  A "container" for information pertaining to
 *      an allowed upgrade.
 * 
 * CONSTRUCTORS:
 *  public Upgrade(int level, String curType, int amt)
 *      Creates a new Role object with respective values
 *      Author: Noah Duggan Erickson
 *      Parameters:
 *          level - the level that the player acquires
 *              upon successful completion of the upgrade
 *          curType - "credits"/"dollars"; represents the
 *              currency type of the upgrade
 *          amt - the amount of curType required to
 *              execute the upgrade
 * 
 * METHODS:
 *  public int getLevel()
 *      Returns the rank of the upgrade
 *      Author: Daniel Wertz
 *      Returns:
 *          this.rank
 * 
 *  public String getCurType()
 *      returns the currency type of the upgrade
 *      Author: Daniel Wertz
 *      Returns:
 *          this.curType
 * 
  *  public int getAmt()
 *      returns the cost of the upgrade
 *      Author: Daniel Wertz
 *      Returns:
 *          this.amt
 * 
 *  public String toString()
 *      Returns a string representation of the Upgrade
 *      Author: Daniel Wertz
 *      Returns:
 *          string representation of this upgrade
 *      Overrides:
 *          toString in class Object
 * 
 * INHERITED METHODS:
 *  Standard java.lang.Object inheritance
 */

public class Upgrade { // implements Comparable<Player>?
    private int level;
    private String curType;
    private int amt;
    public Upgrade(int level, String curType, int amt){
        this.level = level;
        this.curType = curType;
        this.amt = amt;
    }

    public int getLevel() {
        return level;
    }

    public String getCurType() {
        return curType;
    }

    public int getAmt() {
        return amt;
    }

    public String toString() {
        return ("Rank - " + level + ", " + amt + " " + curType + "s");
    }
}
