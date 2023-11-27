package main;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The Toolbox class provides utility methods for image manipulation.
 */
public class Toolbox {

    /**
     * Generates a random integer between 1 (inclusive) and the specified maximum value (exclusive).
     *
     * @param maxWorldCol The upper bound for the random number (exclusive).
     * @return A randomly generated integer between 1 (inclusive) and maxWorldCol (exclusive).
     */
    public static int getRandomNumber(int maxWorldCol) {
        //gets a random Numer between 1 and 49
        return (int) (Math.random() * maxWorldCol-1) + 1;
    }

    /**
     * Genera un número aleatorio dentro de un rango específico.
     *
     * @param min   El valor mínimo del rango (inclusive).
     * @param max   El valor máximo del rango (exclusive).
     * @param limit El límite superior para asegurar que el número esté dentro de ciertos límites.
     * @return Un número aleatorio dentro del rango [min, max) y limitado por el límite.
     */
    public static int getRandomNumberInRange(int min, int max, int limit) {
        int randomNum = min + (int) (Math.random() * (max - min));
        // Limitar el número al rango [0, limit)
        return Math.min(randomNum, limit - 1);
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
