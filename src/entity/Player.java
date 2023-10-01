package entity;

import battleNeeds.superMagic;
import main.BattleSystem;
import main.GamePanel;
import main.KeyHandler;
import Object.*;
import monster.shadowStandar;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity{

    public final int screenX;
    public final int screenY;

    KeyHandler keyH;
    public Entity_stats PLAYERstats;

    //Inventario del jugador
    public ArrayList<Entity> inventory = new ArrayList<>();
    public boolean defending = false;

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
        setItems();
        spells = new ArrayList<>();

        ArrayList<superMagic> availableSpells = gp.spellManager.getSpells();
        for (superMagic spell : availableSpells) {
            if (spell.name.equals("Fireball")) {
                addSpell(spell);
            }
            if (spell.name.equals("IceBlast")) {
                addSpell(spell);
            }
        }
        debugPlayerSpells();
    }

    //DEBUG

    public void debugPlayerSpells() {
        System.out.println("DEBUG: Player spells:");
        for(int spells=0;spells<this.spells.size();spells++){
            System.out.println(this.spells.get(spells).name);
        }

    }

    public String[] printPlayerSpells() {
        String[] spellNames = new String[this.spells.size()];
        for(int spells=0;spells<this.spells.size();spells++){
            spellNames[spells] = this.spells.get(spells).name;
        }
        return spellNames;
    }

    public int numberOfSpells() {
        int numSpells = this.spells.size();
        return numSpells;
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
        PLAYERstats.mag = 5;
        PLAYERstats.exp = 0;
        PLAYERstats.nextLevelExp = 10;
        PLAYERstats.money = 0;
        PLAYERstats.weapon = new OBJ_WEAPON_Slash(gp);
        PLAYERstats.armor = new OBJ_Armor(gp);
        resistances = new String[]{};
        weaknesses = new String[]{};
        nulls = new String[]{};
        repells = new String[]{};
    }

    public void setItems(){

        inventory.add(PLAYERstats.weapon);
        inventory.add(PLAYERstats.armor);

    }

    public void selectItems(){

        int itemIndex = gp.ui.getItemIndexSlot();

        if(itemIndex < inventory.size()){

            Entity selectedItem = inventory.get(itemIndex);

            if(selectedItem instanceof OBJ_Weapon){
                PLAYERstats.weapon = (OBJ_Weapon) selectedItem;
            }

            if(selectedItem instanceof OBJ_Armor){
                PLAYERstats.armor = (OBJ_Armor) selectedItem;
            }

            if(selectedItem.type == 5){
                //CONSUMIBLE
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }

            }
        }

    public int getAttack(){
        int atkReturn;
        if ( PLAYERstats.weapon != null){
            return atkReturn = (PLAYERstats.str + PLAYERstats.weapon.atk);
        }
        else {
            return atkReturn = PLAYERstats.str;
        }
    }

    public int getDefense() {
        int defReturn = 0;
        if (PLAYERstats.armor != null) {
            defReturn = PLAYERstats.agi + PLAYERstats.armor.def;
        }
        else{
            defReturn = PLAYERstats.agi;
        }
        return defReturn;
    }

    public String getWeaponDmgType(){
        String dmgType = "";
        if(PLAYERstats.weapon != null){
            dmgType = PLAYERstats.weapon.damageType;
        }
        return dmgType;
    }

    // Métodos para agregar, quitar y acceder a hechizos del jugador
    public void addSpell(superMagic spell) {
        spells.add(spell);
    }

    public void removeSpell(superMagic spell) {
        spells.remove(spell);
    }

    public ArrayList<superMagic> getSpells() {
        return spells;
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

        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.zPressed){

            if(keyH.upPressed){
                direction = "up";
            }
            else if(keyH.downPressed){
                direction = "down";
            }
            else if(keyH.leftPressed){
                direction = "left";
            }
            else if(keyH.rightPressed){
                direction = "right";
            }


            // CHECKEA LA COLISION DE TILES
            collisionOn=false;
            gp.cCheck.checkTile(this);

            //CHECK COLISION OBJETOS
            int objIndex = gp.cCheck.checkObject(this,true);
            ObjectInteractions(objIndex);
            pickUpObject(objIndex);

            //Colision de NPC
            int npcIndex = gp.cCheck.checkEntity(this,gp.npc);
            interactNPC(npcIndex);

            //COLISION CON EVENTOS
            gp.eventHandler.checkEvent();

            //COLISION CON MOBS
            int mobIndex = gp.cCheck.checkEntity(this,gp.monsters);
            contactMonster(mobIndex);


            if(!collisionOn && !keyH.zPressed){

                switch (direction) {
                    case "up" -> WorldY -= speed;
                    case "down" -> WorldY += speed;
                    case "left" -> WorldX -= speed;
                    case "right" -> WorldX += speed;
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

    public void pickUpObject(int i){
        if(i != 999 && gp.keyH.zPressed && gp.obj[i].isPickupeable){
            String text = "";
                inventory.add(gp.obj[i]);
                gp.playerSe(1);
                text = "You picked up a " + gp.obj[i].name + "!";
            gp.ui.addMessage(text);
            gp.obj[i] = null;
        }
    }


    public void contactMonster(int i){
        if(i != 999){
            shadowStandar shadow = (shadowStandar) gp.monsters[i];
            //Cambio a Combate
            gp.battleSystem = new BattleSystem(this,shadow,gp);
            gp.gameState = gp.combatState;
            gp.monsters[i] = null;
        }
    }

    public void getOldStats(){
        keyH.oldStr = gp.player.PLAYERstats.str;
        keyH.oldDex = gp.player.PLAYERstats.dex;
        keyH.oldMag = gp.player.PLAYERstats.mag;
    }

    public void levelUp(){
        keyH.pointsPerLevel = keyH.pointsPerLevel+3;
        getOldStats();
        gp.gameState = gp.levelUpState;
        PLAYERstats.level++;
        PLAYERstats.nextLevelExp = PLAYERstats.nextLevelExp * 2;
    }

    public void draw(Graphics2D graficos2d){

        BufferedImage image = null;

        if (!keyH.upPressed && !keyH.downPressed && !keyH.leftPressed && !keyH.rightPressed) {
            // El jugador está quieto, selecciona la imagen adecuada según la última dirección.
            switch (direction) {
                case "up" -> image = standBack;
                case "down" -> image = standFront;
                case "left" -> image = standLeft;
                case "right" -> image = standRight;
            }
        } else {
            // El jugador se está moviendo, selecciona la imagen correspondiente a la dirección de movimiento.
            switch (direction) {
                case "up" -> {
                    if (spriteNum == 1) {
                        image = walkUp1;
                    } else if (spriteNum == 2) {
                        image = walkUp2;
                    }
                }
                case "down" -> {
                    if (spriteNum == 1) {
                        image = walkDown1;
                    } else if (spriteNum == 2) {
                        image = walkDown2;
                    }
                }
                case "left" -> {
                    if (spriteNum == 1) {
                        image = walkLeft1;
                    } else if (spriteNum == 2) {
                        image = walkLeft2;
                    }
                }
                case "right" -> {
                    if (spriteNum == 1) {
                        image = walkRight1;
                    } else if (spriteNum == 2) {
                        image = walkRight2;
                    }
                }
            }
        }
        graficos2d.drawImage(image, screenX, screenY,null);

    }
}
