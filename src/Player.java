package src;

import java.util.Scanner;
import java.util.ArrayList;

public class Player {
    
    private String name;
    private int rank;
    private int credits;
    private int dollars = 0;
    private Role currentRole = null;
    private Area currentArea;
    private int rehearsalChips = 0;
    private Scanner scanner = new Scanner(System.in);

    public Player(String name, int rank, int credits) {
        this.name = name;
        this.rank = rank;
        this.credits = credits;
    }

    public void playTurn() {
        System.out.println("It is now " + name + "'s turn. Rank: " + rank + ", Credits: " + credits + ", Dollars: " + dollars);
        System.out.println("Your current location is the " + currentArea.getName() + ".");

        if (currentRole != null) {
            work();
        } else {
            /* Offer role at start of turn?
            if (currentArea instanceof Set) {
                offerRole(((Set)currentArea).getAvailableRoles());
            } */
            move();
        }
        System.out.println("-Ending Turn-\n");
    }

    //TODO: could access Board instead of Office directly?
    private void upgrade() {
        System.out.println("Would you like to upgrade your rank?\n\t0: Yes\n\t1: No");
        int input = scanner.nextInt();
        if (input == 0) {
            ArrayList<Upgrade> upgrades = Office.getInstance().getUpgrades();
            System.out.println("\nSelect a valid upgrade:");
            System.out.println("\t0: Cancel upgrade");
            for (Upgrade upgrade : upgrades) {
                if (upgrades.indexOf(upgrade) + 1 == 6) {
                    System.out.println();
                }
                System.out.println("\t" + (upgrades.indexOf(upgrade) + 1) + ": " + upgrade.toString());
            }
            input = scanner.nextInt();

            int rank = 0;
            int cost = 0;
            String type = null;
            switch (input) { //TODO: get rid of this ugly switch statement
                case 0:
                    System.out.println("\nYour rank remains unchanged");
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
                default:
                    System.out.println("\nInvalid upgrade, input out of range 1-10.");
                    break;
            }

            if (rank <= this.rank) {
                System.out.println("\nThat rank is equal or below yours...");
            } else if (type.equals("dollar") && !Office.getInstance().validateUpgrade(rank, dollars, type)) {
                System.out.println("\nYou cannot afford that upgrade...");
            } else if (type.equals("credit") && !Office.getInstance().validateUpgrade(rank, credits, type)) {
                System.out.println("\nYou cannot afford that upgrade...");
            } else {
                System.out.println("\nYou have upgraded to Rank " + rank + "!");
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
        System.out.println("\nWould you like to move?\n\t0: Yes\n\t1: No");
        int input = scanner.nextInt();
        if (input == 0) {
            ArrayList<String> neighbors = currentArea.getNeighbors();
            System.out.println("\nSelect a neighboring area to move to:");
            System.out.println("\t0: Remain at current location");
            for (String neighbor : neighbors) {
                System.out.println("\t" + (neighbors.indexOf(neighbor) + 1) + ": " + neighbor);
            }
            input = scanner.nextInt();
            if (input == 0) {
                System.out.println("\nYour location remains at the " + currentArea.getName() + ".");
            } else { //TODO: bounds checking
                currentArea = Board.getInstance().getArea(neighbors.get(input - 1));
                System.out.println("\nYou have moved to the " + currentArea.getName() + ".");
            }
        }
        if (currentArea instanceof Set) {
            if (((Set)currentArea).getScene() != null) {
                offerRole(((Set)currentArea).getAvailableRoles());
            } else {
                System.out.println("Today's scene in this area has already completed");
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
            System.out.println("No available roles at current location");
        } else {
            System.out.println("Select a role to take:"); 
            System.out.println("\t0: Don't take role");
            for (Role role : roles) { //TODO: dont offer taken roles
                System.out.print("\t" + (roles.indexOf(role) + 1) + ": " + role.toString()); //TODO: specify which roles are on card
                if (role.checkOnCard()) {
                    System.out.println(" (On Card)");
                } else {
                    System.out.println(" (Off Card)");
                }
            }
            int input = scanner.nextInt();
            if (input == 0) {
                System.out.println("\nYou chose not to take a role.");
            } else { //TODO: bounds checking
                Role role = roles.get(input - 1);
                if (role.takeRole(this)) {
                    currentRole = role;
                    System.out.println("\nYou have taken the role of " + currentRole.getName() + ".");
                } else {
                    System.out.println("\nInsufficient rank to take that role...");
                }
            }
        }
    }

    private void work() {
        //TODO: Display more information about current role
        System.out.println("You are currently working for the scene " + ((Set)currentArea).getScene().toString() + ".");
        System.out.print("The budget for this scene is " + ((Set)currentArea).getScene().getBudget());
        System.out.println(", and there are " + ((Set)currentArea).getShotsRemaining() + " shots remaining.");
        System.out.println("Your role is " + currentRole.getName() + " and you have " + rehearsalChips + " rehearsal chips.");
        System.out.println("What would you like to do?\n\t0: Rehearse\n\t1: Act");
        int input = scanner.nextInt();
        if (input == 0) {
            rehearse();
        } else {
            act();
        }
    }

    private void rehearse() {
        System.out.println("\nYou chose to rehearse. You have gained 1 rehearsal chip.");
        rehearsalChips++;
    }

    private void act() {
        if (((Set)currentArea).shoot(rehearsalChips)) {
            System.out.println("Your acting was a success!");
            if (currentRole.checkOnCard()) {
                System.out.println("You gained 2 credits for succeeding at on card role!");
                credits += 2;
            } else {
                System.out.println("You gained 1 credit and 1 dollar for succeeding at off card role!");
                credits++;
                dollars++;
            }
            if(((Set)currentArea).getShotsRemaining() == 0) {
                ((Set)currentArea).wrap();
            }
        } else {
            System.out.println("Your attempt to act failed...");
            if (!currentRole.checkOnCard()) {
                System.out.println("You gained 1 dollar for failing at off card role...");
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
