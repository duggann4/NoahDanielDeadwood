package src;

/**
 * Title: BoardParser
 * Author: Noah Duggan Erickson
 * CSCI 345
 * Spring 2023
 * 
 * DESCRIPTION:
 *  Reads and parses an xml file containing board-related information in the
 *      format specified below into objects that are used elsewhere in the
 *      program.
 * 
 *  File Format:
 *      The element containing set data must be called "set" with the name
 *          as attribute.
 *      Set elements must have children "neighbors", "takes", and "parts".
 *      Neighbors elements can contain any number of "neighbor" children
 *          containing other location names.
 *      Takes can contain any number of "take" children with arbitrary content.
 *      Parts can contain any number of roles, as specified in Parser.
 * 
 * CONSTRUCTORS:
 *  BoardParser(String filepath)
 *      Constructs a new BoardParser object and attempts to open the file
 *          at provided path
 *      Author: Noah Duggan Erickson
 *      Parameters:
 *          filepath - path to the file to parse
 *              REPO INFO: If the structure of the NoahDanielDeadwood repo
 *                  is unchanged, path is "../data/board.xml"
 *      Error codes:
 *          51: Problem opening file
 * 
 * METHODS:
 *  public ArrayList<Set> readBoard()
 *      Executes the parser on the provided file
 *      Author: Noah Duggan Erickson
 *      Returns:
 *          ArrayList containing Set objects read from the file
 * 
 * INHERITED METHODS:
 *  Standard java.lang.Object inheritance
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

/*
 * Noah's TODO list:
 *  1. Create parsers for <trailers> and <office>
 *  1a. Create corresponding classes, implements Area
 */

public class BoardParser extends Parser{

    public BoardParser(String filepath){
        openFile(filepath);
    }

    public static ArrayList<Set> readBoard(){
        ArrayList<Set> out = new ArrayList<Set>();
        while(scan.hasNextLine()){
            String line = scan.nextLine().strip();
            if(line.contains("<set ")){
                out.add(readSet(line));
            }
        }
        return out;
    }

    private static Set readSet(String line){
        Set s = new Set();
        String[] attrs = line.split("\"");
        s.setName(attrs[1]);

        while(!line.contains("</set>")){
            line = scan.nextLine();
            if(line.contains("<neighbors>")){
                readNeighbors(line, s);
            } else if(line.contains("<takes>")){
                s.setShots(countShots(line));
            } else if(line.contains("<parts>")){
                readParts(line, s);
            }
        }
        return s;
    }

    private static void readNeighbors(String line, Set s){
        line = scan.nextLine();
        while(!line.contains("</neighbors>")){
            String[] attrs = line.split("\"");
            s.addNeighbor(attrs[1]);
            line = scan.nextLine();
        }
    }

    private static int countShots(String line){
        int n = 0;
        line = scan.nextLine();
        while(!line.contains("</takes>")){
            n++;
            line = scan.nextLine();
        }
        return n;
    }

    private static void readParts(String line, Set s){
        line = scan.nextLine();
        while(!line.contains("</parts>")){
            s.addRole(readPart(line));
            line = scan.nextLine();
        }
    }
    
}