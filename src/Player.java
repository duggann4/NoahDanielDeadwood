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
 *  public void updatePosition()
 *      Update the coordinates of the player.
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
import javax.swing.ImageIcon;
import java.util.Map;
import java.util.HashMap;

public class Player extends GUIElement {
    
    private String name;
    private int rank;
    private int credits;
    private int dollars = 0;
    private int rehearsalChips = 0;
    private Area currentArea;
    private Role currentRole = null;
    private Controller controller;
    private String diceColor = null;

    public Player(String name, int rank, int credits, String color) {
        this.name = name;
        this.rank = rank;
        this.credits = credits;
        this.controller = Controller.getInstance();
        this.diceColor = color;
        this.image = new ImageIcon(getClass().getResource("../img/dice/" + diceColor + "1.png"));
        this.width = 40;
        this.height = 40;
        updatePosition();
    }

    public void playTurn() {
        ArrayList<String> options = new ArrayList<String>();
        options.add("Ready!");
        controller.getOption(options, "It is now " + name + "'s turn.");
        if (currentRole != null) {
            work();
        } else {
            move();
        }
    }

    private void move() {
        String input;
        ArrayList<String> options = new ArrayList<String>();
        options.add("Yes");
        options.add("No");
        input = controller.getOption(options, "Would you like to move?");

        if (input.equals("Yes")) {
                ArrayList<String> neighbors = currentArea.getNeighbors();
                options.clear();
                for (String neighbor : neighbors) {
                    options.add(neighbor);
                }
                options.add("Don't Move");
                input = controller.getOption(options, "Where would you like to move?");
                if (!input.equals("Don't Move")) {{
                    currentArea = Board.getInstance().getArea(input);
                    updatePosition();
                }
            }

            updatePosition();
            if (currentArea instanceof Set) {
                if (((Set)currentArea).getScene() != null) {
                    if (!((Set)currentArea).getScene().checkRevealed()) {
                        ((Set)currentArea).getScene().revealScene();
                        controller.updateGUI();
                    }
                    takeRole(((Set)currentArea).getAvailableRoles());
                    updatePosition();
                }
            } else if (currentArea.getName() == "office") {
                updatePosition();
                upgrade();
            }
        }
    }

    private void upgrade() {
        ArrayList<String> options = new ArrayList<String>();
        options.add("Yes");
        options.add("No");
        String input = controller.getOption(options, "Would you like to upgrade?");
        if (input.equals("No")) {
            return;
        }
        Office office = ((Office)currentArea);

        options.clear();
        for (int i = 1; i <= 10; i++) {
            options.add(office.getUpgrade(i).toString());
        }
        options.add("Don't upgrade");
        
        input = controller.getOption(options, "Select a rank upgrade: ");

        Upgrade upgrade; 
        if(input.contains("Rank 1") && input.contains("dollars")) {
            upgrade = office.getUpgrade(0);
        } else if (input.contains("Rank 2") && input.contains("dollars")) {
            upgrade = office.getUpgrade(1);
        } else if (input.contains("Rank 3") && input.contains("dollars")) {
            upgrade = office.getUpgrade(2);
        } else if (input.contains("Rank 4") && input.contains("dollars")) {
            upgrade = office.getUpgrade(3);
        } else if (input.contains("Rank 5") && input.contains("dollars")) {
            upgrade = office.getUpgrade(4);
        } else if (input.contains("Rank 1") && input.contains("credits")) {
            upgrade = office.getUpgrade(5);
        } else if (input.contains("Rank 2") && input.contains("credits")) {
            upgrade = office.getUpgrade(6);
        } else if (input.contains("Rank 3") && input.contains("credits")) {
            upgrade = office.getUpgrade(7);
        } else if (input.contains("Rank 4") && input.contains("credits")) {
            upgrade = office.getUpgrade(8);
        } else if (input.contains("Rank 5") && input.contains("credits")) {
            upgrade = office.getUpgrade(9);
        } else {
            return;
        }

        int level = upgrade.getLevel();
        int cost = upgrade.getAmt();
        String type = upgrade.getCurType();
        if (upgrade.getLevel() <= this.rank) {
            //controller.displayMessage("\nThat rank is equal or below yours...");
        } else if (type.equals("dollar") && !office.validateUpgrade(upgrade, dollars) ||
            type.equals("credits") && !office.validateUpgrade(upgrade, credits)) {
            //controller.displayMessage("\nYou cannot afford that upgrade...");
        } else {
            options.clear();
            options.add("Continue");
            controller.getOption(options, "You have upgraded to Rank " + level + "!");
            this.rank = level;
            this.image = new ImageIcon(getClass().getResource("../img/dice/" + diceColor + rank + ".png"));
            if (type.equals("dollar")) {
                dollars -= cost;
            } else {
                credits -= cost;
            }  
        }
    }

    private void takeRole(ArrayList<Role> roles) {
        if (roles.size() != 0) {
            ArrayList<String> options = new ArrayList<String>();
            Map<String, Integer> map = new HashMap<>();
            int i = 0;
            for (Role role : roles) {
                options.add(role.toString());
                map.put(role.toString(), i);
                i++;
            }
            options.add("Don't take role");
            String input = controller.getOption(options, "Select a role to take:");
            if (!input.equals("Don't take role")) {
                Role role = roles.get(map.get(input));
                if (role.takeRole(this)) {
                    currentRole = role;
                } else {
                    options.clear();
                    options.add("Continue");
                    controller.getOption(options, "Insufficient rank to take that role...");
                }
            }
        }
    }

    private void work() {
        ArrayList<String> options = new ArrayList<String>();
        options.add("Act");
        options.add("Rehearse");
        String input = controller.getOption(options, "You are working on set.\nWhat would you like to do?");
        if (input.equals("Rehearse")) {
            rehearse();
        } else {
            act();
        }
    }

    private void rehearse() {
        rehearsalChips++;
    }

    private void act() {
        ArrayList<String> options = new ArrayList<String>();
        options.add("Continue");
        if (((Set)currentArea).shoot(rehearsalChips)) {
            String prompt = "Your acting was a success!";
            if (currentRole.checkOnCard()) {
                prompt = prompt.concat("\nYou gained 2 credits.");
                credits += 2;
            } else {
                prompt = prompt.concat("\nYou gained 1 credit and 1 dollar.");
                credits++;
                dollars++;
            }
            controller.getOption(options, prompt);
            if (((Set)currentArea).getShotsRemaining() == 0) {
                ((Set)currentArea).wrap();
            }
        } else {
            String prompt = "Your attempt to act failed...";
            if (!currentRole.checkOnCard()) {
                prompt = prompt.concat("\nYou gained 1 dollar.");
                dollars++;
            }
            controller.getOption(options, prompt);
        }
    }

    public void updatePosition() {
        if (currentArea instanceof Set) {
            if (currentRole == null) {
                this.x = ((Set)currentArea).getX() + 10;
                this.y = ((Set)currentArea).getY() + 10;
            } else {
                this.x = currentRole.getX();
                this.y = currentRole.getY();
            }
        } else if(currentArea instanceof Trailer) {
            this.x = ((Trailer)currentArea).getX() + 10;
            this.y = ((Trailer)currentArea).getY() + 10;
        } else if(currentArea instanceof Office) {
            this.x = ((Office)currentArea).getX() + 10;
            this.y = ((Office)currentArea).getY() + 10;
        }   
        controller.updateGUI();
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

    public Area getArea() {
        return currentArea;
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

    public String toString() {
        return "Rank: " + rank + "\nDollars: " + dollars + "\nCredits: " + credits + "\nRehearsalChips: " + rehearsalChips;
    }
}
