package src;

/**
 * Title: Abstract class GUIHandler
 * Author: Daniel Wertz
 * CSCI 345
 * Spring 2023
 * 
 * DESCRIPTION:
 *  An abstract class inhereted by classes that are GUI components.
 *      Stores information regarding the coordinates and size
 *      and methods to adjust them.
 * 
 * METHODS:
 *  public PImage getImage()
 *      Returns the related image if element has one.
 *      Author: Daniel Wertz
 *      Returns:
 *          this.image
 * 
 *  public void setImage(PImage image)
 *      Sets an image for the GUI element, null by default
 *      Author: Daniel Wertz
 *      Parameters:
 *          image - image representing element
 * 
 *  public int getX()
 *      Returns X coordinate of element.
 *      Author: Daniel Wertz
 *      Returns:
 *          this.x
 * 
 *  public void setX(int x)
 *      Sets the X coordinate of element.
 *      Author: Daniel Wertz
 *      Parameters:
 *          x - the x coordinate
 * 
 *  public int getY()
 *      Returns Y coordinate of element.
 *      Author: Daniel Wertz
 *      Returns:
 *          this.y
 * 
 *  public void setY(int y)
 *      Sets the Y coordinate of element.
 *      Author: Daniel Wertz
 *      Parameters:
 *          y - the y coordinate
 * 
 *  public int getWidth()
 *      Returns width of element.
 *      Author: Daniel Wertz
 *      Returns:
 *          this.width
 * 
 *  public void setWidth(int width)
 *      Sets the width of element.
 *      Author: Daniel Wertz
 *      Parameters:
 *          width - the width of the element
 * 
 *  public int getHeight()
 *      Returns the height of the element.
 *      Author: Daniel Wertz
 *      Returns:
 *          this.height
 * 
 *  public void setHeight(int height)
 *      Sets the height of element.
 *      Author: Daniel Wertz
 *      Parameters:
 *          height - the height of the element
 */

//import processing.core.PImage;

public abstract class GUIElement {
    //protected PImage image = null;
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    //public int getImage() {
    //    return image;
    //}
    //public void setImage(PImage image) {
    //    this.image = image;
    //}

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
}