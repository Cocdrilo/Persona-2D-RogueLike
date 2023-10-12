package main;
import Object.Consumables.OBJ_Potion_Health;
import Object.Equipables.OBJ_WEAPON_BASH;
import Object.WorldBuilding.OBJ_Chest;
import Object.WorldBuilding.OBJ_Door;
import Object.WorldBuilding.OBJ_Stairs;
import entity.NPC;
import monster.monsterData;
import monster.shadowStandar;

import java.util.ArrayList;

public class AssetSetter {

    GamePanel gp;
    KeyHandler keyH;

    public AssetSetter(GamePanel gp){

        this.gp = gp;

    }

    public void setObject(){

            gp.obj[0]=new OBJ_Door(gp);
            gp.obj[0].WorldX = gp.tileSize*5;
            gp.obj[0].WorldY = gp.tileSize*5;

            gp.obj[1]=new OBJ_Stairs(gp);
            gp.obj[1].WorldX = gp.tileSize*5;
            gp.obj[1].WorldY = gp.tileSize*10;

            gp.obj[2]=new OBJ_Chest(gp);
            gp.obj[2].WorldX = gp.tileSize*8;
            gp.obj[2].WorldY = gp.tileSize*10;

            gp.obj[3]= new OBJ_WEAPON_BASH(gp);
            gp.obj[3].WorldX = gp.tileSize*16;
            gp.obj[3].WorldY = gp.tileSize*5;

            gp.obj[4]= new OBJ_Potion_Health(gp);
            gp.obj[4].WorldX = gp.tileSize*11;
            gp.obj[4].WorldY = gp.tileSize*38;


    }

    public void setNPC(){

        gp.npc[0]=new NPC(gp);
        gp.npc[0].WorldX = gp.tileSize*4;
        gp.npc[0].WorldY = gp.tileSize*4;
    }

    public void setMonster(){
            ArrayList<monsterData> availableMonsters = gp.monsterManager.getMonsters();
            gp.monsters[0] = new shadowStandar(gp,availableMonsters.get(6));
            gp.monsters[0].WorldX = gp.tileSize*8;
            gp.monsters[0].WorldY = gp.tileSize*4;
    }
}
