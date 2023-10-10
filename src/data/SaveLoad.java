package data;

import Object.Equipables.*;
import Object.Consumables.*;
import Object.WorldBuilding.*;
import entity.Entity;
import main.GamePanel;


import java.io.*;

public class SaveLoad {
    GamePanel gp;
    public SaveLoad(GamePanel gp){
        this.gp = gp;
    }

    public Entity getObject(String itemName){
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
    public void save(){
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

            //Player Inventory
            for(int i = 0;i<gp.player.inventory.size();i++){
                ds.itemNames.add(gp.player.inventory.get(i).name);
                //ds.itemAmounts.add(gp.player.inventory.get(i).amount);

            }

            //Player Equipment
              ds.currentWeaponSlot = gp.player.getWeaponSlot();
              ds.currentArmorSlot = gp.player.getArmorSlot();

            //Objects on Map


            //Write in the file
            oos.writeObject(ds);

        }catch (Exception e){
            System.out.println("Save Exception!");
        }

    }
    public void load(){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));
            DataStorage ds = (DataStorage)ois.readObject();

            //Player Stats
            gp.player.stats.level = ds.level;
            gp.player.stats.exp = ds.exp;
            gp.player.stats.nextLevelExp = ds.nextLevelExp;
            gp.player.stats.maxHp = ds.maxLife;
            gp.player.stats.hp = ds.life;
            gp.player.stats.mp = ds.mana;
            gp.player.stats.maxMp = ds.maxMana;
            gp.player.stats.str = ds.strength;
            gp.player.stats.mag= ds.magic;
            gp.player.stats.agi = ds.agility;
            gp.player.stats.vit = ds.vitality;
            gp.player.stats.money = ds.money;

            //Player Inventory
            gp.player.inventory.clear();
            for(int i = 0;i< ds.itemNames.size();i++){
                gp.player.inventory.add(getObject(ds.itemNames.get(i)));
            }
            //Player Equipment
            gp.player.stats.weapon = (OBJ_Weapon) gp.player.inventory.get(ds.currentWeaponSlot);
            gp.player.stats.armor = (OBJ_Armor) gp.player.inventory.get(ds.currentArmorSlot);
            gp.player.getDefense();
            gp.player.getPlayerImage();



        }catch (Exception e){
            System.out.println("Load Exception!");
        }
    }
}
