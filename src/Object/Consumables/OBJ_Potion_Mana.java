package Object.Consumables;

import entity.Entity;
import entity.Player;
import main.GamePanel;
import monster.shadowStandar;

/**
 * Represents a Mana Potion object in the game.
 * This potion can be used to restore mana points (mp) to the entity using it.
 */
public class OBJ_Potion_Mana extends Entity {

    /**
     * Creates a Mana Potion object.
     *
     * @param gp The GamePanel instance.
     */
    public OBJ_Potion_Mana(GamePanel gp) {
        super(gp);
        name = "Mana Potion";
        collision = true;
        isPickupeable = true;
        type = 5;
        walkDown1 = setUp("/Objects/manaPotion");
        description = "Mana Potion heals +10 mp";
    }

    /**
     * Uses the Mana Potion to restore mana points to the entity.
     *
     * @param entity The entity using the Mana Potion.
     */
    public void battleUse(Entity entity) {
        if (entity instanceof Player healeablePlayer) {
            gp.playerSe(4);
            healeablePlayer.stats.mp += 10;
            System.out.println(healeablePlayer.name + " used a mana potion");
            System.out.println(healeablePlayer.stats.mp);
            if (healeablePlayer.stats.mp > healeablePlayer.stats.maxMp) {
                healeablePlayer.stats.mp = healeablePlayer.stats.maxMp;
            }
        }
        if (entity instanceof shadowStandar healeableMonster) {
            gp.playerSe(4);
            healeableMonster.stats.mp += 10;
            System.out.println(healeableMonster.name + " used a mana potion");
            System.out.println(healeableMonster.stats.mp);
            if (healeableMonster.stats.mp > healeableMonster.stats.maxMp) {
                healeableMonster.stats.mp = healeableMonster.stats.maxMp;
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
            healeablePlayer.stats.mp += 10;
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You used a health potion";
            if (healeablePlayer.stats.mp > healeablePlayer.stats.maxMp) {
                healeablePlayer.stats.mp = healeablePlayer.stats.maxMp;
            }
        }
        if (entity instanceof shadowStandar healeableMonster) {
            gp.playerSe(4);
            healeableMonster.stats.mp += 10;
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You used a health potion";
            if (healeableMonster.stats.mp > healeableMonster.stats.maxMp) {
                healeableMonster.stats.mp = healeableMonster.stats.maxMp;
            }
        }
    }
}
