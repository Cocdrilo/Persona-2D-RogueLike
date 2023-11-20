package Object.Consumables;

import entity.Entity;
import main.GamePanel;

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
    public void use(Entity entity) {
        entity.stats.mp += 10;
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You used a mana potion";
        if (entity.stats.mp > entity.stats.maxMp) {
            entity.stats.mp = entity.stats.maxMp;
        }
    }
}
