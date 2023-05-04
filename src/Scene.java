package src;


// Note to self: This class represents the cards
public class Scene {
    private String title;
    private String flavor;
    private int sceneNo;
    private int budget;
    private Role[] onRoles; // on-card
    
    public Scene(){
        // called exclusively from CardParser
    }

    public String toString(){
        return title + " scene " + sceneNo;
    }

    // Because it's *flavorful*...? No? Tough crowd I guess...
    //
    public String tastyString(){
        return title + "(" + sceneNo + "): " + flavor + "; Budget:" + budget;
    }
}