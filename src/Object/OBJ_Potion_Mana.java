package Object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Mana extends Entity {

    public OBJ_Potion_Mana(GamePanel gp) {
        super(gp);
        name = "Mana Potion";
        collision = true;
        isPickupeable = true;
        type = 5;
        walkDown1 = setUp("/Objects/manaPotion");
        description = "Mana Potion heals +10 mp";
    }

    public void use(Entity entity){
        entity.stats.mp += 10;
        gp.gameState =gp.dialogueState;
        gp.ui.currentDialogue = "You used a mana potion";
        if(entity.stats.mp > entity.stats.maxMp){
            entity.stats.mp = entity.stats.maxMp;
        }
    }
}
