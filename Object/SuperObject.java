package Object;

import main.GamePanel;
import main.Toolbox;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX,worldY;

    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX = 0;
    public int getSolidAreaDefaultY = 0;
    Toolbox tBox = new Toolbox();


    public void draw(Graphics2D g2, GamePanel gp){

        int ScreenX = worldX - gp.player.WorldX + gp.player.screenX;
        int ScreenY = worldY - gp.player.WorldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.WorldX - gp.player.screenX && worldX - gp.tileSize <gp.player.WorldX + gp.player.screenX && worldY + gp.tileSize > gp.player.WorldY - gp.player.screenY && worldY - gp.tileSize < gp.player.WorldY + gp.player.screenX){

            g2.drawImage(image,ScreenX,ScreenY,gp.tileSize,gp.tileSize,null);
        }

    }

}
