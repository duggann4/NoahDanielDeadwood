package src;

/**
 * Title: Player
 * Author: Daniel Wertz
 * CSCI 345
 * Spring 2023
 * 
 * DESCRIPTION:
 *  Represents a player in the game and handles all actions 
 *      directly related to a specific player.
 * 
 * CONSTRUCTORS:
 *  Player(String name, int rank, int credits)
 *      Constructs a new Player object
 *      Author: Daniel Wertz
 *      Parameters:
 *          name - name of player
 *          rank - starting rank
 *          credits - starting credits
 * 
 * METHODS:
 *  public void playTurn
 *      Starts a new turn for the player
 *      Author: Daniel Wertz
 * 
 *  public void removeRole()
 *      Removes the current role of the player.
 *      Author: Daniel Wertz
 *  
 *  public void addDollars(int dollars)
 *      Adds the given amount of dollars to the player's total.
 *      Author: Daniel Wertz
 *      Parameters:
 *          dollars - the amount of dollars to add
 *  
 *  public void addCredits(int credits)
 *      Adds the given amount of credits to the player's total.
 *      Author: Daniel Wertz
 *      Parameters:
 *          credits - the amount of credits to add
 *  
 *  public void setArea(Area area)
 *      Sets the current area of the player to the given area.
 *      Author: Daniel Wertz
 *      Parameters:
 *          area - the area to set as the current area
 *  
 *  public String getName()
 *      Returns the name of the player.
 *      Author: Daniel Wertz
 *      Returns:
 *          this.name
 *  
 *  public int getRank()
 *      Returns the rank of the player.
 *      Author: Daniel Wertz
 *      Returns:
 *          this.rank
 *  
 *  public int getScore()
 *      Returns the score of the player, calculated as the sum of their dollars, credits, and five times their rank.
 *      Author: Daniel Wertz
 *      Returns:
 *          The score of the player as an int.
 * 
 * INHERITED METHODS:
 *  Standard java.lang.Object inheritance
 */

import java.util.ArrayList;

public class Player extends GUIElement {
    
    private String name;
    private int rank;
    private int credits;
    private int dollars = 0;
    private int rehearsalChips = 0;
    private Area currentArea;
    private Role currentRole = null;
    private ViewHandler view = ViewHandler.getInstance();

    public Player(String name, int rank, int credits) {
        this.name = name;
        this.rank = rank;
        this.credits = credits;
    }

    public void playTurn() {
        view.print("\nIt is now " + name + "'s turn. Rank: " + rank + ", Credits: " + credits + ", Dollars: " + dollars);
        view.print("\nYour current location is the " + currentArea.getName() + ".");
        if (currentRole != null) {
            work();
        } else {
            move();
        }
    }

    private void move() {
        int input;
        do {
            view.print("Would you like to move?\n\t0: Yes\n\t1: No\n\t2: View board");
            input = view.readOption(2);
            if (input == 2) {
                Board.getInstance().printBoard();
            }
        } while (input == 2);

        if (input == 0) {
            ArrayList<String> neighbors = currentArea.getNeighbors();
            view.print("\nSelect a neighboring area to move to:");
            view.print("\t0: Remain at current location");
            for (String neighbor : neighbors) {
                view.print("\t" + (neighbors.indexOf(neighbor) + 1) + ": " + neighbor);
            }
            input = view.readOption(neighbors.size());
            if (input == 0) {
                view.print("\nYour location remains at the " + currentArea.getName() + ".");
            } else {
                currentArea = Board.getInstance().getArea(neighbors.get(input - 1));
                view.print("\nYou have moved to the " + currentArea.getName() + ".");
            }
        }

        if (currentArea instanceof Set) {
            if (((Set)currentArea).getScene() != null) {
                takeRole(((Set)currentArea).getAvailableRoles());
            } else {
                view.print("Today's scene in this area has already completed");
            }
        } else if (currentArea.getName() == "office") {
            upgrade();
        }
    }

    private void upgrade() {
        view.print("Would you like to upgrade your rank?\n\t0: Yes\n\t1: No");
        int input = view.readOption(1);
        if (input == 1) {
            return;
        }
        Office office = ((Office)currentArea);
        view.print("\nCurrent rank: " + rank + ", Credits: " + credits + ", Dollars: " + dollars);
        view.print("Select an upgrade to purchase:");
        view.print("\t0: Cancel upgrade");
        for (int i = 1; i <= 10; i++) {
            if (i == 6) {
                view.print("\t-----------------------");
            }
            view.print("\t" + i + ": " + office.getUpgrade(i).toString());
        }

        input = view.readOption(10);
        Upgrade upgrade = office.getUpgrade(input);
        int level = upgrade.getLevel();
        int cost = upgrade.getAmt();
        String type = upgrade.getCurType();
        if (upgrade.getLevel() <= this.rank) {
            view.print("\nThat rank is equal or below yours...");
        } else if (type.equals("dollar") && !office.validateUpgrade(input, dollars) ||
                    type.equals("credits") && !office.validateUpgrade(input, credits)) {
            view.print("\nYou cannot afford that upgrade...");
        } else {
            view.print("\nYou have upgraded to Rank " + level + "!");
            this.rank = level;
            if (type.equals("dollar")) {
                dollars -= cost;
            } else {
                credits -= cost;
            }  
        }
    }

    private void takeRole(ArrayList<Role> roles) {
        if (roles.size() == 0) {
            view.print("No available roles at current location");
        } else {
            view.print("\nSelect a role to take:"); 
            view.print("\t0: Don't take role");
            for (Role role : roles) {
                if (role.checkOnCard()) {
                    view.print("\t" + (roles.indexOf(role) + 1) + ": " + role.toString() + " (On Card)");
                } else {
                    view.print("\t" + (roles.indexOf(role) + 1) + ": " + role.toString() + " (Off Card)");
                }
            }
            int input = view.readOption(roles.size());
            if (input == 0) {
                view.print("\nYou chose not to take a role.");
            } else { 
                Role role = roles.get(input - 1);
                if (role.takeRole(this)) {
                    currentRole = role;
                    view.print("\nYou have taken the role of " + currentRole.getName() + ".");
                } else {
                    view.print("\nInsufficient rank to take that role...");
                }
            }
        }
    }

    private void work() {
        //TODO: Display more information about current role
        view.print("You are working for the scene " + ((Set)currentArea).getScene().toString() + ".");
        view.print("Your role is " + currentRole.getName() + " and you have " + rehearsalChips + " rehearsal chips.");
        view.print("Shots remaining: " + ((Set)currentArea).getShotsRemaining());
        view.print("\nWhat would you like to do?\n\t0: Rehearse\n\t1: Act");
        int input = view.readOption(1);
        if (input == 0) {
            rehearse();
        } else {
            act();
        }
    }

    private void rehearse() {
        view.print("\nYou chose to rehearse. You have gained 1 rehearsal chip.");
        rehearsalChips++;
    }

    private void act() {
        if (((Set)currentArea).shoot(rehearsalChips)) {
            view.print("\nYour acting was a success!");
            if (currentRole.checkOnCard()) {
                view.print("You gained 2 credits for succeeding at on card role.");
                credits += 2;
            } else {
                view.print("You gained 1 credit and 1 dollar for succeeding at off card role!");
                credits++;
                dollars++;
            }
            if (((Set)currentArea).getShotsRemaining() == 0) {
                view.print("\nScene completed!");
                ((Set)currentArea).wrap();
            }
        } else {
            view.print("Your attempt to act failed...");
            if (!currentRole.checkOnCard()) {
                view.print("You gained 1 dollar for failing at off card role...");
                dollars++;
            }
        }
    }

    public void removeRole() {
        currentRole = null;
    }

    public void addDollars(int dollars) {
        this.dollars += dollars;
    }

    public void addCredits(int credits) {
        this.credits += credits;
    }

    public void setArea(Area area) {
        currentArea = area;
    }

    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }

    public int getScore() {
        return dollars + credits + rank*5;
    }
}
