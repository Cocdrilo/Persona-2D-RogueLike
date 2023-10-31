package monster;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;
import java.util.Random;

public class shadowStandar extends Entity {

    public BufferedImage walkDown3, walkDown4, walkUp3, walkUp4, walkLeft3, walkLeft4, walkRight3, walkRight4;

    public int xpGiven;
    private String attackType;
    public BufferedImage combatImage;
    public String combatImagePath;

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

    public String getAttackType() {
        return attackType;
    }

    public int getPhysAttack(int playerEndurance, int attackerStat) {
        return 5 * (int) (Math.sqrt(((double) attackerStat / playerEndurance) * randomFactor()));
    }


    public BufferedImage getCombatImage() {
        combatImage = setUp(combatImagePath);
        return combatImage;
    }

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

    public void setAction() {

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
