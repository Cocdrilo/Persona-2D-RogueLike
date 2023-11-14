package Object.Equipables;

import entity.Entity;
import main.GamePanel;

/**
 * Represents a weapon object in the game.
 * This class extends the Entity class and includes attributes related to the weapon's statistics.
 */
public class OBJ_Weapon extends Entity {

    public String damageType;
    public int atk;
    public int hit;
    public int price;

    /**
     * Creates a weapon object with specific attributes.
     *
     * @param gp The GamePanel instance.
     */
    public OBJ_Weapon(GamePanel gp) {
        super(gp);
        collision = true;
        isPickupeable = true;
        type = 3;
    }

    /**
     * Sets up the description for the weapon, including its name, attack power, hit accuracy, and damage type.
     */
    public void setUpDescription() {
        description = "[" + name + " ]\nAtk: " + atk + " \nHit: " + hit + "\n Tipo de DMG: " + damageType;
    }


}
