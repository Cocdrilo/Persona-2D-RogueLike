package main;

import entity.Entity;
import entity.Player;
import monster.shadowStandar;
import negotiation.NegotiationSystem;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;
import java.util.ArrayList;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, zPressed;
    public int pointsPerLevel = 0;
    public int oldDex = 0, oldStr = 0, oldMag = 0, oldAgi = 0;


    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        //PLAY STATE
        if (gp.gameState == gp.playState) {
            playState(code);
        }
        //PAUSE STATE
        else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        }

        //DIALOGUE STATE
        else if (gp.gameState == gp.dialogueState) {
            dialogueState(code);
        }

        //Menu STATE
        else if (gp.gameState == gp.enterMenuState) {
            enterMenuState(code);
        }

        //TITLESCREEN STATE
        else if (gp.gameState == gp.titleState) {
            titleState(code);
        }
        //STATUS STATE
        else if (gp.gameState == gp.statusState) {
            statusState(code);
        }
        //INVENTORY STATE
        else if (gp.gameState == gp.inventoryState) {
            inventoryState(code);
        }
        //COMBAT STATE
        else if (gp.gameState == gp.combatState) {
            combatState(code);
        }
        //MAGIC MENU STATE
        else if (gp.gameState == gp.magicMenuState) {
            magicMenuState(code);
        }
        //LEVEL UP STATE
        else if (gp.gameState == gp.levelUpState) {
            levelState(code);
        }
        //BATTLE ITEMS STATE
        else if (gp.gameState == gp.battleItemsState) {
            battleItemsState(code);
        }
        //NEGOTIATION STATE
        else if (gp.gameState == gp.negotiationState) {
            negotiationState(code);
        }
        //NEGOTIATION REWARD STATE
        else if (gp.gameState == gp.negotiationRewardState) {
            negotiationRewardState(code);
        }
        //MONEY REQUEST STATE
        else if (gp.gameState == gp.moneyRequestState) {
            moneyRequestState(code);
        }
        //OPTIONS MENU
        else if (gp.gameState == gp.optionsState) {
            optionsState(code);
        }
        

    }

    public void combatState(int code) {

        if (code == KeyEvent.VK_W) {
            if (gp.ui.commandNum > 0 && !gp.ui.magicMenu && !gp.ui.itemMenu) {
                gp.ui.commandNum--;
            } else if (!gp.ui.magicMenu && !gp.ui.itemMenu) {
                gp.ui.commandNum = 5;
            }
        }

        if (code == KeyEvent.VK_S) {
            if (gp.ui.commandNum < 5 && !gp.ui.magicMenu && !gp.ui.itemMenu) {
                gp.ui.commandNum++;
            } else if (!gp.ui.magicMenu && !gp.ui.itemMenu) {
                gp.ui.commandNum = 0;
            }
        }

        if (code == KeyEvent.VK_Z) {
            if (gp.ui.commandNum == 0) {

                Entity attacker = gp.battleSystem.partyMembers.get(gp.battleSystem.currentPartyMemberIndex);

                if (attacker instanceof Player playerAttacker) {
                    // Realiza un casting a Player
                    gp.battleSystem.attack(playerAttacker, gp.battleSystem.monster);
                } else if (attacker instanceof shadowStandar monsterAttacker) {
                    // Realiza un casting a shadowStandar
                    gp.battleSystem.attack(monsterAttacker, gp.battleSystem.monster);
                }
            }

            if (gp.ui.commandNum == 1) {
                gp.ui.magicMenu = true;
                gp.gameState = gp.magicMenuState;
            }
            if (gp.ui.commandNum == 2) {
                gp.ui.itemMenu = true;
                gp.gameState = gp.battleItemsState;
            }
            if (gp.ui.commandNum == 3) {
                gp.battleSystem.defend();
            }
            if (gp.ui.commandNum == 4) {
                gp.battleSystem.fleeFromBattle();
            }
            if (gp.ui.commandNum == 5) {
                gp.gameState = gp.negotiationState;
                gp.battleSystem.negotiateMonster();
            }
        }
    }

    public void magicMenuState(int code) {
        // Aquí obtenemos la cantidad de hechizos disponibles
        int numSpells = gp.player.spells.size();
        Entity attacker = gp.battleSystem.partyMembers.get(gp.battleSystem.currentPartyMemberIndex);
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
                gp.battleSystem.useMagic(attacker, gp.battleSystem.monster, attacker.spells.get(gp.ui.commandNum2));
            }

            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.commandNum2 = 0;
                gp.ui.magicMenu = false;
                gp.gameState = gp.combatState;
            }
        }
    }

    public void battleItemsState(int code) {

        ArrayList<Entity> consumableItems = gp.player.getItems();
        int[] indexConsus = gp.player.saveItemIndexes();

        if (gp.ui.commandNum == 2 && gp.ui.itemMenu) {
            if (code == KeyEvent.VK_W) {
                if (gp.ui.commandNum2 > 0) {
                    gp.ui.commandNum2--;
                } else {
                    gp.ui.commandNum2 = consumableItems.size() - 1;
                }
            }
            if (code == KeyEvent.VK_S) {
                if (gp.ui.commandNum2 < consumableItems.size() - 1) {
                    gp.ui.commandNum2++;
                } else {
                    gp.ui.commandNum2 = 0;
                }
            }
            if (code == KeyEvent.VK_Z) {
                while (consumableItems.size() > 0) {
                    gp.battleSystem.useItem(consumableItems.get(gp.ui.commandNum2));
                    consumableItems.remove(gp.ui.commandNum2);
                    gp.player.inventory.remove(indexConsus[gp.ui.commandNum2]);
                }
            }
            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.commandNum2 = 0;
                gp.ui.itemMenu = false;
                gp.gameState = gp.combatState;
            }
        }
    }

    public void negotiationState(int code) {
        int Opciones = gp.battleSystem.negotiationSystem.getNumOpciones();

        if (code == KeyEvent.VK_W && !gp.battleSystem.negotiationSystem.selectingReward) {
            if (gp.ui.commandNum > 0) {
                gp.ui.commandNum--;
                System.out.println("commandNum1 : " + gp.ui.commandNum2);
            } else {
                gp.ui.commandNum = Opciones - 1;
                System.out.println("commandNum1 : " + gp.ui.commandNum2);
            }
        }

        if (code == KeyEvent.VK_S && !gp.battleSystem.negotiationSystem.selectingReward) {
            if (gp.ui.commandNum < Opciones - 1) {
                gp.ui.commandNum++;
                System.out.println("commandNum1 : " + gp.ui.commandNum2);
            } else {
                gp.ui.commandNum = 0;
                System.out.println("commandNum1 : " + gp.ui.commandNum2);
            }
        }

        if(code == KeyEvent.VK_ESCAPE && !gp.battleSystem.negotiationSystem.selectingReward){
            gp.gameState = gp.combatState;
        }

        if (code == KeyEvent.VK_Z && !gp.battleSystem.negotiationSystem.selectingReward) {
            gp.battleSystem.negotiationSystem.updateMeter();
            if (gp.battleSystem.negotiationSystem.selectingReward) {
                gp.gameState = gp.negotiationRewardState;
            }
            if (gp.battleSystem.negotiationSystem.endNegotiation) {
                if (gp.battleSystem.negotiationSystem.happyMeter >= 20) {
                    gp.gameState = gp.playState;
                } else if (gp.battleSystem.negotiationSystem.angryMeter >= 20) {
                    gp.gameState = gp.combatState;
                }
            } else {
                gp.battleSystem.negotiationSystem.startNegotiation();
            }
        }
        if (code == KeyEvent.VK_Z && gp.battleSystem.negotiationSystem.moneyRequest){
            // Cambia al estado de solicitud de dinero
            gp.gameState = gp.moneyRequestState;
        }
    }
    public void moneyRequestState(int code) {
        // En este estado, puedes manejar las opciones del jugador con respecto a la solicitud de dinero.

        int Opciones = gp.battleSystem.negotiationSystem.getNumOpciones();

        if (code == KeyEvent.VK_W && !gp.battleSystem.negotiationSystem.selectingReward) {
            if (gp.ui.commandNum2 > 0) {
                gp.ui.commandNum2--;
                System.out.println("commandNum2 : " + gp.ui.commandNum2);
            } else {
                gp.ui.commandNum2 = Opciones - 1;
                System.out.println("commandNum2 : " + gp.ui.commandNum2);
            }
        }

        if (code == KeyEvent.VK_S && !gp.battleSystem.negotiationSystem.selectingReward) {
            if (gp.ui.commandNum2 < Opciones - 1) {
                gp.ui.commandNum2++;
                System.out.println("commandNum2 : " + gp.ui.commandNum2);
            } else {
                gp.ui.commandNum2 = 0;
                System.out.println("commandNum2 : " + gp.ui.commandNum2);
            }
        }

        if (code == KeyEvent.VK_Z) {
            // Aquí procesa la respuesta del jugador al pedido de dinero.
            gp.battleSystem.negotiationSystem.processMoneyRequest(gp.ui.commandNum2);
            if (gp.battleSystem.negotiationSystem.selectingReward) {
                gp.gameState = gp.negotiationRewardState;
            } else if (gp.battleSystem.negotiationSystem.endNegotiation) {
                if (gp.battleSystem.negotiationSystem.happyMeter >= 20) {
                    gp.gameState = gp.negotiationRewardState; // Cambiar a negotiationRewardState
                } else if (gp.battleSystem.negotiationSystem.angryMeter >= 20) {
                    gp.gameState = gp.combatState;
                }
            } else {
                gp.gameState = gp.negotiationState;
                gp.battleSystem.negotiationSystem.startNegotiation();
            }
        }
    }


    public void negotiationRewardState(int code) {
        System.out.println("Selecting Reward");
        if (code == KeyEvent.VK_W && gp.battleSystem.negotiationSystem.selectingReward) {
            if (gp.ui.commandNum2 > 0) {
                gp.ui.commandNum2--;
                System.out.println(gp.ui.commandNum2);
            } else {
                gp.ui.commandNum2 = 2;
                System.out.println(gp.ui.commandNum2);
            }
        }

        if (code == KeyEvent.VK_S && gp.battleSystem.negotiationSystem.selectingReward) {
            if (gp.ui.commandNum2 < 2) {
                gp.ui.commandNum2++;
                System.out.println(gp.ui.commandNum2);
            } else {
                gp.ui.commandNum2 = 0;
                System.out.println(gp.ui.commandNum2);
            }
        }
        if (code == KeyEvent.VK_Z && gp.battleSystem.negotiationSystem.selectingReward) {
            gp.battleSystem.negotiationSystem.happyMetterOptions(gp.ui.commandNum2);
            gp.gameState = gp.playState;

        }
    }


    //Necesita Fix
    public void levelState(int code) {
        System.out.println(oldStr);

        if (code == KeyEvent.VK_W) {
            if (gp.ui.commandNum > 0) {
                gp.ui.commandNum--;
            } else {
                gp.ui.commandNum = 3;
            }
        }

        if (code == KeyEvent.VK_S) {
            if (gp.ui.commandNum < 3) {
                gp.ui.commandNum++;
            } else {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
            if (gp.ui.commandNum == 0) {
                if (pointsPerLevel > 0) {
                    gp.player.stats.vit++;
                    gp.player.stats.maxHp += 5;
                    pointsPerLevel--;
                }
            }
            if (gp.ui.commandNum == 1) {
                if (pointsPerLevel > 0) {
                    gp.player.stats.str++;
                    pointsPerLevel--;
                }
            }
            if (gp.ui.commandNum == 2) {
                if (pointsPerLevel > 0) {
                    gp.player.stats.mag++;
                    gp.player.stats.maxMp += 5;
                    pointsPerLevel--;
                }
            }
            if (gp.ui.commandNum == 3) {
                if (pointsPerLevel > 0) {
                    gp.player.stats.agi++;
                    pointsPerLevel--;
                }
            }
        }
        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
            if (gp.ui.commandNum == 0) {
                gp.player.stats.vit--;
                pointsPerLevel++;
                if (gp.player.stats.vit < oldDex) {
                    gp.player.stats.vit = oldDex;
                    pointsPerLevel--;
                }
                gp.player.stats.maxHp -= 5;
            }
            if (gp.ui.commandNum == 1) {
                gp.player.stats.str--;
                pointsPerLevel++;
                if (gp.player.stats.str < oldStr) {
                    gp.player.stats.str = oldStr;
                    pointsPerLevel--;
                }
            }
            if (gp.ui.commandNum == 2) {
                gp.player.stats.mag--;
                pointsPerLevel++;
                if (gp.player.stats.mag < oldMag) {
                    gp.player.stats.mag = oldMag;
                    pointsPerLevel--;
                }
                gp.player.stats.maxMp -= 5;
            }
            if (gp.ui.commandNum == 3) {
                gp.player.stats.agi--;
                pointsPerLevel++;
                if (gp.player.stats.agi < oldAgi) {
                    gp.player.stats.agi = oldAgi;
                    pointsPerLevel--;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
            }
        }
        if (code == KeyEvent.VK_ENTER && pointsPerLevel == 0) {
            gp.gameState = gp.playState;
        }
    }


    public void titleState(int code) {

        if (code == KeyEvent.VK_W) {
            if (gp.ui.commandNum > 0) {
                gp.ui.commandNum--;
            } else {
                gp.ui.commandNum = 2;
            }
        }

        if (code == KeyEvent.VK_S) {
            if (gp.ui.commandNum < 2) {
                gp.ui.commandNum++;
            } else {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_Z) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
                //gp.playMusic(0);
            }
            if (gp.ui.commandNum == 1) {
                gp.saveLoad.load();
                gp.gameState = gp.playState;
                //gp.playMusic(0);
            }
            if (gp.ui.commandNum == 2) {
                System.exit(0);
            }
        }
    }

    public void playState(int code) {

        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }

        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }

        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }

        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }

        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.pauseState;
        }

        if (code == KeyEvent.VK_Z) {
            zPressed = true;
        }

        if (code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.enterMenuState;
        }
    }

    public void pauseState(int code) {

        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
    }

    public void dialogueState(int code) {
        if (code == KeyEvent.VK_Z) {
            gp.gameState = gp.playState;
        }
    }

    public void statusState(int code) {
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.enterMenuState;
        }
    }

    public void inventoryState(int code) {

        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.enterMenuState;
        }

        if (code == KeyEvent.VK_W) {
            if (gp.ui.slotRow != 0) {
                gp.ui.slotRow--;
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gp.ui.slotCol != 0) {
                gp.ui.slotCol--;
            }
        }

        if (code == KeyEvent.VK_S) {
            if (gp.ui.slotRow != 6) {
                gp.ui.slotRow++;
            }
        }

        if (code == KeyEvent.VK_D) {
            if (gp.ui.slotCol != 10) {
                gp.ui.slotCol++;
            }
        }
        if (code == KeyEvent.VK_Z) {
            gp.player.selectItems();
        }

    }

    public void enterMenuState(int code) {

        if (code == KeyEvent.VK_W) {
            if (gp.ui.commandNum > 0) {
                gp.ui.commandNum--;
            } else {
                gp.ui.commandNum = 4;
            }
        }

        if (code == KeyEvent.VK_S) {
            if (gp.ui.commandNum < 4) {
                gp.ui.commandNum++;
            } else {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_Z) {
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
            if (gp.ui.commandNum == 3) {
                gp.gameState = gp.optionsState;
            }
            if (gp.ui.commandNum == 4) {
                System.exit(0);
            }
        }

        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }

    }

    public void optionsState(int code) {
        /*
        if(gp.ui.subState == 3){
            if(gp.ui.commandNum3 == 0 && code == KeyEvent.VK_Z){
                gp.ui.subState = 0;
                gp.gameState = gp.titleState;
            }
            else if(gp.ui.commandNum3 == 1 && code == KeyEvent.VK_Z){
                gp.ui.subState = 0;
            }
        }*/

        if (code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.enterMenuState;
        }

        int maxCommandNum = switch (gp.ui.subState) {
            case 0 -> 5;
            case 3 -> 1;
            default -> 0;
        };

        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum3--;
            gp.playerSe(5);
            if (gp.ui.commandNum3 < 0) {
                gp.ui.commandNum3 = maxCommandNum;
            }
        }

        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum3++;
            gp.playerSe(5);
            if (gp.ui.commandNum3 > maxCommandNum) {
                gp.ui.commandNum3 = 0;
            }
        }
        if (code == KeyEvent.VK_A) {
            if(gp.ui.subState == 0){
                if(gp.ui.commandNum3 == 1 && gp.music.volumeScale > 0){
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playerSe(5);
                }
                if(gp.ui.commandNum3 == 2 && gp.se.volumeScale > 0){
                    gp.se.volumeScale--;
                    gp.playerSe(5);
                }
            }
        }
        if (code == KeyEvent.VK_D) {
            if(gp.ui.subState == 0){
                if(gp.ui.commandNum3 == 1 && gp.music.volumeScale < 5){
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playerSe(5);
                }
                if(gp.ui.commandNum3 == 2 && gp.se.volumeScale < 5){
                    gp.se.volumeScale++;
                    gp.playerSe(5);
                }
            }
        }

        if (code == KeyEvent.VK_Z) {

            if(gp.ui.subState==0){
                if (gp.ui.commandNum3 == 0) {
                    if (!gp.fullScreenOn) {
                        gp.fullScreenOn = true;
                    } else if (gp.fullScreenOn) {
                        gp.fullScreenOn = false;
                    }
                    gp.ui.subState = 1;
                }
            }

            //CONTROL
            if (gp.ui.commandNum3 == 3) {
                gp.ui.subState=2;
            }
            //END GAME
            if (gp.ui.commandNum3 == 4) {
                gp.ui.subState=3;
            }
            //BACK
            if (gp.ui.commandNum3 == 5) {
                gp.ui.subState=0;
                gp.ui.commandNum3=0;
                gp.gameState=gp.enterMenuState;
            }

            //END GAME YES/NO
            if(gp.ui.subState == 3 && gp.ui.commandNum3 == 0){
                gp.ui.subState=0;
                //gp.stopMusic();
                gp.gameState = gp.titleState;
            }
            else if(gp.ui.subState == 3 && gp.ui.commandNum3 == 1){
                System.out.println("No");
                gp.ui.subState=0;
                gp.ui.commandNum3=4;
                gp.gameState = gp.optionsState;
            }


        }
        if (code == KeyEvent.VK_ESCAPE && gp.ui.subState == 1 || code == KeyEvent.VK_ESCAPE && gp.ui.subState == 2) {
            gp.ui.subState = 0;
            gp.gameState = gp.optionsState;
        }
    }



    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }

        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }

        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }

        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
