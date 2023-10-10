package main;
import battleNeeds.superMagic;
import entity.Entity;
import entity.Player;
import entity.partyManager;
import monster.shadowStandar;

public class BattleSystem {
    public partyManager party;
    public shadowStandar monster;
    public GamePanel gp;

    private int turn = 0; // 0 = player, 1 = monster

    public BattleSystem(partyManager party, shadowStandar monster, GamePanel gp) {
        this.party = party; // Asigna la party
        this.monster = monster;
        this.gp = gp;
        printLeaderStats();
    }
    public void printLeaderStats(){
        System.out.println("Player HP: "+party.Leader.stats.hp);
        System.out.println("Player MP: "+party.Leader.stats.mp);
        System.out.println("Player STR: "+party.Leader.stats.str);
        System.out.println("Player MAG: "+party.Leader.stats.mag);
    }

    public void nextTurn() {

        if(party.Leader.stats.hp<=0){
            System.out.println("Player has died");
            gp.gameState = gp.titleState;
        }
        if(monster.stats.hp<=0){
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
            attack(monster,party.Leader);
        }

    }
    public void attack(Player attacker, shadowStandar target) {
        String weaponDmgType = attacker.getWeaponDmgType();
        System.out.println(attacker.name + " attacks " + target.name + " with " + weaponDmgType);
        int DmgPreModifier = calculateDamage(attacker, target, weaponDmgType);
        System.out.println("Player DMG " + attacker.stats.str);
        System.out.println("DmgPreModifier: " + DmgPreModifier);

        if (target.isRepelled(weaponDmgType)) {
            handleRepelledDamage(target, DmgPreModifier,weaponDmgType);
        } else {
            System.out.println("Player hp preDMg "+ target.stats.hp);
            target.stats.hp= target.stats.hp - calculateDamage(attacker,target, weaponDmgType);
            System.out.println("Player hp postDMg "+ target.stats.hp);
        }
        nextTurn();
    }
    public void attack(shadowStandar attacker, shadowStandar target) {
        String weaponDmgType = attacker.getAttackType();
        System.out.println(attacker.name + " attacks " + target.name + " with " + weaponDmgType);
        int DmgPreModifier = calculateDamage(attacker, target, weaponDmgType);
        System.out.println("Player DMG " + attacker.stats.str);
        System.out.println("DmgPreModifier: " + DmgPreModifier);

        if (target.isRepelled(weaponDmgType)) {
            handleRepelledDamage(target, DmgPreModifier,weaponDmgType);
        } else {
            System.out.println("Player hp preDMg "+ target.stats.hp);
            target.stats.hp= target.stats.hp - calculateDamage(attacker,target, weaponDmgType);
            System.out.println("Player hp postDMg "+ target.stats.hp);
        }
        nextTurn();
    }
    public void attack(shadowStandar attacker, Player target) {
        String weaponDmgType = attacker.getAttackType();
        int DmgPreModifier = calculateDamage(attacker, target, weaponDmgType);
        System.out.println("Player DMG " + attacker.stats.str);
        System.out.println("DmgPreModifier: " + DmgPreModifier);

        if (target.isRepelled(weaponDmgType)) {
            handleRepelledDamage(target, DmgPreModifier,weaponDmgType);
        } else {
            System.out.println("Player hp preDMg "+ target.stats.hp);
            target.stats.hp= target.stats.hp - calculateDamage(attacker,target, weaponDmgType);
            System.out.println("Player hp postDMg "+ target.stats.hp);
        }
        nextTurn();
    }

    // Hacer para monster->player y monster monster

    private int calculateDamage(Entity attacker, Entity target, String weaponDmgType) {
        int targetDEF = target.getDefense();
        int DmgPreModifier;

        if (attacker instanceof Player) {
            DmgPreModifier = ((Player) attacker).getPhysAttack(targetDEF, ((Player) attacker).stats.weapon.atk, ((Player) attacker).stats.str);
        } else if (attacker instanceof shadowStandar) {
            DmgPreModifier = ((shadowStandar)attacker).getPhysAttack(targetDEF, attacker.stats.str);
        } else {
            // Manejar el caso en el que attacker no sea ni Player ni Monster
            DmgPreModifier = 0;
        }

        if (target.isWeak(weaponDmgType)) {
            DmgPreModifier *= 2;
        } else if (target.isResistant(weaponDmgType)) {
            DmgPreModifier /= 2;
        } else if (target.isNull(weaponDmgType)) {
            DmgPreModifier = 0;
        }

        return Math.max(0, DmgPreModifier); // Ensure damage is non-negative
    }

    private void handleRepelledDamage(Entity target, int DmgPreModifier, String weaponDmgType) {
        if (target.isWeak(weaponDmgType)) {
            DmgPreModifier *= 2;
        } else if (target.isResistant(weaponDmgType)) {
            DmgPreModifier /= 2;
        } else if (target.isNull(weaponDmgType)) {
            DmgPreModifier = 0;
        }

        target.stats.hp -= DmgPreModifier;
        System.out.println(target.name + " has received " + DmgPreModifier + " damage");
    }



    /*
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
                int damage = player.getMagicAttack(monster.getDefense(),selectedSpell.damage,player.PLAYERstats.mag);;
                // Puedes agregar lógica adicional aquí para diferentes tipos de hechizos

                // Aplicar los efectos del hechizo al enemigo (monstruo)
                if (turn == 0) {
                    monster.stats.hp -= damage;
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
     */


    public void endBattle(){
        //EXP calc

        party.Leader.stats.exp = party.Leader.stats.exp + monster.xpGiven;
        System.out.println("Player has recived " + monster.xpGiven + " exp");

        //Loot calc

        //Random de dinero
        //Random de Objetos

        if(party.Leader.stats.exp>= party.Leader.stats.nextLevelExp){
            //Level Up
            party.Leader.levelUp();
        }
        else {
            gp.gameState = gp.playState;
        }

    }
}
