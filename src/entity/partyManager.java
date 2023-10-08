package entity;

import main.GamePanel;
import monster.monsterData;
import monster.shadowStandar;

import java.util.ArrayList;

public class partyManager {

    private Player Leader;
    public ArrayList<shadowStandar> partyMembers;
    private GamePanel gp;

    public partyManager(Player Leader , GamePanel gp) {
        this.Leader = Leader;
        this.partyMembers = new ArrayList<>();
        this.gp = gp;
    }

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

    public void removeMonsterFromParty(shadowStandar monster) {
        partyMembers.remove(monster);
    }
    //Debug Print Party

    public void printParty(){
        System.out.println("Party Leader: "+Leader.name);
        System.out.println("Party Members: ");
        for (shadowStandar member: partyMembers) {
            System.out.println(member.name);
        }
    }

}
