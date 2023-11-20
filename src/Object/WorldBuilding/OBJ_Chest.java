package Object.WorldBuilding;

import entity.Entity;
import main.GamePanel;

/**
 * Represents a chest object in the game, extending the Entity class.
 * This class defines attributes and behavior for a chest, including its name, collision, image, and pick-up ability.
 */
public class OBJ_Chest extends Entity {

    GamePanel gp;

    /**
     * Creates a chest object with specific attributes.
     *
     * @param gp The GamePanel instance.
     */
    public OBJ_Chest(GamePanel gp) {

        super(gp);

        name = "chest";
        collision = true;
        walkDown1 = setUp("/Objects/Cofre");
        isPickupeable = true;

    }
}
