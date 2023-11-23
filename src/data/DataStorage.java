package data;


import entity.Entity;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a data storage that holds the player stats, inventory, and objects on the map.
 */
public class DataStorage implements Serializable {

    //PLAYER STATS
    int playerX;
    int playerY;
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
    int currentWeaponSlot;
    int currentArmorSlot;

    //Objects on map
    String[] mapObjectNames = new String[10];
    int[] mapObjectWorldX = new int[10];
    int[] mapObjectWorldY = new int[10];
    boolean[] mapObjectVisibility = new boolean[10];

    //Party
    public ArrayList<Entity> party = new ArrayList<Entity>();

}
