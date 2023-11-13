package entity;

import main.GamePanel;

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
    public NPC(GamePanel gp) {
        super(gp);

        name = "Old Man";
        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
        ;
    }

    /**
     * Loads NPC images for different directions.
     */
    public void getImage() {
        walkDown1 = setUp("/NPC/oldman_down_1");
        walkDown2 = setUp("/NPC/oldman_down_2");
        walkLeft1 = setUp("/NPC/oldman_left_1");
        walkLeft2 = setUp("/NPC/oldman_left_2");
        walkRight1 = setUp("/NPC/oldman_right_1");
        walkRight2 = setUp("/NPC/oldman_right_2");
        walkUp1 = setUp("/NPC/oldman_up_1");
        walkUp2 = setUp("/NPC/oldman_up_2");

    }

    /**
     * Sets the dialogue for the NPC.
     */
    public void setDialogue() {
        dialogues[0] = "Greetings Traveler";
        dialogues[1] = "Testeando al maximo de lo que se puede hijodeputa";
        dialogues[2] = "En efecto soy godofredo el maximo exponente de \n tu abuela en pedo";
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
     */
    public void speak() {
        super.speak();
        //onPath = true;
    }
}
