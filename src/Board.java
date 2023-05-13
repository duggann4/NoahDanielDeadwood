package src;

import java.util.ArrayList;
import java.util.Collections;

public class Board {

    private ArrayList<Area> areas = new ArrayList<Area>();
    private ArrayList<Scene> activeScenes = new ArrayList<Scene>();
    private ArrayList<Scene> cardDeck;
    private static Board instance = new Board();
    private Board(){
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

    public Area getArea(String areaName) {
        for(Area area : areas) {
            if(area.getName().equals(areaName)) {
                return area;
            }
        }
        // TODO: throw exception or something
        return null;
    }

    public void removeScene(Scene scene) {
        activeScenes.remove(scene);
    }

    public int getActiveSceneCount() {
        return activeScenes.size();
    }

    public static Board getInstance(){
        return instance;
    }

    // Print board for debugging
    public void printBoard() {
        for (Area area : areas) {
            System.out.println(area.getName() + "\n\t" + area.getNeighbors());
            if (area instanceof Set) {
                System.out.println("\tActice Scene: " + ((Set)area).getScene().toString());
            }
        }
    }
}