package src;

/**
 * Title: Set
 * Authors: Noah Duggan Erickson, Daniel Wertz
 * CSCI 345
 * Spring 2023
 * 
 * DESCRIPTION:
 * CONSTRUCTORS:
 *  public Set()
 *      Creates a new Scene object with non-null but empty field values
 *      Author: Noah Duggan Erickson
 * 
 * METHODS:
 *  public void setName(String name)
 *      Overrides the existing value for name
 *      Author: Noah Duggan Erickson
 *      Parameters:
 *          name - the new name of the set
 * 
 *  public void setShots(int shots)
 *      Overrides the existing value for shotsRemaining
 *      Author: Noah Duggan Erickson
 *      Parameters:
 *          shots - the new value of shotsRemaining
 * 
 *  public void setScene(Scene scene)
 *      Overrides the existing value for currentScene
 *      Author: Daniel Wertz
 *      Parameters:
 *          scene - the new currentScene
 * 
 *  public void addNeighbor(String name)
 *      Adds a the name of another set object
 *          to the internal collection of neighbors
 *      Author: Noah Duggan Erickson
 *      Parameters:
 *          name - the new Set.name to add as a neighbor
 * 
 *  public void addRole(Role r)
 *      Adds a Role object to the internal collection of off-card roles
 *      Author: Noah Duggan Erickson
 *      Parameters:
 *          role - the new role to add to the set\
 * 
 *  public void resetSet()
 *      Resets shotsRemaining and frees offRoles
 *      Author: Daniel Wertz
 * 
 *  public boolean shoot(int rehearsalChips)
 *      Rolls dice to simulate player acting and returns result
 *      Author: Daniel Wertz
 *      Parameters:
 *          rehearsalChips - Players rehearsalChips to be added to roll
 *      Returns:
 *          true if acting is successful
 *      
 *  public void wrap()
 *      Wraps scene and calls private method payout() to handle bonus pay
 *      Author: Daniel Wertz
 * 
 *  public ArrayList<Role> getAvailableRoles()
 *      Generates and returns an ArrayList of all open roles at this Set
 *      Author: Daniel Wertz
 *      Returns:
 *          ArrayList of open roles
 * 
 *  public int getShotsRemaining()
 *      Returns shots remaining
 *      Author: Daniel Wertz
 *      Returns:
 *          this.shotsRemaining
 * 
 *  public String getName()
 *      Returns the name of the set
 *      Author: Noah Duggan Erickson
 *      Returns:
 *          this.name
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
 *  public Scene getScene()
 *      Returns the set's current scene
 *      Author: Daniel Wertz
 *      Returns:
 *          this.currentScene
 * 
 *  public boolean equals(Area a)
 *      Returns whether the two Area objects (this and a) refer to the same location
 *      Author: Noah Duggan Erickson
 *      Parameters:
 *          a - the object to compare against
 *      Returns:
 *          true if this and a have the same name
 *      Overrides:
 *          equals in class Object
 *      Specified by:
 *          equals in interface Area
 * 
 *  public String toString()
 *      Returns a string representation of the set
 *      Author: Noah Duggan Erickson
 *      Returns:
 *          string representation of this set
 *      Overrides:
 *          toString in class Object
 *      Specified by:
 *          toString in interface Area
 * 
 * INHERITED METHODS:
 *  Standard java.lang.Object inheritance
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Set implements Area {
    private String name;
    private ArrayList<String> neighbors;
    private Scene currentScene;
    private ArrayList<Role> offRoles; // off-card
    private int shots;
    private int shotsRemaining;
    private Random random = new Random(); // for dice

    public Set() {
        // called exclusively by BoardParser
        name = "";
        neighbors = new ArrayList<String>();
        currentScene = null;
        offRoles = new ArrayList<Role>();
        shotsRemaining = 0;
    }
    public void setName(String name){
        this.name = name;
    }
    
    public void setShots(int shots) {
        this.shots = shots;
    }

    public void setScene(Scene scene) {
        currentScene = scene;
    }

    public void addNeighbor(String name) {
        neighbors.add(name);
    }

    public void addRole(Role r) {
        offRoles.add(r);
    }

    public void resetSet() {
        shotsRemaining = shots;
        for(Role role : offRoles) {
            role.freeRole();
        }
    }

    public boolean shoot(int rehearsalChips) {
        int roll = rollDice(1, 6) + rehearsalChips;
        if (roll >= currentScene.getBudget()) {
            shotsRemaining--;
            return true;
        }
        else {
            return false;
        }
    }

    public void wrap() {
        System.out.println("\nScene completed!");
        ArrayList<Role> rolesOnCard = new ArrayList<Role>();
        ArrayList<Role> rolesOffCard = new ArrayList<Role>();
        for (Role role : currentScene.getRoles()) {
            if (!role.checkOpen()) {
                rolesOnCard.add(role);
                role.getPlayer().removeRole();
            }
        }
        Collections.reverse(rolesOnCard);
        for (Role role : offRoles) {
            if (!role.checkOpen()) {
                rolesOffCard.add(role);
                role.getPlayer().removeRole();
            }
        }
        payout(rolesOnCard, rolesOffCard);
        Board.getInstance().removeScene(currentScene);
        currentScene = null;
    }

    private void payout(ArrayList<Role> rolesOnCard, ArrayList<Role> rolesOffCard) {
        if (rolesOnCard.size() > 0) {
            System.out.println("\n\tPayout:\n\t-------");
            ArrayList<Integer> diceRolls = new ArrayList<Integer>();
            for (int i = 0; i < currentScene.getBudget(); i++) {
                diceRolls.add(rollDice(1, 6));
            }
            Collections.sort(diceRolls, Collections.reverseOrder());

            int playerNum = 0;
            for (int roll : diceRolls) {
                if (playerNum == rolesOnCard.size()) {
                    playerNum = 0;
                }
                Player player = rolesOnCard.get(playerNum).getPlayer();
                player.addDollars(roll);
                playerNum++;
                System.out.println("\t" + player.getName() + " Gained " + roll + " Dollars!");
            }
            for (Role role : rolesOffCard) {
                role.getPlayer().addDollars(role.getRank());
                System.out.println("\t" + role.getPlayer().getName() + " Gained " + role.getRank() + " Dollars!");
            }
        } else {
            System.out.println("No players acting on any on card roles, so no bonus pay...");
        }
    }

    private int rollDice(int rolls, int sides) {
        int total = 0;
        for (int i = 0; i < rolls; i++) {
            total += random.nextInt(sides) + 1;
        }
        return total;
    }

    public ArrayList<Role> getAvailableRoles() {
        ArrayList<Role> availableRoles = new ArrayList<Role>();
        for (Role role : offRoles) {
            if (role.checkOpen()) {
                availableRoles.add(role);
            }
        }
        for (Role role : currentScene.getRoles()) {
            if (role.checkOpen()) {
                availableRoles.add(role);
            }
        }
        return availableRoles;
    }

    public int getShotsRemaining() {
        return shotsRemaining;
    }

    public String getName() {
        return name;
    }
    public ArrayList<String> getNeighbors() {
        return neighbors;
    }

    public Scene getScene() {
        return currentScene;
    }

    public boolean equals(Area a) {
        return (this.name).equals(a.getName());
    }

    public String toString() {
        return name + " has " + neighbors.size() + " neighbors, " + offRoles.size() + " off-card roles, and " + shotsRemaining + " shots";
    }
}