package main;
import battleNeeds.superMagic;
import entity.Entity;
import entity.Player;
import entity.partyManager;
import monster.shadowStandar;

import java.util.ArrayList;
import java.util.List;

public class BattleSystem {
    public partyManager party;
    public shadowStandar monster;
    public GamePanel gp;

    private int turn = 0; // 0 = player, 1 = monster
    private int pressTurn = 8; // 2 turns per action, if hit weakness or crit 1 turn per action
    public ArrayList<Entity> partyMembers; // Lista de miembros del partido
    public int currentPartyMemberIndex; // Índice del miembro del partido que está atacando

    public BattleSystem(partyManager party, shadowStandar monster, GamePanel gp) {
        this.party = party; // Asigna la party
        this.monster = monster;
        this.gp = gp;
        this.partyMembers = new ArrayList<>(); // Inicializa la lista de miembros del partido
        this.partyMembers.add(party.Leader); // Agrega al líder a la lista

        // Agrega a los miembros del partido a la lista
        this.partyMembers.addAll(party.partyMembers);
        this.currentPartyMemberIndex = 0; // Inicialmente, el primer miembro del partido ataca
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
        System.out.println(attacker + " has attacked " + target + " with " + weaponDmgType);

        target.stats.hp= target.stats.hp - calculateDamage(attacker,target, weaponDmgType);


        System.out.println(pressTurn);
        if (pressTurn <= 0 || target.stats.hp <= 0) {
            pressTurn = 8;
            nextTurn();
        }
        currentPartyMemberIndex++;
        if (currentPartyMemberIndex >= partyMembers.size()) {
            currentPartyMemberIndex = 0; // Reiniciar al primer miembro del partido
        }
    }
    public void attack(shadowStandar attacker, shadowStandar target) {
        String weaponDmgType = attacker.getAttackType();
        System.out.println(attacker + " has attacked " + target + " with " + weaponDmgType);

        target.stats.hp= target.stats.hp - calculateDamage(attacker,target, weaponDmgType);

        if (pressTurn <= 0 || target.stats.hp <= 0) {
            pressTurn = 8;
            nextTurn();
        }
        currentPartyMemberIndex++;
        if (currentPartyMemberIndex >= partyMembers.size()) {
            currentPartyMemberIndex = 0; // Reiniciar al primer miembro del partido
        }
    }
    public void attack(shadowStandar attacker, Player target) {
        String weaponDmgType = attacker.getAttackType();
        System.out.println(attacker + " has attacked " + target + " with " + weaponDmgType);

        target.stats.hp= target.stats.hp - calculateDamage(attacker,target, weaponDmgType);

        if (pressTurn <= 0 || target.stats.hp <= 0) {
            pressTurn = 8;
            nextTurn();
        }
        currentPartyMemberIndex++;
        if (currentPartyMemberIndex >= partyMembers.size()) {
            currentPartyMemberIndex = 0; // Reiniciar al primer miembro del partido
        }
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
        handleDamageAndPressTurn(target, DmgPreModifier, weaponDmgType);

        return Math.max(0, DmgPreModifier); // Ensure damage is non-negative
    }

    private void handleDamageAndPressTurn(Entity target, int damage, String weaponDmgType) {
        if (target.isWeak(weaponDmgType)) {
            damage *= 2;
            pressTurn--;
        } else if (target.isResistant(weaponDmgType)) {
            damage /= 2;
            pressTurn -= 2;
        } else if (target.isNull(weaponDmgType)) {
            damage = 0;
            pressTurn -= 3;
        } else if (target.isRepelled(weaponDmgType)) {
            handleRepelledDamage(target, damage, weaponDmgType);
        }else{
            pressTurn-=2;
        }
    }

    private void handleDamageAndPressTurn(Entity attacker, Entity target, int damage, superMagic selectedSpell) {
        if (target.isWeak(selectedSpell.damageType)) {
            damage *= 2;
            pressTurn--;
        } else if (target.isResistant(selectedSpell.damageType)) {
            damage /= 2;
            pressTurn -= 2;
        } else if (target.isNull(selectedSpell.damageType)) {
            damage = 0;
            pressTurn -= 3;
        } else if (target.isRepelled(selectedSpell.damageType)) {
            handleRepelledDamage(attacker, damage, selectedSpell.damageType);
        }else{
            pressTurn-=2;
        }

        if (damage > 0) {
            target.stats.hp -= damage;
            System.out.println(target.name + " has received " + damage + " damage from " + selectedSpell.name);
        } else {
            System.out.println("No damage dealt by " + selectedSpell.name);
        }
    }


    private void handleRepelledDamage(Entity target, int DmgPreModifier, String weaponDmgType) {
        if (target.isWeak(weaponDmgType)) {
            DmgPreModifier *= 2;
        } else if (target.isResistant(weaponDmgType)) {
            DmgPreModifier /= 2;
        } else if (target.isNull(weaponDmgType)) {
            DmgPreModifier = 0;
        }
        pressTurn -=3;

        target.stats.hp -= DmgPreModifier;
        System.out.println(target.name + " has received " + DmgPreModifier + " damage");
    }


    private superMagic selectSpell(Entity attacker) {
        ArrayList<superMagic> entitySpells = attacker.getSpells();
        if(gp.ui.commandNum2 >=0 &&gp.ui.commandNum2 < entitySpells.size()){
            return entitySpells.get(gp.ui.commandNum2);
        }
        return null;
    }

    public void useMagic(Entity attacker, Entity target, superMagic selectedSpell) {
        if (attacker.stats.mp < selectedSpell.mpCost) {
            System.out.println("Not enough MP to cast " + selectedSpell.name);
            return;
        }

        int damage = calculateMagicDamage(attacker, target, selectedSpell);

        handleDamageAndPressTurn(attacker, target, damage, selectedSpell);
        if(pressTurn <= 0 || target.stats.hp <= 0){
            pressTurn = 8;
            nextTurn();
        }
        // Cambiar al siguiente miembro del partido
        currentPartyMemberIndex++;
        if (currentPartyMemberIndex >= partyMembers.size()) {
            currentPartyMemberIndex = 0; // Reiniciar al primer miembro del partido
        }
    }

    private int calculateMagicDamage(Entity attacker, Entity target, superMagic selectedSpell) {
        int damage = 0;

        if (attacker instanceof Player playerAttacker) {
            damage = playerAttacker.getMagicAttack(target.getDefense(), selectedSpell.damage, playerAttacker.stats.mag);
        } else if (attacker instanceof shadowStandar monsterAttacker) {
            damage = monsterAttacker.getMagicAttack(target.getDefense(), selectedSpell.damage, monsterAttacker.stats.mag);
        }

        return damage;
    }


    public void useItem(Object item) {
        // Implementar el uso de un objeto (poción, etc.)
        // Actualizar la interfaz de usuario
    }

    public void defend() {
        if (turn == 0) {
            // El jugador decide defender, reduce el daño recibido en el siguiente turno
            party.Leader.defending = true;
            nextTurn();
        }
    }


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
        /*
        for(int i = 0; i < party.partyMembers.size(); i++){
            if(party.partyMembers.get(i).stats.exp>= party.partyMembers.get(i).stats.nextLevelExp){
                //Level Up
                party.partyMembers.get(i).levelUp();
            }
        }
         */
        else {
            gp.gameState = gp.playState;
        }

    }
}
