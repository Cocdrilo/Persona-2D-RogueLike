package monster;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents data for a monster, including its name, health, attributes (strength, agility, magic, vitality), level,
 * experience points given upon defeat, attack type, combat image path, weaknesses, resistances, nullifications, repelled elements,
 * and spells.
 */
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
    public String[] spells;


    // Constructor predeterminado sin argumentos
    public monsterData() {
    }

    /**
     * Constructor with arguments annotated with @JsonCreator to deserialize JSON data into a MonsterData object.
     *
     * @param name            The name of the monster.
     * @param health          The health points of the monster.
     * @param str             The strength attribute of the monster.
     * @param agi             The agility attribute of the monster.
     * @param mag             The magic attribute of the monster.
     * @param vit             The vitality attribute of the monster.
     * @param lvl             The level of the monster.
     * @param xpGiven         The experience points given upon defeat.
     * @param attackType      The attack type of the monster.
     * @param combatImagePath The file path to the combat image of the monster.
     * @param weaknesses      An array representing the weaknesses of the monster.
     * @param resistances     An array representing the resistances of the monster.
     * @param nulls           An array representing the nullified elements by the monster.
     * @param repells         An array representing the elements repelled by the monster.
     * @param spells          An array representing the spells cast by the monster.
     */
    @JsonCreator
    public monsterData(@JsonProperty("name") String name, @JsonProperty("health") int health, @JsonProperty("str") int str, @JsonProperty("agi") int agi, @JsonProperty("mag") int mag, @JsonProperty("vit") int vit, @JsonProperty("lvl") int lvl, @JsonProperty("xpGiven") int xpGiven, @JsonProperty("attackType") String attackType, @JsonProperty("combatImagePath") String combatImagePath, @JsonProperty("weaknesses") String[] weaknesses, @JsonProperty("resistances") String[] resistances, @JsonProperty("nulls") String[] nulls, @JsonProperty("repells") String[] repells, @JsonProperty("spells") String[] spells) {
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
        this.spells = spells;
    }
}
