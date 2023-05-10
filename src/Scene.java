package src;

/**
 * Title: Scene
 * Author: Noah Duggan Erickson
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
 *  public void setBasic(String title, int budget)
 *      Overrides the existing values for title and budget, respectively
 *      Author: Noah Duggan Erickson
 *      Parameters:
 *          title - the new title of the scene
 *          budget - the new budget of the scene
 * 
 *  public void setFlavor(int sceneNo, String flavor)
 *      Overrides the existing values for sceneNo and flavorText, respectively
 *      Author: Noah Duggan Erickson
 *      Parameters:
 *          sceneNo - the new sceneNo of the scene
 *          flavor - the new flavor text of the scene
 * 
 *  public void addRole(Role role)
 *      Adds a Role object to the internal collection of roles
 *      Author: Noah Duggan Erickson
 *      Parameters:
 *          role - the new role to add to the scene
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
// Note to self: This class represents the cards
public class Scene{
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