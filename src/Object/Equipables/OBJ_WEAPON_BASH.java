package Object.Equipables;

import Object.Equipables.OBJ_Weapon;
import main.GamePanel;

public class OBJ_WEAPON_BASH extends OBJ_Weapon {
    public OBJ_WEAPON_BASH(GamePanel gp) {
        super(gp);
        name = "Bashing Weapon";
        damageType = "Bashing";
        atk = 42;
        hit = 85;
        price = 60;
        walkDown1 = setUp("/Objects/weapon_Maza");
        setUpDescription();
    }

    //IDEA PARA GUARDAR ARMAS CONTUNDENTES, HACER UN ARRAY DE ESPADAS QUE TOME DE UN TXT LOS DATOS PARA CREAR BASH WEAPONS

}