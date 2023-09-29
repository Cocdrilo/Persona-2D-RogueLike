package main;
import battleNeeds.superMagic;
import entity.Player;
import monster.shadowStandar;

import java.util.ArrayList;

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
        String weaponDmgType = player.getWeaponDmgType();
        int playerDMG = player.getAttack();
        int monsterDEF = monster.defense;
        int calculatedDMG = playerDMG - monsterDEF;

        // Si el monstruo es débil al tipo de daño del arma, el daño se duplica
        if (monster.isWeak(weaponDmgType)) {
            calculatedDMG *= 2;
            System.out.println("Weak");
        }

        // Si el monstruo es resistente al tipo de daño del arma, el daño se reduce a la mitad
        if (monster.isResistant(weaponDmgType)) {
            calculatedDMG /= 2;
            System.out.println("resistant");
        }

        // Si el monstruo es inmune al tipo de daño del arma, el daño es 0
        if (monster.isNull(weaponDmgType)) {
            calculatedDMG = 0;
            System.out.println("null");
        }

        // Si el monstruo repela el tipo de daño del arma, el calculated dmg te lo haces a ti mismo Falta Implementar Logica de weak resistant null y repel dentro de cuando se repele a ti mismo
        if (monster.isRepelled(weaponDmgType)) {
            // Aplica la lógica al propio personaje
            if (player.isWeak(weaponDmgType)) {
                calculatedDMG *= 2;
            }
            if (player.isResistant(weaponDmgType)) {
                calculatedDMG /= 2;
            }
            if (player.isNull(weaponDmgType)) {
                calculatedDMG = 0;
            }
            // Aquí puedes aplicar el daño al propio personaje
            player.PLAYERstats.hp -= calculatedDMG;
            System.out.println("Player" + " has recived" + calculatedDMG + " damage");
            nextTurn();
            return;
        }

        if(calculatedDMG<0){
            calculatedDMG = 0;
        }
        monster.health = monster.health - calculatedDMG;
        System.out.println(monster.name + " has recived" + calculatedDMG + " damage");
        nextTurn();
    }

    public void monsterAttack() {
             // Turno del monstruo
            String attackType = monster.getAttackType();
            int monsterDMG = monster.attack;
            int playerDEF = player.getDefense();
            int calculatedDMG = monsterDMG - playerDEF;

            if (player.isWeak(attackType)) {
                calculatedDMG *= 2;
                System.out.println("Weak");
            }
            if (player.isResistant(attackType)) {
                calculatedDMG /= 2;
                System.out.println("resistant");
            }
            if (player.isNull(attackType)) {
                calculatedDMG = 0;
                System.out.println("null");
            }
            if(player.isRepelled(attackType)){
                //Aplica la logica al propio monstruo
                if (monster.isWeak(attackType)) {
                    calculatedDMG *= 2;
                }
                if (monster.isResistant(attackType)) {
                    calculatedDMG /= 2;
                }
                if (monster.isNull(attackType)) {
                    calculatedDMG = 0;
                }
                // Aquí puedes aplicar el daño al propio monstruo
                monster.health -= calculatedDMG;
                System.out.println(monster.name + " has recived" + calculatedDMG + " damage");
                nextTurn();
                return;
            }

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


    public void useMagic() {
        // Obtener el hechizo seleccionado por el jugador
        ArrayList<superMagic> playerSpells = player.getSpells();
        if (gp.ui.commandNum2 >= 0 && gp.ui.commandNum2 < playerSpells.size()) {
            superMagic selectedSpell = playerSpells.get(gp.ui.commandNum2);

            // Verificar si el jugador tiene suficiente MP para lanzar el hechizo
            if (player.PLAYERstats.mp >= selectedSpell.mpCost) {
                // Restar el costo de MP al jugador
                player.PLAYERstats.mp -= selectedSpell.mpCost;

                // Realizar cálculos de daño o efectos del hechizo según sea necesario
                int damage = selectedSpell.damage;
                // Puedes agregar lógica adicional aquí para diferentes tipos de hechizos

                // Aplicar los efectos del hechizo al enemigo (monstruo)
                if (turn == 0) {
                    monster.health -= damage;
                    System.out.println(monster.name + " has recived " + damage + " damage from " + selectedSpell.name);
                }
                // Aplicar los efectos del hechizo al jugador
                else if (turn == 1) {
                    player.PLAYERstats.hp -= damage;
                    System.out.println("Player has recived " + damage + " damage from " + selectedSpell.name);
                }

                // Actualizar la interfaz de usuario para reflejar los cambios
                // Puedes agregar código aquí para mostrar mensajes o actualizaciones visuales

                // Cambiar al siguiente turno
                nextTurn();
            } else {
                System.out.println("Not enough MP to cast " + selectedSpell.name);
            }
        }
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

        //Loot calc

        //Random de dinero
        //Random de Objetos

        if(player.PLAYERstats.exp>= player.PLAYERstats.nextLevelExp){
            //Level Up
            player.levelUp();
        }
        else {
            gp.gameState = gp.playState;
        }

    }
}
