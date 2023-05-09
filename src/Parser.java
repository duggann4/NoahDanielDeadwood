package src;

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
