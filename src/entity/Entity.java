package entity;

import battleNeeds.superMagic;
import main.GamePanel;
import main.KeyHandler;
import main.Toolbox;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Entity {

    public GamePanel gp;
    KeyHandler keyH;


    //SOLID AREAS
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX,SolidAreaDefaultY;
    //DIALOGOS

    public String dialogues[] = new String[20];
    public int dialogueIndex = 0;

    //ESTADOS DEL JUEGO
    public int WorldX, WorldY;
    public String direction= "down";
    public int spriteNum = 1;
    public boolean collisionOn = false;


    //ATRIBUTOS DE PERSONAJES
    public int speed;

    public String name;
    public int type; // 0 = player, 1 = npc, 2 = mosnter 3 = equipableSword 4 = equipableArmor 5 = consumableItem
    public Entity_stats stats;
    public String description = "";
    public boolean isPickupeable = false;

    //Maps de Resistencias y Debilidades.
    public String resistances[];
    public String weaknesses[];
    public String nulls[];
    public String repells[];

    //Array de hechizos:
    protected ArrayList<superMagic> spells;


    //CombatMethods

    public boolean isWeak(String attckType){
        for (String weakness : weaknesses) {
            if (Objects.equals(attckType, weakness)) {
                return true;
            }
        }
        return false;
        }

    public boolean isResistant(String attckType){
        for (String resistance : resistances) {
            if (Objects.equals(attckType, resistance)) {
                return true;
            }
        }
        return false;
    }

    public boolean isNull(String attckType){
        for (String aNull : nulls) {
            if (Objects.equals(attckType, aNull)) {
                return true;
            }
        }
        return false;
    }

    public boolean isRepelled(String attckType){
        for (String repel : repells) {
            if (Objects.equals(attckType, repel)) {
                return true;
            }
        }
        return false;
    }


    //CONTADORES
    public int spriteCounter = 0;
    public int actionLockCounter = 0;

    //IMAGE DATA
    public BufferedImage image;
    public boolean collision = false;
    public BufferedImage standFront,standLeft,standBack,standRight,walkDown1,walkDown2,walkLeft1,walkLeft2,walkRight1,walkRight2,walkUp1,walkUp2;

    public Entity(GamePanel gp){

        this.gp = gp;
        stats = new Entity_stats();
    }

    public void setAction(){

    }

    public void update(){
        setAction();

        collisionOn=false;
        gp.cCheck.checkTile(this);
        gp.cCheck.checkObject(this,false);
        gp.cCheck.checkEntity(this,gp.npc);
        gp.cCheck.checkEntity(this,gp.monsters);
        boolean contactPlayer = gp.cCheck.checkPlayer(this);


        if(this.type == 2 && contactPlayer == true){
            //Aqui habra que empieza combate
        }

        //COLISON = FALSO ->PUEDE MOVER
        if(collisionOn==false){

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

    public BufferedImage setUp(String ImagePath){
        Toolbox tbox = new Toolbox();
        BufferedImage scaledImage = null;

        try{
            scaledImage = ImageIO.read(getClass().getResourceAsStream(ImagePath+".png"));
            scaledImage = tbox.scaleImage(scaledImage,gp.tileSize,gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
        return scaledImage;
    }

    public void speak(){
        gp.ui.currentName = name;
        if(dialogues[dialogueIndex]==null){
            dialogueIndex=0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        //HACE QUE EL NPC MIRE AL JUGADOR CUANDO LE HABLAS

        switch (gp.player.direction){
            case "up":
                direction="down";
                break;

            case "down":
                direction="up";
                break;
            case"left":
                direction="right";
                break;
            case"right":
                direction="left";
                break;
        }

    }

    public void use(Entity entity){

    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;
        int ScreenX = WorldX - gp.player.WorldX + gp.player.screenX;
        int ScreenY = WorldY - gp.player.WorldY + gp.player.screenY;

        if(WorldX + gp.tileSize > gp.player.WorldX - gp.player.screenX && WorldX - gp.tileSize <gp.player.WorldX + gp.player.screenX && WorldY + gp.tileSize > gp.player.WorldY - gp.player.screenY && WorldY - gp.tileSize < gp.player.WorldY + gp.player.screenX){

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
            g2.drawImage(image,ScreenX,ScreenY,gp.tileSize,gp.tileSize,null);
        }
        }

    }
