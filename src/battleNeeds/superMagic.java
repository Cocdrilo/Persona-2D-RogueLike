package battleNeeds;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class superMagic {

    public String name;
    public int mpCost;
    public int hpCost;
    public int damage;
    public int hit;
    public String damageType;
    public String description;

    // Constructor predeterminado sin argumentos
    public superMagic() {
    }

    // Constructor con argumentos anotado con @JsonCreator
    @JsonCreator
    public superMagic(@JsonProperty("name") String name,
                      @JsonProperty("mpCost") int mpCost,
                      @JsonProperty("hpCost") int hpCost,
                      @JsonProperty("damage") int damage,
                      @JsonProperty("hit") int hit,
                      @JsonProperty("damageType") String damageType,
                      @JsonProperty("description") String description) {
        this.name = name;
        this.mpCost = mpCost;
        this.hpCost = hpCost;
        this.damage = damage;
        this.hit = hit;
        this.damageType = damageType;
        this.description = description;
    }

}
