package Object.Equipables;

import entity.Entity;
import main.GamePanel;

/**
 * Represents an armor object in the game.
 * This armor provides a defensive bonus (def) when equipped by an entity.
 */
public class OBJ_Armor extends Entity {

    public int def = 1;

    /**
     * Creates an armor object with a specific name and appearance.
     *
     * @param gp The GamePanel instance.
     */
    public OBJ_Armor(GamePanel gp) {
        super(gp);
        name = "Cota de malla";
        walkDown1 = setUp("/Objects/Armor",gp.tileSize,gp.tileSize);
        description = "[" + name + " ]\nDef: " + def;
    }
}
