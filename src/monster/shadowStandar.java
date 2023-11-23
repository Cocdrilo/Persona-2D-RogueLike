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
public class shadowStandar extends Entity{

    public BufferedImage walkDown3, walkDown4, walkUp3, walkUp4, walkLeft3, walkLeft4, walkRight3, walkRight4;

    public int xpGiven;
    private String attackType;
    public BufferedImage combatImage;
    public String combatImagePath;

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
    public void swapStats(int level,int experience,int nextLevelExperience,int hp,int maxHp,int mp,int maxMp,int str,int agi,int mag,int vit){
        this.stats.hp = hp;
        this.stats.maxHp = maxHp;
        this.stats.exp = experience;
        this.stats.nextLevelExp = nextLevelExperience;
        this.stats.mp = mp;
        this.stats.maxMp = maxMp;
        this.stats.str = str;
        this.stats.agi = agi;
        this.stats.mag = mag;
        this.stats.vit = vit;
        this.stats.level = level;
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
     * Gets the combat image of the monster.
     *
     * @return The combat image.
     */
    public BufferedImage getCombatImage() {
        combatImage = setUp(combatImagePath);
        return combatImage;
    }

    /**
     * Loads images for different walking directions.
     */
    public void getImage() {
        walkDown1 = setUp("/Monsters/Demon1");
        walkDown2 = setUp("/Monsters/Demon2");
        walkDown3 = setUp("/Monsters/Demon3");
        walkDown4 = setUp("/Monsters/Demon4");
        walkLeft1 = setUp("/Monsters/Demon1");
        walkLeft2 = setUp("/Monsters/Demon2");
        walkLeft3 = setUp("/Monsters/Demon3");
        walkLeft4 = setUp("/Monsters/Demon4");
        walkRight1 = setUp("/Monsters/Demon1");
        walkRight2 = setUp("/Monsters/Demon2");
        walkRight3 = setUp("/Monsters/Demon3");
        walkRight4 = setUp("/Monsters/Demon4");
        walkUp1 = setUp("/Monsters/Demon1");
        walkUp2 = setUp("/Monsters/Demon2");
        walkUp3 = setUp("/Monsters/Demon3");
        walkUp4 = setUp("/Monsters/Demon4");


    }

    /**
     * Sets the action for the monster (basic random movement).
     */
    public void setAction() {

        if (onPath){
            int goalCol = (gp.player.WorldX + gp.player.solidArea.x+1)/gp.tileSize;
            int goalRow = (gp.player.WorldY + gp.player.solidArea.y+1)/gp.tileSize;

            searchPath(goalCol, goalRow);
        }

        else{
            //IA BASICA DE MOVIMIENTO ALEATORIO
            actionLockCounter++;

            if (actionLockCounter == 90) {
                Random random = new Random();
                int i = random.nextInt(100 + 1);

                if (i <= 25) {
                    direction = "up";
                }
                if (i > 25 && i <= 50) {
                    direction = "down";
                }
                if (i > 50 && i <= 75) {
                    direction = "left";
                }
                if (i > 75) {
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }
    }


    public void update(){

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
        int tileDistance = (xDistance + yDistance)/gp.tileSize;

        if(!onPath && tileDistance < 5){
            onPath = true;
        }

        //Agro Range
        if(onPath && tileDistance > 10){
            onPath = false;
        }

    }



}
