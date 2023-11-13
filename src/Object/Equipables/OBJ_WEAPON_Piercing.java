package Object.Equipables;

import Object.Equipables.OBJ_Weapon;
import main.GamePanel;

/**
 * Represents a piercing-type weapon in the game, extending the OBJ_Weapon class.
 * This specific weapon class sets attributes for a piercing weapon, such as name, damage type, attack power, hit accuracy, price, and description.
 */
public class OBJ_WEAPON_Piercing extends OBJ_Weapon {

    /**
     * Creates a piercing weapon with specific attributes.
     *
     * @param gp The GamePanel instance.
     */
    public OBJ_WEAPON_Piercing(GamePanel gp) {
        super(gp);
        name = "Piercing Weapon";
        damageType = "Piercing";
        atk = 42;
        hit = 90;
        price = 80;
        description = "[" + name + " ]\nAtk: " + atk + " \nHit: " + hit + "\n Tipo de DMG: " + damageType;
    }
}
