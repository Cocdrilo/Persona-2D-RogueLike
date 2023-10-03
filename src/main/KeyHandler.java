package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed , enterPressed ,zPressed;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        //PLAY STATE
        if(gp.gameState ==gp.playState){
            playState(code);
        }
        //PAUSE STATE
        else if(gp.gameState==gp.pauseState){
            pauseState(code);
        }

        //DIALOGUE STATE
        else if(gp.gameState == gp.dialogueState){
            dialogueState(code);
        }

        //Menu STATE
        else if(gp.gameState == gp.enterMenuState){
            enterMenuState(code);
        }

        //TITLESCREEN STATE
        else if(gp.gameState == gp.titleState){
            titleState(code);
        }
        //STATUS STATE
        else if(gp.gameState == gp.statusState){
            statusState(code);
        }
        //INVENTORY STATE
        else if(gp.gameState == gp.inventoryState){
            inventoryState(code);
        }
        //COMBAT STATE
        else if(gp.gameState == gp.combatState){
            combatState(code);
        }

    }

    public void combatState(int code){

        if(code == KeyEvent.VK_W) {
            if(gp.ui.commandNum>0){
                gp.ui.commandNum--;
                System.out.println(gp.ui.commandNum);
            }
            else{
                gp.ui.commandNum=4;
            }
        }

        if(code == KeyEvent.VK_S) {
            if(gp.ui.commandNum<4){
                gp.ui.commandNum++;
                System.out.println(gp.ui.commandNum);
            }
            else{
                gp.ui.commandNum=0;
            }
        }
        if(code == KeyEvent.VK_Z){
            if(gp.ui.commandNum == 0){
                gp.battleSystem.attack();
            }
            if(gp.ui.commandNum == 1){
                //Use Magic Aun sin Implementar
            }
            if(gp.ui.commandNum == 2){
                //Use Items Aun sin Implementar
            }
            if(gp.ui.commandNum == 3){
                gp.battleSystem.defend();
            }
            if(gp.ui.commandNum == 4){
                //Flee Aun sin Implementar
            }
        }


    }

    public void titleState(int code){

        if(code == KeyEvent.VK_W) {
            if(gp.ui.commandNum>0){
                gp.ui.commandNum--;
            }
            else{
                gp.ui.commandNum=2;
            }
        }

        if(code == KeyEvent.VK_S) {
            if(gp.ui.commandNum<2){
                gp.ui.commandNum++;
            }
            else{
                gp.ui.commandNum=0;
            }
        }
        if(code == KeyEvent.VK_Z){
            if(gp.ui.commandNum == 0){
                gp.gameState = gp.playState;
                //gp.playMusic(0);
            }
            if(gp.ui.commandNum == 1){
                gp.saveLoad.load();
                gp.gameState = gp.playState;
                //gp.playMusic(0);
            }
            if(gp.ui.commandNum == 2){
                System.exit(0);
            }
        }
    }
    public void playState(int code){

        if(code == KeyEvent.VK_W) {
            upPressed = true;
        }

        if(code == KeyEvent.VK_A) {
            leftPressed = true;
        }

        if(code == KeyEvent.VK_S) {
            downPressed = true;
        }

        if(code == KeyEvent.VK_D) {
            rightPressed = true;
        }

        if(code == KeyEvent.VK_ESCAPE) {
            gp.gameState= gp.pauseState;
        }

        if(code == KeyEvent.VK_Z) {
            zPressed = true;
        }

        if(code == KeyEvent.VK_ENTER){
            gp.gameState = gp.enterMenuState;
        }
    }
    public void pauseState(int code){

        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState=gp.playState;
        }
    }
    public void dialogueState(int code){
        if(code == KeyEvent.VK_Z){
            gp.gameState = gp.playState;
        }
    }

    public void statusState(int code){
        if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.enterMenuState;
        }
    }

    public void inventoryState(int code){

        if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.enterMenuState;
        }

        if(code == KeyEvent.VK_W) {
            if(gp.ui.slotRow !=0){
                gp.ui.slotRow--;
            }
        }

        if(code == KeyEvent.VK_A) {
            if(gp.ui.slotCol !=0){
                gp.ui.slotCol--;
            }
        }

        if(code == KeyEvent.VK_S) {
            if(gp.ui.slotRow !=6){
                gp.ui.slotRow++;
            }
        }

        if(code == KeyEvent.VK_D) {
            if(gp.ui.slotCol !=10){
                gp.ui.slotCol++;
            }
        }
        if(code == KeyEvent.VK_Z) {
            gp.player.selectItems();
        }

    }

    public void enterMenuState(int code){

        if(code == KeyEvent.VK_W) {
            if(gp.ui.commandNum>0){
                gp.ui.commandNum--;
            }
            else{
                gp.ui.commandNum=3;
            }
        }

        if(code == KeyEvent.VK_S) {
            if(gp.ui.commandNum<3){
                gp.ui.commandNum++;
            }
            else{
                gp.ui.commandNum=0;
            }
        }
        if(code == KeyEvent.VK_Z) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.statusState;
            }
            if (gp.ui.commandNum == 1) {
                gp.gameState = gp.inventoryState;
            }
            if (gp.ui.commandNum == 2) {
                gp.saveLoad.save();
                //System.out.println("Saved!");
                gp.gameState = gp.dialogueState;
                gp.ui.currentDialogue = "Has guardado el progreso";
            }
            if (gp.ui.commandNum == 3){
                System.exit(0);
            }
        }

        if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }

    }


    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }

        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }

        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }

        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
