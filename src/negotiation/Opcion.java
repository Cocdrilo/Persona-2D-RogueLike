package negotiation;

/**
 * Represents an option in a negotiation question.
 */
public class Opcion {
    private int id;
    private String texto;

    /**
     * Gets the unique identifier of the option.
     *
     * @return The identifier of the option.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the option.
     *
     * @param id The identifier to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the text content of the option.
     *
     * @return The text content of the option.
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Sets the text content of the option.
     *
     * @param texto The text content to set.
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }
}
