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

    //Objects on map
    String[][] mapObjectNames;
    int[][] mapObjectWorldX;
    int[][] mapObjectWorldY;
    String[][] mapObjectLootNames;
    boolean[][] mapObjectOpened;

}
