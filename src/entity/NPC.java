package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

/**
 * This class represents a non-playable character (NPC) entity.
 */
public class NPC extends Entity {

    /**
     * Constructs an NPC with the specified GamePanel.
     * Initializes the NPC's name, direction, speed, images, and dialogue.
     *
     * @param gp The GamePanel associated with the NPC.
     */
    boolean unido = false;
    public NPC(GamePanel gp) {
        super(gp);

        name = "Demon";
        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }

    /**
     * Loads NPC images for different directions.
     */
    public void getImage() {
        int i = 1;
        walkDown1 = setUp("/NPC/demon_1",gp.tileSize*i,gp.tileSize*i);
        walkDown2 = setUp("/NPC/demon_2",gp.tileSize*i,gp.tileSize*i);
        walkLeft1 = setUp("/NPC/demon_left_1" ,gp.tileSize*i,gp.tileSize*i);
        walkLeft2 = setUp("/NPC/demon_left_2" ,gp.tileSize*i,gp.tileSize*i);
        walkRight1 = setUp("/NPC/demon_right_1" ,gp.tileSize*i,gp.tileSize*i);
        walkRight2 = setUp("/NPC/demon_right_2" ,gp.tileSize*i,gp.tileSize*i);
        walkUp1 = setUp("/NPC/demon_back_1" ,gp.tileSize*i,gp.tileSize*i);
        walkUp2 = setUp("/NPC/demon_back_2" ,gp.tileSize*i,gp.tileSize*i);

    }

    /**
     * Sets the dialogue for the NPC.
     */
    public void setDialogue() {
        dialogues[0] = "Como? que me una a tu equipo?, bueno esta bien";
        dialogues[1] = "Cuando haya un combate me uniré a ti";
        dialogues[2] = "No te mueras, no quiero que me maten";
    }

    /**
     * Defines the NPC's action and movement.
     */
    public void setAction() {

        if(onPath){
            int goalCol = 15;
            int goalRow = 6;

            searchPath(goalCol, goalRow);
        }
        else{
            actionLockCounter++;

            if (actionLockCounter == 90) {
                Random random = new Random();
                int i = random.nextInt(100 + 1);

                if (i <= 25) {
                    direction = "up";
                }
                if (i > 25 && i <= 50) {
                    direction = "down";
                }
                if (i > 50 && i <= 75) {
                    direction = "left";
                }
                if (i > 75) {
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }



    }

    /**
     * Overrides the speak method from the base class.
     * First time you talk to npc joins party
     */
    public void speak() {
        super.speak();
        if(!unido){
            gp.party.addMonsterToParty("Mascara Sonriente");
            unido = true;
        }
        //onPath = true;
    }
}
