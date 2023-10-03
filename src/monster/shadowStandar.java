package monster;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Random;

public class shadowStandar extends Entity{

    transient BufferedImage walkDown3,walkDown4,walkUp3,walkUp4,walkLeft3,walkLeft4,walkRight3,walkRight4;

    public int xpGiven;
    private String attackType;
    transient public BufferedImage combatImage;

    public shadowStandar(GamePanel gp) {
        super(gp);
    }

    @JsonCreator
    public shadowStandar(
            @JsonProperty("gp") GamePanel gp,
            @JsonProperty("name") String name,
            @JsonProperty("health") int health,
            @JsonProperty("str") int str,
            @JsonProperty("agi") int agi,
            @JsonProperty("mag") int mag,
            @JsonProperty("vit") int vit,
            @JsonProperty("xpGiven") int xpGiven,
            @JsonProperty("attackType") String attackType,
            @JsonProperty("combatImagePath") String combatImagePath,
            @JsonProperty("weaknesses") String[] weaknesses,
            @JsonProperty("resistances") String[] resistances,
            @JsonProperty("nulls") String[] nulls,
            @JsonProperty("repells") String[] repells
    ) {
        super(gp);
        type = 2;
        this.name = name;
        this.stats.hp = health;
        this.stats.maxHp = this.stats.hp;
        this.stats.str = str;
        this.stats.agi = agi;
        this.stats.mag = mag;
        this.stats.vit = vit;
        this.xpGiven = xpGiven;
        this.attackType = attackType;
        speed = 1;
        loadCombatImage(combatImagePath); // Cargar la imagen de combate específica
        getImage(); // Cargar las imágenes del mundo

        this.weaknesses = weaknesses;
        this.resistances = resistances;
        this.nulls = nulls;
        this.repells = repells;
    }

    public String getAttackType(){
        return attackType;
    }

    public int getPhysAttack(int playerEndurance,int attackerStat){
        return 5*(int)(Math.sqrt(((double) attackerStat/playerEndurance)*randomFactor()));
    }


    public void loadCombatImage(String combatImagePath){
        combatImage = setUp(combatImagePath);
    }
    public void getImage(){
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
    public void setAction(){

        //IA BASICA DE MOVIMIENTO ALEATORIO
        actionLockCounter++;

        if(actionLockCounter == 90){
            Random random = new Random();
            int i = random.nextInt(100+1);

            if(i<=25){
                direction="up";
            }
            if(i>25 && i<=50){
                direction="down";
            }
            if(i>50 && i<=75){
                direction="left";
            }
            if(i>75){
                direction="right";
            }
            actionLockCounter =0;
        }
    }
}
