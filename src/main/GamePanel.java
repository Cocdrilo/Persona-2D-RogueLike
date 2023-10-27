package main;

import battleNeeds.SpellManager;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import entity.partyManager;
import monster.MonsterManager;
import negotiation.NegotiationManager;
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
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12 ;
    public final int screenWidth = tileSize * maxScreenCol; //768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    //SETTINGS DEL MUNDO
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public BattleSystem battleSystem;

    //FPS
    int FPS = 60;

    //ScreenVars
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;

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
    public SpellManager spellManager = SpellManager.getInstance();
    public MonsterManager monsterManager = MonsterManager.getInstance();
    public NegotiationManager negotiationManager = NegotiationManager.getInstance();
    SaveLoad saveLoad = new SaveLoad(this);

    //ENTITY AND OBJECT
    public Player player = new Player(this,keyH);
    public partyManager party = new partyManager(player,this);
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
    public final int levelUpState = 8;
    public final int magicMenuState = 9;
    public final int battleItemsState = 10;


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground((Color.black));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setFullScreen() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        Main.window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        screenWidth2 = (int) width;
        screenHeight2 = (int) height;
    }



    public void setUpGame(){

        Asetter.setObject();
        Asetter.setNPC();
        Asetter.setMonster();

        //playMusic(0);
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_ARGB);
        g2= (Graphics2D) tempScreen.getGraphics();

        setFullScreen();
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
                drawToTempScreen();
                drawToScreen();
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
    public void drawToTempScreen() {
        // Limpia el búfer antes de dibujar
        g2.clearRect(0, 0, screenWidth, screenHeight);

        // PANTALLA DE TITULOS
        if (gameState == titleState) {
            ui.draw(g2);
        } else {
            // Dibuja los tiles de fondo primero
            tileM.draw(g2);

            // Organiza las entidades en una lista para ordenarlas
            ArrayList<Entity> entityList = new ArrayList<>();

            // Agrega al jugador y a otras entidades necesarias
            entityList.add(player);
            for (Entity entity : npc) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            for (Entity entity : obj) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            for (Entity monster : monsters) {
                if (monster != null) {
                    entityList.add(monster);
                }
            }

            // Ordena las entidades por su posición en el eje Y
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity o1, Entity o2) {
                    int result = Integer.compare(o1.WorldY, o2.WorldY);
                    return result;
                }
            });

            // Dibuja las entidades ordenadas
            for (int entitySorted = 0; entitySorted < entityList.size(); entitySorted++) {
                entityList.get(entitySorted).draw(g2);
            }

            // Dibuja la interfaz de usuario encima de todo
            ui.draw(g2);

            // Limpia la lista de entidades
            entityList.clear();
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
