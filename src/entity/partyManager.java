package entity;

import main.GamePanel;
import monster.monsterData;
import monster.shadowStandar;

import java.util.ArrayList;

public class partyManager {

    private Player Leader;
    private ArrayList<shadowStandar> partyMembers;
    private GamePanel gp;

    public partyManager(Player Leader, ArrayList<shadowStandar> partyMembers, GamePanel gp) {
        this.Leader = Leader;
        this.partyMembers = partyMembers;
        this.gp = gp;
    }
    public partyManager(Player Leader , GamePanel gp) {
        this.Leader = Leader;
        this.partyMembers = new ArrayList<>();
        this.gp = gp;
    }

    public void addMonsterToParty(String monsterName) {
        ArrayList<monsterData> availableMonsters = gp.monsterManager.getMonsters();
        for (monsterData monster: availableMonsters) {
            if (monster.name.equals(monsterName)) {
                partyMembers.add(new shadowStandar(gp,monster));
            }
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
