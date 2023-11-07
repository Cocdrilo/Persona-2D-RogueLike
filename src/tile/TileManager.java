package tile;

import main.GamePanel;
import main.Toolbox;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;

    public int mapTileNum[][];

    public TileManager(GamePanel gp) {

        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        LoadMap("/Maps/Map01.txt");
    }

    public void getTileImage() {

        setUp(0, "Floor_1", false);
        setUp(1, "WallMid", true);
        setUp(2, "column", true);
    }

    public void setUp(int index, String imagePath, boolean collision) {

        Toolbox toolbox = new Toolbox();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/" + imagePath + ".png"));
            tile[index].image = toolbox.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void LoadMap(String map) {

        try {

            InputStream is = getClass().getResourceAsStream(map);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine();

                while (col < gp.maxWorldCol) {

                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;

                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }

            }
            br.close();

        } catch (Exception e) {

        }
    }

    public void draw(Graphics2D g2) {

        int WorldCol = 0;
        int WorldRow = 0;

        while (WorldCol < gp.maxWorldCol && WorldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[WorldCol][WorldRow];

            int worldX = WorldCol * gp.tileSize;
            int worldY = WorldRow * gp.tileSize;
            int ScreenX = worldX - gp.player.WorldX + gp.player.screenX;
            int ScreenY = worldY - gp.player.WorldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.WorldX - gp.player.screenX && worldX - gp.tileSize < gp.player.WorldX + gp.player.screenX && worldY + gp.tileSize > gp.player.WorldY - gp.player.screenY && worldY - gp.tileSize < gp.player.WorldY + gp.player.screenX) {

                g2.drawImage(tile[tileNum].image, ScreenX, ScreenY, null);
            }

            WorldCol++;

            if (WorldCol == gp.maxWorldCol) {
                WorldCol = 0;
                WorldRow++;
            }
        }
    }


}
