package entity;

import main.GamePanel;

import java.util.Random;

/**
 * This class represents a non-playable character (NPC) entity.
 */
public class NPC extends Entity {
    boolean alreadyJoinedtoParty = false;
    /**
     * Constructs an NPC with the specified GamePanel.
     * Initializes the NPC's name, direction, speed, images, and dialogue.
     *
     * @param gp The GamePanel associated with the NPC.
     */
    public NPC(GamePanel gp) {
        super(gp);
        setDefault_Name_Direction_Speed();
        getImage();
        setDialogue();
    }

    private void setDefault_Name_Direction_Speed(){
        name = "Demon";
        direction = "down";
        speed = 1;
    }

    /**
     * Loads NPC images for different directions.
     */
    private void getImage() {
        int scale = 1;
        walkDown1 = setUp("/NPC/demon_1",gp.tileSize*scale,gp.tileSize*scale);
        walkDown2 = setUp("/NPC/demon_2",gp.tileSize*scale,gp.tileSize*scale);
        walkLeft1 = setUp("/NPC/demon_left_1" ,gp.tileSize*scale,gp.tileSize*scale);
        walkLeft2 = setUp("/NPC/demon_left_2" ,gp.tileSize*scale,gp.tileSize*scale);
        walkRight1 = setUp("/NPC/demon_right_1" ,gp.tileSize*scale,gp.tileSize*scale);
        walkRight2 = setUp("/NPC/demon_right_2" ,gp.tileSize*scale,gp.tileSize*scale);
        walkUp1 = setUp("/NPC/demon_back_1" ,gp.tileSize*scale,gp.tileSize*scale);
        walkUp2 = setUp("/NPC/demon_back_2" ,gp.tileSize*scale,gp.tileSize*scale);

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
        randomMoveForEntities();
    }


    /**
     * Overrides the speak method from the base class.
     * First time you talk to npc joins party
     */
    public void speak() {
        super.speak();
        if(!alreadyJoinedtoParty){
            gp.party.addMonsterToParty("Mascara Sonriente");
            alreadyJoinedtoParty = true;
        }
    }
}
