package Object;

import main.GamePanel;

public class OBJ_WEAPON_Slash extends OBJ_Weapon{
    public OBJ_WEAPON_Slash(GamePanel gp) {
        super(gp);
        name = "Espadón";
        damageType = "slash";
        atk = 10;
        hit = 97;
        price = 100;
        walkDown1 = setUp("/Objects/weapon_duel_sword");
    }
}
