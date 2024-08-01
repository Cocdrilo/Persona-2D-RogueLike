package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

/**
 * The Sound class manages audio clips for the game.
 */
public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];
    FloatControl fc;
    int volumeScale = 3;
    float volume;


    /**
     * Constructs a Sound object and initializes the array of sound URLs.
     */
    public Sound() {

        soundURL[0] = getClass().getResource("/Sound/DarkLoop.wav");
        soundURL[1] = getClass().getResource("/Sound/coin.wav");
        soundURL[2] = getClass().getResource("/Sound/unlock.wav");
        soundURL[3] = getClass().getResource("/Sound/fanfare.wav");
        soundURL[4] = getClass().getResource("/Sound/powerup.wav");
        soundURL[5] = getClass().getResource("/Sound/cursor.wav");
        soundURL[6] = getClass().getResource("/Sound/DarkMusicChoir.wav");
    }

    /**
     * Sets the sound file to be played based on the specified index.
     *
     * @param soundArrayIndex The index of the sound file.
     */
    public void setFile(int soundArrayIndex) {

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[soundArrayIndex]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        } catch (Exception e) {
            System.err.println("Error in audio Files ");
            e.printStackTrace(System.err);
        }

    }

    /**
     * Plays the current audio clip.
     */
    public void play() {

        clip.start();

    }

    /**
     * Loops the current audio clip continuously.
     */
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stops the current audio clip.
     */
    public void stop() {
        clip.stop();
    }

    /**
     * Adjusts the volume of the audio clip based on the volume scale.
     */
    public void checkVolume() {
        switch (volumeScale) {
            case 0 -> volume = -80f;
            case 1 -> volume = -20f;
            case 2 -> volume = -12f;
            case 3 -> volume = -5f;
            case 4 -> volume = 1f;
            case 5 -> volume = 6f;
        }
        fc.setValue(volume);

    }

}
