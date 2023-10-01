package Object.Equipables;

import entity.Entity;
import main.GamePanel;

public class OBJ_Weapon extends Entity {

    public String damageType;
    public int atk;
    public int hit;
    public int price;

    public OBJ_Weapon(GamePanel gp) {
        super(gp);
        collision = true;
        isPickupeable = true;
        type = 3;
    }
    public void setUpDescription(){
        description = "["+ name +" ]\nAtk: "+atk+ " \nHit: " + hit + "\n Tipo de DMG: " + damageType;
    }


}
