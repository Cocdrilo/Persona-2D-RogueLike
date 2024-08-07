package monster;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Manages the loading and retrieval of monster data from a JSON file.
 */
public class MonsterManager {
    private static MonsterManager instance;
    private ArrayList<monsterData> monsters;
    private ArrayList<monsterData> bosses;

    /**
     * Private constructor to ensure singleton pattern and load monsters from a specified JSON file.
     */
    private MonsterManager() {
        loadMonstersFromFile("/Jsons/monsters.json"); // Ajusta la ruta según la ubicación de tu archivo JSON
        loadBossesFromFile("/Jsons/bosses.json");

    }

    /**
     * Returns the singleton instance of MonsterManager, creating it if it does not exist.
     *
     * @return The singleton instance of MonsterManager.
     */
    public static MonsterManager getInstance() {
        if (instance == null) {
            instance = new MonsterManager();
        }
        return instance;
    }

    /**
     * Loads monsters from a JSON file using Jackson ObjectMapper.
     *
     * @param filePath The file path to the JSON file containing monster data.
     */
    private void loadMonstersFromFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            monsters = objectMapper.readValue(is, new TypeReference<ArrayList<monsterData>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar excepciones apropiadamente
        }
    }

    private void loadBossesFromFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            bosses = objectMapper.readValue(is, new TypeReference<ArrayList<monsterData>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar excepciones apropiadamente
        }
    }

    /**
     * Returns the list of monster data.
     *
     * @return The list of monster data.
     */
    public ArrayList<monsterData> getMonsters() {
        return monsters;
    }

    /**
     * Retrieves the list of boss monster data.
     *
     * @return An ArrayList containing the data of boss monsters.
     */
    public ArrayList<monsterData> getBosses() {
        return bosses;
    }
}
