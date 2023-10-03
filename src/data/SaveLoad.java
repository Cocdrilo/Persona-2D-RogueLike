package data;

import entity.Entity;
import main.GamePanel;
import Object.OBJ_Armor;
import Object.OBJ_Chest;
import Object.OBJ_Door;
import Object.OBJ_Potion_Health;
import Object.OBJ_Potion_Mana;
import Object.OBJ_Stairs;
import Object.OBJ_WEAPON_BASH;
import Object.OBJ_WEAPON_Piercing;
import Object.OBJ_WEAPON_Slash;

import java.io.*;

public class SaveLoad {
    GamePanel gp;
    public SaveLoad(GamePanel gp){
        this.gp = gp;
    }

    public Entity getObject(String itemName){
        Entity obj = null;
        switch (itemName){
            case "Cota de malla": obj = new OBJ_Armor(gp); break;
            case "chest": obj = new OBJ_Chest(gp); break;
            case "door": obj = new OBJ_Door(gp); break;
            case "Health Potion": obj = new OBJ_Potion_Health(gp); break;
            case "Mana Potion": obj = new OBJ_Potion_Mana(gp); break;
            case "stairs": obj = new OBJ_Stairs(gp); break;
            case "Bashing Weapon": obj = new OBJ_WEAPON_BASH(gp); break;
            case "Piercing Weapon": obj = new OBJ_WEAPON_Piercing(gp); break;
            case "Espadon": obj = new OBJ_WEAPON_Slash(gp); break;

        }
        return obj;
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
            ds.strength = gp.player.PLAYERstats.level;
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
            /*
             * ds.currentWeaponSlot = gp.player.getcurrentWeaponSlot();
             * ds.currentShieldSlot = gp.player.getcurrentShieldSlot();
             * */

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
            /* CURRENTSLOT IMPORTANTE
            *  gp.player.currentWeapon = gp.player.inventory.get(ds.currentWeaponSlot);
            *  gp.player.currentShield = gp.player.inventory.get(ds.currentShieldSlot);
            * */
            gp.player.getAttack();
            gp.player.getDefense();
            gp.player.getPlayerImage();


        }catch (Exception e){
            System.out.println("Load Exception!");
        }
    }
}
