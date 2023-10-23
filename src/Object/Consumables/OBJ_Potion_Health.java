package Object.Consumables;

import entity.Entity;
import entity.Player;
import main.GamePanel;
import monster.shadowStandar;

public class OBJ_Potion_Health extends Entity {

    public OBJ_Potion_Health(GamePanel gp) {
        super(gp);
        name = "Health Potion";
        collision = true;
        isPickupeable = true;
        type = 5;
        walkDown1 = setUp("/Objects/healthPotion");
        description = "Health Potion heals +10 hp";
    }

    public void battleUse(Entity entity){
        if(entity instanceof Player healeablePlayer){
            gp.playerSe(4);
            healeablePlayer.stats.hp += 10;
            System.out.println(healeablePlayer.name + " used a health potion");
            System.out.println(healeablePlayer.stats.hp);
            if(healeablePlayer.stats.hp > healeablePlayer.stats.maxHp){
                healeablePlayer.stats.hp = healeablePlayer.stats.maxHp;
            }
        }
        if(entity instanceof shadowStandar healeableMonster){
            gp.playerSe(4);
            healeableMonster.stats.hp += 10;
            System.out.println(healeableMonster.name + " used a health potion");
            System.out.println(healeableMonster.stats.hp);
            if(healeableMonster.stats.hp > healeableMonster.stats.maxHp){
                healeableMonster.stats.hp = healeableMonster.stats.maxHp;
            }
        }
    }

    public void overWorldUse(Entity entity){
        gp.playerSe(4);
        if(entity instanceof Player healeablePlayer){
            gp.playerSe(4);
            healeablePlayer.stats.hp += 10;
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You used a health potion";
            if(healeablePlayer.stats.hp > healeablePlayer.stats.maxHp){
                healeablePlayer.stats.hp = healeablePlayer.stats.maxHp;
            }
        }
        if(entity instanceof shadowStandar healeableMonster){
            gp.playerSe(4);
            healeableMonster.stats.hp += 10;
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You used a health potion";
            if(healeableMonster.stats.hp > healeableMonster.stats.maxHp){
                healeableMonster.stats.hp = healeableMonster.stats.maxHp;
            }
        }
        gp.gameState = gp.playState;
    }

}
