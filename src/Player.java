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
        System.out.println("It is now " + name + "'s turn.");

        if (currentArea.getName() == "Office") {
            System.out.println("Would you like to upgrade your rank?\n\t0: Yes\n\t1: No");
            int input = scanner.nextInt();
            if (input == 0) {
                upgrade();
            }
        }

        if (currentRole != null) {
            //TODO: Display more information about current role
            System.out.println("Your current role is " + currentRole.getName() + " and you have " + rehearsalChips + "rehearsal chips.");
            System.out.println("What would you like to do?\n\t0: Rehearse\n\t1: Act");
            int input = scanner.nextInt();
            if (input == 0) {
                rehearse();
            } else {
                act();
            }
        } else {
            move();
        }
        System.out.println("-Ending Turn-\n");
    }

    private void upgrade() {
        // TODO: Display options, have player select valid rank, and update rank
    }

    private void move() {
        System.out.println("Your current location is the " + currentArea.getName() + ".\nWould you like to move?\n\t0: Yes\n\t1: No");
        int input = scanner.nextInt();
        if (input == 0) {
            ArrayList<String> neighbors = currentArea.getNeighbors();
            System.out.println("Select a neighboring area to move to:");
            System.out.println("\t0: Remain at current location");
            for (String neighbor : neighbors) {
                System.out.println("\t" + (neighbors.indexOf(neighbor) + 1) + ": " + neighbor);
            }
            input = scanner.nextInt();
            if (input == 0) {
                System.out.println("Your location remains at the " + currentArea.getName());
            } else { //TODO: bounds checking
                currentArea = Board.getInstance().getArea(neighbors.get(input - 1));
                System.out.println("You have moved to the " + currentArea.getName()); // Fix
            }
        } 
    }

    public void setArea(Area area) {
        currentArea = area;
    }

    private void displayMoveOptions() {
        
    }

    private void takeRole(Role role) {

    }

    private void rehearse() {
        System.out.println("You chose to rehearse. You have gained 1 rehearsal chip.");
        rehearsalChips++;
    }

    private void act() {

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
