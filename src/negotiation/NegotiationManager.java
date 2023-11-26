package negotiation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import monster.MonsterManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Manages negotiations and handles questions.
 *
 * The code uses the Singleton design pattern to ensure that there is only one instance of the NegotiationManager class in the application. Let's break down the reasons for using the Singleton pattern in this context:
 * Single Instance Requirement:
 *
 * The NegotiationManager is designed to manage negotiations and handle questions.
 * In many scenarios, having multiple instances of such a manager could lead to inconsistent behavior and unnecessary resource consumption.
 * Global Access Point:
 *
 * The getInstance() method provides a global access point to the single instance of the NegotiationManager.
 * This allows other parts of the codebase to obtain a reference to the manager and use its functionality without the need to create multiple instances.
 * Lazy Initialization:
 *
 * The Singleton pattern in this implementation adopts lazy initialization. The instance is created only when it is first requested. This is achieved through the if (instance == null) check in the getInstance() method.
 * Lazy initialization can be beneficial for performance, especially if the creation of the instance involves resource-intensive operations.
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
