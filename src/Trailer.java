package src;

// USES SINGLETON DESIGN PATTERN

import java.util.ArrayList;
public class Trailer implements Area{
    private ArrayList<String> neighbors;
    private static Trailer instance = new Trailer();
    private Trailer(){
        neighbors = new ArrayList<String>();
    }
    public String getName(){
        return "Trailer";
    }
    public ArrayList<String> getNeighbors(){
        return instance.neighbors;
    }
    public boolean equals(Area a){
        return a.getName().equals("Trailer");
    }
    public void addNeighbor(String name){
        instance.neighbors.add(name);
    }
    public static Trailer getInstance(){
        return instance;
    }
}
