package Object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity {

    GamePanel gp;

    public OBJ_Chest(GamePanel gp){

        super(gp);

        name = "chest";
        collision=true;
        walkDown1 = setUp("/Objects/Cofre");


    }
}
