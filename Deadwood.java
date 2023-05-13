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
        for(int day = 1; day <= numberOfDays; day++) {
            System.out.println("\n-- Start Day " + day + " --\n");
            while (board.getActiveSceneCount() > 1) {
                for (Player player : players) {
                    player.playTurn();
                    if (board.getActiveSceneCount() <= 1) { //TODO: better loop logic
                        break;
                    }
                }
            }
            endDay();
        }
        score();
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

        board = Board.getInstance();
        board.placeNewScenes();
        //board.printBoard();

        for(Player player : players) {
            player.setArea(board.getArea("trailer"));
        }
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
            players.add(new Player(name, rank, credits));
        }
    }

    private static void endDay() {
        for (Player player : players) {
            player.setArea(board.getArea("trailer"));
            player.removeRole();
        }
        board.placeNewScenes();
    }

    private static void score() {
        int highScore = 0;
        Player winner = null;
        for (Player player : players) {
            int score = player.getScore();
            System.out.println(player.getName() + " scored " + score + " points!");
            if (score > highScore) {
                highScore = score;
                winner = player;
            }
        }
        System.out.println("\n\n Congradulations " + winner.getName() + "! You won!");
    }

    private static void close() {

    }
}