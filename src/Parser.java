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
 *  protected static Role readPart(String line)
 *      Parses information pertaining to roles/parts into a new Role object.
 *      Author: Noah Duggan Erickson
 *      Parameters:
 *          line - the current line of scan
 *      Returns:
 *          Role object containing parsed information
 * 
 * INHERITED METHODS:
 *  Standard java.lang.Object inheritance
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Parser {
    static File f;
    static Scanner scan;

    static void openFile(String path){
        try{
            f = new File(path);
            scan = new Scanner(f);
        } catch(IOException e){
            System.err.println("Problem opening file!");
            System.exit(51);
        }
    }

    static Role readPart(String line){
        Role role = new Role();
        String flavorText = "";
        String[] attrs = line.split("\"");
        role.setBasic(attrs[1], Integer.parseInt(attrs[3]));
        line = scan.nextLine();
        while(!line.contains("</part>")){
            if(line.contains("<line>")){
                flavorText = line.replaceAll("<line>", "").replaceAll("</line>", "").trim();
            }
            line = scan.nextLine();
        }
        role.setFlavor(flavorText);
        return role;
    }
}
