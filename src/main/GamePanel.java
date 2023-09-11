package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{
    //Screen Settings
    final int originalTileSize = 16; //16*16
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12 ;
    public final int screenWidth = tileSize * maxScreenCol; //768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    //SETTINGS DEL MUNDO
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    //FPS
    int FPS = 60;

    //SYSTEM
    public KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    Sound music = new Sound();
    Sound se = new Sound();
    TileManager tileM = new TileManager(this);
    public CollisionCheck cCheck = new CollisionCheck(this);
    public AssetSetter Asetter = new AssetSetter(this);
    public UI ui= new UI(this);
    public Events eventHandler = new Events(this);

    //ENTITY AND OBJECT
    public Player player = new Player(this,keyH);
    public Entity obj [] = new Entity[10];
    public Entity npc[] = new Entity[10];
    public Entity monsters[] = new Entity[20];

    //VAMOS A ORGANIZAR COMO ENTIDADES TANTO PERSONAJES COMO OBJETOS Y ALMACENARLOS EN UN ARRAY DE OBJETOS, EN ESTE CASO UN ARRAYLIST PARA JAVA
    ArrayList<Entity> entityList = new ArrayList<Entity>();

    //GAME STATES
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int statusState = 4;
    public final int combatState = 5;



    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground((Color.black));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setUpGame(){

        Asetter.setObject();
        Asetter.setNPC();
        Asetter.setMonster();

        //playMusic(0);
        gameState = titleState;
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double accumulator = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer =0;
        int drawCount = 0;



        while(gameThread !=null){

            currentTime= System.nanoTime();

            accumulator+=(currentTime-lastTime)/drawInterval;
            timer+=(currentTime-lastTime);
            lastTime=currentTime;

            if(accumulator>=1){
                update();
                repaint();
                accumulator--;
                drawCount++;
            }

            if(timer>=1000000000){
                System.out.println("FPS:"+drawCount);
                drawCount=0;
                timer=0;
            }
        }
    }
    public void update(){


        if(gameState== playState){

            //PLAYER ACT
            player.update();

            //NPC ACTUALZ
            for (int x=0;x< npc.length;x++){
                if(npc[x]!=null){
                    npc[x].update();
                }
            }
            for(int x=0;x<monsters.length;x++){
                if(monsters[x]!=null){
                    monsters[x].update();
                }
            }

        }
        if(gameState==pauseState){
            //NOTHING
        }
    }
    public void paintComponent(Graphics graficos){

        super.paintComponent(graficos);
        //Pasamos los graficos a super clase graficos2D le damos color posicion y el dispose es una buena practica de liberar memoria
        Graphics2D graficos2d = (Graphics2D) graficos;

        //PANTALLA DE TITULOS
        if(gameState==titleState){

            ui.draw(graficos2d);

        }

        //OTHERS
        else{
            //Tile Dibujo (Ponemos antes para que este layereado debajo
            tileM.draw(graficos2d);

            //ENTITY ARRAYLIST

            entityList.add(player);

            for(int x=0;x< npc.length;x++){
                if(npc[x]!=null){
                    entityList.add(npc[x]);
                }
            }

            for(int x=0;x< obj.length;x++){
                if(obj[x]!=null){
                    entityList.add(obj[x]);
                }
            }
            for(int x=0;x<monsters.length;x++){
                if(monsters[x]!=null){
                    entityList.add(monsters[x]);
                }
            }

            //SE VIENE LO CHIDO, para organizar el renderizado de entidades correctamente utilizamos el sort del Collections library
            //Utilizamos un comparador para ordenar las entidades por su posicion en el eje Y del mundo La funcion compare devuelve un int que es el resultado de la comparacion
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity o1, Entity o2) {
                    int result = Integer.compare(o1.WorldY,o2.WorldY);
                    return result;
                }
            });

            //DIBUJAMOS LAS ENTIDADES SORTED
            for(int x=0;x< entityList.size();x++){
                entityList.get(x).draw(graficos2d);
            }
            //Limpiamos la lista para que no se acumulen entidades
            entityList.clear();


            //UI (Se llama aqui para que no esté por debajo de nada )
            ui.draw(graficos2d);

        }


        graficos2d.dispose();

    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playerSe(int i){

        se.setFile(i);
        se.play();

    }
}
