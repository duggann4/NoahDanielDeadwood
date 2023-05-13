package src;

import java.util.Scanner;

public class ViewHandler {

    private static Scanner scanner = new Scanner(System.in);
    private static ViewHandler instance = new ViewHandler();
    private ViewHandler(){}

    public void print(String string) {
        System.out.println(string);
    }

    public int getOption(int maxOption) {
        int input = -1;
        while (input < 0 || input > maxOption) {
            System.out.print("> ");
            if (!scanner.hasNextInt()) {
                print("Invalid input. Enter an integer (0 - " + maxOption + ")");
                scanner.next();
            } else {
                input = scanner.nextInt();
                if (input < 0 || input > maxOption) {
                    print("Invalid input. Enter an integer (0 - " + maxOption + ")");
                }
            }
        }
        return input;
    }

    public String getString() {
        System.out.print("> ");
        return scanner.nextLine();
    }

    public static ViewHandler getInstance(){
        return instance;
    }
}