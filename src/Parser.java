package src;

/**
 * Title: abstract Parser
 * Author: Noah Duggan Erickson
 * CSCI 345
 * Spring 2023
 * 
 * DESCRIPTION:
 *  An abstract class providing implementations for
 *      common functionalities in the parsers
 *      elsewhere in this program.
 * 
 * CONSTRUCTORS:
 *  public Parser()
 * 
 * FIELDS:
 *  protected static File f
 *      File object containing the file at the specified path
 *      Author: Noah Duggan Erickson
 *  protected static Scanner scan
 *      Scanner object using f as input
 *      Author: Noah Duggan Erickson
 * 
 * METHODS:
 *  protected static void openFile(String path)
 *      Attempts to open the file at the specifed path into f and scan
 *      Author: Noah Duggan Erickson
 *      Parameters:
 *          path - the path of the file to open
 *      Error codes:
 *          51: Could not open file
 * 
 *  protected static Role readPart(String line, String area, boolean onCard)
 *      Parses information pertaining to roles/parts into a new Role object.
 *      Author: Noah Duggan Erickson
 *      Parameters:
 *          line - line containing part info
 *          area - line containing coordinates and dimensions for gui
 *          onCard - true if role is on card
 *      Returns:
 *          Role object containing parsed information
 * 
 *  protected static void readArea(GUIElement element, String line)
 *      Parses coordinates and dimensions from area xml line.
 *      Author: Daniel Wertz
 *      Parameters:
 *          element - an object that inherets GUIElement
 *          line - area xml line        
 * 
 * INHERITED METHODS:
 *  Standard java.lang.Object inheritance
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public abstract class Parser {
    protected static File file;
    protected static Scanner scan;

    protected static void openFile(String path) {
        try {
            file = new File(path);
            scan = new Scanner(file);
        } catch(IOException e) {
            System.err.println("Problem opening file!");
            System.exit(51);
        }
    }

    protected static Role readPart(String line, String area, boolean onCard) {
        Role role = new Role();
        String flavorText = "";
        String[] attrs = line.split("\"");
        role.setBasic(attrs[1], Integer.parseInt(attrs[3]));
        
        line = scan.nextLine();
        while (!line.contains("</part>")) {
            if (line.contains("<line>")) {
                flavorText = line.replaceAll("<line>", "").replaceAll("</line>", "").trim();
            }
            line = scan.nextLine();
        }

        readArea(role, area);
        role.setFlavor(flavorText);
        role.setOnCard(onCard);
        
        return role;
    }

    protected static void readArea(GUIElement element, String line) {
        int x = Integer.parseInt(line.substring(line.indexOf("x=\"") + 3, line.indexOf("\" y=")));
        int y = Integer.parseInt(line.substring(line.indexOf("y=\"") + 3, line.indexOf("\" h=")));
        int h = Integer.parseInt(line.substring(line.indexOf("h=\"") + 3, line.indexOf("\" w=")));
        int w = Integer.parseInt(line.substring(line.indexOf("w=\"") + 3, line.lastIndexOf("\"")));

        element.setX(x);
        element.setY(y);
        element.setHeight(h);
        element.setWidth(w);
    }
}
