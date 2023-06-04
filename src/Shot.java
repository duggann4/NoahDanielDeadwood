package src;

/**
 * Title: Shot
 * Author: Daniel Wertz
 * CSCI 345
 * Spring 2023
 * 
 * DESCRIPTION:
 * Shot object to represent an individual take on a set. 
 *      tracks the complete status of the shot and stores it's GUI information
 * 
 * CONSTRUCTOR:
 *  Shot()
 *      Constructs a new Shot object with incomplete status as default
 *      Author: Daniel Wertz
 * 
 * METHODS:
 *  public void setComplete(boolean complete)
 *      Set the completion status of the shot
 *      Author: Daniel Wertz
 *      Parameters:
 *          complete - true if completed
 * 
 *  public boolean checkCompelte()
 *      Check the whether or not a shot has been completed for the day.
 *      Author: Daniel Wertz
 *      Returns:
 *          Completion status of the shot
 */

import javax.swing.ImageIcon;

public class Shot extends GUIElement {

    private boolean shotComplete;

    public Shot() {
        shotComplete = false;
        image = new ImageIcon(getClass().getResource("../img/shot.png"));
    }
    
    public void setComplete(boolean complete) {
        shotComplete = complete;
    }

    public boolean checkComplete() {
        return shotComplete;
    }
}