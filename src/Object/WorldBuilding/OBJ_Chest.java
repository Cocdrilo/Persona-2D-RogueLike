package Object.WorldBuilding;

import Object.Consumables.OBJ_Potion_Health;
import Object.Consumables.OBJ_Potion_Mana;
import Object.Equipables.OBJ_WEAPON_Bash;
import Object.Equipables.OBJ_WEAPON_Piercing;
import entity.Entity;
import main.GamePanel;

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
        walkDown1 = setUp("/Objects/Cofre", gp.tileSize, gp.tileSize);
        isPickupeable = true;
        description = "Chest With Loot Inside should be opened";
    }

    public void use() {
        int goldInChest = (int) (Math.random() * 100);
        gp.playerSe(4);
        gp.gameState = gp.dialogueState;

        // Decide si el cofre dará oro o un objeto
        boolean isGold = Math.random() < 0.5;

        if (isGold) {
            gp.ui.currentDialogue = "Chest Opened with this gold: " + goldInChest;
            gp.party.Leader.addMoney(goldInChest);
        } else {
            // Decide qué objeto dar
            int randomItem = (int) (Math.random() * 4);
            String item = "";

            switch (randomItem) {
                case 0 -> {
                    item = "Health Potion";
                    gp.player.inventory.add(new OBJ_Potion_Health(gp));
                }
                case 1 -> {
                    item = "Mana Potion";
                    gp.player.inventory.add(new OBJ_Potion_Mana(gp));
                }
                case 2 -> {
                    item = "Hammer";
                    gp.player.inventory.add(new OBJ_WEAPON_Bash(gp));
                }
                case 3 -> {
                    item = "Rapier";
                    gp.player.inventory.add(new OBJ_WEAPON_Piercing(gp));
                }
            }

            gp.ui.currentDialogue = "Chest Opened with this item: " + item;
            // Aquí debes implementar la lógica para agregar el objeto al inventario del jugador.
            // Puedes modificar gp.party.Leader para tener un método addItemAtInventory(item) o algo similar.
        }
    }
}
