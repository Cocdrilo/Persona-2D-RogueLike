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

        // Gestionar el turno actual (jugador o monstruo)
        if(player.PLAYERstats.hp!=0 && monster.health!=0){
            turn ++;
            if(turn >= 2){
                turn = 0;
            }

        }
        else if (player.PLAYERstats.hp <= 0) {

            //GameOver State

        }
        else if (monster.health <= 0){

            endBattle();

        }
    }

    public void attack() {
        // Realizar cálculos de daño para un ataque
        if(turn == 0){
            int playerDMG = player.getAttack();
            int monsterDEF = monster.defense;
            monster.health = monster.health - (playerDMG - monsterDEF);
        } else if (turn == 1) {
            int monsterDMG = monster.attack;
            int playerDEF = player.getDefense();
            player.PLAYERstats.hp = player.PLAYERstats.hp - (monsterDMG - playerDEF);
        }
        // Actualizar la interfaz de usuario
        }

    public void useItem(Object item) {
        // Implementar el uso de un objeto (poción, etc.)
        // Actualizar la interfaz de usuario
    }

    public void defend() {
        // Implementar la acción de defensa
        // Actualizar la interfaz de usuario
    }

    public void endBattle(){
        //EXP calc

        player.PLAYERstats.exp = player.PLAYERstats.exp + monster.xpGiven;
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
