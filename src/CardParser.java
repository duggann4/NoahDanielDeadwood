package src;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;


// ERROR EXIT CODES:
//   51: Could not open cards.xml
public class CardParser {
    private static File f;
    private static Scanner scan;

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

    private static void openFile(String path){
        try{
            f = new File(path);
            scan = new Scanner(f);
        } catch(IOException e){
            System.err.println("Problem opening file!");
            System.exit(51);
        }
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
                s.addRole(readPart(line));
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