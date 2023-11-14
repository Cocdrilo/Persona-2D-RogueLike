package negotiation;

import java.util.List;

/**
 * Represents a question in a negotiation.
 */
public class Pregunta {
    private int id;
    private String texto;
    private List<Opcion> opciones;

    /**
     * Gets the unique identifier of the question.
     *
     * @return The identifier of the question.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the question.
     *
     * @param id The identifier to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the text content of the question.
     *
     * @return The text content of the question.
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Sets the text content of the question.
     *
     * @param texto The text content to set.
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * Gets the list of options associated with the question.
     *
     * @return The list of options.
     */
    public List<Opcion> getOpciones() {
        return opciones;
    }

    /**
     * Sets the list of options associated with the question.
     *
     * @param opciones The list of options to set.
     */
    public void setOpciones(List<Opcion> opciones) {
        this.opciones = opciones;
    }
}
