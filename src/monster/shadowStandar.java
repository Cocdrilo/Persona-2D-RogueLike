package monster;

import entity.Entity;
import main.BattleSystem;
import main.GamePanel;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

/**
 * Represents a standard shadow monster in the game.
 */
public class shadowStandar extends Entity {

    public BufferedImage walkDown3, walkDown4, walkUp3, walkUp4, walkLeft3, walkLeft4, walkRight3, walkRight4;

    public int xpGiven;
    private String attackType;
    public BufferedImage combatImage;
    public String combatImagePath;
    public boolean boss = false;

    /**
     * Constructs a shadow standard monster with data from the provided monster data.
     *
     * @param gp   The GamePanel instance.
     * @param data The monster data used for initialization.
     */
    public shadowStandar(GamePanel gp, monsterData data) {
        super(gp);
        this.name = data.name;
        this.stats.hp = data.hp;
        this.stats.maxHp = this.stats.hp;
        this.stats.str = data.str;
        this.stats.agi = data.agi;
        this.stats.mag = data.mag;
        this.stats.vit = data.vit;
        this.stats.level = data.lvl;
        this.xpGiven = data.xpGiven;
        this.stats.nextLevelExp = stats.level * 20;
        this.stats.exp = 0;
        this.attackType = data.attackType;
        this.combatImagePath = data.combatImagePath;
        this.resistances = data.resistances;
        this.weaknesses = data.weaknesses;
        this.nulls = data.nulls;
        this.repells = data.repells;
        fillSpells(data.spells);
        this.type = 2;
        speed = 1;
        getImage();
    }

    /**
     * Gets the type of attack used by the monster.
     *
     * @return The attack type.
     */
    public String getAttackType() {
        return attackType;
    }

    /**
     * Calculates the physical attack damage based on player endurance and attacker's stat.
     *
     * @param playerEndurance Player's endurance stat.
     * @param attackerStat    Attacker's stat.
     * @return The calculated physical attack damage.
     */
    public int getPhysAttack(int playerEndurance, int attackerStat) {
        return 5 * (int) (Math.sqrt(((double) attackerStat / playerEndurance) * randomFactor()));
    }

    /**
     * Increases the level of the shadowStandar instance and allocates
     * a random number of points to its stats upon leveling up.
     * The total number of points distributed is fixed at 3, and they are
     * randomly assigned to the available stats (hp, str, agi, mag).
     * The distribution ensures that each stat receives at least 1 point.
     *
     * @see Random
     */
    public void levelUp() {
        stats.level++;
        stats.nextLevelExp = stats.nextLevelExp * 2;

        // Randomly distribute 3 points among stats
        Random random = new Random();
        int totalPoints = 3;

        while (totalPoints > 0) {
            int pointsToAdd = random.nextInt(3) + 1; // Randomly choose 1 to 3 points

            // Randomly choose a stat to add points to
            int statChoice = random.nextInt(4); // Assuming you have 4 stats (hp, str, agi, mag)

            switch (statChoice) {
                case 0 -> this.stats.hp += pointsToAdd;
                case 1 -> this.stats.str += pointsToAdd;
                case 2 -> this.stats.agi += pointsToAdd;
                case 3 -> this.stats.mag += pointsToAdd;

                // Add more cases if you have additional stats
            }

            totalPoints -= pointsToAdd;
        }
        System.out.println("Level up!" + name + " is now level " + stats.level);
    }


    /**
     * Gets the combat image of the monster.
     *
     * @return The combat image.
     */
    public BufferedImage getCombatImage() {
        combatImage = setUp(combatImagePath, gp.tileSize, gp.tileSize);
        return combatImage;
    }

    /**
     * Loads images for different walking directions.
     */
    public void getImage() {
        walkDown1 = setUp("/Monsters/Demon1", gp.tileSize, gp.tileSize);
        walkDown2 = setUp("/Monsters/Demon2", gp.tileSize, gp.tileSize);
        walkDown3 = setUp("/Monsters/Demon3", gp.tileSize, gp.tileSize);
        walkDown4 = setUp("/Monsters/Demon4", gp.tileSize, gp.tileSize);
        walkLeft1 = setUp("/Monsters/Demon1", gp.tileSize, gp.tileSize);
        walkLeft2 = setUp("/Monsters/Demon2", gp.tileSize, gp.tileSize);
        walkLeft3 = setUp("/Monsters/Demon3", gp.tileSize, gp.tileSize);
        walkLeft4 = setUp("/Monsters/Demon4", gp.tileSize, gp.tileSize);
        walkRight1 = setUp("/Monsters/Demon1", gp.tileSize, gp.tileSize);
        walkRight2 = setUp("/Monsters/Demon2", gp.tileSize, gp.tileSize);
        walkRight3 = setUp("/Monsters/Demon3", gp.tileSize, gp.tileSize);
        walkRight4 = setUp("/Monsters/Demon4", gp.tileSize, gp.tileSize);
        walkUp1 = setUp("/Monsters/Demon1", gp.tileSize, gp.tileSize);
        walkUp2 = setUp("/Monsters/Demon2", gp.tileSize, gp.tileSize);
        walkUp3 = setUp("/Monsters/Demon3", gp.tileSize, gp.tileSize);
        walkUp4 = setUp("/Monsters/Demon4", gp.tileSize, gp.tileSize);
    }

    /**
     * Swaps the entity's sprites to a boss image with a specified size.
     * The entity's movement sprites (walk) in all directions are set to the boss image with the given size.
     * The solid area dimensions are adjusted accordingly, and the entity is marked as a boss.
     */
    public void swaptoBossImage() {
        int i = 3;

        walkDown1 = setUp("/Monsters/Archangel1", gp.tileSize * i, gp.tileSize * i);
        walkDown2 = setUp("/Monsters/Archangel2", gp.tileSize * i, gp.tileSize * i);
        walkDown3 = setUp("/Monsters/Archangel3", gp.tileSize * i, gp.tileSize * i);
        walkDown4 = setUp("/Monsters/Archangel1", gp.tileSize * i, gp.tileSize * i);
        walkLeft1 = setUp("/Monsters/Archangel2", gp.tileSize * i, gp.tileSize * i);
        walkLeft2 = setUp("/Monsters/Archangel3", gp.tileSize * i, gp.tileSize * i);
        walkLeft3 = setUp("/Monsters/Archangel1", gp.tileSize * i, gp.tileSize * i);
        walkLeft4 = setUp("/Monsters/Archangel2", gp.tileSize * i, gp.tileSize * i);
        walkRight1 = setUp("/Monsters/Archangel3", gp.tileSize * i, gp.tileSize * i);
        walkRight2 = setUp("/Monsters/Archangel1", gp.tileSize * i, gp.tileSize * i);
        walkRight3 = setUp("/Monsters/Archangel2", gp.tileSize * i, gp.tileSize * i);
        walkRight4 = setUp("/Monsters/Archangel3", gp.tileSize * i, gp.tileSize * i);
        walkUp1 = setUp("/Monsters/Archangel1", gp.tileSize * i, gp.tileSize * i);
        walkUp2 = setUp("/Monsters/Archangel2", gp.tileSize * i, gp.tileSize * i);
        walkUp3 = setUp("/Monsters/Archangel3", gp.tileSize * i, gp.tileSize * i);
        walkUp4 = setUp("/Monsters/Archangel1", gp.tileSize * i, gp.tileSize * i);

        solidArea.width = gp.tileSize * i;
        solidArea.height = gp.tileSize * i;
        boss = true;
    }

    /**
     * Sets the action for the monster (basic random movement).
     */
    public void setAction() {

        if (onPath) {
            int goalCol = (gp.player.WorldX + gp.player.solidArea.x + 1) / gp.tileSize;
            int goalRow = (gp.player.WorldY + gp.player.solidArea.y + 1) / gp.tileSize;

            searchPath(goalCol, goalRow);
        } else {
            randomMoveForEntities();
        }
    }

    /**
     * Updates the state of the monster, including collision with the player,
     * checking the distance to the player, and managing the monster's path.
     */
    public void update() {

        super.update();

        // Verifica la colisión con el jugador
        if (gp.cCheck.checkPlayer(this)) {
            gp.player.enemyContactPlayer(this);

            // Establece la posición del monstruo en null
            int monsterIndex = Arrays.asList(gp.monsters).indexOf(this);
            if (monsterIndex != -1) {
                gp.monsters[monsterIndex] = null;
            }
        }

        int xDistance = Math.abs(WorldX - gp.player.WorldX);
        int yDistance = Math.abs(WorldY - gp.player.WorldY);
        int tileDistance = (xDistance + yDistance) / gp.tileSize;

        if (!onPath && tileDistance < 5) {
            onPath = true;
        }

        //Agro Range
        if (onPath && tileDistance > 10) {
            onPath = false;
        }

    }

    //ds.monsterLevel[i],ds.monsterEXP[i],ds.monsterNextLevelEXP[i],ds.monsterLife[i],ds.monsterMaxLife[i],ds.monsterMana[i],ds.monsterMaxMana[i],ds.monsterStrength[i],ds.monsterAgility[i],ds.monsterMagic[i],ds.monsterVitality[i]

    /**
     * Swaps the stats of the entity with the provided values.
     *
     * @param level        The new level for the entity.
     * @param exp          The new experience points for the entity.
     * @param nextLevelExp The experience points required for the next level.
     * @param life         The new current life points (HP) for the entity.
     * @param maxLife      The new maximum life points (HP) for the entity.
     * @param mana         The new current mana points (MP) for the entity.
     * @param maxMana      The new maximum mana points (MP) for the entity.
     * @param strength     The new strength stat for the entity.
     * @param agility      The new agility stat for the entity.
     * @param magic        The new magic stat for the entity.
     * @param vitality     The new vitality stat for the entity.
     */
    public void swapStats(int level, int exp, int nextLevelExp, int life, int maxLife, int mana, int maxMana, int strength, int agility, int magic, int vitality) {
        stats.level = level;
        stats.exp = exp;
        stats.nextLevelExp = nextLevelExp;
        stats.hp = life;
        stats.maxHp = maxLife;
        stats.mp = mana;
        stats.maxMp = maxMana;
        stats.str = strength;
        stats.agi = agility;
        stats.mag = magic;
        stats.vit = vitality;
    }
}
