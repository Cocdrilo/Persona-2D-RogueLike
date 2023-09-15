package main;
import Object.*;
import entity.NPC_Mimic;
import monster.MON_ChestDemon;

public class AssetSetter {

    GamePanel gp;
    KeyHandler keyH;

    public AssetSetter(GamePanel gp){

        this.gp = gp;

    }

    public void setObject(){

            gp.obj[0]=new OBJ_Door(gp);
            gp.obj[0].WorldX = 64;
            gp.obj[0].WorldY = 64;

            gp.obj[1]=new OBJ_Stairs(gp);
            gp.obj[1].WorldX = 64;
            gp.obj[1].WorldY = 128;

            gp.obj[2]=new OBJ_Chest(gp);
            gp.obj[2].WorldX = 64;
            gp.obj[2].WorldY = 192;

    }

    public void setNPC(){

        gp.npc[0]=new NPC_Mimic(gp);
        gp.npc[0].WorldX = gp.tileSize*4;
        gp.npc[0].WorldY = gp.tileSize*4;
    }

    public void setMonster(){
        gp.monsters[0]=new MON_ChestDemon(gp);
        gp.monsters[0].WorldX = gp.tileSize*6;
        gp.monsters[0].WorldY = gp.tileSize*6;

    }
}
