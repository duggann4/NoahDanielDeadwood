package src;

/**
 * Title: ViewHandler
 * Author: Daniel Wertz
 * CSCI 345
 * Spring 2023
 * 
 * DESCRIPTION:
 *  Singleton that provides methods for input and output handling.
 * 
 * METHODS:
 *  public int readOption(int maxOption)
 *      Gets user input between 0 and maxOption
 *      Author: Daniel Wertz
 *      Returns:
 *          User's option
 * 
 *  public String readString()
 *      Gets a line of user input as a String
 *      Author: Daniel Wertz
 *      Returns:
 *          User's input as a String
 * 
 *  public void print(String string)
 *      Prints the given string to the console
 *      Author: Daniel Wertz
 *      Parameters:
 *          string - the string to print
 * 
 *  public static ViewHandler getInstance()
 *      Gets the singleton instance of the ViewHandler
 *      Author: Daniel Wertz
 *      Returns:
 *          ViewHandler object
 * 
 * INHERITED METHODS:
 *  Standard java.lang.Object inheritance
 */

import java.util.Scanner;

public class ViewHandler {

    private static Scanner scanner = new Scanner(System.in);
    private static ViewHandler instance = new ViewHandler();
    private ViewHandler() {}

    public int readOption(int maxOption) {
        int input;
        do {
            System.out.print("> ");
            while (!scanner.hasNextInt()) {
                print("Invalid input. Enter an integer (0 - " + maxOption + ")");
                scanner.next();
            }
            input = scanner.nextInt();
            if (input < 0 || input > maxOption) {
                print("Invalid input. Enter an integer (0 - " + maxOption + ")");
            }
        } while (input < 0 || input > maxOption);
        return input;
    }

    public String readString() {
        System.out.print("> ");
        return scanner.nextLine();
    }

    public void print(String string) {
        System.out.println(string);
    }

    public static ViewHandler getInstance() {
        return instance;
    }
}