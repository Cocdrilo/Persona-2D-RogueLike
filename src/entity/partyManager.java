package entity;

import main.GamePanel;
import monster.monsterData;
import monster.shadowStandar;

import java.util.ArrayList;

/**
 * This class manages the player's party, which consists of a leader and a list of party members.
 */
public class partyManager {

    public Player Leader;
    public ArrayList<shadowStandar> partyMembers;
    private GamePanel gp;

    /**
     * Constructs a party manager with the specified leader and GamePanel.
     *
     * @param Leader The leader of the party.
     * @param gp     The GamePanel associated with the party manager.
     */
    public partyManager(Player Leader, GamePanel gp) {
        this.Leader = Leader;
        this.partyMembers = new ArrayList<>();
        this.gp = gp;
        //addMonsterToParty("Mascara Sonriente");
        //addMonsterToParty("Cuervo Oscuro");
    }

    /**
     * Adds a monster to the party with the specified monster name.
     * If the party has less than 3 members and a matching monster is found, it's added to the party.
     *
     * @param monsterName The name of the monster to add to the party.
     */
    public void addMonsterToParty(String monsterName) {
        if (partyMembers.size() < 3) {
            ArrayList<monsterData> availableMonsters = gp.monsterManager.getMonsters();
            for (monsterData monster : availableMonsters) {
                if (monster.name.equals(monsterName)) {
                    partyMembers.add(new shadowStandar(gp, monster));
                    System.out.println(monsterName + " ha sido añadido al grupo.");
                    return; // Sal del bucle una vez que hayas agregado un miembro.
                }
            }
            System.out.println("No se pudo encontrar un monstruo con el nombre: " + monsterName);
        } else {
            System.out.println("El grupo ya tiene 3 miembros, no se puede agregar más.");
        }
    }

    /**
     * Removes a monster from the party.
     *
     * @param monster The shadowStandar monster to remove from the party.
     */
    public void removeMonsterFromParty(shadowStandar monster) {
        partyMembers.remove(monster);
    }
    //Debug Print Party

    /**
     * Prints information about the party, including the leader and all party members.
     */
    public void printParty() {
        System.out.println("Party Leader: " + Leader.name);
        System.out.println("Party Members: ");
        for (shadowStandar member : partyMembers) {
            System.out.println(member.name);
        }
    }

}
