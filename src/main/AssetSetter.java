package main;

import Object.Consumables.OBJ_Potion_Health;
import Object.Consumables.OBJ_Potion_Mana;
import Object.Equipables.OBJ_WEAPON_BASH;
import Object.WorldBuilding.OBJ_Chest;
import Object.WorldBuilding.OBJ_Door;
import Object.WorldBuilding.OBJ_Stairs;
import entity.NPC;
import monster.monsterData;
import monster.shadowStandar;

import java.util.ArrayList;
import java.util.Random;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {

        this.gp = gp;

    }

    public void setObject() {
        generateRandomObjects();
        setBoss();
    }

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


    public void setNPC() {
        gp.npc[0] = new NPC(gp);

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
        } while (gp.tileM.mapTileNum[npcTileCol][npcTileRow] != 0); // Asegurarse de que esté en un suelo

        // Establecer la posición del NPC en las coordenadas encontradas
        gp.npc[0].WorldX = gp.tileSize * npcTileCol;
        gp.npc[0].WorldY = gp.tileSize * npcTileRow;
    }

    public void setMonsters() {
        ArrayList<monsterData> availableMonsters = gp.monsterManager.getMonsters();

        for (int i = 0; i < 3; i++) {
            gp.monsters[i] = generateRandomMonster(availableMonsters);
        }
    }

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

    private shadowStandar generateRandomBoss(ArrayList<monsterData> availableMonsters) {
        Random random = new Random();
        int randomIndex = random.nextInt(availableMonsters.size());
        monsterData randomMonsterData = availableMonsters.get(randomIndex);

        shadowStandar monster = new shadowStandar(gp, randomMonsterData);
        monster.WorldX = gp.tileM.specialRoomX*gp.tileSize +2*gp.tileSize;
        monster.WorldY = gp.tileM.specialRoomY*gp.tileSize +1*gp.tileSize;
        monster.speed = 0;
        monster.swaptoBossImage();

        return monster;
    }

    public void setBoss(){
        ArrayList<monsterData> availableBosses = gp.monsterManager.getBosses();

        gp.monsters[9] = generateRandomBoss(availableBosses);
        gp.monsters[9].speed = 0;
        System.out.println("Boss set at " + gp.monsters[9].WorldX + " " + gp.monsters[9].WorldY);
    }


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
