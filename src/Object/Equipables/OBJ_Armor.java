package Object.Equipables;

import entity.Entity;
import main.GamePanel;

public class OBJ_Armor extends Entity {

    public int def = 1;

    public OBJ_Armor(GamePanel gp) {
        super(gp);
        name = "Cota de malla";
        walkDown1 = setUp("/Objects/Armor");

    }
}
