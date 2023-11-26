package Object.Equipables;

import main.GamePanel;

/**
 * Represents a bashing-type weapon in the game, extending the OBJ_Weapon class.
 * This specific weapon class sets attributes for a bashing weapon, such as name, damage type, attack power, hit accuracy, and price.
 */
public class OBJ_WEAPON_Bash extends OBJ_Weapon {

    /**
     * Creates a bashing weapon with specific attributes.
     *
     * @param gp The GamePanel instance.
     */
    public OBJ_WEAPON_Bash(GamePanel gp) {
        super(gp);
        name = "Bashing Weapon";
        damageType = "Bashing";
        atk = 42;
        hit = 85;
        price = 60;
        walkDown1 = setUp("/Objects/weapon_Maza",gp.tileSize,gp.tileSize);
        setUpDescription();
    }

    //IDEA PARA GUARDAR ARMAS CONTUNDENTES, HACER UN ARRAY DE ESPADAS QUE TOME DE UN TXT LOS DATOS PARA CREAR BASH WEAPONS

}
