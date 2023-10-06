package monster;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class monsterData {

    public String name;
    public int hp;
    public int maxHp;
    public int str;
    public int agi;
    public int mag;
    public int vit;
    public int lvl;
    public int xpGiven;
    public String attackType;
    public String combatImagePath;
    public String[] weaknesses;
    public String[] resistances;
    public String[] nulls;
    public String[] repells;


    // Constructor predeterminado sin argumentos
    public monsterData() {
    }

    // Constructor con argumentos anotado con @JsonCreator
    @JsonCreator
    public monsterData(
            @JsonProperty("name") String name,
            @JsonProperty("health") int health,
            @JsonProperty("str") int str,
            @JsonProperty("agi") int agi,
            @JsonProperty("mag") int mag,
            @JsonProperty("vit") int vit,
            @JsonProperty("lvl") int lvl,
            @JsonProperty("xpGiven") int xpGiven,
            @JsonProperty("attackType") String attackType,
            @JsonProperty("combatImagePath") String combatImagePath,
            @JsonProperty("weaknesses") String[] weaknesses,
            @JsonProperty("resistances") String[] resistances,
            @JsonProperty("nulls") String[] nulls,
            @JsonProperty("repells") String[] repells
    ) {
        this.name = name;
        this.hp = health;
        this.maxHp = this.hp;
        this.str = str;
        this.agi = agi;
        this.mag = mag;
        this.vit = vit;
        this.lvl = lvl;
        this.xpGiven = xpGiven;
        this.attackType = attackType;
        this.combatImagePath = combatImagePath;

        this.weaknesses = weaknesses;
        this.resistances = resistances;
        this.nulls = nulls;
        this.repells = repells;
    }
}
