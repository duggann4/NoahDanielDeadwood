package src;

import java.util.ArrayList;

public class Player {
    
    private String name;
    private int rank;
    private int credits;
    private int dollars = 0;
    private Role currentRole = null;
    private Area currentArea;
    private int rehearsalChips = 0;
    private ViewHandler view = ViewHandler.getInstance();

    public Player(String name, int rank, int credits) {
        this.name = name;
        this.rank = rank;
        this.credits = credits;
    }

    public void playTurn() {
        view.print("It is now " + name + "'s turn. Rank: " + rank + ", Credits: " + credits + ", Dollars: " + dollars);
        view.print("\nYour current location is the " + currentArea.getName() + ".");
        if (currentRole != null) {
            work();
        } else {
            move();
        }
    }

    //TODO: could access Board instead of Office directly?
    private void upgrade() {
        view.print("Would you like to upgrade your rank?\n\t0: Yes\n\t1: No");
        int input = view.getOption(1);
        if (input == 0) {
            ArrayList<Upgrade> upgrades = Office.getInstance().getUpgrades();
            view.print("\nSelect a valid upgrade:");
            view.print("\t0: Cancel upgrade");
            for (Upgrade upgrade : upgrades) {
                if (upgrades.indexOf(upgrade) + 1 == 6) {
                    view.print("-----------------------");
                }
                view.print("\t" + (upgrades.indexOf(upgrade) + 1) + ": " + upgrade.toString());
            }
            input = view.getOption(10);

            int rank = 0;
            int cost = 0;
            String type = null;
            switch (input) { //TODO: get rid of this ugly switch statement
                case 0:
                    view.print("\nYour rank remains unchanged");
                    return;
                case 1:
                    rank = 2;
                    cost = 4;
                    type = "dollar";
                    break;
                case 2:
                    rank = 3;
                    cost = 10;
                    type = "dollar";
                    break;
                case 3:
                    rank = 4;
                    cost = 18;
                    type = "dollar";
                    break;
                case 4:
                    rank = 5;
                    cost = 28;
                    type = "dollar";
                    break;
                case 5:
                    rank = 6;
                    cost = 40;
                    type = "dollar";
                    break;
                case 6:
                    rank = 2;
                    cost = 5;
                    type = "credit";
                    break;
                case 7:
                    rank = 3;
                    cost = 10;
                    type = "credit";
                    break;
                case 8:
                    rank = 4;
                    cost = 15;
                    type = "credit";
                    break;
                case 9:
                    rank = 5;
                    cost = 20;
                    type = "credit";
                    break;
                case 10:
                    rank = 6;
                    cost = 25;
                    type = "credit";
                    break;
            }

            if (rank <= this.rank) {
                view.print("\nThat rank is equal or below yours...");
            } else if (type.equals("dollar") && !Office.getInstance().validateUpgrade(rank, dollars, type)) {
                view.print("\nYou cannot afford that upgrade...");
            } else if (type.equals("credit") && !Office.getInstance().validateUpgrade(rank, credits, type)) {
                view.print("\nYou cannot afford that upgrade...");
            } else {
                view.print("\nYou have upgraded to Rank " + rank + "!");
                this.rank = rank;
                if (type.equals("dollar")) {
                    dollars -= cost;
                } else {
                    credits -= cost;
                }  
            }
        }
    }

    private void move() {
        view.print("Would you like to move?\n\t0: Yes\n\t1: No");
        int input = view.getOption(1);
        if (input == 0) {
            ArrayList<String> neighbors = currentArea.getNeighbors();
            view.print("\nSelect a neighboring area to move to:");
            view.print("\t0: Remain at current location");
            for (String neighbor : neighbors) {
                view.print("\t" + (neighbors.indexOf(neighbor) + 1) + ": " + neighbor);
            }
            input = view.getOption(neighbors.size());
            if (input == 0) {
                view.print("\nYour location remains at the " + currentArea.getName() + ".");
            } else { //TODO: bounds checking
                currentArea = Board.getInstance().getArea(neighbors.get(input - 1));
                view.print("\nYou have moved to the " + currentArea.getName() + ".");
            }
        }
        if (currentArea instanceof Set) {
            if (((Set)currentArea).getScene() != null) {
                offerRole(((Set)currentArea).getAvailableRoles());
            } else {
                view.print("Today's scene in this area has already completed");
            }
        } else if (currentArea.getName() == "office") {
            upgrade();
        }
    }

    public void setArea(Area area) {
        currentArea = area;
    }

    private void displayMoveOptions() {
        
    }

    private void offerRole(ArrayList<Role> roles) {
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
            int input = view.getOption(roles.size());
            if (input == 0) {
                view.print("\nYou chose not to take a role.");
            } else { //TODO: bounds checking
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
        int input = view.getOption(1);
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
            if(((Set)currentArea).getShotsRemaining() == 0) {
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
