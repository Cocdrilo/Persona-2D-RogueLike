package Object.WorldBuilding;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door extends Entity {

    GamePanel gp;

    public OBJ_Door(GamePanel gp) {
        super(gp);

        name = "door";
        walkDown1 = setUp("/Objects/PuertaCerrada");
        collision = true;
    }
}

