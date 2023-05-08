package src;

import java.util.Scanner;

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

        // TODO: Might need to change check for casting office
        if (currentArea.getName() == "CastingOffice") {
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
    }

    private void upgrade() {
        // TODO: Display options, have player select valid rank, and update rank
    }

    private void move() {

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
