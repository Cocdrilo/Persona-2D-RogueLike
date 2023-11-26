package data;

import Object.Equipables.*;
import Object.Consumables.*;
import Object.WorldBuilding.*;
import entity.Entity;
import main.GamePanel;
import proceduralNeeds.RandomDungeonGenerator;


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
            case "Bashing Weapon" -> new OBJ_WEAPON_BASH(gp);
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
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
            DataStorage ds = new DataStorage();

            //Player Stats
            ds.level = gp.player.stats.level;
            ds.exp = gp.player.stats.exp;
            ds.nextLevelExp = gp.player.stats.nextLevelExp;
            ds.maxLife = gp.player.stats.maxHp;
            ds.life = gp.player.stats.hp;
            ds.mana = gp.player.stats.mp;
            ds.maxMana = gp.player.stats.maxMp;
            ds.strength = gp.player.stats.str;
            ds.magic = gp.player.stats.mag;
            ds.agility = gp.player.stats.agi;
            ds.vitality = gp.player.stats.vit;
            ds.money = gp.player.stats.money;
            ds.playerX = gp.player.WorldX;
            ds.playerY = gp.player.WorldY;
            System.out.println("Player X: " + gp.player.WorldX);
            System.out.println("Player Y: " + gp.player.WorldY);

            //Player Inventory
            for (int i = 0; i < gp.player.inventory.size(); i++) {
                ds.itemNames.add(gp.player.inventory.get(i).name);
                //ds.itemAmounts.add(gp.player.inventory.get(i).amount);

            }

            //Player Equipment
            ds.currentWeaponSlot = gp.player.getWeaponSlot();
            ds.currentArmorSlot = gp.player.getArmorSlot();

            //Save Objects:
            for (int i = 0; i < gp.obj.length; i++) {
                if (gp.obj[i] != null) {
                    ds.mapObjectNames[i] = gp.obj[i].name;
                    ds.mapObjectWorldX[i] = gp.obj[i].WorldX;
                    ds.mapObjectWorldY[i] = gp.obj[i].WorldY;
                    ds.mapObjectVisibility[i] = gp.obj[i].isVisible;
                    //System.out.println("Saved: " + gp.obj[i].name + "," + gp.obj[i].WorldX + "," + gp.obj[i].WorldY);
                }
            }


            //Party
            for(int i = 0;i<gp.party.partyMembers.size();i++){
                ds.monsterName[i] = gp.party.partyMembers.get(i).name;
                ds.monsterLevel[i] = gp.party.partyMembers.get(i).stats.level;
                ds.monsterEXP[i] = gp.party.partyMembers.get(i).stats.exp;
                ds.monsterNextLevelEXP[i] = gp.party.partyMembers.get(i).stats.nextLevelExp;
                ds.monsterMaxLife[i] = gp.party.partyMembers.get(i).stats.maxHp;
                ds.monsterLife[i] = gp.party.partyMembers.get(i).stats.hp;
                ds.monsterMana[i] = gp.party.partyMembers.get(i).stats.mp;
                ds.monsterMaxMana[i] = gp.party.partyMembers.get(i).stats.maxMp;
                ds.monsterStrength[i] = gp.party.partyMembers.get(i).stats.str;
                ds.monsterMagic[i] = gp.party.partyMembers.get(i).stats.mag;
                ds.monsterAgility[i] = gp.party.partyMembers.get(i).stats.agi;
                ds.monsterVitality[i] = gp.party.partyMembers.get(i).stats.vit;
                ds.membersInParty++;
            }
            System.out.println("Members in party: " + ds.membersInParty);

            //RandomDungeonGenerator.saveDungeonToFile(gp.tileM.mapTileNum,"src/tile/Map.txt");

            for(int i = 0;i<50;i++){
                for(int j = 0;j<50;j++){
                    ds.dungeonMap[i][j] = gp.tileM.mapTileNum[i][j];
                }
            }


            //Write in the file
            oos.writeObject(ds);

        } catch (Exception e) {
            System.out.println("Save Exception!");
            e.printStackTrace(System.err);
        }

    }

    /**
     * Load game data from a file.
     */
    public void load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));
            DataStorage ds = (DataStorage) ois.readObject();

            //Player Stats
            gp.player.stats.level = ds.level;
            gp.player.stats.exp = ds.exp;
            gp.player.stats.nextLevelExp = ds.nextLevelExp;
            gp.player.stats.maxHp = ds.maxLife;
            gp.player.stats.hp = ds.life;
            gp.player.stats.mp = ds.mana;
            gp.player.stats.maxMp = ds.maxMana;
            gp.player.stats.str = ds.strength;
            gp.player.stats.mag = ds.magic;
            gp.player.stats.agi = ds.agility;
            gp.player.stats.vit = ds.vitality;
            gp.player.stats.money = ds.money;
            gp.player.WorldX = ds.playerX;
            gp.player.WorldY= ds.playerY;
            System.out.println("Player X: " + gp.player.WorldX);
            System.out.println("Player Y: " + gp.player.WorldY);

                    //Player Inventory
            gp.player.inventory.clear();
            for (int i = 0; i < ds.itemNames.size(); i++) {
                gp.player.inventory.add(getObject(ds.itemNames.get(i)));
            }
            //Player Equipment
            gp.player.stats.weapon = (OBJ_Weapon) gp.player.inventory.get(ds.currentWeaponSlot);
            gp.player.stats.armor = (OBJ_Armor) gp.player.inventory.get(ds.currentArmorSlot);
            gp.player.getDefense();
            gp.player.getPlayerImage();
            gp.tileM.loadedGame = true;

            for (int i = 0; i < gp.obj.length; i++) {
                if (gp.obj[i] != null) {
                    gp.obj[i].name = ds.mapObjectNames[i];
                    gp.obj[i].WorldX = ds.mapObjectWorldX[i];
                    gp.obj[i].WorldY = ds.mapObjectWorldY[i];
                    gp.obj[i].isVisible = ds.mapObjectVisibility[i];
                    //System.out.println("Loaded: " + ds.mapObjectNames[i] + "," + ds.mapObjectWorldX[i] + "," + ds.mapObjectWorldY[i]);
                }
            }




            System.out.println("Members in party: " + ds.membersInParty);
            gp.party.partyMembers.clear();
            for(int i = 0;i<ds.membersInParty;i++){
                gp.party.addMonsterToParty(ds.monsterName[i]);
                gp.party.partyMembers.get(i).swapStats(ds.monsterLevel[i],ds.monsterEXP[i],ds.monsterNextLevelEXP[i],ds.monsterLife[i],ds.monsterMaxLife[i],ds.monsterMana[i],ds.monsterMaxMana[i],ds.monsterStrength[i],ds.monsterAgility[i],ds.monsterMagic[i],ds.monsterVitality[i]);

            }
            gp.tileM.loadedGame = true;

            for(int i = 0;i<50;i++){
                for(int j = 0;j<50;j++){
                    gp.tileM.oldmapTileNum[i][j] = ds.dungeonMap[i][j];
                }
            }



        } catch (Exception e) {
            System.out.println("Load Exception!");
            e.printStackTrace(System.err);
        }
    }
}
