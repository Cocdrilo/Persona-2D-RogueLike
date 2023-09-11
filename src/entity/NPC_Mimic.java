package entity;

import main.GamePanel;
import main.KeyHandler;

import java.util.Random;

public class NPC_Mimic extends Entity{


    public NPC_Mimic(GamePanel gp){
        super(gp);

        name="Old Man";
        direction = "down";
        speed = 1;

        getImage();
        setDialogue();;
    }

    public void getImage(){
        walkDown1 = setUp("/NPC/oldman_down_1");
        walkDown2 = setUp("/NPC/oldman_down_2");
        walkLeft1 = setUp("/NPC/oldman_left_1");
        walkLeft2 = setUp("/NPC/oldman_left_2");
        walkRight1 = setUp("/NPC/oldman_right_1");
        walkRight2 = setUp("/NPC/oldman_right_2");
        walkUp1 = setUp("/NPC/oldman_up_1");
        walkUp2 = setUp("/NPC/oldman_up_2");

    }

    public void setDialogue(){
        dialogues[0] = "Greetings Traveler";
        dialogues[1] = "Testeando al maximo de lo que se puede hijodeputa";
        dialogues[2] = "En efecto soy godofredo el maximo exponente de \n tu abuela en pedo";
    }

    public void setAction(){

        actionLockCounter++;

        if(actionLockCounter == 90){
            Random random = new Random();
            int i = random.nextInt(100+1);

            if(i<=25){
                direction="up";
            }
            if(i>25 && i<=50){
                direction="down";
            }
            if(i>50 && i<=75){
                direction="left";
            }
            if(i>75){
                direction="right";
            }
            actionLockCounter =0;
        }

    }

    public void speak(){
        super.speak();
    }
}
