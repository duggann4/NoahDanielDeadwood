package src;

/**
 * Title: Controller
 * Author: Daniel Wertz
 * CSCI 345
 * Spring 2023
 * 
 * DESCRIPTION:
 *  A Singleton class that handles interactions between the model components of the program and the GUI
 * 
 * METHODS:
 *  public static Controller getInstance()
 *      Artefact of the Singleton design
 *      Returns:
 *          The instance of Controller
 * 
 *  public String getOption(options, prompt)
 *      Creates text and buttons using ViewHandler and wait for player to select option.
 *      Parameters:
 *          options: the text to be displayed on the buttons
 *          prompt: the name of the dialog
 *      Returns:
 *          the text of the selected option
 * 
 *  public void updateOptions(options, prompt)
 *      Resets prompt and buttons
 *      Parameters:
 *          options: the text to be displayed on the dialog buttons
 *          prompt: the name of the dialog
 * 
 *  public void updateGUI()
 *      Gets view to push changes to screen
 * 
 */
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
