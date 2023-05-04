package src;

import java.util.ArrayList;
// Note to self: This class represents the cards
public class Scene extends Object{
    private String title;
    private String flavor;
    private int sceneNo;
    private int budget;
    private ArrayList<Role> onRoles; // on-card
    
    public Scene(){
        title = "";
        flavor = "";
        sceneNo = 0;
        budget = 0;
        onRoles = new ArrayList<Role>();
    }

    public void setBasic(String title, int budget){
        this.title = title;
        this.budget = budget;
    }
    public void setFlavor(int sceneNo, String flavor){
        this.sceneNo = sceneNo;
        this.flavor = flavor;
    }
    public void addRole(Role role){
        onRoles.add(role);
    }
    public String toString(){
        return title + " scene " + sceneNo;
    }

    // Because it's *flavorful*...? No? Tough crowd I guess...
    //
    public String tastyString(){
        return title + "(" + sceneNo + "), Budget:" + budget + flavor;
    }
}