package src;

import java.util.ArrayList;
public class Set extends Area {
    private String name;
    private ArrayList<String> neighbors;
    private Scene currentScene;
    private ArrayList<Role> offRoles; // off-card
    private int shotsRemaining;

    public Set(){
        // called exclusively by BoardParser
        name = "";
        neighbors = new ArrayList<String>();
        currentScene = null;
        offRoles = new ArrayList<Role>();
        shotsRemaining = 0;
    }
    public void setName(String name){
        this.name = name;
    }
    public void addNeighbor(String n){
        neighbors.add(n);
    }
    public void setShots(int shots){
        shotsRemaining = shots;
    }
    public void addRole(Role r){
        offRoles.add(r);
    }

    public String toString(){
        return name + " has " + neighbors.size() + " neighbors, " + offRoles.size() + " off-card roles, and " + shotsRemaining + " shots";
    }

    public void setScene(){

    }

    public void shoot(){
    }

    public void wrap(){

    }

    private void payout(){

    }

    private int rollDice(int n, int s){
        return 0;
    }
}