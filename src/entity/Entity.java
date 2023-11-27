package entity;

import battleNeeds.superMagic;
import main.BattleSystem;
import main.GamePanel;
import main.KeyHandler;
import main.Toolbox;
import monster.shadowStandar;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * The abstract base class for all entities in the game. Entities are objects that exist
 * in the game world, such as players, NPCs, monsters, and items.
 * <p>
 * This class implements the Drawable interface, providing a method for drawing entities on
 * the game panel.
 */
public abstract class Entity implements Drawable {

    public GamePanel gp;

    //SOLID AREAS
    public Rectangle solidArea = new Rectangle(1, 1, 47, 47);
    public int solidAreaDefaultX, SolidAreaDefaultY;
    //DIALOGOS

    public String dialogues[] = new String[20];
    public int dialogueIndex = 0;

    //ESTADOS DEL JUEGO
    public int WorldX, WorldY;
    public String direction = "down";
    public int spriteNum = 1;
    public boolean collisionOn = false;
    public boolean onPath = false;


    //ATRIBUTOS DE PERSONAJES
    public int speed;

    public String name;
    public int type; // 0 = player, 1 = npc, 2 = mosnter 3 = equipableSword 4 = equipableArmor 5 = consumableItem
    public Entity_stats stats;
    public String description = "";
    public boolean defending = false;
    public boolean isPickupeable = false;

    //Maps de Resistencias y Debilidades.
    public String resistances[];
    public String weaknesses[];
    public String nulls[];
    public String repells[];

    //Sprites
    //CONTADORES
    public int spriteCounter = 0;
    public int actionLockCounter = 0;

    //IMAGE DATA
    public BufferedImage image;
    public boolean collision = false;
    public BufferedImage standFront, standLeft, standBack, standRight, walkDown1, walkDown2, walkLeft1, walkLeft2, walkRight1, walkRight2, walkUp1, walkUp2;

    //Array de hechizos:
    public ArrayList<superMagic> spells;
    public boolean isVisible = true;

    /**
     * Constructs a new Entity with the specified GamePanel.
     *
     * @param gp The GamePanel instance associated with this entity.
     */

    public Entity(GamePanel gp) {

        this.gp = gp;
        stats = new Entity_stats();
    }

    /**
     * Adds Spells by name receibed
     */

    public void fillSpells(String[] spellNames) {
        spells = new ArrayList<>();

        ArrayList<superMagic> availableSpells = gp.spellManager.getSpells();
        for (superMagic spell : availableSpells) {
            for (String spellName : spellNames) {
                if (spell.name.equals(spellName)) {
                    addSpell(spell);
                    break; // Exit the inner loop when a match is found
                }
            }
        }
    }

    //CombatMethodss

    /**
     * Checks inCombat if a weakness has been hitted
     */

    public boolean isWeak(String attckType) {
        for (String weakness : weaknesses) {
            if (Objects.equals(attckType, weakness)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks inCombat if a resistance has been hitted
     */

    public boolean isResistant(String attckType) {
        for (String resistance : resistances) {
            if (Objects.equals(attckType, resistance)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks inCombat if attack has been nulled
     */

    public boolean isNull(String attckType) {
        for (String aNull : nulls) {
            if (Objects.equals(attckType, aNull)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks inCombat if attack was repelled
     */

    public boolean isRepelled(String attckType) {
        for (String repel : repells) {
            if (Objects.equals(attckType, repel)) {
                return true;
            }
        }
        return false;
    }

    //DMG = 5 x sqrt(ST/EN x ATK) x MOD x HITS X RND
    //
    //DMG = Damage
    //ST = Character's Strength stat
    //EN = Enemy's Endurance stat
    //ATK = Atk value of equipped weapon OR Pwr value of used skill
    //HITS= Number of hits (for physical skills)
    //RND = Randomness factor (according to DragoonKain33, may be roughly between
    //0.95 and 1.05)

    /**
     * Generates a random factor between 0.85 and 1.20.
     */

    public double randomFactor() {
        double minFactor = 0.85;
        double maxFactor = 1.20;

        // Crear una instancia de la clase Random
        Random random = new Random();

        // Generar un valor aleatorio entre minFactor y maxFactor

        return minFactor + (maxFactor - minFactor) * random.nextDouble();
    }

    /**
     * Calculates the damage of a physical attack.
     *
     * @param monsterEndurance
     * @param physDmg
     * @param attackerStat
     */

    public int getPhysAttack(int monsterEndurance, int physDmg, int attackerStat) {
        return 5 * (int) (Math.sqrt(((double) attackerStat / monsterEndurance) * Math.sqrt(physDmg) * randomFactor()));
    }

    /**
     * Calculates the damage of a magical attack.
     *
     * @param monsterEndurance
     * @param spellDmg
     * @param attackMagicStat
     */

    public int getMagicAttack(int monsterEndurance, int spellDmg, int attackMagicStat) {
        return 5 * (int) (Math.sqrt(((double) attackMagicStat / monsterEndurance) * Math.sqrt(spellDmg) * randomFactor()));
    }


    /**
     * Retrieves the defense stat.
     *
     * @return The defense stat.
     */
    public int getDefense() {
        return stats.vit;
    }
    //Spell Methods

    // Métodos para agregar, quitar y acceder a hechizos del jugador

    /**
     * Adds a spell to the entity's spell list.
     *
     * @param spell The spell to be added.
     */
    public void addSpell(superMagic spell) {
        spells.add(spell);
    }

    /**
     * Prints the names of spells available to the entity.
     *
     * @return An array of spell names.
     */
    public String[] printSpells() {
        String[] spellNames = new String[this.spells.size()];
        for (int spells = 0; spells < this.spells.size(); spells++) {
            spellNames[spells] = this.spells.get(spells).name + "  " + (this.spells.get(spells).mpCost == 0 ? "hp: " + this.spells.get(spells).hpCost + "%" : "mp: " + this.spells.get(spells).mpCost);
        }
        return spellNames;
    }

    /**
     * Returns an array of spell costs.
     *
     * @return The array of spell costs.
     */
    public String[] getCosts() {
        String[] spellCosts = new String[this.spells.size()];
        for (int spells = 0; spells < this.spells.size(); spells++) {
            spellCosts[spells] = (this.spells.get(spells).mpCost == 0 ? "HP: " + this.spells.get(spells).hpCost : "MP: " + this.spells.get(spells).mpCost);
        }
        return spellCosts;
    }

    /**
     * Removes a spell from the entity's spell list.
     *
     * @param spell The spell to be removed.
     */
    public void removeSpell(superMagic spell) {
        spells.remove(spell);
    }

    /**
     * Retrieves the list of spells available to the entity.
     *
     * @return The list of spells.
     */
    public ArrayList<superMagic> getSpells() {
        return spells;
    }

    /**
     * Sets the action of the entity.
     */
    public void setAction() {

    }

    /**
     * Updates the state of the entity.
     */
    public void update() {
        setAction();
        checkCollisiOn();

        //COLISON = FALSO ->PUEDE MOVER
        if (!collisionOn) {

            switch (direction) {
                case "up" -> WorldY -= speed;
                case "down" -> WorldY += speed;
                case "left" -> WorldX -= speed;
                case "right" -> WorldX += speed;
            }
        }

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
     * Checks for collisions and updates the collision state.
     */
    public void checkCollisiOn() {
        collisionOn = false;
        gp.cCheck.checkTile(this);
        gp.cCheck.checkObject(this, false);
        gp.cCheck.checkEntity(this, gp.npc);
        gp.cCheck.checkEntity(this, gp.monsters);
        boolean contactPlayer = gp.cCheck.checkPlayer(this);
    }

    /**
     * Searches for a path to the specified goal position.
     *
     * @param goalCol The column of the goal position.
     * @param goalRow The row of the goal position.
     */
    public void searchPath(int goalCol, int goalRow) {

        int startCol = (WorldX + solidArea.x) / gp.tileSize;
        int startRow = (WorldY + solidArea.y) / gp.tileSize;

        gp.pathFinder.setNode(startCol, startRow, goalCol, goalRow);
        if (gp.pathFinder.search()) {
            //Next WorldX&&Next WorldY
            int nextX = gp.pathFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pathFinder.pathList.get(0).row * gp.tileSize;
            //SolidAreaPosition
            int entityLeftX = WorldX + solidArea.x;
            int entityTopY = WorldY + solidArea.y;
            int entityRightX = WorldX + solidArea.x + solidArea.width;
            int entityBottomY = WorldY + solidArea.y + solidArea.height;

            if (entityTopY > nextY && entityLeftX >= nextX && entityRightX < nextX + gp.tileSize) {
                direction = "up";
            } else if (entityTopY < nextY && entityLeftX >= nextX && entityRightX < nextX + gp.tileSize) {
                direction = "down";
            } else if (entityTopY >= nextY && entityBottomY < nextY + gp.tileSize) {
                //Left | right
                if (entityLeftX > nextX) {
                    direction = "left";
                }
                if (entityLeftX < nextX) {
                    direction = "right";
                }
            } else if (entityTopY > nextY && entityLeftX > nextX) {
                //up or left
                direction = "up";
                checkCollisiOn();
                if (collisionOn) {
                    direction = "left";
                }
            } else if (entityTopY > nextY && entityLeftX < nextX) {
                //Up or right
                direction = "up";
                checkCollisiOn();
                if (collisionOn) {
                    direction = "right";
                }
            } else if (entityTopY < nextY && entityLeftX > nextX) {
                direction = "down";
                checkCollisiOn();
                if (collisionOn) {
                    direction = "left";
                }
            } else if (entityTopY < nextY && entityLeftX < nextX) {
                direction = "down";
                checkCollisiOn();
                if (collisionOn) {
                    direction = "right";
                }
            } else {
                //System.out.println("No se que hacer");
            }
        }

        /*
        int nextCol = gp.pathFinder.pathList.get(0).col;
        int nextRow = gp.pathFinder.pathList.get(0).row;
        if(nextCol == goalCol && nextRow == goalRow){
            onPath = false;
        }

         */

    }

    /**
     * Sets up the entity's image based on the specified image path, width, and height.
     *
     * @param ImagePath The image path.
     * @param width     The desired width of the image.
     * @param height    The desired height of the image.
     *                  * @return The scaled image.
     */
    public BufferedImage setUp(String ImagePath, int width, int height) {
        Toolbox tbox = new Toolbox();
        BufferedImage scaledImage = null;

        try {
            scaledImage = ImageIO.read(getClass().getResourceAsStream(ImagePath + ".png"));
            scaledImage = tbox.scaleImage(scaledImage, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }

    /**
     * Initiates a conversation with the entity.
     */
    public void speak() {
        gp.ui.currentName = name;
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        //HACE QUE EL NPC MIRE AL JUGADOR CUANDO LE HABLAS

        switch (gp.player.direction) {
            case "up" -> direction = "down";
            case "down" -> direction = "up";
            case "left" -> direction = "right";
            case "right" -> direction = "left";
        }

    }

    /**
     * Performs an action when the entity is used in the overworld.
     *
     * @param entity The entity to interact with.
     */
    public void overWorldUse(Entity entity) {

    }

    /**
     * Performs an action when the entity is used in battle.
     *
     * @param entity The entity to interact with in battle.
     */
    public void battleUse(Entity entity) {

    }

    /**
     * Draws the entity on the graphics context.
     *
     * @param g2 The graphics context.
     */
    @Override
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int ScreenX = WorldX - gp.player.WorldX + gp.player.screenX;
        int ScreenY = WorldY - gp.player.WorldY + gp.player.screenY;

        if (WorldX + gp.tileSize > gp.player.WorldX - gp.player.screenX && WorldX - gp.tileSize < gp.player.WorldX + gp.player.screenX && WorldY + gp.tileSize > gp.player.WorldY - gp.player.screenY && WorldY - gp.tileSize < gp.player.WorldY + gp.player.screenX) {

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
            g2.drawImage(image, ScreenX, ScreenY, image.getWidth(), image.getHeight(), null);
        }
    }

}
