package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{
    //Screen Settings
    final int originalTileSize = 16; //16*16
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol =20;
    public final int maxScreenRow = 12 ;
    public final int screenWidth = tileSize * maxScreenCol; //960 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    //SETTINGS DEL MUNDO
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    //PARA PANTALLA COMPLETA
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;

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
    public final int enterMenuState = 4;
    public final int combatState = 5;
    public final int statusState = 6;
    public final int inventoryState = 7;




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

        tempScreen = new BufferedImage(screenWidth , screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        setFullScreen();


    }

    public void setFullScreen(){

        //Cogemos la pantalla
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        //Cogemos la altura y anchura de la pantalla
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();

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
                //repaint();
                drawToTempScreen(); // dibuja todo a una imagen buffered
                drawToScreen(); // dibuja la imagen buffered a la pantalla
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
            for (int npcInArray=0;npcInArray< npc.length;npcInArray++){
                if(npc[npcInArray]!=null){
                    npc[npcInArray].update();
                }
            }
            for(int monsterInArray=0;monsterInArray<monsters.length;monsterInArray++){
                if(monsters[monsterInArray]!=null){
                    monsters[monsterInArray].update();
                }
            }

        }
        if(gameState==pauseState){
            //NOTHING
        }
    }

    public void drawToTempScreen(){
        //PANTALLA DE TITULOS
        if(gameState==titleState){

            ui.draw(g2);

        }

        //OTHERS
        else{
            //Tile Dibujo (Ponemos antes para que este layereado debajo
            tileM.draw(g2);

            //ENTITY ARRAYLIST

            entityList.add(player);

            for(int npcInArray=0;npcInArray< npc.length;npcInArray++){
                if(npc[npcInArray]!=null){
                    entityList.add(npc[npcInArray]);
                }
            }

            for(int objInArray=0;objInArray< obj.length;objInArray++){
                if(obj[objInArray]!=null){
                    entityList.add(obj[objInArray]);
                }
            }
            for(int monsterInArray=0;monsterInArray<monsters.length;monsterInArray++){
                if(monsters[monsterInArray]!=null){
                    entityList.add(monsters[monsterInArray]);
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
            for(int entitySorted=0;entitySorted< entityList.size();entitySorted++){
                entityList.get(entitySorted).draw(g2);
            }
            //Limpiamos la lista para que no se acumulen entidades
            entityList.clear();


            //UI (Se llama aqui para que no esté por debajo de nada )
            ui.draw(g2);

        }
    }


    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null );
        g.dispose();
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
