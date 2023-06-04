package src;

/**
 * Title: Scene
 * Author: Noah Duggan Erickson, Daniel Wertz
 * CSCI 345
 * Spring 2023
 * 
 * DESCRIPTION:
 *  A "container" for information pertaining to Scene cards
 * 
 * CONSTRUCTORS:
 *  public Scene()
 *      Creates a new Scene object with non-null but empty field values
 *      Author: Noah Duggan Erickson
 * 
 * METHODS:
 *  public void setBasic(String title, int budget, int imageNum)
 *      Overrides the existing values for title, budget, image, and revealed image.
 *      Author: Noah Duggan Erickson, Daniel Wertz
 *      Parameters:
 *          title - the new title of the scene
 *          budget - the new budget of the scene
 *          imageNum - images number in directory
 * 
 *  public void setFlavor(int sceneNo, String flavor)
 *      Overrides the existing values for sceneNo and flavorText, respectively
 *      Author: Noah Duggan Erickson
 *      Parameters:
 *          sceneNo - the new sceneNo of the scene
 *          flavor - the new flavor text of the scene
 * 
 *  public void updateRolePositions()
 *      updates the coordinates of on card roles.
 *      Use after placing cards.
 *      Author: Daniel Wertz
 * 
 *  public void revealScene()
 *      Replaces scene image with revelead scene.
 *      Author: Daniel Wertz
 * 
 *  public boolean checkRevealed()
 *      Check if a scene has been revealed yet.
 *      Author: Daniel Wertz
 *      Returns:
 *          true if scene is revealed
 * 
 *  public void addRole(Role role)
 *      Adds a Role object to the internal collection of roles
 *      Author: Noah Duggan Erickson
 *      Parameters:
 *          role - the new role to add to the scene
 * 
 *  public ArrayList<Role> getRoles()
 *      Returns list of roles on this card
 *      Author: Daniel Wertz
 *      Returns:
 *          this.onRoles
 * 
 *  public int getBudget()
 *      Returns the budget of this card
 *      Author: Daniel Wertz
 *      Returns:
 *          this.budget
 * 
 *  public String toString()
 *      Returns a string representation of the Scene
 *      Author: Noah Duggan Erickson
 *      Returns:
 *          A string represention of the Scene's basic information
 *      Overrides:
 *          toString in class Object
 * 
 *  public String tastyString()
 *      Returns a string representation of the Scene
 *      Author: Noah Duggan Erickson
 *      Returns:
 *          A string represention of the Scene's detailed information
 * 
 * INHERITED METHODS:
 *  Standard java.lang.Object inheritance
 */

import java.util.ArrayList;
import javax.swing.ImageIcon;
// Note to self: This class represents the cards
public class Scene extends GUIElement {
    private String title;
    private String flavor;
    private int sceneNo;
    private int budget;
    private ArrayList<Role> onRoles; // on-card
    private boolean revealed;
    private ImageIcon revealedImage;
    
    public Scene() {
        title = "";
        flavor = "";
        sceneNo = 0;
        budget = 0;
        onRoles = new ArrayList<Role>();
        revealed = false;
        this.width = 205;
        this.height = 115;
    }

    public void setBasic(String title, int budget, int imageNum) {
        this.title = title;
        this.budget = budget;
        this.image = new ImageIcon(getClass().getResource("../img/CardBack-small.jpg"));
        this.revealedImage = new ImageIcon(getClass().getResource("../img/cards/" + String.format("%02d.png", imageNum)));
    }
    public void setFlavor(int sceneNo, String flavor) {
        this.sceneNo = sceneNo;
        this.flavor = flavor;
    }

    public void updateRolePositions() {
        for(Role role : onRoles) {
            role.setX(role.getX() + x);
            role.setY(role.getY() + y);
        }
    }

    public void revealScene() {
        revealed = true;
        image = revealedImage;
    }

    public boolean checkRevealed() {
        return revealed;
    }

    public void addRole(Role role) {
        onRoles.add(role);
    }

    public ArrayList<Role> getRoles() {
        return onRoles;
    }

    public int getBudget() {
        return budget;
    }

    public String toString() {
        return title + ", Budget: " + budget;
    }

    // Because it's *flavorful*...? No? Tough crowd I guess...
    //
    public String tastyString() {
        return title + " (" + sceneNo + "), Budget: " + budget + flavor;
    }
}