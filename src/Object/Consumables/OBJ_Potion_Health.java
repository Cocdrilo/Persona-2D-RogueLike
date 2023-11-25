package Object.Consumables;

import entity.Entity;
import entity.Player;
import main.GamePanel;
import monster.shadowStandar;

/**
 * Represents a Health Potion object in the game.
 * This potion can be used in both battle and the overworld to heal the user.
 */
public class OBJ_Potion_Health extends Entity {

    /**
     * Creates a Health Potion object.
     *
     * @param gp The GamePanel instance.
     */
    public OBJ_Potion_Health(GamePanel gp) {
        super(gp);
        name = "Health Potion";
        collision = true;
        isPickupeable = true;
        type = 5;
        walkDown1 = setUp("/Objects/healthPotion",gp.tileSize,gp.tileSize);
        description = "Health Potion heals +10 hp";
    }

    /**
     * Uses the Health Potion in a battle scenario to heal the entity.
     *
     * @param entity The entity using the Health Potion.
     */
    public void battleUse(Entity entity) {
        if (entity instanceof Player healeablePlayer) {
            gp.playerSe(4);
            healeablePlayer.stats.hp += 10;
            System.out.println(healeablePlayer.name + " used a health potion");
            System.out.println(healeablePlayer.stats.hp);
            if (healeablePlayer.stats.hp > healeablePlayer.stats.maxHp) {
                healeablePlayer.stats.hp = healeablePlayer.stats.maxHp;
            }
        }
        if (entity instanceof shadowStandar healeableMonster) {
            gp.playerSe(4);
            healeableMonster.stats.hp += 10;
            System.out.println(healeableMonster.name + " used a health potion");
            System.out.println(healeableMonster.stats.hp);
            if (healeableMonster.stats.hp > healeableMonster.stats.maxHp) {
                healeableMonster.stats.hp = healeableMonster.stats.maxHp;
            }
        }
    }

    /**
     * Uses the Health Potion in the overworld to heal the entity and update game state.
     *
     * @param entity The entity using the Health Potion.
     */
    public void overWorldUse(Entity entity) {
        gp.playerSe(4);
        if (entity instanceof Player healeablePlayer) {
            gp.playerSe(4);
            healeablePlayer.stats.hp += 10;
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You used a health potion";
            if (healeablePlayer.stats.hp > healeablePlayer.stats.maxHp) {
                healeablePlayer.stats.hp = healeablePlayer.stats.maxHp;
            }
        }
        if (entity instanceof shadowStandar healeableMonster) {
            gp.playerSe(4);
            healeableMonster.stats.hp += 10;
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You used a health potion";
            if (healeableMonster.stats.hp > healeableMonster.stats.maxHp) {
                healeableMonster.stats.hp = healeableMonster.stats.maxHp;
            }
        }
    }

}
