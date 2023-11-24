package Object.WorldBuilding;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Represents a stairs object in the game, extending the Entity class.
 * This class defines attributes and behavior for stairs, including its name, image, and collision property.
 */
public class OBJ_Stairs extends Entity {

    GamePanel gp;

    /**
     * Creates a stairs object with specific attributes.
     *
     * @param gp The GamePanel instance.
     */
    public OBJ_Stairs(GamePanel gp) {
        super(gp);

        name = "stairs";
        walkDown1 = setUp("/Objects/Stairs",gp.tileSize,gp.tileSize);
        collision = false;
    }
}

