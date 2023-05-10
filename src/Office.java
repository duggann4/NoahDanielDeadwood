package src;

import java.util.ArrayList;
import java.util.Arrays;

public class Office implements Area{
    private ArrayList<String> neighbors;
    private ArrayList<Upgrade> upgrades;
    private static Office instance = new Office();
    private Office(){
        neighbors = new ArrayList<String>();
        upgrades = new ArrayList<Upgrade>();
    }
    public String getName(){
        return "Office";
    }
    public ArrayList<String> getNeighbors(){
        return instance.neighbors;
    }
    public ArrayList<Upgrade> getUpgrades(){
        return instance.upgrades;
    }
    public boolean equals(Area a){
        return a.getName().equals("Office");
    }
    public void addNeighbor(String name){
        instance.neighbors.add(name);
    }
    public void addUpgrade(String line){
        String[] attrs = line.split("\"");
        instance.upgrades.add(new Upgrade(Integer.parseInt(attrs[1]), attrs[3], Integer.parseInt(attrs[5])));
    }
    public static Office getInstance(){
        return instance;
    }
}