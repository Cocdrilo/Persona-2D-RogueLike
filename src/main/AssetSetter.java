package main;
import Object.*;
import entity.NPC_Mimic;
import monster.shadowStandar;

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

            gp.obj[3]= new OBJ_WEAPON_BASH(gp);
            gp.obj[3].WorldX = 64;
            gp.obj[3].WorldY = 256;

            gp.obj[4]= new OBJ_Potion_Health(gp);
            gp.obj[4].WorldX = 64;
            gp.obj[4].WorldY = 320;


    }

    public void setNPC(){

        gp.npc[0]=new NPC_Mimic(gp);
        gp.npc[0].WorldX = gp.tileSize*4;
        gp.npc[0].WorldY = gp.tileSize*4;
    }

    public void setMonster(){
        gp.monsters[0]= new shadowStandar(gp, "Shadow", 100, 20, 10, 50, "Piercing", "/Monsters/MonstersBattleDisplay/Quimera", new String[]{"Fuego", "Eléctrico"}, new String[]{"Hielo"}, new String[]{}, new String[]{});
        gp.monsters[0].WorldX = gp.tileSize*6;
        gp.monsters[0].WorldY = gp.tileSize*6;

        gp.monsters[1] = new shadowStandar(gp, "Shadow", 100, 5, 10, 50, "Slashing", "/Monsters/MonstersBattleDisplay/Goblin", new String[]{"Hielo", "Fuerza"}, new String[]{"Slashing"}, new String[]{}, new String[]{});
        gp.monsters[1].WorldX = gp.tileSize*8;
        gp.monsters[1].WorldY = gp.tileSize*8;

    }
}
