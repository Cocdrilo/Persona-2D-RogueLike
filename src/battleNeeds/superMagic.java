package battleNeeds;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a super magic spell that can be used in the game.
 */
public class superMagic {

    public String name;
    public int mpCost;
    public int hpCost;
    public int damage;
    public int hit;
    public String damageType;
    public String description;

    /**
     * The default constructor of the class.
     */
    public superMagic() {
    }

    /**
     * The constructor of the class that takes arguments.
     *
     * @param name        the name of the spell
     * @param mpCost      the amount of MP required to cast the spell
     * @param hpCost      the amount of HP required to cast the spell
     * @param damage      the amount of damage dealt by the spell
     * @param hit         the accuracy of the spell
     * @param damageType  the type of damage dealt by the spell
     * @param description a description of the spell
     */
    @JsonCreator
    public superMagic(@JsonProperty("name") String name, @JsonProperty("mpCost") int mpCost, @JsonProperty("hpCost") int hpCost, @JsonProperty("damage") int damage, @JsonProperty("hit") int hit, @JsonProperty("damageType") String damageType, @JsonProperty("description") String description) {
        this.name = name;
        this.mpCost = mpCost;
        this.hpCost = hpCost;
        this.damage = damage;
        this.hit = hit;
        this.damageType = damageType;
        this.description = description;
    }

}
