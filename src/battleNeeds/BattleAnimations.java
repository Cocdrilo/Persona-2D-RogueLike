package battleNeeds;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
 * The BattleAnimations class handles the animations for melee and magic attacks in a game.
 */
public class BattleAnimations {
    private Image meleeSlash1, meleeSlash2, meleeSlash3, meleeSlash4;
    private Image magicBlast1, magicBlast2, magicBlast3, magicBlast4;
    private final int animationDuration = 1400;
    private long startTime;

    private Timer meleeAnimationTimer;
    private Timer magicAnimationTimer;
    private int meleeFrameCount = 0;
    private int magicFrameCount = 0;

    /**
     * Constructs a new BattleAnimations object, loading the required animation images.
     */
    public BattleAnimations() {
        // Load melee animation images
        meleeSlash1 = loadImage("res/BattleImages/MeleAttackAnimation/Slash_1.png");
        meleeSlash2 = loadImage("res/BattleImages/MeleAttackAnimation/Slash_2.png");
        meleeSlash3 = loadImage("res/BattleImages/MeleAttackAnimation/Slash_3.png");
        meleeSlash4 = loadImage("res/BattleImages/MeleAttackAnimation/Slash_4.png");

        // Load magic animation images
        magicBlast1 = loadImage("res/BattleImages/MagicAttackAnimation/Fireball1.png");
        magicBlast2 = loadImage("res/BattleImages/MagicAttackAnimation/Fireball2.png");
        magicBlast3 = loadImage("res/BattleImages/MagicAttackAnimation/Fireball3.png");
        magicBlast4 = loadImage("res/BattleImages/MagicAttackAnimation/Fireball3.png");

        // Set up melee animation timer
        meleeAnimationTimer = createAnimationTimer();

        // Set up magic animation timer
        magicAnimationTimer = createAnimationTimer();
    }

    /**
     * Creates a Timer object for animation purposes.
     *
     * @return The created Timer object.
     */

    private Timer createAnimationTimer() {
        return new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                meleeFrameCount++;
                magicFrameCount++;  // Increment magic frame count as well
                if (meleeFrameCount > 3) {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
    }

    /**
     * Initiates and plays the melee attack animation.
     *
     * @param g2d     The Graphics2D object to draw on.
     * @param targetX The x-coordinate of the target location.
     * @param targetY The y-coordinate of the target location.
     */
    public void playMeleeAttackAnimation(Graphics2D g2d, int targetX, int targetY) {
        startTime = System.currentTimeMillis();
        meleeFrameCount = 0;

        meleeAnimationTimer.start();

        Timer drawTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawMeleeFrame(g2d, targetX, targetY);

                if (System.currentTimeMillis() - startTime >= animationDuration) {
                    ((Timer) e.getSource()).stop();
                }
            }
        });

        drawTimer.start();
    }

    /**
     * Initiates and plays the magic attack animation.
     *
     * @param g2d     The Graphics2D object to draw on.
     * @param targetX The x-coordinate of the target location.
     * @param targetY The y-coordinate of the target location.
     */
    public void playMagicAttackAnimation(Graphics2D g2d, int targetX, int targetY) {
        startTime = System.currentTimeMillis();
        magicFrameCount = 0;

        magicAnimationTimer.start();

        Timer drawTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawMagicFrame(g2d, targetX, targetY);

                if (System.currentTimeMillis() - startTime >= animationDuration) {
                    ((Timer) e.getSource()).stop();
                }
            }
        });

        drawTimer.start();
    }

    /**
     * Draws the current frame of the melee attack animation.
     *
     * @param g2d     The Graphics2D object to draw on.
     * @param targetX The x-coordinate of the target location.
     * @param targetY The y-coordinate of the target location.
     */
    private void drawMeleeFrame(Graphics2D g2d, int targetX, int targetY) {
        switch (meleeFrameCount) {
            case 0 -> g2d.drawImage(meleeSlash1, targetX, targetY, null);
            case 1 -> g2d.drawImage(meleeSlash2, targetX, targetY, null);
            case 2 -> g2d.drawImage(meleeSlash3, targetX, targetY, null);
            case 3 -> g2d.drawImage(meleeSlash4, targetX, targetY, null);
        }
    }

    /**
     * Draws the current frame of the magic attack animation.
     *
     * @param g2d     The Graphics2D object to draw on.
     * @param targetX The x-coordinate of the target location.
     * @param targetY The y-coordinate of the target location.
     */
    private void drawMagicFrame(Graphics2D g2d, int targetX, int targetY) {
        switch (magicFrameCount) {
            case 0 -> g2d.drawImage(magicBlast1, targetX, targetY, null);
            case 1 -> g2d.drawImage(magicBlast2, targetX, targetY, null);
            case 2 -> g2d.drawImage(magicBlast3, targetX, targetY, null);
            case 3 -> g2d.drawImage(magicBlast4, targetX, targetY, null);
        }
    }

    /**
     * Loads an image from a file.
     *
     * @param filename The path to the image file.
     * @return The loaded BufferedImage, or null if an error occurs.
     */
    private static BufferedImage loadImage(String filename) {
        try {
            return ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace(System.err);
            return null;
        }
    }
}