package src;

import java.util.ArrayList;

public class Controller {
    
    private ViewHandler view;
    private Board board;
    private ArrayList<Player> playerList;
    private String selectedOption;
    private Player currentPlayer;
    private static Controller instance;

    private Controller() {
        board = Board.getInstance();
        view = new ViewHandler(this);
    }

    public String getOption(ArrayList<String> options, String prompt) {
        updateOptions(options, prompt);
        // Wait for the option to be selected
        while (selectedOption == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String selected = selectedOption;
        selectedOption = null;
        return selected;
    }

    public void updateOptions(ArrayList<String> options, String prompt) {
        view.clearButtons();
        view.addButtons(options);
        view.setPrompt(prompt);
    }

    public void updateGUI() {
        view.repaint();
    }

    public ArrayList<Player> getPlayers() {
        return playerList;
    }

    public void addPlayers(ArrayList<Player> players) {
        playerList = players;
    }

    public void setCurrentPlayer(Player player) {
        view.setActivePlayer(player);
    }

    public void setSelectedOption(String option) {
        selectedOption = option;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public ArrayList<Scene> getActiveScenes() {
        return board.getActiveScenes();
    }

    public ArrayList<Area> getAreas() {
        return board.getAreas();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }
}