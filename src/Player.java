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

        if (currentArea.getName() == "office") {
            System.out.println("Would you like to upgrade your rank?\n\t0: Yes\n\t1: No");
            int input = scanner.nextInt();
            if (input == 0) {
                upgrade();
            }
        }

        if (currentRole != null) {
            //TODO: Display more information about current role
            System.out.println("You are currently working for the scene " + ((Set)currentArea).getScene().toString() + ".");
            System.out.println("The budget for this scene is " + ((Set)currentArea).getScene().getBudget());
            System.out.println("Your role is " + currentRole.getName() + " and you have " + rehearsalChips + " rehearsal chips.");
            System.out.println("What would you like to do?\n\t0: Rehearse\n\t1: Act");
            int input = scanner.nextInt();
            if (input == 0) {
                rehearse();
            } else {
                act();
            }
        } else {
            if (currentArea instanceof Set) {
                offerRole(((Set)currentArea).getAvailableRoles());
            }
            move();
        }
        System.out.println("-Ending Turn-\n");
    }

    private void upgrade() {
        // TODO: Display options, have player select valid rank, and update rank
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
                if (currentArea instanceof Set) {
                    offerRole(((Set)currentArea).getAvailableRoles());
                }
            }
        } 
    }

    public void setArea(Area area) {
        currentArea = area;
    }

    private void displayMoveOptions() {
        
    }

    //TODO: Apply rank check for roles
    private void offerRole(ArrayList<Role> roles) {
        if (roles.size() == 0) {
            System.out.println("No available roles at current location");
        } else {
            System.out.println("Select a role to take:"); 
            System.out.println("\t0: Don't take role");
            for (Role role : roles) {
                System.out.println("\t" + (roles.indexOf(role) + 1) + ": " + role.toString()); //TODO: specify which roles are on card
            }
            int input = scanner.nextInt();
            if (input == 0) {
                System.out.println("\nYou chose not to take a role.");
            } else { //TODO: bounds checking
                currentRole = roles.get(input - 1);
                currentRole.setPlayer(this);
                System.out.println("\nYou have taken the role of " + currentRole.getName() + ".");
            }
        }
    }

    private void rehearse() {
        System.out.println("\nYou chose to rehearse. You have gained 1 rehearsal chip.");
        rehearsalChips++;
    }

    private void act() {
        if (((Set)currentArea).shoot(rehearsalChips)) {
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
}
