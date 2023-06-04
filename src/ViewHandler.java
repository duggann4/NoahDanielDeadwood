package src;

/**
 * Title: ViewHandler
 * Author: Daniel Wertz
 * CSCI 345
 * Spring 2023
 * 
 * DESCRIPTION:
 *  Handles GUI responsibilities and communicates changes to Controller.
 * 
 * CONSTRUCTORS:
 *  public ViewHandler()
 *      Initializes JFrame and all element within.
 *      Links ViewHandler to Controller;
 *      Author: Daniel Wertz
 * 
 * METHODS:
 * 
 *  public static ViewHandler getInstance()
 *      Gets the singleton instance of the ViewHandler
 *      Author: Daniel Wertz
 *      Returns:
 *          ViewHandler object
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ViewHandler extends JPanel {
    private static JFrame frame;
    private static ImageIcon backgroundImage;
    private static JLabel promptLabel;
    private static JPanel buttonPanel;
    private static JPanel playerInfoPanel;
    private static JPanel playerHeadingPanel;
    private static JPanel playerImagePanel;
    private static JPanel playerTextPanel;
    private static JPanel sidePanel;
    private static JPanel mainPanel;
    private static ActionListener buttonListener;
    private static Controller controller;

    public ViewHandler(Controller controller) {
        this.controller = controller;
        backgroundImage = new ImageIcon(getClass().getResource("../img/board.jpg"));

        frame = new JFrame("Deadwood");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(1200, 900));
        mainPanel.add(this, BorderLayout.CENTER);

        sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(400, 900));
        sidePanel.setLayout(new BorderLayout());
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        promptLabel = new JLabel();
        promptLabel.setFont(promptLabel.getFont().deriveFont(promptLabel.getFont().getSize() + 11f));
        promptLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        promptLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sidePanel.add(promptLabel, BorderLayout.NORTH);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        sidePanel.add(buttonPanel, BorderLayout.CENTER);

        playerInfoPanel = new JPanel();
        playerInfoPanel.setLayout(new BorderLayout());
        playerInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        playerHeadingPanel = new JPanel();

        playerImagePanel = new JPanel();

        playerTextPanel = new JPanel();

        playerInfoPanel.add(playerHeadingPanel, BorderLayout.NORTH);
        playerInfoPanel.add(playerImagePanel, BorderLayout.CENTER);
        playerInfoPanel.add(playerTextPanel, BorderLayout.SOUTH);

        sidePanel.add(playerInfoPanel, BorderLayout.SOUTH);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(sidePanel, BorderLayout.LINE_END);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Background
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);

        // Shots
        for (Area area : controller.getAreas()) {
            if (area instanceof Set) {
                Set set = (Set) area;
                for (Shot shot : set.getShots()) {
                    if (!shot.checkComplete()) {
                        g.drawImage(shot.getImageIcon().getImage(), shot.getX(), shot.getY(), shot.getWidth(), shot.getHeight(), this);
                    }
                }
            }
        }

        // Scenes
        for (Scene scene : controller.getActiveScenes()) {
            g.drawImage(scene.getImageIcon().getImage(), scene.getX(), scene.getY(), scene.getWidth(), scene.getHeight(), this);
        }

        // Players
        if (controller.getPlayers() != null) {
            for (Player player : controller.getPlayers()) {
                for (Player otherPlayer : controller.getPlayers()) { // offset position if players are in same spot
                    if (player != otherPlayer && player.getX() == otherPlayer.getX() && player.getY() == otherPlayer.getY()) {
                        player.setX(player.getX() + 20);
                    }
                }
                g.drawImage(player.getImageIcon().getImage(), player.getX(), player.getY(), player.getWidth(), player.getHeight(), this);
            }
        }
    }

    public void addButtons(ArrayList<String> options) {
        for (String option : options) {
            addButton(option);
        }
    }

    private void addButton(String option) {
        JButton button = new JButton(option);
        button.addActionListener(e -> {
            JButton source = (JButton) e.getSource();
            String buttonText = source.getText();
            handleButtonClick(buttonText);
        });

        Font newFont = button.getFont().deriveFont(Font.PLAIN, 12f);
        button.setFont(newFont);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        Insets buttonPadding = new Insets(10, 20, 10, 20);
        button.setMargin(buttonPadding);

        buttonPanel.add(button);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.revalidate();
    }

    public void clearButtons() {
        buttonPanel.removeAll();
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    public void setActivePlayer(Player player) {
        JLabel playerHeading = new JLabel("Current Player - " + player.getName());
        playerHeading.setFont(playerHeading.getFont().deriveFont(Font.BOLD, 20f));
        playerHeadingPanel.removeAll();
        playerHeadingPanel.add(playerHeading);

        JLabel playerImage = new JLabel(new ImageIcon(player.getImageIcon().getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH)));
        playerImagePanel.removeAll();
        playerImagePanel.add(playerImage);

        JLabel playerText = new JLabel();
        playerText.setFont(playerHeading.getFont().deriveFont(Font.PLAIN, 15f));
        playerText.setText("<html>" + player.toString().replace("\n", "<br>") + "</html>");
        playerTextPanel.removeAll();
        playerTextPanel.add(playerText);

        playerInfoPanel.removeAll();
        playerInfoPanel.add(playerHeadingPanel, BorderLayout.NORTH);
        playerInfoPanel.add(playerImagePanel, BorderLayout.CENTER);
        playerInfoPanel.add(playerTextPanel, BorderLayout.SOUTH);

        mainPanel.repaint();
        sidePanel.revalidate();
        sidePanel.repaint();
    }

    public void setPrompt(String prompt) {
        promptLabel.setText("<html>" + prompt.replace("\n", "<br>") + "</html>");
    }

    public void handleButtonClick(String buttonText) {
        controller.setSelectedOption(buttonText);
    }
}