package battleNeeds;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
 * The {@code BattleAnimations} class manages battle animations, such as attack, magic, and escape animations.
 * It handles the loading of images, animation coordinates, and animation speed.
 *
 * <p>
 * The class uses a timer to control the animation speed and coordinates to move the images smoothly.
 * </p>
 */
public class BattleAnimations {
    private BufferedImage meleImage;  // La imagen del personaje o arma
    private BufferedImage rangedImage;  // La imagen del personaje o arma
    private int x, y;  // Coordenadas actuales de la imagen
    private int targetX, targetY;  // Coordenadas a las que se desplazará la imagen
    private int animationSpeed;  // Velocidad de la animación
    private Timer animationTimer;  // Temporizador para controlar la animación

    /**
     * Constructs a new {@code BattleAnimations} instance.
     * Loads the melee and ranged attack images and initializes coordinates and animation speed.
     */
    public BattleAnimations() {
        this.meleImage = loadImage("res/Objects/weapon_Maza.png");
        this.rangedImage = loadImage("res/Objects/Stairs.png");
        x = 0;
        y = 0;
        targetX = 0;
        targetY = 0;
        animationSpeed = 5;  // Ajusta la velocidad según tus necesidades
        animationTimer = new Timer(1000 / 60, e -> animate());
    }

    /**
     * Initiates the attack animation with the specified target coordinates.
     *
     * @param targetX The x-coordinate of the target for the animation.
     * @param targetY The y-coordinate of the target for the animation.
     */
    public void attackAnimation(int targetX, int targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
        animationTimer.start();
    }

    /**
     * Initiates the magic animation with the specified target coordinates.
     *
     * @param targetX The x-coordinate of the target for the animation.
     * @param targetY The y-coordinate of the target for the animation.
     */
    public void magicAnimation(int targetX, int targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
        animationTimer.start();
    }

    /**
     * Initiates the escape animation with the specified target coordinates.
     *
     * @param targetX The x-coordinate of the target for the animation.
     * @param targetY The y-coordinate of the target for the animation.
     */
    public void escapeAnimation(int targetX, int targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
        animationTimer.start();
    }

    /**
     * Draws the current melee attack animation image at its current coordinates.
     *
     * @param g The graphics object used for drawing.
     */
    public void draw(Graphics g) {
        g.drawImage(meleImage, x, y, null);
    }

    /**
     * Animates the movement of the image to the target coordinates.
     * Adjusts the image coordinates based on the animation speed until it reaches the target coordinates.
     * Stops the animation timer when the image reaches the target coordinates.
     */
    private void animate() {
        if (x < targetX) {
            x += animationSpeed;
        } else if (x > targetX) {
            x -= animationSpeed;
        }
        if (y < targetY) {
            y += animationSpeed;
        } else if (y > targetY) {
            y -= animationSpeed;
        }

        if (x == targetX && y == targetY) {
            animationTimer.stop();
        }
    }

    /**
     * Loads an image from the specified file path.
     *
     * @param filename The path to the image file.
     * @return The loaded BufferedImage, or {@code null} in case of an error.
     */
    private static BufferedImage loadImage(String filename) {
        try {
            return ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace(System.err);
            return null; // En caso de error, devuelve null
        }
    }
}