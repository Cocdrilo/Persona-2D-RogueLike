package entity;

import Object.Equipables.OBJ_Armor;
import Object.Equipables.OBJ_WEAPON_Slash;
import Object.Equipables.OBJ_Weapon;
import Object.WorldBuilding.OBJ_Chest;
import battleNeeds.superMagic;
import main.BattleSystem;
import main.GamePanel;
import main.KeyHandler;
import monster.shadowStandar;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {

    public final int screenX;
    public final int screenY;

    KeyHandler keyH;
    public Entity_stats stats;

    //Inventario del jugador
    public ArrayList<Entity> inventory = new ArrayList<>();
    public boolean defending = false;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(14, 15, 26, 29);

        solidAreaDefaultX = solidArea.x;
        SolidAreaDefaultY = solidArea.y;

        stats = new Entity_stats();
        String[] defaultSpells = {"Zio", "Agi"};

        setDefaultValues();
        getPlayerImage();
        setItems();
        fillSpells(defaultSpells);
        debugPlayerSpells();
    }

    //DEBUG

    public void debugPlayerSpells() {
        System.out.println("DEBUG: Player spells:");
        for (superMagic spell : this.spells) {
            System.out.println(spell.name);
        }

    }

    public void setDefaultValues() {
        speed = 10;
        direction = "down";
        name = "Raidou";

        setRandomCol();
        setRandomRow();
        System.out.println("DEBUG: Player position: " + WorldX + ", " + WorldY);

        stats.level = 1;
        stats.maxHp = 190;
        stats.hp = stats.maxHp;
        stats.maxMp = 41;
        stats.mp = stats.maxMp;
        stats.str = 30;
        stats.agi = 3;
        stats.mag = 2;
        stats.vit = 3;
        stats.exp = 0;
        stats.nextLevelExp = 10;
        stats.money = 50;
        stats.weapon = new OBJ_WEAPON_Slash(gp);
        stats.armor = new OBJ_Armor(gp);
        resistances = new String[]{};
        weaknesses = new String[]{};
        nulls = new String[]{};
        repells = new String[]{};
    }

    public void setRandomCol() {
        int[] datos = gp.tileM.setPlayerRandomPosition();
        WorldX = datos[0]*gp.tileSize;
    }

    public void setRandomRow() {
        int[] datos = gp.tileM.setPlayerRandomPosition();
        WorldY = datos[1]*gp.tileSize;
    }

    public void setItems() {

        inventory.add(stats.weapon);
        inventory.add(stats.armor);

    }

    public int getWeaponSlot() {
        int weaponSlot = -1; // Valor predeterminado para indicar que no se ha encontrado un arma en el inventario
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) instanceof OBJ_Weapon && inventory.get(i) == stats.weapon) {
                weaponSlot = i;
                break; // Terminamos la búsqueda cuando se encuentra un arma
            }
        }
        return weaponSlot;
    }

    public int getArmorSlot() {
        int armorSlot = -1; // Valor predeterminado para indicar que no se ha encontrado armadura en el inventario
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) instanceof OBJ_Armor && inventory.get(i) == stats.armor) {
                armorSlot = i;
                break; // Terminamos la búsqueda cuando se encuentra armadura
            }
        }
        return armorSlot;
    }

    public String[] printItems() {
        String[] Items = new String[inventory.size()];
        int ItemsIndex = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).type == 5) {
                Items[ItemsIndex] = inventory.get(i).name;
                ItemsIndex++;
            }
        }
        String[] consumableItems = new String[ItemsIndex];
        for (int i = 0; i < ItemsIndex; i++) {
            consumableItems[i] = Items[i];
        }
        return consumableItems;
    }

    public ArrayList<Entity> getItems() {
        ArrayList<Entity> Items = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).type == 5) {
                Items.add(inventory.get(i));
            }
        }
        return Items;
    }

    public int[] saveItemIndexes() {
        int[] itemIndexes = new int[inventory.size()];
        int itemCounter = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).type == 5) {
                itemIndexes[itemCounter] = i;
                itemCounter++;
            }
        }
        int[] consumableItemsIndex = new int[itemIndexes.length];
        for (int i = 0; i < itemCounter; i++) {
            consumableItemsIndex[i] = itemIndexes[i];
        }
        return consumableItemsIndex;
    }

    public void selectItems() {

        int itemIndex = gp.ui.getItemIndexSlot();

        if (itemIndex < inventory.size()) {

            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem instanceof OBJ_Weapon) {
                stats.weapon = (OBJ_Weapon) selectedItem;
            }

            if (selectedItem instanceof OBJ_Armor) {
                stats.armor = (OBJ_Armor) selectedItem;
            }
            if (selectedItem instanceof OBJ_Chest cofre){
                cofre.use();
                inventory.remove(itemIndex);
            }

            if (selectedItem.type == 5) {
                //CONSUMIBLE
                selectedItem.overWorldUse(this);
                inventory.remove(itemIndex);
            }

        }
    }

    public int getDefense() {
        int defReturn = 0;
        if (stats.armor != null) {
            defReturn = stats.agi + stats.armor.def;
        } else {
            defReturn = stats.agi;
        }
        return defReturn;
    }

    public String getWeaponDmgType() {
        String dmgType = "";
        if (stats.weapon != null) {
            dmgType = stats.weapon.damageType;
        } else {
            dmgType = "Bashing";
        }
        return dmgType;
    }

    public void addMoney(int money) {
        stats.money += money;
    }
    public void subtractMoney(int money){ stats.money -= money;}

    public void getPlayerImage() {
        standFront = setUp("/player/RaidouFront");
        standLeft = setUp("/player/RaidouLeft");
        standRight = setUp("/player/RaidouRight");
        standBack = setUp("/player/RaidouBack");
        walkDown1 = setUp("/player/RaidouFrontWalk1");
        walkDown2 = setUp("/player/RaidouFrontWalk2");
        walkLeft1 = setUp("/player/RaidouLeftWalk1");
        walkLeft2 = setUp("/player/RaidouLeftWalk2");
        walkRight1 = setUp("/player/RaidouRightWalk1");
        walkRight2 = setUp("/player/RaidouRightWalk2");
        walkUp1 = setUp("/player/RaidouBackWalk1");
        walkUp2 = setUp("/player/RaidouBackWalk2");
    }

    public void update() {

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.zPressed) {

            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }


            // CHECKEA LA COLISION DE TILES
            collisionOn = false;
            gp.cCheck.checkTile(this);

            //CHECK COLISION OBJETOS
            int objIndex = gp.cCheck.checkObject(this, true);
            ObjectInteractions(objIndex);
            pickUpObject(objIndex);

            //Colision de NPC
            int npcIndex = gp.cCheck.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //COLISION CON EVENTOS
            gp.eventHandler.checkEvent();

            //COLISION CON MOBS
            int mobIndex = gp.cCheck.checkEntity(this, gp.monsters);
            contactMonster(mobIndex);


            if (!collisionOn && !keyH.zPressed) {

                switch (direction) {
                    case "up" -> WorldY -= speed;
                    case "down" -> WorldY += speed;
                    case "left" -> WorldX -= speed;
                    case "right" -> WorldX += speed;
                }
            }
            //Despues de tod0 para actualizar estado
            gp.keyH.zPressed = false;

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

    }

    private void interactNPC(int i) {
        if (i != 999) {
            if (gp.keyH.zPressed) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }

    public void ObjectInteractions(int i) {

        if (i != 999) {

        }
    }

    public void pickUpObject(int i) {
        if (i != 999 && gp.keyH.zPressed && gp.obj[i].isPickupeable) {
            String text = "";
            inventory.add(gp.obj[i]);
            gp.playerSe(1);
            text = "You picked up a " + gp.obj[i].name + "!";
            gp.ui.addMessage(text);
            gp.obj[i] = null;
        }
    }


    public void contactMonster(int i) {
        if (i != 999) {
            shadowStandar shadow = (shadowStandar) gp.monsters[i];
            //Cambio a Combate
            gp.battleSystem = new BattleSystem(gp.party, shadow, gp);
            gp.gameState = gp.combatState;
            gp.monsters[i] = null;
        }
    }

    public void enemyContactPlayer(shadowStandar shadow) {
        //Cambio a Combate
        gp.battleSystem = new BattleSystem(gp.party, shadow, gp);
        gp.gameState = gp.combatState;
    }

    public void getOldStats() {
        keyH.oldStr = gp.player.stats.str;
        keyH.oldDex = gp.player.stats.vit;
        keyH.oldMag = gp.player.stats.mag;
        keyH.oldAgi = gp.player.stats.agi;
    }

    public void levelUp() {
        keyH.pointsPerLevel = keyH.pointsPerLevel + 3;
        getOldStats();
        gp.gameState = gp.levelUpState;
        stats.level++;
        stats.nextLevelExp = stats.nextLevelExp * 2;
    }

    @Override
    public void draw(Graphics2D graficos2d) {

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
        graficos2d.drawImage(image, screenX, screenY, null);

    }

}
