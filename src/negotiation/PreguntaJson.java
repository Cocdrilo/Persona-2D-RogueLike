package negotiation;

import java.util.List;

/**
 * Represents a JSON object containing a list of questions for negotiation.
 */
public class PreguntaJson {
    private List<Pregunta> preguntas;

    /**
     * Gets the list of questions for negotiation.
     *
     * @return The list of questions.
     */
    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    /**
     * Sets the list of questions for negotiation.
     *
     * @param preguntas The list of questions to set.
     */
    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }
}
