package battleNeeds;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SpellManager {
    private static SpellManager instance;
    private ArrayList<superMagic> spells;

    private SpellManager() {
        loadSpellsFromFile("res/Jsons/spells.json"); // Ajusta la ruta según la ubicación de tu archivo JSON
    }

    public static SpellManager getInstance() {
        if (instance == null) {
            instance = new SpellManager();
        }
        return instance;
    }

    private void loadSpellsFromFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            spells = objectMapper.readValue(new File(filePath), new TypeReference<ArrayList<superMagic>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar excepciones apropiadamente
        }
    }

    public ArrayList<superMagic> getSpells() {
        return spells;
    }
}
