package Object.Equipables;

import Object.Equipables.OBJ_Weapon;
import main.GamePanel;

/**
 * Represents a slashing-type weapon in the game, extending the OBJ_Weapon class.
 * This specific weapon class sets attributes for a slashing weapon, such as name, damage type, attack power, hit accuracy, price, and description.
 */
public class OBJ_WEAPON_Slash extends OBJ_Weapon {

    /**
     * Creates a slashing weapon with specific attributes.
     *
     * @param gp The GamePanel instance.
     */
    public OBJ_WEAPON_Slash(GamePanel gp) {
        super(gp);
        name = "Espadon";
        damageType = "Slashing";
        atk = 42;
        hit = 97;
        price = 100;
        walkDown1 = setUp("/Objects/weapon_duel_sword");
        setUpDescription();
    }
}
