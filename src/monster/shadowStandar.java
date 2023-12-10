package monster;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

/**
 * Represents a standard shadow monster in the game.
 */
public class shadowStandar extends Entity {

    public BufferedImage walkDown3, walkDown4, walkUp3, walkUp4, walkLeft3, walkLeft4, walkRight3, walkRight4;
    public int xpGivenAtMonsterDeath;
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
        setJsonDataToGameMonster(data);
        setSpeedAndEntityType();
        getImage();
    }

    private void setJsonDataToGameMonster(monsterData selectedMonsterData){
        this.name = selectedMonsterData.name;
        this.stats.hp = selectedMonsterData.hp;
        this.stats.maxHp = this.stats.hp;
        this.stats.str = selectedMonsterData.str;
        this.stats.agi = selectedMonsterData.agi;
        this.stats.mag = selectedMonsterData.mag;
        this.stats.vit = selectedMonsterData.vit;
        this.stats.level = selectedMonsterData.lvl;
        this.xpGivenAtMonsterDeath = selectedMonsterData.xpGiven;
        this.stats.nextLevelExp = stats.level * 20;
        this.stats.exp = 0;
        this.attackType = selectedMonsterData.attackType;
        this.combatImagePath = selectedMonsterData.combatImagePath;
        this.resistances = selectedMonsterData.resistances;
        this.weaknesses = selectedMonsterData.weaknesses;
        this.nulls = selectedMonsterData.nulls;
        this.repells = selectedMonsterData.repells;
        fillSpells(selectedMonsterData.spells);
    }
    private void setSpeedAndEntityType(){
        this.type = 2;
        speed = 1;
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
     * Neccesary Function to create Boss Monsters using this class
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
        monsterLevelUpAndExperienceUpdate();
        randomStatDistribution();
        System.out.println("Level up!" + name + " is now level " + stats.level);
    }

    private void monsterLevelUpAndExperienceUpdate(){
        stats.level++;
        stats.nextLevelExp = stats.nextLevelExp * 2;
    }

    private void randomStatDistribution(){
        Random random = new Random();
        int totalPointsToDistribute = 3;

        while (totalPointsToDistribute > 0) {
            int pointsToAdd = random.nextInt(totalPointsToDistribute) + 1; // Randomly choose 1 to 3 points

            int statChoice = random.nextInt(4); // Assuming you have 4 stats (hp, str, agi, mag)

            switch (statChoice) {
                case 0 -> this.stats.hp += pointsToAdd;
                case 1 -> this.stats.str += pointsToAdd;
                case 2 -> this.stats.agi += pointsToAdd;
                case 3 -> this.stats.mag += pointsToAdd;

            }
            totalPointsToDistribute -= pointsToAdd;
        }
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
     * Swaps the entity's sprites to a boss image with a specified size.
     * The entity's movement sprites (walk) in all directions are set to the boss image with the given size.
     * The solid area dimensions are adjusted accordingly, and the entity is marked as a boss.
     */
    public void swaptoBossImage() {
        int scale = 3;
        walkDown1 = setUp("/Monsters/Archangel1", gp.tileSize * scale, gp.tileSize * scale);
        walkDown2 = setUp("/Monsters/Archangel2", gp.tileSize * scale, gp.tileSize * scale);
        walkDown3 = setUp("/Monsters/Archangel3", gp.tileSize * scale, gp.tileSize * scale);
        walkDown4 = setUp("/Monsters/Archangel1", gp.tileSize * scale, gp.tileSize * scale);
        walkLeft1 = setUp("/Monsters/Archangel2", gp.tileSize * scale, gp.tileSize * scale);
        walkLeft2 = setUp("/Monsters/Archangel3", gp.tileSize * scale, gp.tileSize * scale);
        walkLeft3 = setUp("/Monsters/Archangel1", gp.tileSize * scale, gp.tileSize * scale);
        walkLeft4 = setUp("/Monsters/Archangel2", gp.tileSize * scale, gp.tileSize * scale);
        walkRight1 = setUp("/Monsters/Archangel3", gp.tileSize * scale, gp.tileSize * scale);
        walkRight2 = setUp("/Monsters/Archangel1", gp.tileSize * scale, gp.tileSize * scale);
        walkRight3 = setUp("/Monsters/Archangel2", gp.tileSize * scale, gp.tileSize * scale);
        walkRight4 = setUp("/Monsters/Archangel3", gp.tileSize * scale, gp.tileSize * scale);
        walkUp1 = setUp("/Monsters/Archangel1", gp.tileSize * scale, gp.tileSize * scale);
        walkUp2 = setUp("/Monsters/Archangel2", gp.tileSize * scale, gp.tileSize * scale);
        walkUp3 = setUp("/Monsters/Archangel3", gp.tileSize * scale, gp.tileSize * scale);
        walkUp4 = setUp("/Monsters/Archangel1", gp.tileSize * scale, gp.tileSize * scale);

        setBossSolidAreaAndBossStatus(scale);
    }
    private void setBossSolidAreaAndBossStatus(int scale){
        solidArea.width = gp.tileSize * scale;
        solidArea.height = gp.tileSize * scale;
        boss = true;
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
     * Sets the action for the monster (basic random movement).
     */
    public void setAction() {
        if (onPath) {
            chasingThePlayerMoving();
        } else {
            randomMoveForEntities();
        }
    }

    private void chasingThePlayerMoving(){
        int goalCol = (gp.player.WorldX + gp.player.solidArea.x + 1) / gp.tileSize;
        int goalRow = (gp.player.WorldY + gp.player.solidArea.y + 1) / gp.tileSize;

        searchPath(goalCol, goalRow);
    }

    /**
     * Updates the state of the monster, including collision with the player,
     * checking the distance to the player, and managing the monster's path.
     */
    public void update() {

        super.update();

        checkCollisionWithPlayer();

        int tileDistance = calculateTileDistanceFromPlayer();

        checkRangeForAgroAndDeAgro(tileDistance);

    }
    private void checkCollisionWithPlayer(){
        if (gp.cCheck.checkPlayer(this)) {
            gp.player.enemyContactPlayer(this);

            // Establece la posición del monstruo en null
            int monsterIndex = Arrays.asList(gp.monsters).indexOf(this);
            if (monsterIndex != -1) {
                gp.monsters[monsterIndex] = null;
            }
        }
    }
    private int calculateTileDistanceFromPlayer(){
        int xDistance = Math.abs(WorldX - gp.player.WorldX);
        int yDistance = Math.abs(WorldY - gp.player.WorldY);
        return (xDistance + yDistance) / gp.tileSize;
    }
    private void checkRangeForAgroAndDeAgro(int tileDistance){
        if (!onPath && tileDistance < 5) {
            onPath = true;
        }
        //Agro Range
        if (onPath && tileDistance > 10) {
            onPath = false;
        }
    }
}
