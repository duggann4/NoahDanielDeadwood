package src;

/**
 * Title: CardParser
 * Author: Noah Duggan Erickson
 * CSCI 345
 * Spring 2023
 * 
 * DESCRIPTION:
 *  Reads and parses an xml file containing card-related information in the
 *      format specified below into objects that are used elsewhere in the
 *      program.
 * 
 *  File Format:
 *      The element containing card data must be called "card" with the name
 *          and integer budget as attributes, in that order.
 *      Card elements must have children "part" and any number of "roles".
 *      Part elements must have an integer sceneID number as attribute, and
 *          contain a string (can be multiline).
 *      Specification for Roles provided in Parser.
 * 
 * CONSTRUCTORS:
 *  CardParser(String filepath)
 *      Constructs a new CardParser object and attempts to open the file
 *          at provided path.
 *      Author: Noah Duggan Erickson
 *      Parameters:
 *          filepath - path to the file to parse
 *              REPO INFO: If the structure of the NoahDanielDeadwood repo
 *                  is unchanged, path is "../data/cards.xml"
 *      Error codes:
 *          51: Problem opening file
 * 
 * METHODS:
 *  public ArrayList<Scene> readCards()
 *      Executes the parser on the provided file
 *      Author: Noah Duggan Erickson
 *      Returns:
 *          ArrayList containing Scene objects read from the file
 * 
 * INHERITED METHODS:
 *  Standard java.lang.Object inheritance
 */

import java.util.ArrayList;

public class CardParser extends Parser{

    public CardParser(String filepath){
        openFile(filepath);
    }

    public static ArrayList<Scene> readCards(){
        ArrayList<Scene> out = new ArrayList<Scene>();
        while(scan.hasNextLine()){
            String line = scan.nextLine().strip();
            if(line.contains("<card ")){
                out.add(readCard(line));
            }
        }
        return out;
    }

    private static Scene readCard(String line){
        Scene s = new Scene();
        String[] attrs = line.split("\"");
        s.setBasic(attrs[1], Integer.parseInt(attrs[5]));
        

        while(!line.contains("</card>")){
            line = scan.nextLine();
            if(line.contains("<scene ")){
                readFlavor(line, s);
            } else if(line.contains("<part ")){
                s.addRole(readPart(line, true));
            }
        }
        return s;
    }

    private static void readFlavor(String line, Scene s){
        String flavorText = "";
        int sceneNo = Integer.parseInt(line.split("\"")[1]);
        line = scan.nextLine();
        while(!line.contains("</scene>")){
            flavorText = flavorText + "\n" + line.trim();
            line = scan.nextLine();
        }
        s.setFlavor(sceneNo, flavorText);
    }
}