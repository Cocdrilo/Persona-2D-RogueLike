package main;

import Object.Consumables.*;
import battleNeeds.superMagic;
import entity.Entity;
import entity.Player;
import entity.partyManager;
import monster.shadowStandar;
import negotiation.NegotiationSystem;

import java.util.ArrayList;
import java.util.Random;

public class BattleSystem {
    public partyManager party;
    public shadowStandar monster;
    public GamePanel gp;
    public NegotiationSystem negotiationSystem;

    private int turn = 0; // 0 = player, 1 = monster
    public int pressTurn = 8; // 2 turns per action, if hit weakness or crit 1 turn per action
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
        pressTurn = 8;
        System.out.println(pressTurn);

        if (party.Leader.stats.hp <= 0) {
            System.out.println("Player has died");
            gp.gameState = gp.titleState;
        }
        if (monster.stats.hp <= 0) {
            System.out.println("Monster has died");
            endBattle();
        }
        for (int i = 0; i < partyMembers.size(); i++) {
            if (partyMembers.get(i).stats.hp <= 0) {
                partyMembers.get(i).stats.hp = 0;
                System.out.println(partyMembers.get(i).name + " has died");
                partyMembers.remove(i);
            }
        }
        // Cambiar el turno al siguiente
        if (turn == 0) {
            turn = 1;
        } else {
            turn = 0;
        }
        if (turn == 1) {
            monsterAI();
        }

    }

    public void attack(Entity attacker, Entity target) {
        String weaponDmgType = "";

        if (attacker instanceof Player) {
            weaponDmgType = ((Player) attacker).getWeaponDmgType();
        } else if (attacker instanceof shadowStandar) {
            weaponDmgType = ((shadowStandar) attacker).getAttackType();
        }

        System.out.println(attacker + " has attacked " + target + " with " + weaponDmgType);

        target.stats.hp = target.stats.hp - calculateDamage(attacker, target, weaponDmgType);

        if (pressTurn <= 0 || target.stats.hp <= 0) {
            pressTurn = 8;
            nextTurn();
        }

        currentPartyMemberIndex++;
        if (currentPartyMemberIndex >= partyMembers.size()) {
            currentPartyMemberIndex = 0; // Reiniciar al primer miembro del partido
        }
    }

    public void monsterAttack(Entity attacker, Entity target) {
        String weaponDmgType = "";

        if (attacker instanceof Player) {
            weaponDmgType = ((Player) attacker).getWeaponDmgType();
        } else if (attacker instanceof shadowStandar) {
            weaponDmgType = ((shadowStandar) attacker).getAttackType();
        }

        System.out.println(attacker + " has attacked " + target + " with " + weaponDmgType);

        target.stats.hp = target.stats.hp - calculateDamage(attacker, target, weaponDmgType);

    }


    private int calculateDamage(Entity attacker, Entity target, String weaponDmgType) {
        int targetDEF = target.getDefense();
        int DmgPreModifier;

        if (attacker instanceof Player) {
            DmgPreModifier = ((Player) attacker).getPhysAttack(targetDEF, ((Player) attacker).stats.weapon.atk, ((Player) attacker).stats.str);
        } else if (attacker instanceof shadowStandar) {
            DmgPreModifier = ((shadowStandar) attacker).getPhysAttack(targetDEF, attacker.stats.str);
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
        } else {
            pressTurn -= 2;
        }
    }

    private void handleDamageAndPressTurn(Entity attacker, Entity target, int damage, superMagic selectedSpell) {
        damage = handleDamageAndPressTurnBasedOnWeakness(attacker, target, damage, selectedSpell);
        if (damage > 0) {
            if (target instanceof Player targetPlayer) {
                targetPlayer.stats.hp -= damage;
            }
            if (target instanceof shadowStandar targetMonster) {
                targetMonster.stats.hp -= damage;
            }
            System.out.println(target.name + " has received " + damage + " damage from " + selectedSpell.name);
        } else {
            System.out.println("No damage dealt by " + selectedSpell.name);
        }
    }

    private int handleDamageAndPressTurnBasedOnWeakness(Entity attacker, Entity target, int damage, superMagic selectedSpell) {
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
        } else {
            pressTurn -= 2;
        }
        return damage;
    }


    private void handleRepelledDamage(Entity target, int DmgPreModifier, String weaponDmgType) {
        if (target.isWeak(weaponDmgType)) {
            DmgPreModifier *= 2;
        } else if (target.isResistant(weaponDmgType)) {
            DmgPreModifier /= 2;
        } else if (target.isNull(weaponDmgType)) {
            DmgPreModifier = 0;
        }
        pressTurn -= 3;

        target.stats.hp -= DmgPreModifier;
        System.out.println(target.name + " has received " + DmgPreModifier + " damage");
    }


    private superMagic selectSpell(Entity attacker) {
        ArrayList<superMagic> entitySpells = attacker.getSpells();
        if (gp.ui.commandNum2 >= 0 && gp.ui.commandNum2 < entitySpells.size()) {
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
        if (pressTurn <= 0 || target.stats.hp <= 0) {
            nextTurn();
        }
        // Cambiar al siguiente miembro del partido
        currentPartyMemberIndex++;
        if (currentPartyMemberIndex >= partyMembers.size()) {
            currentPartyMemberIndex = 0; // Reiniciar al primer miembro del partido
        }
    }

    public void monsterUseMagic(Entity attacker, Entity target, superMagic selectedSpell) {
        //Enemy monsters Dont Use Mana

        int damage = calculateMagicDamage(attacker, target, selectedSpell);

        handleDamageAndPressTurn(attacker, target, damage, selectedSpell);
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

    public void monsterAI() {

        superMagic selectedSpell = null;
        Entity target = null;
        int maxPhysDmg = 0;
        int maxMagicDmg = 0;
        do {
            int realPressTurn = pressTurn;
            System.out.println("Monster AI - Calculating damage for each party member and spell:");
            System.out.println("Monster AI - Press Turn: " + pressTurn);

            for (Entity partyMember : partyMembers) {
                if (partyMember instanceof Player targetPlayer) {
                    int physicalDamage = calculateDamage(monster, targetPlayer, monster.getAttackType());
                    if (physicalDamage > maxPhysDmg) {
                        maxPhysDmg = physicalDamage;
                        target = targetPlayer;
                    }
                }
                if (partyMember instanceof shadowStandar targetMonster) {
                    int physicalDamage = calculateDamage(monster, targetMonster, monster.getAttackType());
                    if (physicalDamage > maxPhysDmg) {
                        maxPhysDmg = physicalDamage;
                        target = targetMonster;
                    }
                }

                for (int i = 0; i < monster.spells.size(); i++) {
                    superMagic spell = monster.spells.get(i);
                    int magicalDamage = calculateMagicDamage(monster, partyMember, spell);
                    magicalDamage = handleDamageAndPressTurnBasedOnWeakness(monster, partyMember, magicalDamage, spell);
                    if (magicalDamage > maxMagicDmg) {
                        maxMagicDmg = magicalDamage;
                        selectedSpell = spell;
                        target = partyMember;
                    }

                    System.out.println("  - Damage to " + partyMember.name + " from " + spell.name + " is " + magicalDamage);
                }
            }

            System.out.println("Monster AI - Selected target: " + target.name);
            System.out.println("Monster AI - Max Physical Damage: " + maxPhysDmg);
            System.out.println("Monster AI - Max Magic Damage: " + maxMagicDmg);

            // Encuentra al miembro al que el monstruo le haría más daño
            // Decide si atacar físicamente o mágicamente
            int physicalDamage = calculateDamage(monster, target, monster.getAttackType());
            int magicalDamage = calculateMagicDamage(monster, target, selectedSpell);

            System.out.println("Monster AI - Calculated Physical Damage: " + physicalDamage);
            System.out.println("Monster AI - Calculated Magic Damage: " + magicalDamage);

            //Los cáculos de IA Gastan turnos de PressTurn así que los Hardcodeamos
            pressTurn = realPressTurn;

            if (physicalDamage > magicalDamage) {
                if (target instanceof Player targetPlayer) {
                    monsterAttack(monster, targetPlayer);
                }
                if (target instanceof shadowStandar targetMonster) {
                    monsterAttack(monster, targetMonster);
                }
            } else {
                monsterUseMagic(monster, target, selectedSpell);
            }
            System.out.println("Monster AI - Press Turn: " + pressTurn);

        } while (pressTurn > 0);
        nextTurn();
    }

    public void useItem(Entity item) {
        if (item instanceof OBJ_Potion_Health healthPotion) {
            healthPotion.battleUse(partyMembers.get(currentPartyMemberIndex));
        }
    }

    public void defend() {
        if (turn == 0) {
            // El jugador decide defender, reduce el daño recibido en el siguiente turno
            party.Leader.defending = true;
            nextTurn();
        }
    }

    public void fleeFromBattle() {
        //Implementar el escape de la batalla
        Random random = new Random();
        int randomNum = random.nextInt(100);
        if (randomNum < 50) {
            System.out.println("Player has escaped");
            gp.Asetter.respawnMonster();
            gp.gameState = gp.playState;
        } else {
            System.out.println("Player has failed to escape");
            nextTurn();
        }
    }

    public void negotiateMonster() {
        //Implementar la negociacion con el monstruo
        negotiationSystem = new NegotiationSystem(this);
        negotiationSystem.startNegotiation();
        //gp.gameState = gp.negotiationState;
    }


    public void endBattle() {
        //EXP calc

        party.Leader.stats.exp = party.Leader.stats.exp + monster.xpGiven;
        System.out.println("Player has recived " + monster.xpGiven + " exp");

        //Loot calc

        //Random de dinero
        //Random de Objetos
        gp.Asetter.respawnMonster();

        if (party.Leader.stats.exp >= party.Leader.stats.nextLevelExp) {
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
