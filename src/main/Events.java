package main;

import java.awt.*;

public class Events {

    GamePanel gp;
    EventRect eventRect[][];
    //Para si queremos que un evento solo suceda una vez
    int prevEventX,prevEventY;
    boolean canTouchEvent = true;

    public Events(GamePanel gp){
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while(col < gp.maxWorldCol && row < gp.maxWorldRow){

            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = col * gp.tileSize;
            eventRect[col][row].y = row * gp.tileSize;
            eventRect[col][row].width = 10;
            eventRect[col][row].height = 10;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }

    public void checkEvent(){

        //COMPROBAR SI EL JUGADOR ESTA EN AL MENOS 1 TILE AWAY DEL EVENTO
        int xDistance = Math.abs(gp.player.WorldX - prevEventX);
        int yDistance = Math.abs(gp.player.WorldY - prevEventY);
        int distance = Math.max(xDistance,yDistance);

        if(distance> gp.tileSize){
            canTouchEvent = true;
        }

        //COMPROBAR SI EL JUGADOR ESTA EN UN EVENTO
        if(canTouchEvent == true){
            //DAMAGE PIT
            if(hit(5,5,"any")){ damagePit(5, 5, gp.dialogueState) ;}
            if(hit(4,4,"any")){ healPool(gp.dialogueState); ;}
        }


    }

    public boolean hit(int col, int row, String reqDirection){

        boolean hit = false;

        gp.player.solidArea.x = gp.player.WorldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.WorldY + gp.player.solidArea.y;
        eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;

        if(gp.player.solidArea.intersects(eventRect[col][row])&& !eventRect[col][row].eventDone){
            if(gp.player.direction.equals(reqDirection) || reqDirection.equals("any")){
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

    public void damagePit(int col, int row, int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue = "Te caiste mongo";
        gp.player.PLAYERstats.hp -= 1;
        canTouchEvent = false;

    }

    public void healPool(int gameState){
        if(gp.keyH.zPressed){
            gp.gameState = gameState;
            gp.ui.currentDialogue = "Te curaste y guardaste partida";
            gp.player.PLAYERstats.hp = gp.player.PLAYERstats.maxHp;
            gp.player.PLAYERstats.mp = gp.player.PLAYERstats.maxMp;
        }
    }

    public void teleportTile(int gameState){
            gp.gameState = gameState;
            gp.player.WorldX = 25;
            gp.player.WorldY = 20;
    }



}
