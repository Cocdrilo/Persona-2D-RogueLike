package data;

import main.GamePanel;

import javax.xml.crypto.Data;
import java.io.*;

public class SaveLoad {
    GamePanel gp;
    public SaveLoad(GamePanel gp){
        this.gp = gp;
    }
    public void save(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
            DataStorage ds = new DataStorage();
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


        }catch (Exception e){
            System.out.println("Load Exception!");
        }
    }
}
