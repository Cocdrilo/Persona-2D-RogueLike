package main;

import Object.Consumables.OBJ_Potion_Health;
import Object.Consumables.OBJ_Potion_Mana;
import Object.WorldBuilding.OBJ_Chest;
import Object.WorldBuilding.OBJ_Stairs;
import entity.NPC;
import monster.monsterData;
import monster.shadowStandar;

import java.util.ArrayList;
import java.util.Random;

/**
 * The AssetSetter class is responsible for setting up various game assets such as objects, NPCs, and monsters.
 */
public class AssetSetter {

    GamePanel gp;

    /**
     * Constructs a new AssetSetter object.
     *
     * @param gp The GamePanel object associated with the AssetSetter.
     */
    public AssetSetter(GamePanel gp) {

        this.gp = gp;

    }

    /**
     * Sets up game objects, including random chests and potions, and initializes the boss.
     */
    public void setObject() {
        generateRandomObjects();
        setBoss();
    }

    /**
     * Generates random objects such as chests and potions on the game map.
     */
    private void generateRandomObjects() {
        Random random = new Random();

        // Generar cofres
        int numChests = random.nextInt(3) + 1; // Entre 1 y 3 cofres
        for (int i = 0; i < numChests; i++) {
            int chestTileCol = random.nextInt(49);
            int chestTileRow = random.nextInt(49);

            // Verificar si el tile en la ubicación del cofre tiene índice 0
            while (gp.tileM.mapTileNum[chestTileCol][chestTileRow] != 0) {
                chestTileCol = random.nextInt(49);
                chestTileRow = random.nextInt(49);
            }

            // Generar un cofre y asignar su posición
            OBJ_Chest chest = new OBJ_Chest(gp);
            chest.WorldX = gp.tileSize * chestTileCol;
            chest.WorldY = gp.tileSize * chestTileRow;

            gp.obj[i] = chest;
        }

        // Generar pociones
        for (int i = numChests; i < numChests + 2; i++) {
            int potionTileCol = random.nextInt(49);
            int potionTileRow = random.nextInt(49);

            // Verificar si el tile en la ubicación de la poción tiene índice 0
            while (gp.tileM.mapTileNum[potionTileCol][potionTileRow] != 0) {
                potionTileCol = random.nextInt(49);
                potionTileRow = random.nextInt(49);
            }

            // Generar una poción (puede ser health o mana) y asignar su posición
            if (random.nextBoolean()) {
                gp.obj[i] = new OBJ_Potion_Health(gp);
            } else {
                gp.obj[i] = new OBJ_Potion_Mana(gp);
            }
            gp.obj[i].WorldX = gp.tileSize * potionTileCol;
            gp.obj[i].WorldY = gp.tileSize * potionTileRow;
        }
    }


    /**
     * Sets up NPCs in the game world, ensuring they are positioned within a certain range of the player.
     */
    public void setNPC() {
        gp.npc[0] = new NPC(gp);

        try {
            // Obtener la posición aleatoria cercana al jugador
            int[] playerPosition = gp.tileM.setPlayerRandomPosition();
            int playerCol = playerPosition[0];
            int playerRow = playerPosition[1];

            // Definir un rango de distancia en el que aparecerá el NPC respecto al jugador
            int distanceRange = 5;

            // Generar posiciones aleatorias dentro del rango especificado
            int npcTileCol;
            int npcTileRow;
            do {
                npcTileCol = Toolbox.getRandomNumberInRange(playerCol - distanceRange, playerCol + distanceRange, gp.maxWorldCol);
                npcTileRow = Toolbox.getRandomNumberInRange(playerRow - distanceRange, playerRow + distanceRange, gp.maxWorldRow);
                // Asegurarse de que esté en un suelo 1,3,4 o 5
            } while (gp.tileM.mapTileNum[npcTileCol][npcTileRow] != 0);

            // Establecer la posición del NPC en las coordenadas encontradas
            gp.npc[0].WorldX = gp.tileSize * npcTileCol;
            gp.npc[0].WorldY = gp.tileSize * npcTileRow;
        } catch (Exception e) {
            System.out.println("NPC out of bounds");
            setNPC();
        }


    }

    /**
     * Summons stairs at a specified position on the game map.
     *
     * @param x The x-coordinate of the stairs' position.
     * @param y The y-coordinate of the stairs' position.
     */
    public void summonStairs(int x, int y) {
        gp.obj[9] = new OBJ_Stairs(gp);
        gp.obj[9].WorldX = x;
        gp.obj[9].WorldY = y;
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
     * Generates a random monster from the available pool of monsters.
     *
     * @param availableMonsters The list of available monsters.
     * @return A randomly generated monster.
     */
    private shadowStandar generateRandomMonster(ArrayList<monsterData> availableMonsters) {
        Random random = new Random();
        int randomIndex = random.nextInt(availableMonsters.size());
        monsterData randomMonsterData = availableMonsters.get(randomIndex);

        int monsterTileCol = random.nextInt(49);
        int monsterTileRow = random.nextInt(49);

        // Check if the tile at the monster's location has index 0 , 3,4 or 5
        while (gp.tileM.mapTileNum[monsterTileCol][monsterTileRow] != 0 && gp.tileM.mapTileNum[monsterTileCol][monsterTileRow] != 3 && gp.tileM.mapTileNum[monsterTileCol][monsterTileRow] != 4 && gp.tileM.mapTileNum[monsterTileCol][monsterTileRow] != 5) {
            monsterTileCol = random.nextInt(49);
            monsterTileRow = random.nextInt(49);
            System.out.println("Monster repet of bounds");
        }

        shadowStandar monster = new shadowStandar(gp, randomMonsterData);
        monster.WorldX = gp.tileSize * monsterTileCol;
        monster.WorldY = gp.tileSize * monsterTileRow;

        return monster;
    }

    /**
     * Generates a random boss monster from the available pool of bosses.
     *
     * @param availableMonsters The list of available boss monsters.
     * @return A randomly generated boss monster.
     */
    private shadowStandar generateRandomBoss(ArrayList<monsterData> availableMonsters) {
        Random random = new Random();
        int randomIndex = random.nextInt(availableMonsters.size());
        monsterData randomMonsterData = availableMonsters.get(randomIndex);

        shadowStandar monster = new shadowStandar(gp, randomMonsterData);
        monster.WorldX = gp.tileM.specialRoomX * gp.tileSize + 1 * gp.tileSize;
        monster.WorldY = gp.tileM.specialRoomY * gp.tileSize + 1 * gp.tileSize;
        monster.speed = 0;
        monster.swaptoBossImage();

        return monster;
    }

    /**
     * Sets up a boss monster in a special room on the game map.
     */
    public void setBoss() {
        ArrayList<monsterData> availableBosses = gp.monsterManager.getBosses();

        gp.monsters[9] = generateRandomBoss(availableBosses);
        gp.monsters[9].speed = 0;
        System.out.println("Boss set at " + gp.monsters[9].WorldX + " " + gp.monsters[9].WorldY);
    }


    /**
     * Respawns monsters in the game world.
     */
    public void respawnMonster() {
        ArrayList<monsterData> availableMonsters = gp.monsterManager.getMonsters();
        for (int i = 0; i < 6; i++) {
            if (gp.monsters[i] == null) {
                gp.monsters[i] = generateRandomMonster(availableMonsters);
            }
        }
        System.out.println("Monsters respawned");

    }

}
