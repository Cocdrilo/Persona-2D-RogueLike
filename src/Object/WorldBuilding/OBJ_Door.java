package Object.WorldBuilding;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Represents a door object in the game, extending the Entity class.
 * This class defines attributes and behavior for a door, including its name, image, collision, and interaction.
 */
public class OBJ_Door extends Entity {

    GamePanel gp;

    /**
     * Creates a door object with specific attributes.
     *
     * @param gp The GamePanel instance.
     */
    public OBJ_Door(GamePanel gp) {
        super(gp);

        name = "door";
        walkDown1 = setUp("/Objects/PuertaCerrada");
        collision = true;
    }
}

