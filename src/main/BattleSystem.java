package main;
import entity.Player;
import monster.shadowStandar;

public class BattleSystem {
    private Player player;
    public shadowStandar monster;
    public GamePanel gp;

    private int turn = 0; //0 = player, 1 = monster


    public BattleSystem(Player player, shadowStandar monster,GamePanel gp) {
        this.gp = gp;
        this.player = player;
        this.monster = monster;
    }

    public void nextTurn() {

        if(player.PLAYERstats.hp<=0){
            System.out.println("Player has died");
            gp.gameState = gp.titleState;
        }
        if(monster.health<=0){
            System.out.println("Monster has died");
            endBattle();
        }
        // Cambiar el turno al siguiente
        if (turn == 0) {
            turn = 1;
        } else {
            turn = 0;
        }
        if(turn == 1){
            monsterAttack();
        }

    }


    public void attack() {
        // Realizar cálculos de daño para un ataque
        if (turn == 0) {
            int playerDMG = player.getAttack();
            System.out.println(playerDMG);
            int monsterDEF = monster.defense;
            int calculatedDMG = playerDMG - monsterDEF;

            if(calculatedDMG<0){
                calculatedDMG = 0;
            }

            monster.health = monster.health - calculatedDMG;
            System.out.println(monster.name + " has recived" + calculatedDMG + " damage");
            nextTurn();
        }
    }

    public void monsterAttack() {
             // Turno del monstruo
            int monsterDMG = monster.attack;
            int playerDEF = player.getDefense();
            int calculatedDMG = monsterDMG - playerDEF;

            // Si el jugador está defendiendo, reduce el daño del monstruo a la mitad
            if (player.defending) {
                calculatedDMG /= 2;
            }
            if(calculatedDMG<0){
                calculatedDMG = 0;
            }

            player.PLAYERstats.hp = player.PLAYERstats.hp - calculatedDMG;
            System.out.println("Player" + " has recived-" + calculatedDMG + " damage");
            nextTurn();

    }

    public void useItem(Object item) {
        // Implementar el uso de un objeto (poción, etc.)
        // Actualizar la interfaz de usuario
    }

    public void defend() {
        if (turn == 0) {
            // El jugador decide defender, reduce el daño recibido en el siguiente turno
            player.defending = true;
            nextTurn();
        }
    }


    public void endBattle(){
        //EXP calc

        player.PLAYERstats.exp = player.PLAYERstats.exp + monster.xpGiven;
        System.out.println("Player has recived " + monster.xpGiven + " exp");
        if(player.PLAYERstats.exp>= player.PLAYERstats.nextLevelExp){
            //Level Up
            player.levelUp();
        }

        //Loot calc

            //Random de dinero
            //Random de Objetos

        //End Battle
        gp.gameState = gp.playState;
    }
}
