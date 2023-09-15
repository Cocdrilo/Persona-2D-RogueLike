package Object;

import main.GamePanel;

public class OBJ_WEAPON_Piercing extends OBJ_Weapon{
    public OBJ_WEAPON_Piercing(GamePanel gp) {
        super(gp);
        name = "Piercing Weapon";
        damageType = "pierce";
        atk = 8;
        hit = 90;
        price = 80;
    }
}
