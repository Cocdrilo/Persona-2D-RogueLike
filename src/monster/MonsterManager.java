package monster;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MonsterManager {
    private static MonsterManager instance;
    private ArrayList<monsterData> monsters;

    private MonsterManager() {
        loadMonstersFromFile("res/Jsons/monsters.json"); // Ajusta la ruta según la ubicación de tu archivo JSON
    }

    public static MonsterManager getInstance() {
        if (instance == null) {
            instance = new MonsterManager();
        }
        return instance;
    }

    private void loadMonstersFromFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            monsters = objectMapper.readValue(new File(filePath), new TypeReference<ArrayList<monsterData>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar excepciones apropiadamente
        }
    }

    public ArrayList<monsterData> getMonsters() {
        return monsters;
    }
}
