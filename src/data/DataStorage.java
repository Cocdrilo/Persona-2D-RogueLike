package data;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a data storage that holds the player stats, inventory, and objects on the map.
 */
public class DataStorage implements Serializable {

    //PLAYER STATS
    int level, exp, nextLevelExp, maxLife, life ,mana, maxMana;
    int strength, magic, agility, vitality, money, playerX, playerY;

    //Player inventory
    ArrayList<String> itemNames = new ArrayList<>();
    int currentWeaponSlot;
    int currentArmorSlot;

    //Objects on map
    String[] mapObjectNames = new String[10];
    int[] mapObjectWorldX = new int[10];
    int[] mapObjectWorldY = new int[10];
    boolean[] mapObjectVisibility = new boolean[10];


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

    //Dungeon Map Storage
    int[][] dungeonMap = new int[50][50];

}
