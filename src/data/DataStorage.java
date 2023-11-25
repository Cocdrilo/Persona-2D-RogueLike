package data;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a data storage that holds the player stats, inventory, and objects on the map.
 */
public class DataStorage implements Serializable {

    //PLAYER STATS
    int level;
    int exp;
    int nextLevelExp;
    int maxLife;
    int life;
    int mana;
    int maxMana;
    int strength;
    int magic;
    int agility;
    int vitality;
    int money;

    //Player inventory
    ArrayList<String> itemNames = new ArrayList<>();
    ArrayList<Integer> itemAmounts = new ArrayList<>();
    int currentWeaponSlot;
    int currentArmorSlot;

    //Party
    int[] monsterLevel = new int[3];
    int[] monsterEXP = new int[3];
    int[] monsterNextLevelEXP = new int[3];
    int[] monsterMaxLife = new int[3];
    int[] monsterLife = new int[3];
    int[] monsterMana = new int[3];
    int[] monsterMaxMana = new int[3];
    int[] monsterStrength = new int[3];
    int[] monsterMagic = new int[3];
    int[] monsterAgility = new int[3];
    int[] monsterVitality = new int[3];
    String[] monsterName = new String[3];
    int membersInParty = 0;

}
