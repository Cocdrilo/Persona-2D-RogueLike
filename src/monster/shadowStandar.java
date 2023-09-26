package monster;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;
import java.util.Random;

public class shadowStandar extends Entity {

    BufferedImage walkDown3,walkDown4,walkUp3,walkUp4,walkLeft3,walkLeft4,walkRight3,walkRight4;

    public int health;
    public int maxHealth;
    public int attack;
    public int defense;
    public int xpGiven;
    private final String attackType;
    public BufferedImage combatImage;


    public shadowStandar(GamePanel gp,String name,int health,int attack,int defense,int xpGiven,String attackType,String combatImagePath,String[] weaknesses, String[] resistances, String[] nulls, String[] repells) {
        super(gp);
        type = 2;
        this.name = name;
        this.maxHealth = health;
        this.attack = attack;
        this.defense = defense;
        this.xpGiven = xpGiven;
        this.health = maxHealth;
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
