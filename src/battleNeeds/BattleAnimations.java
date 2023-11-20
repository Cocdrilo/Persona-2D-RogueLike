package battleNeeds;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Timer;

public class BattleAnimations {
    private Image meleeSlash1;
    private Image meleeSlash2;
    private Image meleeSlash3;
    private Image meleeSlash4;

    private Image magicBlast1;
    private Image magicBlast2;
    private Image magicBlast3;  // Same image as magicBlast4
    private Image magicBlast4;  // Same image as magicBlast3

    private int animationDuration = 1400;
    private long startTime;

    private Timer meleeAnimationTimer;
    private Timer magicAnimationTimer;
    private int meleeFrameCount = 0;
    private int magicFrameCount = 0;

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

    private void drawMeleeFrame(Graphics2D g2d, int targetX, int targetY) {
        switch (meleeFrameCount) {
            case 0 -> g2d.drawImage(meleeSlash1, targetX, targetY, null);
            case 1 -> g2d.drawImage(meleeSlash2, targetX, targetY, null);
            case 2 -> g2d.drawImage(meleeSlash3, targetX, targetY, null);
            case 3 -> g2d.drawImage(meleeSlash4, targetX, targetY, null);
        }
    }

    private void drawMagicFrame(Graphics2D g2d, int targetX, int targetY) {
        switch (magicFrameCount) {
            case 0 -> g2d.drawImage(magicBlast1, targetX, targetY, null);
            case 1 -> g2d.drawImage(magicBlast2, targetX, targetY, null);
            case 2 -> g2d.drawImage(magicBlast3, targetX, targetY, null);
            case 3 -> g2d.drawImage(magicBlast4, targetX, targetY, null);
        }
    }

    private static BufferedImage loadImage(String filename) {
        try {
            return ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace(System.err);
            return null;
        }
    }
}