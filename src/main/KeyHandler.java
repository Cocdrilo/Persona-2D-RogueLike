package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed , enterPressed ,zPressed;
    public int pointsPerLevel = 0;
    public int oldDex = 0,oldStr=0,oldMag=0,oldAgi=0;


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
        //MAGIC MENU STATE
        else if(gp.gameState == gp.magicMenuState){
            magicMenuState(code);
        }
        else if(gp.gameState == gp.levelUpState){
            levelState(code);
        }

    }

    public void combatState(int code) {

        if (code == KeyEvent.VK_W) {
            if (gp.ui.commandNum > 0 && !gp.ui.magicMenu) {
                gp.ui.commandNum--;
            } else if (!gp.ui.magicMenu) {
                gp.ui.commandNum = 4;
            }
        }

        if (code == KeyEvent.VK_S) {
            if (gp.ui.commandNum < 4 && !gp.ui.magicMenu) {
                gp.ui.commandNum++;
            } else if (!gp.ui.magicMenu) {
                gp.ui.commandNum = 0;
            }
        }

        if (code == KeyEvent.VK_Z) {
            if (gp.ui.commandNum == 0) {
                gp.battleSystem.attack();
            }
            if (gp.ui.commandNum == 1) {
                gp.ui.magicMenu = true;
                gp.gameState = gp.magicMenuState;
            }
            if (gp.ui.commandNum == 2) {
                // Use Items Aun sin Implementar
            }
            if (gp.ui.commandNum == 3) {
                gp.battleSystem.defend();
            }
            if (gp.ui.commandNum == 4) {
                // Flee Aun sin Implementar
            }
        }
    }

    public void magicMenuState(int code){
        // Aquí obtenemos la cantidad de hechizos disponibles
        int numSpells = gp.player.numberOfSpells();
        if (gp.ui.commandNum == 1 && gp.ui.magicMenu) {

            if (code == KeyEvent.VK_W) {
                if (gp.ui.commandNum2 > 0) {
                    gp.ui.commandNum2--;
                    System.out.println(gp.ui.commandNum2);
                } else {
                    gp.ui.commandNum2 = numSpells - 1; // Ir al último hechizo
                }
            }
            if (code == KeyEvent.VK_S) {
                if (gp.ui.commandNum2 < numSpells - 1) {
                    gp.ui.commandNum2++;
                    System.out.println(gp.ui.commandNum2);
                } else {
                    gp.ui.commandNum2 = 0; // Volver al primer hechizo
                }
            }
            // Verificar si estamos dentro del submenu de magia antes de activar el hechizo
            if (code == KeyEvent.VK_Z) {
                // Aquí activa el hechizo seleccionado (sin pasar una variable)
                gp.battleSystem.useMagic();
            }

            if(code == KeyEvent.VK_ESCAPE){
                gp.ui.commandNum2 = 0;
                gp.ui.magicMenu = false;
                gp.gameState = gp.combatState;
            }
        }
    }

    //Necesita Fix
    public void levelState(int code){
        System.out.println(oldStr);

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
        if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D){
            if(gp.ui.commandNum == 0){
                if(pointsPerLevel>0){
                    gp.player.PLAYERstats.vit++;
                    gp.player.PLAYERstats.maxHp+=5;
                    pointsPerLevel--;
                }
            }
            if(gp.ui.commandNum == 1){
                if(pointsPerLevel>0){
                    gp.player.PLAYERstats.str++;
                    pointsPerLevel--;
                }
            }
            if(gp.ui.commandNum == 2){
                if(pointsPerLevel>0){
                    gp.player.PLAYERstats.mag++;
                    gp.player.PLAYERstats.maxMp+=5;
                    pointsPerLevel--;
                }
            }
            if(gp.ui.commandNum == 3){
                gp.player.PLAYERstats.agi++;
                pointsPerLevel--;
            }
        }
        if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
            if (gp.ui.commandNum == 0) {
                gp.player.PLAYERstats.vit--;
                pointsPerLevel++;
                if(gp.player.PLAYERstats.vit <oldDex){
                    gp.player.PLAYERstats.vit =oldDex;
                    pointsPerLevel--;
                }
                gp.player.PLAYERstats.maxHp-=5;
            }
            if (gp.ui.commandNum == 1) {
                gp.player.PLAYERstats.str--;
                pointsPerLevel++;
                if(gp.player.PLAYERstats.str<oldStr){
                    gp.player.PLAYERstats.str=oldStr;
                    pointsPerLevel--;
                }
            }
            if (gp.ui.commandNum == 2) {
                gp.player.PLAYERstats.mag--;
                pointsPerLevel++;
                if(gp.player.PLAYERstats.mag<oldMag){
                    gp.player.PLAYERstats.mag=oldMag;
                    pointsPerLevel--;
                }
                gp.player.PLAYERstats.maxMp-=5;
            }
            if(gp.ui.commandNum == 3){
                gp.player.PLAYERstats.agi--;
                pointsPerLevel++;
                if(gp.player.PLAYERstats.agi<oldAgi){
                    gp.player.PLAYERstats.agi=oldAgi;
                    pointsPerLevel--;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
            }
        }
        if(code == KeyEvent.VK_ENTER && pointsPerLevel == 0){
            gp.gameState = gp.playState;
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
                gp.playerSe(1);
                gp.gameState = gp.playState;
                String text = "Has guardado el progreso!";
                gp.ui.addMessage(text);
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
