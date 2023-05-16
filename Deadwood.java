import src.*;

/**
 * Title: Deadwood
 * Authors: Daniel Wertz
 * CSCI 345
 * Spring 2023
 * 
 * DESCRIPTION:
 *  Main class for the Deadwood game. This class is responsible for
 *  running the game and handling the overarching game logic
 * 
 * METHODS:
 *  public static void main(String args[])
 *      This method is the starting point of the program. It first checks
 *      for the number of players provided as a command line argument, then
 *      sets up the game accordingly. It then runs the game until the number 
 *      of days reaches 0, with each day consisting of several turns by each
 *      player. Finally, it scores the game and displays results before closing.
 */

import java.util.ArrayList;
import java.util.Collections;

public class Deadwood {

    private static int numberOfDays;
    private static Board board = Board.getInstance();
    private static ViewHandler view = ViewHandler.getInstance();
    private static ArrayList<Player> players = new ArrayList<>();

    public static void main(String args[]) {
        if (args.length == 0) {
            view.print("Must enter number of players as a command line argument");
            return;
        }
        int numPlayers = Integer.parseInt(args[0]);
        if (numPlayers < 2 || numPlayers > 8) {
            view.print("Number of players must be between 2 and 8.");
            return;
        }

        setup(numPlayers);
        for(int day = 1; day <= numberOfDays; day++) {
            playDay(day);
        }
        endGame();
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
        board.placeNewScenes();
        //board.printBoard();
        for(Player player : players) {
            player.setArea(board.getArea("trailer"));
        }
    }

    private static void welcomeMessage() {
        // TODO: Add proper welcome message
        view.print("Hello! Welcome to Deadwood and stuff!");
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
            view.print("\nEnter name for player " + (i+1) + ":");
            String name = view.readString();
            players.add(new Player(name, rank, credits));
        }
    }

    private static void playDay(int day) {
        view.print("\n==================== Start Day " + day + " ====================");
        while (board.getActiveSceneCount() > 1) {
            for (Player player : players) {
                player.playTurn();
                view.print("\n-------------------- Ending Turn --------------------");
                if (board.getActiveSceneCount() <= 1) {
                    break;
                }
            }
        }
        endDay();
    }

    private static void endDay() {
        for (Player player : players) {
            player.setArea(board.getArea("trailer"));
            player.removeRole();
        }
        board.placeNewScenes();
    }

    private static void endGame() {
        view.print("\n==================== Ending Final Day ====================");
        score();
    }

    private static void score() {
        int highScore = 0;
        Player winner = null;
        view.print("\n\tScore Board:\n\t------------");
        for (Player player : players) {
            int score = player.getScore();
            view.print("\t" + player.getName() + " scored " + score + " points.");
            if (score > highScore) {
                highScore = score;
                winner = player;
            }
        }
        view.print("\nCongradulations " + winner.getName() + ", you won!\n");
    }
}