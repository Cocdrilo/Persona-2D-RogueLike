package main;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The Toolbox class provides utility methods for image manipulation.
 */
public class Toolbox {

    public static int getRandomNumber(int maxWorldCol) {
        //gets a random Numer between 1 and 49
        return (int) (Math.random() * maxWorldCol-1) + 1;
    }

    /**
     * Scales the given BufferedImage to the specified width and height.
     *
     * @param original The original BufferedImage to be scaled.
     * @param width    The desired width of the scaled image.
     * @param height   The desired height of the scaled image.
     * @return A new BufferedImage that is a scaled version of the original image.
     */
    public BufferedImage scaleImage(BufferedImage original, int width, int height) {

        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;

    }
}
