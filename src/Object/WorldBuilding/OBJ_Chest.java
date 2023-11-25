package Object.WorldBuilding;

import entity.Entity;
import main.GamePanel;
import monster.shadowStandar;

/**
 * Represents a chest object in the game, extending the Entity class.
 * This class defines attributes and behavior for a chest, including its name, collision, image, and pick-up ability.
 */
public class OBJ_Chest extends Entity {


    /**
     * Creates a chest object with specific attributes.
     *
     * @param gp The GamePanel instance.
     */
    public OBJ_Chest(GamePanel gp) {

        super(gp);

        name = "chest";
        collision = true;
        walkDown1 = setUp("/Objects/Cofre",gp.tileSize,gp.tileSize);
        isPickupeable = true;
        description = "Chest With Gold Inside should be opened";
    }

    public void use(){
        int goldInChest = (int) (Math.random() * 100);
        gp.playerSe(4);
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "Chest Opened with this gold : " + goldInChest ;
        gp.party.Leader.addMoney(goldInChest);
    }
}
