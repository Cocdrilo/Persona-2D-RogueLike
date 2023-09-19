package Object;

import entity.Entity;
import entity.Player;
import main.GamePanel;

public class OBJ_Potion_Health extends Entity {

    public OBJ_Potion_Health(GamePanel gp) {
        super(gp);
        collision = true;
        isPickupeable = true;
        type = 5;
        walkDown1 = setUp("/Objects/Potion_Health");
        description = "Health Potion +5 hp";
    }

    public void use(Entity entity){
        entity.stats.hp += 10;
        gp.gameState =gp.dialogueState;
        gp.ui.currentDialogue = "You used a health potion";
        if(entity.stats.hp > entity.stats.maxHp){
            entity.stats.hp = entity.stats.maxHp;
        }
    }
}
