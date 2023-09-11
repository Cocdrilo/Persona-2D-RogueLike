package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

        //STATUS STATE
        else if(gp.gameState == gp.statusState){
            statusState(code);
        }

        //TITLESCREEN STATE
        else if(gp.gameState == gp.titleState){
            titleState(code);
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
            }
            if(gp.ui.commandNum == 1){
                //CARGAR (TODAVIA SIN AÑADIR)
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
            gp.gameState = gp.statusState;
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
        if(code == KeyEvent.VK_ENTER){
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
