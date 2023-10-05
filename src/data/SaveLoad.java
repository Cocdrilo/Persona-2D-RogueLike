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
            ds.level = gp.player.PLAYERstats.level;
            ds.exp = gp.player.PLAYERstats.exp;
            ds.nextLevelExp = gp.player.PLAYERstats.nextLevelExp;
            ds.maxLife = gp.player.PLAYERstats.maxHp;
            ds.life = gp.player.PLAYERstats.hp;
            ds.mana = gp.player.PLAYERstats.mp;
            ds.maxMana = gp.player.PLAYERstats.maxMp;
            ds.strength = gp.player.PLAYERstats.str;
            ds.magic = gp.player.PLAYERstats.mag;
            ds.agility = gp.player.PLAYERstats.agi;
            ds.vitality = gp.player.PLAYERstats.vit;
            ds.money = gp.player.PLAYERstats.money;

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
            gp.player.PLAYERstats.level = ds.level;
            gp.player.PLAYERstats.exp = ds.exp;
            gp.player.PLAYERstats.nextLevelExp = ds.nextLevelExp;
            gp.player.PLAYERstats.maxHp = ds.maxLife;
            gp.player.PLAYERstats.hp = ds.life;
            gp.player.PLAYERstats.mp = ds.mana;
            gp.player.PLAYERstats.maxMp = ds.maxMana;
            gp.player.PLAYERstats.str = ds.strength;
            gp.player.PLAYERstats.mag= ds.magic;
            gp.player.PLAYERstats.agi = ds.agility;
            gp.player.PLAYERstats.vit = ds.vitality;
            gp.player.PLAYERstats.money = ds.money;

            //Player Inventory
            gp.player.inventory.clear();
            for(int i = 0;i< ds.itemNames.size();i++){
                gp.player.inventory.add(getObject(ds.itemNames.get(i)));
            }
            //Player Equipment
            gp.player.PLAYERstats.weapon = (OBJ_Weapon) gp.player.inventory.get(ds.currentWeaponSlot);
            gp.player.PLAYERstats.armor = (OBJ_Armor) gp.player.inventory.get(ds.currentArmorSlot);
            gp.player.getDefense();
            gp.player.getPlayerImage();



        }catch (Exception e){
            System.out.println("Load Exception!");
        }
    }
}
