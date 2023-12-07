package data;

import Object.Equipables.*;
import Object.Consumables.*;
import Object.WorldBuilding.*;
import entity.Entity;
import main.GamePanel;


import java.io.*;

/**
 * The SaveLoad class is responsible for saving and loading game data, including player stats, inventory, and game objects.
 */
public class SaveLoad {
    GamePanel gp;

    /**
     * Constructs a SaveLoad instance with a reference to the GamePanel.
     *
     * @param gp The GamePanel to associate with this SaveLoad instance.
     */
    public SaveLoad(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Retrieve an Entity object based on the item name.
     *
     * @param itemName The name of the item.
     * @return An Entity object corresponding to the provided item name.
     */
    public Entity getObject(String itemName) {
        return switch (itemName) {
            case "Cota de malla" -> new OBJ_Armor(gp);
            case "chest" -> new OBJ_Chest(gp);
            case "door" -> new OBJ_Door(gp);
            case "Health Potion" -> new OBJ_Potion_Health(gp);
            case "Mana Potion" -> new OBJ_Potion_Mana(gp);
            case "stairs" -> new OBJ_Stairs(gp);
            case "Bashing Weapon" -> new OBJ_WEAPON_Bash(gp);
            case "Piercing Weapon" -> new OBJ_WEAPON_Piercing(gp);
            case "Espadon" -> new OBJ_WEAPON_Slash(gp);
            default -> null;
        };
    }

    /**
     * Save game data to a file.
     */
    public void save() {
        try {
            saveAllGameData();
        } catch (Exception fileExceptions) {
            fileExceptions.printStackTrace(System.err);
        }
    }

    private void saveAllGameData() throws IOException {
        ObjectOutputStream objectOutputStreamWritter = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
        DataStorage dataStorageFile = new DataStorage();

        savePlayerStats(dataStorageFile);

        savePlayerInventory(dataStorageFile);

        saveMapObjects(dataStorageFile);

        savePartyStats(dataStorageFile);

        saveDungeonLayout(dataStorageFile);

        //Write in the file
        objectOutputStreamWritter.writeObject(dataStorageFile);
    }

    private void savePlayerStats(DataStorage dataStorageFile){
        dataStorageFile.level = gp.player.stats.level;
        dataStorageFile.exp = gp.player.stats.exp;
        dataStorageFile.nextLevelExp = gp.player.stats.nextLevelExp;
        dataStorageFile.maxLife = gp.player.stats.maxHp;
        dataStorageFile.life = gp.player.stats.hp;
        dataStorageFile.mana = gp.player.stats.mp;
        dataStorageFile.maxMana = gp.player.stats.maxMp;
        dataStorageFile.strength = gp.player.stats.str;
        dataStorageFile.magic = gp.player.stats.mag;
        dataStorageFile.agility = gp.player.stats.agi;
        dataStorageFile.vitality = gp.player.stats.vit;
        dataStorageFile.money = gp.player.stats.money;
        dataStorageFile.playerX = gp.player.WorldX;
        dataStorageFile.playerY = gp.player.WorldY;
    }

    private void savePlayerInventory(DataStorage dataStorageFile) {
        for (int inventorySlot = 0; inventorySlot < gp.player.inventory.size(); inventorySlot++) {
            dataStorageFile.itemNames.add(gp.player.inventory.get(inventorySlot).name);
        }
        dataStorageFile.currentWeaponSlot = gp.player.getWeaponSlot();
        dataStorageFile.currentArmorSlot = gp.player.getArmorSlot();
    }

    private void saveMapObjects(DataStorage dataStorageFile) {
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                dataStorageFile.mapObjectNames[i] = gp.obj[i].name;
                dataStorageFile.mapObjectWorldX[i] = gp.obj[i].WorldX;
                dataStorageFile.mapObjectWorldY[i] = gp.obj[i].WorldY;
                dataStorageFile.mapObjectVisibility[i] = gp.obj[i].isVisible;
            }
        }
    }

    private void savePartyStats(DataStorage dataStorageFile){
        for(int monsterSlotInParty = 0;monsterSlotInParty<gp.party.partyMembers.size();monsterSlotInParty++){
            dataStorageFile.monsterName[monsterSlotInParty] = gp.party.partyMembers.get(monsterSlotInParty).name;
            dataStorageFile.monsterLevel[monsterSlotInParty] = gp.party.partyMembers.get(monsterSlotInParty).stats.level;
            dataStorageFile.monsterEXP[monsterSlotInParty] = gp.party.partyMembers.get(monsterSlotInParty).stats.exp;
            dataStorageFile.monsterNextLevelEXP[monsterSlotInParty] = gp.party.partyMembers.get(monsterSlotInParty).stats.nextLevelExp;
            dataStorageFile.monsterMaxLife[monsterSlotInParty] = gp.party.partyMembers.get(monsterSlotInParty).stats.maxHp;
            dataStorageFile.monsterLife[monsterSlotInParty] = gp.party.partyMembers.get(monsterSlotInParty).stats.hp;
            dataStorageFile.monsterMana[monsterSlotInParty] = gp.party.partyMembers.get(monsterSlotInParty).stats.mp;
            dataStorageFile.monsterMaxMana[monsterSlotInParty] = gp.party.partyMembers.get(monsterSlotInParty).stats.maxMp;
            dataStorageFile.monsterStrength[monsterSlotInParty] = gp.party.partyMembers.get(monsterSlotInParty).stats.str;
            dataStorageFile.monsterMagic[monsterSlotInParty] = gp.party.partyMembers.get(monsterSlotInParty).stats.mag;
            dataStorageFile.monsterAgility[monsterSlotInParty] = gp.party.partyMembers.get(monsterSlotInParty).stats.agi;
            dataStorageFile.monsterVitality[monsterSlotInParty] = gp.party.partyMembers.get(monsterSlotInParty).stats.vit;
            dataStorageFile.membersInParty++;
        }
    }
    private void saveDungeonLayout(DataStorage dataStorageFile){
        for(int i = 0;i<50;i++){
            System.arraycopy(gp.tileM.mapTileNum[i], 0, dataStorageFile.dungeonMap[i], 0, 50);
        }
    }


    /**
     * Load game data from a file.
     */
    public void load() {
        try {
            loadGameData();

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
    private void loadGameData() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File("save.dat")));
        DataStorage dataStorageFile = (DataStorage) objectInputStream.readObject();

        loadPlayerStats(dataStorageFile);

        loadPlayerInventory(dataStorageFile);

        loadMapObjects(dataStorageFile);

        loadPartyMembersStats(dataStorageFile);

        loadDungeonLayout(dataStorageFile);
    }
    private void loadPlayerStats(DataStorage dataStorageFile){
        gp.player.stats.level = dataStorageFile.level;
        gp.player.stats.exp = dataStorageFile.exp;
        gp.player.stats.nextLevelExp = dataStorageFile.nextLevelExp;
        gp.player.stats.maxHp = dataStorageFile.maxLife;
        gp.player.stats.hp = dataStorageFile.life;
        gp.player.stats.mp = dataStorageFile.mana;
        gp.player.stats.maxMp = dataStorageFile.maxMana;
        gp.player.stats.str = dataStorageFile.strength;
        gp.player.stats.mag = dataStorageFile.magic;
        gp.player.stats.agi = dataStorageFile.agility;
        gp.player.stats.vit = dataStorageFile.vitality;
        gp.player.stats.money = dataStorageFile.money;
        gp.player.WorldX = dataStorageFile.playerX;
        gp.player.WorldY= dataStorageFile.playerY;
    }
    private void loadPlayerInventory(DataStorage dataStorageFile){
        //Hacemos un clear del inventario para evitar duplicados siempre
        gp.player.inventory.clear();
        for (String itemName : dataStorageFile.itemNames) {
            gp.player.inventory.add(getObject(itemName));
        }
        gp.player.stats.weapon = (OBJ_Weapon) gp.player.inventory.get(dataStorageFile.currentWeaponSlot);
        gp.player.stats.armor = (OBJ_Armor) gp.player.inventory.get(dataStorageFile.currentArmorSlot);
    }
    private void loadMapObjects(DataStorage dataStorageFile){
        for (int i = 0; i < gp.obj.length; i++) {
            if (dataStorageFile.mapObjectNames[i] != null) {
                gp.obj[i] = getObject(dataStorageFile.mapObjectNames[i]);
                gp.obj[i].WorldX = dataStorageFile.mapObjectWorldX[i];
                gp.obj[i].WorldY = dataStorageFile.mapObjectWorldY[i];
                gp.obj[i].isVisible = dataStorageFile.mapObjectVisibility[i];
            }
        }
    }
    private void loadPartyMembersStats(DataStorage dataStorageFile){
        //Igual que con el inventario hacemos un clear para evitar duplicados siempre
        gp.party.partyMembers.clear();

        for(int i = 0;i<dataStorageFile.membersInParty;i++){
            gp.party.addMonsterToParty(dataStorageFile.monsterName[i]);
            gp.party.partyMembers.get(i).swapStats(dataStorageFile.monsterLevel[i],dataStorageFile.monsterEXP[i],dataStorageFile.monsterNextLevelEXP[i],dataStorageFile.monsterLife[i],dataStorageFile.monsterMaxLife[i],dataStorageFile.monsterMana[i],dataStorageFile.monsterMaxMana[i],dataStorageFile.monsterStrength[i],dataStorageFile.monsterAgility[i],dataStorageFile.monsterMagic[i],dataStorageFile.monsterVitality[i]);

        }
    }
    private void loadDungeonLayout(DataStorage dataStorageFile){
        //Marcamos la variable de la clase que monta el mapa para que utilice este y no cree uno nuevo
        gp.tileM.loadedGame = true;

        for(int i = 0;i<50;i++){
            System.arraycopy(dataStorageFile.dungeonMap[i], 0, gp.tileM.oldmapTileNum[i], 0, 50);
        }
    }
}
