package entity;

import Object.Equipables.OBJ_Armor;
import Object.Equipables.OBJ_Weapon;

/**
 * This class represents the statistics and equipment of an entity, particularly a player.
 */
public class Entity_stats {
    //PLAYER STATS
    public int level;
    public int money;
    public int exp;
    public int nextLevelExp;
    public int hp;
    public int maxHp;
    public int mp;
    public int maxMp;
    public int str;
    public int vit;
    public int agi;
    public int mag;

    //PLAYER EQUIPMENT
    public OBJ_Weapon weapon;
    public OBJ_Armor armor;
    public Entity accessory;

    /**
     * Initializes the player's statistics and equipment with default values.
     */
    public Entity_stats() {
        //PLAYER STATS DEFAULT
        level = 1;
        money = 0;
        exp = 0;
        nextLevelExp = 10;
        hp = 10;
        maxHp = 10;
        mp = 10;
        maxMp = 10;

        str = 5;
        vit = 5;
        agi = 5;
        mag = 5;

        //PLAYER EQUIPMENT DEFAULT
        weapon = null;
        armor = null;
        accessory = null;

    }

}
