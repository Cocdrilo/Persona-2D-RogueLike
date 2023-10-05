package Object.Equipables;

import Object.Equipables.OBJ_Weapon;
import main.GamePanel;

public class OBJ_WEAPON_Piercing extends OBJ_Weapon {
    public OBJ_WEAPON_Piercing(GamePanel gp) {
        super(gp);
        name = "Piercing Weapon";
        damageType = "Piercing";
        atk = 42;
        hit = 90;
        price = 80;
        description = "["+ name +" ]\nAtk: "+atk+ " \nHit: " + hit + "\n Tipo de DMG: " + damageType;
    }
}
