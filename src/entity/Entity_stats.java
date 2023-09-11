package entity;

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
    //PERSONA STATS
    public int str;
    public int intel;
    public int luck;
    public int agi;
    public int vit;

    //PLAYER EQUIPMENT
    public Entity weapon;
    public Entity armor;
    public Entity accessory;

    //EQUIPMENT STATS
    public int def;
    public int atkDmg;
    public int hit;

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
        //PERSONA STATS DEFAULT
        str = 5;
        intel = 5;
        agi = 5;
        vit = 5;

        //PLAYER EQUIPMENT DEFAULT
        weapon = null;
        armor = null;
        accessory = null;

    }

}
