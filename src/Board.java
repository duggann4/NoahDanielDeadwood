package src;

/**
 * Title: Board
 * Author: Daniel Wertz
 * CSCI 345
 * Spring 2023
 * 
 * DESCRIPTION:
 * Singleton representing the game board, including the deck and active scenes.
 * 
 * METHODS:
 *  public void placeNewScenes()
 *      Places new scenes on the board by taking  the top 10 cards 
 *          from the deck and assigning them to sets.
 *      Author: Daniel Wertz
 * 
 *  public void removeScene(Scene scene)
 *      Removes the given scene from the list of active scenes.
 *      Author: Daniel Wertz
 *      Parameters:
 *          scene - the scene to remove
 * 
 *  public int getActiveSceneCount()
 *      Returns the number of active scenes on the board.
 *      Author: Daniel Wertz
 *      Returns:
 *          the number of active scenes
 * 
 *  public Area getArea(String areaName)
 *      Returns the area with the given name.
 *      Author: Daniel Wertz
 *      Parameters:
 *          areaName - the name of the area to retrieve
 *      Returns:
 *          Area object with the given name
 * 
 *  public void printBoard()
 *      Prints the Areas on the board and information on their 
 *          neighbors, scenes, and players.
 *      Author: Daniel Wertz
 * 
 *  public static Board getInstance()
 *      Returns singleton instance of Board.
 *      Author: Daniel Wertz
 *      Returns:
 *          instance of Board
 * 
 * INHERITED METHODS:
 *  Standard java.lang.Object inheritance
 */

import java.util.ArrayList;
import java.util.Collections;

public class Board {

    private ArrayList<Area> areas = new ArrayList<Area>();
    private ArrayList<Scene> activeScenes = new ArrayList<Scene>();
    private ArrayList<Scene> cardDeck;
    private static Board instance = new Board();
    private Board() {
        BoardParser boardParser = new BoardParser("data/board.xml");
        ArrayList<Set> sets = boardParser.readBoard();
        areas.add(Trailer.getInstance());
        areas.add(Office.getInstance());
        for (Set set : sets) {
            areas.add(set);
        }

        CardParser cardParser = new CardParser("data/cards.xml");
        cardDeck = cardParser.readCards();
        Collections.shuffle(cardDeck);
    }

    public void placeNewScenes() {
        activeScenes.clear();
        for(Area area : areas) {
            if (area instanceof Set) {
                ((Set)area).resetSet();
            }
        }
        for (int i = 0; i < 10; i++) {
            activeScenes.add(cardDeck.get(0));
            cardDeck.remove(0);
        }
        
        int i = 0;
        for(Area area : areas) {
            if (area instanceof Set) {
                ((Set)area).setScene(activeScenes.get(i));
                i++;
            }
        }
    }

    public void removeScene(Scene scene) {
        activeScenes.remove(scene);
    }

    public int getActiveSceneCount() {
        return activeScenes.size();
    }

    public Area getArea(String areaName) {
        for(Area area : areas) {
            if(area.getName().equals(areaName)) {
                return area;
            }
        }
        return null;
    }

    public void printBoard() {
        System.out.println();
        for (Area area : areas) {
            System.out.println(area.getName() + "\n\tNeighbors: " + area.getNeighbors());
            if (area instanceof Set) {
                System.out.println("\tActice Scene: " + ((Set)area).getScene().toString());
            }
        }
        System.out.println();
    }

    public static Board getInstance() {
        return instance;
    }
}