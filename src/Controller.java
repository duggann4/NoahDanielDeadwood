package src;

import java.util.ArrayList;

public class Controller {
    
    private static Board board;
    private static ViewHandler view;
    private static ArrayList<Player> playerList;
    private static Controller instance;

    private Controller() {
        board = Board.getInstance();
        view = ViewHandler.getInstance();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public static void addPlayers(ArrayList<Player> players) {
        playerList = players;
        view.addPlayers(players);
    }

    public static void displayMessage(String string) {
        view.print(string);
    }

    public static int getOption(int maxOption) {
        return view.readOption(maxOption);
    }

    public static String getString() {
        return view.readString();
    }
}