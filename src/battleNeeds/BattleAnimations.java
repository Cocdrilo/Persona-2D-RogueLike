package battleNeeds;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Timer;

public class BattleAnimations {
    private BufferedImage meleImage;  // La imagen del personaje o arma
    private BufferedImage rangedImage;  // La imagen del personaje o arma
    private int x, y;  // Coordenadas actuales de la imagen
    private int targetX, targetY;  // Coordenadas a las que se desplazará la imagen
    private int animationSpeed;  // Velocidad de la animación
    private Timer animationTimer;  // Temporizador para controlar la animación

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

    public void attackAnimation(int targetX, int targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
        animationTimer.start();
    }

    public void magicAnimation(int targetX, int targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
        animationTimer.start();
    }

    public void escapeAnimation(int targetX, int targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
        animationTimer.start();
    }

    public void draw(Graphics g) {
        g.drawImage(meleImage, x, y, null);
    }

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
    private static BufferedImage loadImage(String filename) {
        try {
            return ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace(System.err);
            return null; // En caso de error, devuelve null
        }
    }
}