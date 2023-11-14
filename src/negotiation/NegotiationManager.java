package negotiation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import monster.MonsterManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Manages negotiations and handles questions.
 */
public class NegotiationManager {
    private static NegotiationManager instance;
    private List<Pregunta> preguntas; // Añadir esta lista para almacenar las preguntas

    /**
     * Constructor to initialize the negotiation manager.
     */
    public NegotiationManager() {
        cargarPreguntasDesdeJSON("res/Jsons/questions.json"); // Reemplaza "preguntas.json" con la ubicación de tu archivo JSON.
    }

    /**
     * Gets the instance of the NegotiationManager.
     *
     * @return The instance of NegotiationManager.
     */
    public static NegotiationManager getInstance() {
        if (instance == null) {
            instance = new NegotiationManager();
        }
        return instance;
    }

    /**
     * Loads questions from a JSON file.
     *
     * @param filePath The file path to the JSON file.
     */
    private void cargarPreguntasDesdeJSON(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Lee el archivo JSON y deserializa en una lista de preguntas
            PreguntaJson preguntaJson = objectMapper.readValue(new File(filePath), PreguntaJson.class);

            // Obtiene la lista de preguntas desde el objeto PreguntaJson
            preguntas = preguntaJson.getPreguntas();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the list of questions.
     *
     * @return The list of questions.
     */
    public List<Pregunta> getPreguntas() {
        return preguntas;
    }


}
