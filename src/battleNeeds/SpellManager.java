package battleNeeds;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This class is a spell manager that loads and stores the spells available for the game.
 * It uses the Singleton design pattern to ensure that only one instance of this class exists in the program.
 */
public class SpellManager {
    private static SpellManager instance;
    private ArrayList<superMagic> spells;

    /**
     * The private constructor of the class. Calls the loadSpellsFromFile method to load the spells from the JSON file.
     */
    private SpellManager() {
        loadSpellsFromFile("res/Jsons/spells.json"); // Ajusta la ruta según la ubicación de tu archivo JSON
    }

    /**
     * This method returns the unique instance of this class. If the instance does not exist, it creates and returns it.
     *
     * @return the instance of SpellManager
     */
    public static SpellManager getInstance() {
        if (instance == null) {
            instance = new SpellManager();
        }
        return instance;
    }

    /**
     * This method loads the spells from a JSON file using the Jackson library. It creates an ObjectMapper object and uses it to read the file and convert it into a list of superMagic objects.
     *
     * @param filePath the path of the JSON file that contains the spells
     */
    private void loadSpellsFromFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            spells = objectMapper.readValue(new File(filePath), new TypeReference<ArrayList<superMagic>>() {
            });
        } catch (IOException e) {
            e.printStackTrace(System.err);
            // Manejar excepciones apropiadamente
        }
    }

    /**
     * This method returns the list of spells loaded from the JSON file.
     *
     * @return the list of spells
     */
    public ArrayList<superMagic> getSpells() {
        return spells;
    }
}
