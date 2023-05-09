package src;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class BoardParser {
    private static File f;
    private static Scanner scan;

    public BoardParser(String filepath){
        openFile(filepath);
    }

    public static void main(String[] args){
        BoardParser bp = new BoardParser("../data/board.xml");
        bp.readBoard();
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

    private static void openFile(String path){
        try{
            f = new File(path);
            scan = new Scanner(f);
        } catch(IOException e){
            System.err.println("Problem opening file!");
            System.exit(51);
        }
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
        System.out.println(s);
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
    private static Role readPart(String line){
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