package main;

import javax.swing.*;

/**
 * Main class for the game. Initializes the game window and starts the game.
 */

public class Main {
    public static JFrame window;

    /**
     * The main entry point for the application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("PersonaRemake");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        gamePanel.config.loadConfig();
        if (gamePanel.fullScreenOn) {
            window.setUndecorated(true);
        }

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setUpGame();

        gamePanel.startGameThread();
    }
}