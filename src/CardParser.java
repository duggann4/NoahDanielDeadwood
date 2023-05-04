package src;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;


// ERROR EXIT CODES:
//   5: Could not open cards.xml
public class CardParser {
    private File f;
    private Scanner scan;

    public CardParser(String filepath){
        openFile(filepath);
    }

    public Scene[] readCards(){
        return null;
    }

    private void openFile(String path){
        try{
            f = new File(path);
            scan = new Scanner(f);
        } catch(IOException e){
            System.err.println("Problem opening file!");
            System.exit(5);
        }
    }
}