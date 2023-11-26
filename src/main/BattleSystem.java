package main;

import Object.Consumables.*;
import battleNeeds.BattleAnimations;
import battleNeeds.superMagic;
import entity.Entity;
import entity.Player;
import entity.partyManager;
import monster.shadowStandar;
import negotiation.NegotiationSystem;

import java.util.ArrayList;
import java.util.Random;

/**
 * The BattleSystem class handles turn-based battles between the player's party and monsters.
 */
public class BattleSystem {
    public partyManager party;
    public shadowStandar monster;
    public GamePanel gp;
    public NegotiationSystem negotiationSystem;
    private BattleAnimations battleAnimations;

    private int turn = 0; // 0 = player, 1 = monster
    public int pressTurn = 8; // 2 turns per action, if hit weakness or crit 1 turn per action
    public ArrayList<Entity> partyMembers; // Lista de miembros del partido
    public int currentPartyMemberIndex; // Índice del miembro del partido que está atacando
    public int attackedSlot = 0;
    public boolean playerIsAttacking = false;
    public boolean monsterIsAttacking = false;
    public boolean playerMagic = false;
    public boolean monsterMagic = false;

    /**
     * Constructs a BattleSystem object with the specified party, monster, and GamePanel.
     *
     * @param party   The player's party.
     * @param monster The enemy monster.
     * @param gp      The GamePanel.
     */
    public BattleSystem(partyManager party, shadowStandar monster, GamePanel gp) {
        this.party = party; // Asigna la party
        this.monster = monster;
        this.gp = gp;
        this.partyMembers = new ArrayList<>(); // Inicializa la lista de miembros del partido
        this.partyMembers.add(party.Leader); // Agrega al líder a la lista
        battleAnimations = new BattleAnimations();

        // Agrega a los miembros del partido a la lista
        this.partyMembers.addAll(party.partyMembers);
        this.currentPartyMemberIndex = 0; // Inicialmente, el primer miembro del partido ataca

        if(monster.boss){
            System.out.println("Boss Battle");
            gp.music.stop();
            gp.playMusic(6);
        }
    }

    /**
     * Proceeds to the next turn in the battle, resetting the pressTurn.
     */
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
            return;
        }
        for (int i = partyMembers.size() - 1; i >= 0; i--) {
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

    private void checkForCharacterDeath() {
        // Check if the player's leader has died
        if (party.Leader.stats.hp <= 0) {
            System.out.println("Player has died");
            gp.gameState = gp.titleState; // Or handle game over as needed
        }

        // Check if any party member has died
        for (int i = partyMembers.size() - 1; i >= 0; i--) {
            if (partyMembers.get(i).stats.hp <= 0) {
                partyMembers.remove(i);
                System.out.println(partyMembers.get(i).name + " has died");
                gp.party.partyMembers.remove(i-1);
            }
        }
    }

    /**
     * Handles an attack by an entity against another entity.
     *
     * @param attacker The attacking entity.
     * @param target   The target entity.
     */
    public void attack(Entity attacker, Entity target) {
        String weaponDmgType = "";

        if (attacker instanceof Player) {
            weaponDmgType = ((Player) attacker).getWeaponDmgType();
        } else if (attacker instanceof shadowStandar) {
            weaponDmgType = ((shadowStandar) attacker).getAttackType();
        }

        System.out.println(attacker.name + " has attacked " + target + " with " + weaponDmgType);

        playerIsAttacking = true;

        int damage = calculateDamage(attacker, target, weaponDmgType);

        // Verifica si la entidad objetivo está defendiendo
        if (target.defending) {
            damage /= 2;  // Reduce el daño a la mitad si la entidad está defendiendo
        }

        target.stats.hp = target.stats.hp - damage;

        if (pressTurn <= 0 || target.stats.hp <= 0) {
            pressTurn = 8;
            nextTurn();
        }

        currentPartyMemberIndex++;
        if (currentPartyMemberIndex >= partyMembers.size()) {
            currentPartyMemberIndex = 0; // Reiniciar al primer miembro del partido
        }
    }

    /**
     * Handles a monster's attack against a target entity.
     *
     * @param attacker The monster attacking entity.
     * @param target   The target entity.
     */
    public void monsterAttack(Entity attacker, Entity target) {
        String weaponDmgType = "";

        if (attacker instanceof Player) {
            weaponDmgType = ((Player) attacker).getWeaponDmgType();
        } else if (attacker instanceof shadowStandar) {
            weaponDmgType = ((shadowStandar) attacker).getAttackType();
        }

        System.out.println(attacker.name + " has attacked " + target + " with " + weaponDmgType);

        monsterIsAttacking = true;

        int damage = calculateDamage(attacker, target, weaponDmgType);

        // Verifica si la entidad objetivo está defendiendo
        if (target.defending) {
            damage /= 2;  // Reduce el daño a la mitad si la entidad está defendiendo
        }

        target.stats.hp = target.stats.hp - damage;

    }


    /**
     * Calculates the damage inflicted by an attacker to a target entity based on the weapon's damage type.
     *
     * @param attacker      The attacking entity.
     * @param target        The target entity.
     * @param weaponDmgType The damage type of the weapon.
     * @return The calculated damage value.
     */
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

    /**
     * Handles damage calculation and pressTurn reduction based on weapon damage type and entity weaknesses.
     *
     * @param target        The entity being attacked.
     * @param damage        The calculated damage.
     * @param weaponDmgType The damage type of the attack.
     */
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

    /**
     * Handles damage and press turn based on the attacker's selected spell.
     *
     * @param attacker      The attacking entity.
     * @param target        The target entity.
     * @param damage        The calculated damage value.
     * @param selectedSpell The selected spell.
     */
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
            gp.ui.addMessage("Weakpoint Hitted on " + target.name);
        } else if (target.isResistant(selectedSpell.damageType)) {
            damage /= 2;
            pressTurn -= 2;
            gp.ui.addMessage("Attack resisted by " + target.name);
        } else if (target.isNull(selectedSpell.damageType)) {
            damage = 0;
            pressTurn -= 3;
            gp.ui.addMessage("Attack nulled by " + target.name);
        } else if (target.isRepelled(selectedSpell.damageType)) {
            handleRepelledDamage(attacker, damage, selectedSpell.damageType);
        } else {
            pressTurn -= 2;
        }
        return damage;
    }


    /**
     * Handles damage calculation and pressTurn reduction for attacks that are repelled.
     *
     * @param target         The entity being attacked.
     * @param DmgPreModifier The pre-modified damage.
     * @param weaponDmgType  The damage type of the attack.
     */
    private void handleRepelledDamage(Entity target, int DmgPreModifier, String weaponDmgType) {
        if (target.isWeak(weaponDmgType)) {
            DmgPreModifier *= 2;
            gp.ui.addMessage("The attack was repelled and dealt " + DmgPreModifier + " damage to " + target.name);
        } else if (target.isResistant(weaponDmgType)) {
            DmgPreModifier /= 2;
            gp.ui.addMessage("The attack was repelled and dealt " + DmgPreModifier + " damage to " + target.name);
        } else if (target.isNull(weaponDmgType)) {
            gp.ui.addMessage("The attack was repelled and then nulled");
            DmgPreModifier = 0;
        }
        pressTurn -= 3;

        target.stats.hp -= DmgPreModifier;
        System.out.println(target.name + " has received " + DmgPreModifier + " damage");
    }


    /**
     * Selects a magic spell for the attacker to use based on user input.
     *
     * @param attacker The entity performing the magic attack.
     * @return The selected magic spell, or null if no spell was selected.
     */
    private superMagic selectSpell(Entity attacker) {
        ArrayList<superMagic> entitySpells = attacker.getSpells();
        if (gp.ui.commandNum2 >= 0 && gp.ui.commandNum2 < entitySpells.size()) {
            return entitySpells.get(gp.ui.commandNum2);
        }
        return null;
    }

    /**
     * Performs a magic attack from the attacker to the target entity using a selected spell.
     *
     * @param attacker      The entity performing the magic attack.
     * @param target        The entity being attacked.
     * @param selectedSpell The magic spell used for the attack.
     */
    public void useMagic(Entity attacker, Entity target, superMagic selectedSpell) {
        if(selectedSpell.mpCost > 0){
            if (attacker.stats.mp < selectedSpell.mpCost) {
                System.out.println("Not enough MP to cast " + selectedSpell.name);
                return;
            }

            int damage = calculateMagicDamage(attacker, target, selectedSpell);
            playerMagic = true;

            if(target.defending){
                damage /= 2;
            }

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
        else if(selectedSpell.hpCost>0){
            if (attacker.stats.hp < attacker.stats.maxHp % selectedSpell.hpCost) {
                System.out.println("Not enough HP to cast " + selectedSpell.name);
                return;
            }

            boolean phys = true;
            int damage = calculateMagicDamage(attacker, target, selectedSpell,phys);
            playerMagic = true;

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

    }

    /**
     * Performs a magic attack from the monster to the target entity using a selected spell.
     *
     * @param attacker      The monster performing the magic attack.
     * @param target        The entity being attacked.
     * @param selectedSpell The magic spell used for the attack.
     */
    public void monsterUseMagic(Entity attacker, Entity target, superMagic selectedSpell) {
        //Enemy monsters Dont Use Mana

        int damage = calculateMagicDamage(attacker, target, selectedSpell);
        monsterMagic = true;

        if(target.defending){
            damage /= 2;
        }

        handleDamageAndPressTurn(attacker, target, damage, selectedSpell);
    }


    /**
     * Calculates the damage dealt by an attacker to a target entity using magic spells.
     *
     * @param attacker      The entity performing the magic attack.
     * @param target        The entity being attacked.
     * @param selectedSpell The magic spell used for the attack.
     * @return The calculated damage.
     */
    private int calculateMagicDamage(Entity attacker, Entity target, superMagic selectedSpell) {
        int damage = 0;

        if (attacker instanceof Player playerAttacker) {
            damage = playerAttacker.getMagicAttack(target.getDefense(), selectedSpell.damage, playerAttacker.stats.mag);
            playerAttacker.stats.mp -= selectedSpell.mpCost;
        } else if (attacker instanceof shadowStandar monsterAttacker) {
            damage = monsterAttacker.getMagicAttack(target.getDefense(), selectedSpell.damage, monsterAttacker.stats.mag);
            monsterAttacker.stats.mp -= selectedSpell.mpCost;
        }

        return damage;
    }

    private int calculateMagicDamage(Entity attacker, Entity target, superMagic selectedSpell,boolean isphys) {
        int damage = 0;

        if (attacker instanceof Player playerAttacker) {
            damage = playerAttacker.getMagicAttack(target.getDefense(), selectedSpell.damage, playerAttacker.stats.str);
            playerAttacker.stats.hp -= playerAttacker.stats.maxHp % selectedSpell.hpCost;
        } else if (attacker instanceof shadowStandar monsterAttacker) {
            damage = monsterAttacker.getMagicAttack(target.getDefense(), selectedSpell.damage, monsterAttacker.stats.str);
            monsterAttacker.stats.hp -= monsterAttacker.stats.maxHp % selectedSpell.hpCost;
        }

        return damage;
    }

    /**
     * Handles the AI decision-making for the monster's attacks and spells.
     */
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
                    getTargetSlot(targetPlayer);
                }
                if (target instanceof shadowStandar targetMonster) {
                    monsterAttack(monster, targetMonster);
                    getTargetSlot(targetMonster);
                }
            } else {
                monsterUseMagic(monster, target, selectedSpell);
                getTargetSlot(target);
            }
            System.out.println("Monster AI - Press Turn: " + pressTurn);

            checkForCharacterDeath();

        } while (pressTurn > 0);
        pressTurn = 8;
    }

    public void getTargetSlot(Entity target){
        String nombre = target.name;
        System.out.println(nombre);
        partyMembers.forEach((partyMember) -> {
            if (partyMember.name.equals(nombre)) {
                attackedSlot = partyMembers.indexOf(partyMember);
            }
        });
    }

    /**
     * Uses a health potion item to restore health to a party member.
     *
     * @param item The health potion item to be used.
     */
    public void useItem(Entity item) {
        if (item instanceof OBJ_Potion_Health healthPotion) {
            healthPotion.battleUse(partyMembers.get(currentPartyMemberIndex));
        }
    }

    /**
     * Allows an entity to defend during their turn.
     * Reduces the damage received in the next turn.
     *
     * @param entity The entity defending (e.g., player or monster).
     */
    public void defend(Entity entity) {
        // The entity decides to defend, reduces the damage received in the next turn
        if (!entity.defending) {
            entity.defending = true;
            pressTurn -= 3;
            gp.ui.addMessage(entity.name + " is defending");

            // Cambiar al siguiente miembro del partido
            currentPartyMemberIndex++;
            if (currentPartyMemberIndex >= partyMembers.size()) {
                currentPartyMemberIndex = 0; // Reiniciar al primer miembro del partido
            }
        } else {
            gp.ui.addMessage(entity.name + " is already defending");
        }

    }

    /**
     * Implements the player's attempt to flee from the battle.
     * The outcome of the escape attempt is determined randomly.
     */
    public void fleeFromBattle() {
        //Implementar el escape de la batalla
        if(!monster.boss){
            Random random = new Random();
            int randomNum = random.nextInt(100);
            if (randomNum < 50) {
                System.out.println("Player has escaped");
                gp.Asetter.respawnMonster();
                gp.gameState = gp.playState;
            } else {
                System.out.println("Player has failed to escape");
                gp.ui.addMessage("Player Failed to escape");
                nextTurn();
            }
        }
        else{
            System.out.println("You can't escape from a boss");
            gp.ui.addMessage("You can't escape from a boss");
        }

    }

    /**
     * Initiates the negotiation process with the monster.
     * The NegotiationSystem is responsible for handling the negotiation.
     */
    public void negotiateMonster() {
        if(monster.boss){
            System.out.println("You can't negotiate with a boss");
            gp.ui.addMessage("You can't negotiate with a boss");
        }
        else{
            //Implementar la negociacion con el monstruo
            negotiationSystem = new NegotiationSystem(this);
            negotiationSystem.startNegotiation();
            //gp.gameState = gp.negotiationState;
        }

    }


    /**
     * Ends the battle, granting experience points to the player and party and handling loot.
     */
    public void endBattle() {
        //EXP calc

        party.Leader.stats.exp = party.Leader.stats.exp + monster.xpGiven;
        System.out.println("Player has recived " + monster.xpGiven + " exp");


        //Random de dinero
        Random random = new Random();
        int randomNum = random.nextInt(100);
        if (randomNum < 50) {
            gp.ui.messageList.add("Player has recived " + randomNum + " money");
            gp.player.stats.money = gp.player.stats.money + randomNum;
        }

        for(int i = 0; i < party.partyMembers.size(); i++){
            //Exp /2 por cada miembro del partido para que no esten al mismo level o mas que el Lider y porque se reparte entre los miembros
            party.partyMembers.get(i).stats.exp = party.partyMembers.get(i).stats.exp + (monster.xpGiven / 2 );

            if(party.partyMembers.get(i).stats.exp>= party.partyMembers.get(i).stats.nextLevelExp){
                //Level Up
                party.partyMembers.get(i).levelUp();
                System.out.println(party.partyMembers.get(i).name + " has leveled up");
            }
        }

        if(monster.boss){
            gp.stopMusic();
            gp.playMusic(0);
            gp.Asetter.summonStairs(monster.WorldX,monster.WorldY);
        }

        if (party.Leader.stats.exp >= party.Leader.stats.nextLevelExp) {
            //Level Up
            party.Leader.levelUp();
            System.out.println("Player has leveled up");
            return;
        }

        if(!monster.boss){
            //Random de Objetos
            gp.Asetter.respawnMonster();
        }


        gp.gameState = gp.playState;

        }

    }
