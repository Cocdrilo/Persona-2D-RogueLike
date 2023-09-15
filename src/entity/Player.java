package entity;

import main.GamePanel;
import main.KeyHandler;
import Object.*;
import main.Toolbox;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    public final int screenX;
    public final int screenY;

    KeyHandler keyH;
    public Entity_stats PLAYERstats;

    public Player(GamePanel gp,KeyHandler keyH){
        super (gp);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(14,35,26,29);

        solidAreaDefaultX = solidArea.x;
        SolidAreaDefaultY = solidArea.y;

        PLAYERstats = new Entity_stats();

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        WorldX = 100;
        WorldY = 100;
        speed=4;
        direction="down";

        PLAYERstats.level = 1;
        PLAYERstats.maxHp = 10;
        PLAYERstats.hp = PLAYERstats.maxHp;
        PLAYERstats.maxMp = 10;
        PLAYERstats.mp = PLAYERstats.maxMp;
        PLAYERstats.str = 5;
        PLAYERstats.agi = 5;
        PLAYERstats.vit = 5;
        PLAYERstats.exp = 0;
        PLAYERstats.nextLevelExp = 10;
        PLAYERstats.money = 0;
        PLAYERstats.weapon = new OBJ_WEAPON_Slash(gp);
        PLAYERstats.armor = new OBJ_Armor(gp);

        //TENGO QUE HACER DOWNCASTING PARA WEAPON POR QUERER HACERLO EN UNA SUBCLASE
        // Verifica si PLAYERstats.weapon es una instancia de OBJ_WEAPON_Slash

        if (PLAYERstats.weapon instanceof OBJ_WEAPON_Slash) {
            OBJ_WEAPON_Slash slashingWeapon = (OBJ_WEAPON_Slash) PLAYERstats.weapon;

            PLAYERstats.atkDmg = getAttack(slashingWeapon);

        } else {
            PLAYERstats.atkDmg = 0; // Establecer un valor predeterminado para el ataque.
        }

        // Verifica si PLAYERstats.armor es una instancia de OBJ_ARMOR
        if (PLAYERstats.armor instanceof OBJ_Armor) {
            OBJ_Armor armor = (OBJ_Armor) PLAYERstats.armor;
            PLAYERstats.def = getDefense(armor);
        } else {
            // Manejo de caso en el que PLAYERstats.armor no es una instancia de OBJ_ARMOR
            // Puedes establecer un valor predeterminado o tomar otra acción aquí.
            PLAYERstats.def = 0; // Establecer un valor predeterminado para la defensa.
        }
    }

    public int getAttack(OBJ_Weapon weapon){
        int atkReturn = 0;
        atkReturn = PLAYERstats.str + weapon.atk;
        return atkReturn;
    }

    public int getDefense(OBJ_Armor armor) {
        int defense = 0;
        if (armor != null) {
            defense = PLAYERstats.agi * armor.def;
        }
        return defense;
    }

    public void getPlayerImage(){
        standFront = setUp("/player/Raidou1");
        standLeft =  setUp("/player/Raidou2");
        standRight = setUp("/player/Raidou3");
        standBack = setUp("/player/Raidou4");
        walkDown1 = setUp("/player/Raidou5");
        walkDown2 = setUp("/player/Raidou9");
        walkLeft1 = setUp("/player/Raidou6");
        walkLeft2 = setUp("/player/Raidou10");
        walkRight1 = setUp("/player/Raidou7");
        walkRight2 = setUp("/player/Raidou11");
        walkUp1 = setUp("/player/Raidou8");
        walkUp2 = setUp("/player/Raidou12");
    }
    public void update(){

        if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true|| keyH.zPressed == true){

            if(keyH.upPressed==true){
                direction = "up";
            }
            else if(keyH.downPressed==true){
                direction = "down";
            }
            else if(keyH.leftPressed==true){
                direction = "left";
            }
            else if(keyH.rightPressed==true){
                direction = "right";
            }


            // CHECKEA LA COLISION DE TILES
            collisionOn=false;
            gp.cCheck.checkTile(this);

            //CHECK COLISION OBJETOS
            int objIndex = gp.cCheck.checkObject(this,true);
            ObjectInteractions(objIndex);

            //Colision de NPC
            int npcIndex = gp.cCheck.checkEntity(this,gp.npc);
            interactNPC(npcIndex);

            //COLISION CON EVENTOS
            gp.eventHandler.checkEvent();

            //COLISION CON MOBS
            int mobIndex = gp.cCheck.checkEntity(this,gp.monsters);
            contactMonster(mobIndex);


            if(collisionOn==false && keyH.zPressed == false){

                switch(direction){
                    case "up":
                        WorldY -=speed;
                        break;
                    case "down":
                        WorldY +=speed;
                        break;
                    case "left":
                        WorldX -=speed;
                        break;
                    case "right":
                        WorldX +=speed;
                        break;
                }
            }
            //Despues de todo para actualizar estado
            gp.keyH.zPressed = false;

            spriteCounter++;
            if(spriteCounter > 12){
                if(spriteNum==1){
                    spriteNum=2;
                } else if (spriteNum==2) {
                    spriteNum=1;
                }
                spriteCounter = 0;
            }
        }

    }

    private void interactNPC(int i) {
        if(i!=999){
            if(gp.keyH.zPressed){
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
                }
            }
    }

    public void ObjectInteractions(int i){

        if(i != 999){

        }
    }

    public void contactMonster(int i){
        if(i != 999){
            //Cambio a Combate
            gp.gameState = gp.combatState;
        }
    }

    public void draw(Graphics2D graficos2d){

        BufferedImage image = null;

        if (keyH.upPressed == false && keyH.downPressed == false && keyH.leftPressed == false && keyH.rightPressed == false) {
            // El jugador está quieto, selecciona la imagen adecuada según la última dirección.
            switch (direction) {
                case "up":
                    image = standBack;
                    break;
                case "down":
                    image = standFront;
                    break;
                case "left":
                    image = standLeft;
                    break;
                case "right":
                    image = standRight;
                    break;
            }
        } else {
            // El jugador se está moviendo, selecciona la imagen correspondiente a la dirección de movimiento.
            switch (direction) {
                case "up":
                    if (spriteNum == 1) {
                        image = walkUp1;
                    } else if (spriteNum == 2) {
                        image = walkUp2;
                    }
                    break;
                case "down":
                    if (spriteNum == 1) {
                        image = walkDown1;
                    } else if (spriteNum == 2) {
                        image = walkDown2;
                    }
                    break;
                case "left":
                    if (spriteNum == 1) {
                        image = walkLeft1;
                    } else if (spriteNum == 2) {
                        image = walkLeft2;
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image = walkRight1;
                    } else if (spriteNum == 2) {
                        image = walkRight2;
                    }
                    break;
            }
        }
        graficos2d.drawImage(image, screenX, screenY,null);

    }
}
