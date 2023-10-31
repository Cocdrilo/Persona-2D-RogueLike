package Object.WorldBuilding;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Stairs extends Entity {

    GamePanel gp;

    public OBJ_Stairs(GamePanel gp) {
        super(gp);

        name = "stairs";
        walkDown1 = setUp("/Objects/Stairs");
        collision = false;
    }
}

