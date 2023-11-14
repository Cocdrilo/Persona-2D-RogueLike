package main;

import Object.Consumables.OBJ_Potion_Health;
import Object.Equipables.OBJ_WEAPON_BASH;
import Object.WorldBuilding.OBJ_Chest;
import Object.WorldBuilding.OBJ_Door;
import Object.WorldBuilding.OBJ_Stairs;
import entity.NPC;
import monster.monsterData;
import monster.shadowStandar;

import java.util.ArrayList;
import java.util.Random;

/**
 * The AssetSetter class is responsible for initializing and setting up various game assets
 * such as doors, stairs, chests, NPCs, and monsters in the game world.
 * It works in conjunction with the GamePanel to place these assets at specific locations.
 */
public class AssetSetter {

    GamePanel gp;
    KeyHandler keyH;

    /**
     * Initializes the AssetSetter with the specified GamePanel.
     *
     * @param gp The GamePanel instance to associate with the AssetSetter.
     */
    public AssetSetter(GamePanel gp) {

        this.gp = gp;

    }

    /**
     * Sets up objects in the game world.
     */
    public void setObject() {

        gp.obj[0] = new OBJ_Door(gp);
        gp.obj[0].WorldX = gp.tileSize * 5;
        gp.obj[0].WorldY = gp.tileSize * 5;

        gp.obj[1] = new OBJ_Stairs(gp);
        gp.obj[1].WorldX = gp.tileSize * 5;
        gp.obj[1].WorldY = gp.tileSize * 10;

        gp.obj[2] = new OBJ_Chest(gp);
        gp.obj[2].WorldX = gp.tileSize * 8;
        gp.obj[2].WorldY = gp.tileSize * 10;

        gp.obj[3] = new OBJ_WEAPON_BASH(gp);
        gp.obj[3].WorldX = gp.tileSize * 16;
        gp.obj[3].WorldY = gp.tileSize * 5;

        gp.obj[4] = new OBJ_Potion_Health(gp);
        gp.obj[4].WorldX = gp.tileSize * 16;
        gp.obj[4].WorldY = gp.tileSize * 6;


    }

    /**
     * Sets up non-player characters (NPCs) in the game world.
     */
    public void setNPC() {

        gp.npc[0] = new NPC(gp);
        gp.npc[0].WorldX = gp.tileSize * 4;
        gp.npc[0].WorldY = gp.tileSize * 4;
    }

    /**
     * Sets up monsters in the game world.
     */
    public void setMonsters() {
        ArrayList<monsterData> availableMonsters = gp.monsterManager.getMonsters();

        for (int i = 0; i < 3; i++) {
            gp.monsters[i] = generateRandomMonster(availableMonsters);
        }
    }

    /**
     * Generates a random monster from the available monster data.
     *
     * @param availableMonsters The list of available monster data.
     * @return A randomly generated monster.
     */
    private shadowStandar generateRandomMonster(ArrayList<monsterData> availableMonsters) {
        Random random = new Random();
        int randomIndex = random.nextInt(availableMonsters.size());
        monsterData randomMonsterData = availableMonsters.get(randomIndex);

        int monsterTileCol = random.nextInt(49);
        int monsterTileRow = random.nextInt(49);

        // Check if the tile at the monster's location has index 0
        while (gp.tileM.mapTileNum[monsterTileCol][monsterTileRow] != 0) {
            monsterTileCol = random.nextInt(49);
            monsterTileRow = random.nextInt(49);
        }

        shadowStandar monster = new shadowStandar(gp, randomMonsterData);
        monster.WorldX = gp.tileSize * monsterTileCol;
        monster.WorldY = gp.tileSize * monsterTileRow;

        return monster;
    }

    /**
     * Respawns monsters in the game world.
     */
    public void respawnMonster() {
        ArrayList<monsterData> availableMonsters = gp.monsterManager.getMonsters();
        for(int i = 0; i < 6; i++){
            if(gp.monsters[i] == null){
                gp.monsters[i] = generateRandomMonster(availableMonsters);
            }
        }
        System.out.println("Monsters respawned");

    }
}
