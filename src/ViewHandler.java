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
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.util.ArrayList;

public class ViewHandler extends JPanel {

    private static JFrame frame;
    private static ImageIcon backgroundImage;
    private static Scanner scanner = new Scanner(System.in);
    private static Board board = Board.getInstance();
    private static ArrayList<Player> playerList = new ArrayList<>();

    private static ViewHandler instance;

    private ViewHandler() {
        backgroundImage = new ImageIcon(getClass().getResource("../img/board.jpg"));

        frame = new JFrame("Deadwood");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(1200, 900));
        mainPanel.add(this, BorderLayout.CENTER);

        // Create the side panel for text and buttons
        JPanel sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(500, 900));

        // Add the main panel and side panel to the frame
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(sidePanel, BorderLayout.LINE_END);

        frame.pack();
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw the background image
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);

        for (Player player : playerList) {
            g.drawImage(player.getImageIcon().getImage(), player.getX(), player.getY(), player.getWidth(), player.getHeight(), this);
        }
    }

    public void addPlayers(ArrayList<Player> players) {
        playerList = players;
        repaint();
    }

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
        if (instance == null) {
            instance = new ViewHandler();
        }
        return instance;
    }
}