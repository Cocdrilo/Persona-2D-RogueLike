package main;

import java.awt.*;

public class Events {

    GamePanel gp;
    EventRect eventRect[][];
    //Para si queremos que un evento solo suceda una vez
    int prevEventX, prevEventY;
    boolean canTouchEvent = true;

    public Events(GamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = col * gp.tileSize;
            eventRect[col][row].y = row * gp.tileSize;
            eventRect[col][row].width = gp.tileSize;
            eventRect[col][row].height = gp.tileSize;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void checkEvent() {

        //COMPROBAR SI EL JUGADOR ESTA EN AL MENOS 1 TILE AWAY DEL EVENTO
        int xDistance = Math.abs(gp.player.WorldX - prevEventX);
        int yDistance = Math.abs(gp.player.WorldY - prevEventY);
        int distance = Math.max(xDistance, yDistance);

        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }

        //COMPROBAR SI EL JUGADOR ESTA EN UN EVENTO
        if (canTouchEvent) {
            //DAMAGE PIT
            if (hit(9, 4, "any")) {
                damagePit(gp.dialogueState);
            }
        }


    }

    public boolean hit(int col, int row, String reqDirection) {

        boolean hit = false;

        gp.player.solidArea.x = gp.player.WorldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.WorldY + gp.player.solidArea.y;
        eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;

        if (gp.player.solidArea.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone) {
            if (gp.player.direction.equals(reqDirection) || reqDirection.equals("any")) {
                hit = true;

                prevEventX = gp.player.WorldX;
                prevEventY = gp.player.WorldY;
            }
        }
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.SolidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }

    public void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "Te caiste mongo";
        canTouchEvent = false;
    }

    public void HealPool(int gameState) {
        if (gp.keyH.zPressed) {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "Te curaste";
        }
    }

    public void teleportTile(int gameState) {
        gp.gameState = gameState;
        gp.player.WorldX = 25;
        gp.player.WorldY = 20;
    }


}
