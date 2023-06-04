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
 *      for the number of players, then sets up the game accordingly. 
 *      It then runs the game until the number of days reaches 0, with each day 
 *      consisting of several turns by each player. 
 *      Finally, it scores the game and displays results before closing.
 */

import java.util.ArrayList;
import java.util.Collections;

public class Deadwood {

    private static int numberOfDays;
    private static Board board = Board.getInstance();
    private static ArrayList<Player> players = new ArrayList<>();
    private static Controller controller = Controller.getInstance();

    public static void main(String[] args) {

        ArrayList<String> options = new ArrayList<String>();
        options.add("2");
        options.add("3");
        options.add("4");
        options.add("5");
        options.add("6");
        options.add("7");
        int input = Integer.parseInt(controller.getOption(options, "How many players?"));
        setup(input);

        controller.addPlayers(players);

        for(int day = 1; day <= numberOfDays; day++) {
            playDay(day);
        }
        endGame();
    }

    private static void setup(int numPlayers) {
        createPlayers(numPlayers);
        Collections.shuffle(players);

        if (numPlayers < 4) {
            numberOfDays = 3;
        } else {
            numberOfDays = 4;
        }
        board.placeNewScenes();

        for(Player player : players) {
            player.setArea(board.getArea("trailer"));
            player.updatePosition();
        }
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
            String name = "Player " + (i+1);
            String diceColor = null;
            switch (i) {
                case 0:
                    diceColor = "b";
                    break;
                case 1:
                    diceColor = "c";
                    break;
                case 2:
                    diceColor = "g";
                    break;
                case 3:
                    diceColor = "o";
                    break;
                case 4:
                    diceColor = "p";
                    break;
                case 5:
                    diceColor = "r";
                    break;
                case 6:
                    diceColor = "v";
                    break;
                case 7:
                    diceColor = "y";
                    break;
            }

            players.add(new Player(name, rank, credits, diceColor));
        }
    }

    private static void playDay(int day) {
        while (board.getActiveSceneCount() > 1) {
            for (Player player : players) {
                controller.setCurrentPlayer(player);
                player.playTurn();
                if (board.getActiveSceneCount() <= 1) {
                    break;
                }
            }
        }
        endDay(day);
    }

    private static void endDay(int day) {
        ArrayList<String> options = new ArrayList<String>();
        options.add("Continue");
        String prompt;
        if (day != numberOfDays) {
            prompt = "Day " + day + " has completed, all actors will return to their trailers.\n\n" + "Remaining days: " + (numberOfDays - day);
        } else {
            prompt = "The final day has ended.";
        }
        controller.getOption(options, prompt);
        for (Player player : players) {
            player.setArea(board.getArea("trailer"));
            player.removeRole();
            player.updatePosition();
        }
        board.placeNewScenes();
    }

    private static void endGame() {
        score();
        System.exit(0);
    }

    private static void score() {
        ArrayList<String> options = new ArrayList<String>();
        options.add("Exit");
        String prompt = "Score Board\n--------------------------------";
        int highScore = 0;
        Player winner = null;
        for (Player player : players) {
            int score = player.getScore();
            prompt = prompt.concat("\n" + player.getName() + " scored " + score + " points.");
            if (score > highScore) {
                highScore = score;
                winner = player;
            }
        }
        prompt = prompt.concat("\n\nThe winner is " + winner.getName() + "!");
        controller.getOption(options, prompt);
    }
}