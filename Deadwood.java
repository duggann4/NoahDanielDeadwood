import src.*;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Deadwood {

    private static Board board;
    private static int numberOfDays;
    private static ArrayList<Player> players = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String args[]) {
        if (args.length == 0) {
            System.out.println("Must enter number of players as a command line argument");
            return;
        }
        
        int numPlayers = Integer.parseInt(args[0]);
        if (numPlayers < 2 || numPlayers > 8) {
            System.out.println("Number of players must be between 2 and 8.");
            return;
        }

        setup(numPlayers);

        // Turn Loop
        for(int day = 1; day <= numberOfDays; day++) {
            System.out.println("\n-- Start Day " + day + " --\n");
            // Player turns
            for (Player player : players) {
                player.playTurn();
            }
            endDay();
        }
    }

    private static void setup(int numPlayers) {
        welcomeMessage();
        createPlayers(numPlayers);
        Collections.shuffle(players);

        if (numPlayers < 4) {
            numberOfDays = 3;
        } else {
            numberOfDays = 4;
        }

        // TODO: Load scenes and setup board
    }

    private static void welcomeMessage() {
        // TODO: Add proper welcome message
        System.out.println("Hello! Welcome to Deadwood and stuff!\n");
    }

    private static void createPlayers(int numPlayers) {
        int credits = 0;
        int rank = 1;
        if (numPlayers == 5) {
            credits = 2;
        } else if (numPlayers == 6) {
            credits = 4;
        } else if (numPlayers >= 7 && numPlayers <= 8) {
            rank = 2;
        }

        for (int i = 0; i < numPlayers; i++) {
            System.out.println("Enter name for player " + (i+1) + ":");
            String name = scanner.nextLine();
            players.add(new Player(name, credits, rank));
        }
    }

    private static void endDay() {
        for (Player player : players) {
            // TODO: Set player positions to Trailer
        }

        // TODO: Remove remaining scene, reset shot counters, and place new scenes
    }

    private static void score() {
        // TODO: Calculate scores and display results
    }

    private static void close() {

    }
}