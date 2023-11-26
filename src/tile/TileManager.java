package tile;

import entity.Drawable;
import main.GamePanel;
import main.Toolbox;
import proceduralNeeds.RandomDungeonGenerator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Manages tiles and the game world map.
 */
public class TileManager implements Drawable {

    GamePanel gp;
    public Tile[] tile;

    public int[][] mapTileNum;
    public int[][] oldmapTileNum;
    public boolean drawPath = true;
    public boolean loadedGame = false;
    public int specialRoomX;
    public int specialRoomY;

    /**
     * Constructs a TileManager with the specified GamePanel.
     *
     * @param gp The GamePanel associated with this TileManager.
     */
    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        oldmapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();

    }

    public void initializeMap(){
        System.out.println("Initializing map... " + loadedGame);
        if (loadedGame){
            mapTileNum = oldmapTileNum;
            System.out.println("Old Map loaded");
            //print loadedMap
            for (int i = 0; i < gp.maxWorldCol; i++) {
                for (int j = 0; j < gp.maxWorldRow; j++) {
                    System.out.print(mapTileNum[i][j] + " ");
                }
                System.out.println();
            }
        }else{
            System.out.println("New Map created");
            generateRandomDungeon();
        }
    }

    /**
     * Generates a random dungeon map using the RandomDungeonGenerator class.
     */
    private void generateRandomDungeon() {
        RandomDungeonGenerator rdg = new RandomDungeonGenerator(gp);

        // Set the generated dungeon as the current map

        mapTileNum = rdg.dungeon;
        setSpecialRoom(rdg.specialRoomX, rdg.specialRoomY);

        //Print Map:
        for (int i = 0; i < gp.maxWorldCol; i++) {
            for (int j = 0; j < gp.maxWorldRow; j++) {
                System.out.print(mapTileNum[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void setSpecialRoom(int x, int y){
        specialRoomX = x -2;
        specialRoomY = y -2;
        System.out.println("Special room set at: " + specialRoomX + " " + specialRoomY);
    }

    /**
     * Loads tile images and sets up the tile array.
     */
    public void getTileImage() {

        setUp(0, "Floor_1", false);
        setUp(1, "Wall_1", true);
        setUp(2, "pitchBlack", true);
        setUp(3,"Floor_2",false);
        setUp(4,"Floor_3",false);
        setUp(5,"Floor_4",false);
        setUp(6,"Wall_2",true);
        setUp(7,"Wall_3",true);
        setUp(8,"Wall_4",true);
        setUp(9,"Wall_5",true);

    }

    /**
     * Sets up a tile with the specified index, image path, and collision status.
     *
     * @param index     The index of the tile.
     * @param imagePath The image path of the tile.
     * @param collision The collision status of the tile.
     */
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

    public int[] setPlayerRandomPosition() {
        int col;
        int row;

        do {
            // Generate random coordinates within the map boundaries
            col = Toolbox.getRandomNumber(gp.maxWorldCol);
            row = Toolbox.getRandomNumber(gp.maxWorldRow);

            // Check if the generated coordinates are not in the boss room
        } while (isInBossRoom(col, row) || mapTileNum[col][row] != 0); // Repeat until a ground tile is found outside the boss room

        // Set the player's position to the found coordinates
        return new int[]{col, row};
    }

    private boolean isInBossRoom(int col, int row) {
        // Assuming specialRoomX and specialRoomY represent the top-left corner of the boss room
        int bossRoomWidth = 5;
        int bossRoomHeight = 5;

        return col >= specialRoomX && col < specialRoomX + bossRoomWidth &&
                row >= specialRoomY && row < specialRoomY + bossRoomHeight;
    }

    /**
     * Loads the game world map from a text file.
     *
     * @param map The path to the map file.
     */
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
            e.printStackTrace(System.err);
        }
    }

    /**
     * Draws the visible tiles of the game world.
     *
     * @param g2 The graphics context used for drawing.
     */
    @Override
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
        if (drawPath){
            g2.setColor(new Color (255,0,0,70));

            for(int i = 0; i <gp.pathFinder.pathList.size();i++){

                int worldX = gp.pathFinder.pathList.get(i).col * gp.tileSize;
                int worldY = gp.pathFinder.pathList.get(i).row * gp.tileSize;
                int ScreenX = worldX - gp.player.WorldX + gp.player.screenX;
                int ScreenY = worldY - gp.player.WorldY + gp.player.screenY;

                g2.fillRect(ScreenX,ScreenY,gp.tileSize,gp.tileSize);
            }

        }
    }


}
