package main;

import java.io.*;

/**
 * Handles the configuration settings for the game.
 */
public class Config {

    GamePanel gp;

    /**
     * Initializes a new Config with the given GamePanel reference.
     *
     * @param gp The GamePanel associated with the configuration.
     */
    public Config(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Saves the current configuration settings to a file.
     */
    public void saveConfig() {
        try {
            File dir = new File("configs");
            if (!dir.exists()) dir.mkdirs();
            BufferedWriter bw = new BufferedWriter(new FileWriter("res/configs/config.txt"));
            // Full Screen
            bw.write(gp.fullScreenOn ? "On" : "Off");
            bw.newLine();
            // Music Volume
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();
            // SE Volume
            bw.write(String.valueOf(gp.se.volumeScale));
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    public void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("res/configs/config.txt"));
            String s = br.readLine();
            gp.fullScreenOn = s.equals("On");
            s = br.readLine();
            gp.music.volumeScale = Integer.parseInt(s);
            s = br.readLine();
            gp.se.volumeScale = Integer.parseInt(s);
            br.close();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}
