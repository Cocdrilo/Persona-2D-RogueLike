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

/**
 * The Player class represents the main character in the game.
 */
public class Player extends Entity {

    public final int screenX;
    public final int screenY;

    KeyHandler keyH;
    public Entity_stats stats;

    //Inventario del jugador
    public ArrayList<Entity> inventory = new ArrayList<>();

    /**
     * Constructs a new Player object.
     *
     * @param gp   The GamePanel object associated with the player.
     * @param keyH The KeyHandler object for handling player input.
     */
    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        setPlayerSolidArea();
        getPlayerImage();
        setDefaultValues();
        fillSpells(addDefaultSpells());
    }


    private void setPlayerSolidArea(){
        solidArea = new Rectangle(15, 20, 32, 64);
        solidAreaDefaultX = solidArea.x;
        SolidAreaDefaultY = solidArea.y;
    }

    /**
     * Loads the player character images for different directions and movements.
     */
    public void getPlayerImage() {
        standFront = setUp("/player/NuevoPlayer/Front", (int) (gp.tileSize * 1.5), gp.tileSize * 2);
        standLeft = setUp("/player/NuevoPlayer/Left", (int) (gp.tileSize * 1.5), gp.tileSize * 2);
        standRight = setUp("/player/NuevoPlayer/Right", (int) (gp.tileSize * 1.5), gp.tileSize * 2);
        standBack = setUp("/player/NuevoPlayer/Back", (int) (gp.tileSize * 1.5), gp.tileSize * 2);
        walkDown1 = setUp("/player/NuevoPlayer/Front2", (int) (gp.tileSize * 1.5), gp.tileSize * 2);
        walkDown2 = setUp("/player/NuevoPlayer/Front3", (int) (gp.tileSize * 1.5), gp.tileSize * 2);
        walkLeft1 = setUp("/player/NuevoPlayer/Left2", (int) (gp.tileSize * 1.5), gp.tileSize * 2);
        walkLeft2 = setUp("/player/NuevoPlayer/Left3", (int) (gp.tileSize * 1.5), gp.tileSize * 2);
        walkRight1 = setUp("/player/NuevoPlayer/Right2", (int) (gp.tileSize * 1.5), gp.tileSize * 2);
        walkRight2 = setUp("/player/NuevoPlayer/Right3", (int) (gp.tileSize * 1.5), gp.tileSize * 2);
        walkUp1 = setUp("/player/NuevoPlayer/Back2", (int) (gp.tileSize * 1.5), gp.tileSize * 2);
        walkUp2 = setUp("/player/NuevoPlayer/Back3", (int) (gp.tileSize * 1.5), gp.tileSize * 2);
    }

    /**
     * Sets default values for the player character.
     */
    public void setDefaultValues() {
        setPlayer_Speed_Name_DefDirection();
        setPlayerBaseStats();
        setPlayerDefaultWeapon_Armor();
        setPlayerBattleDebilities();
    }

    private void setPlayer_Speed_Name_DefDirection(){
        speed = 4;
        direction = "down";
        name = "Raidou";
    }

    private void setPlayerBaseStats(){
        stats = new Entity_stats();
        stats.level = 1;
        stats.maxHp = 190;
        stats.hp = stats.maxHp;
        stats.maxMp = 41;
        stats.mp = stats.maxMp;
        stats.str = 7;
        stats.agi = 3;
        stats.mag = 2;
        stats.vit = 3;
        stats.exp = 0;
        stats.nextLevelExp = 10;
        stats.money = 50;
    }
    private void setPlayerDefaultWeapon_Armor(){
        stats.weapon = new OBJ_WEAPON_Slash(gp);
        stats.armor = new OBJ_Armor(gp);
        addDefaultPlayerItemsToInventory();
    }

    private void setPlayerBattleDebilities(){
        resistances = new String[]{};
        weaknesses = new String[]{};
        nulls = new String[]{};
        repells = new String[]{};
    }

    /**
     * Sets the initial items in the player's inventory.
     */
    public void addDefaultPlayerItemsToInventory() {

        inventory.add(stats.weapon);
        inventory.add(stats.armor);

    }

    private String[] addDefaultSpells(){
        return new String[]{"Zio", "Agi", "Fatal End"};
    }
    /**
     * Sets a random position for the player on the game map.
     */
    public void setRandomPos() {
        int[] datos = gp.tileM.setPlayerRandomPosition();
        WorldX = datos[0] * gp.tileSize;
        WorldY = datos[1] * gp.tileSize;
    }

    /**
     * Returns the index of the weapon in the player's inventory.
     *
     * @return The index of the weapon, or -1 if not found.
     */
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

    /**
     * Returns the index of the armor in the player's inventory.
     *
     * @return The index of the armor, or -1 if not found.
     */
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

    /**
     * Prints the names of consumable items in the player's inventory.
     *
     * @return An array of consumable item names.
     */
    public String[] printItems() {
        String[] Items = new String[inventory.size()];
        int ItemsIndex = 0;
        for (Entity entity : inventory) {
            if (entity.type == 5) {
                Items[ItemsIndex] = entity.name;
                ItemsIndex++;
            }
        }
        String[] consumableItems = new String[ItemsIndex];
        System.arraycopy(Items, 0, consumableItems, 0, ItemsIndex);
        return consumableItems;
    }

    /**
     * Returns a list of consumable items in the player's inventory.
     *
     * @return ArrayList of consumable items.
     */
    public ArrayList<Entity> getItems() {
        ArrayList<Entity> Items = new ArrayList<>();
        for (Entity entity : inventory) {
            if (entity.type == 5) {
                Items.add(entity);
            }
        }
        return Items;
    }

    /**
     * Saves the indexes of consumable items in the player's inventory.
     *
     * @return An array of indexes of consumable items.
     */
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
        if (itemCounter >= 0) System.arraycopy(itemIndexes, 0, consumableItemsIndex, 0, itemCounter);
        return consumableItemsIndex;
    }

    /**
     * Selects and uses items from the player's inventory.
     */
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
            if (selectedItem instanceof OBJ_Chest cofre) {
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

    /**
     * Returns the total defense value of the player, taking armor into account.
     *
     * @return The total defense value.
     */
    public int getDefense() {
        int defReturn = 0;
        if (stats.armor != null) {
            defReturn = stats.agi + stats.armor.def;
        } else {
            defReturn = stats.agi;
        }
        return defReturn;
    }

    /**
     * Returns the damage type of the player's equipped weapon.
     *
     * @return The damage type of the weapon.
     */
    public String getWeaponDmgType() {
        String dmgType = "";
        if (stats.weapon != null) {
            dmgType = stats.weapon.damageType;
        } else {
            dmgType = "Bashing";
        }
        return dmgType;
    }

    /**
     * Adds money to the player's currency.
     *
     * @param money The amount of money to add.
     */
    public void addMoney(int money) {
        stats.money += money;
    }

    /**
     * Subtracts money from the player's currency.
     *
     * @param money The amount of money to subtract.
     */
    public void subtractMoney(int money) {
        stats.money -= money;
    }

    /**
     * Updates the player's position and handles interactions based on user input.
     */
    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.zPressed) {
            directionSwapperOnPress();
            checkAllCollisionsPlayer();
            if (!collisionOn && !keyH.zPressed) {
                moveNoCollision();
            }
            spriteCounterUpdater();
        }
    }

    private void checkAllCollisionsPlayer(){
        collisionOn = false;
        checkCollisionTile();
        checkObjectCollision();
        checkNPCCollision();
        checkEventCollision();
        checkEnemyMonsterCollision();
    }

    private void checkCollisionTile(){
        gp.cCheck.checkTile(this);
    }

    private void checkObjectCollision(){
        int objIndex = gp.cCheck.checkObject(this, true);
        ObjectInteractions(objIndex);
        pickUpObject(objIndex);
    }
    private void checkNPCCollision(){
        int npcIndex = gp.cCheck.checkEntity(this, gp.npc);
        interactNPC(npcIndex);
    }
    private void checkEventCollision(){
        gp.eventHandler.checkEvent();
    }
    private void checkEnemyMonsterCollision(){
        int mobIndex = gp.cCheck.checkEntity(this, gp.monsters);
        contactMonster(mobIndex);
    }

    private void directionSwapperOnPress(){
        if (keyH.upPressed) {
            direction = "up";
        } else if (keyH.downPressed) {
            direction = "down";
        } else if (keyH.leftPressed) {
            direction = "left";
        } else if (keyH.rightPressed) {
            direction = "right";
        }
    }

    private void moveNoCollision(){
        switch (direction) {
            case "up" -> WorldY -= speed;
            case "down" -> WorldY += speed;
            case "left" -> WorldX -= speed;
            case "right" -> WorldX += speed;
        }
    }

    private void spriteCounterUpdater(){
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

    /**
     * Handles NPC interactions when the "Z" key is pressed.
     *
     * @param i The index of the interacting NPC.
     */
    private void interactNPC(int i) {
        if (i != 999) {
            if (gp.keyH.zPressed) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }

    /**
     * Handles interactions with objects in the game world.
     *
     * @param i The index of the interacting object.
     */
    public void ObjectInteractions(int i) {

        if (i != 999 && gp.keyH.zPressed && gp.obj[i].name.equals("stairs")) {
            gp.stopMusic();
            gp.gameState = gp.endScreenState;
        }
    }

    /**
     * Picks up objects in the game world when the "Z" key is pressed.
     *
     * @param i The index of the object to pick up.
     */
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


    /**
     * Initiates combat when the player contacts a monster.
     *
     * @param i The index of the contacting monster.
     */
    public void contactMonster(int i) {
        if (i != 999) {
            shadowStandar shadow = (shadowStandar) gp.monsters[i];
            //Cambio a Combate
            gp.battleSystem = new BattleSystem(gp.party, shadow, gp);
            gp.gameState = gp.combatState;
            gp.monsters[i] = null;
        }
    }

    /**
     * Initiates combat when an enemy contacts the player.
     *
     * @param shadow The enemy contacting the player.
     */
    public void enemyContactPlayer(shadowStandar shadow) {
        //Cambio a Combate
        gp.battleSystem = new BattleSystem(gp.party, shadow, gp);
        gp.gameState = gp.combatState;
    }

    /**
     * Records the player's old statistics for leveling up.
     */
    public void getOldStats() {
        keyH.oldStr = gp.player.stats.str;
        keyH.oldDex = gp.player.stats.vit;
        keyH.oldMag = gp.player.stats.mag;
        keyH.oldAgi = gp.player.stats.agi;
    }

    /**
     * Initiates the leveling up process for the player character.
     */
    public void levelUp() {
        keyH.pointsPerLevel = keyH.pointsPerLevel + 3;
        getOldStats();
        gp.gameState = gp.levelUpState;
        stats.level++;
        stats.nextLevelExp = stats.nextLevelExp * 2;
    }

    /**
     * Draws the player character on the screen.
     *
     * @param graficos2d The Graphics2D object for drawing.
     */
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
