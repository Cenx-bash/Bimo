import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class BimoEngine {
    private final Map<String, Map<String, String>> knowledge = new HashMap<>();

    public BimoEngine(String jsonFilePath) {
        loadResponses(jsonFilePath);
    }

    @SuppressWarnings("unchecked")
    private void loadResponses(String path) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(path));
            for (Object categoryKey : jsonObject.keySet()) {
                Map<String, String> catMap = new HashMap<>();
                JSONObject entries = (JSONObject) jsonObject.get(categoryKey);
                for (Object key : entries.keySet()) {
                    catMap.put(key.toString(), entries.get(key).toString());
                }
                knowledge.put(categoryKey.toString(), catMap);
            }
        } catch (Exception e) {
            System.err.println("Failed to load JSON: " + e.getMessage());
        }
    }

    public String getResponse(String input) {
        String normalized = input.toLowerCase().trim();

        for (Map.Entry<String, Map<String, String>> category : knowledge.entrySet()) {
            for (Map.Entry<String, String> entry : category.getValue().entrySet()) {
                if (normalized.contains(entry.getKey())) {
                    return entry.getValue();
                }
            }
        }

        return knowledge.get("default").getOrDefault("unknown", "I'm not sure how to respond.");
    }

    public void logInput(String userInput) {
        try (FileWriter fw = new FileWriter("user_logs.txt", true)) {
            fw.write("User: " + userInput + "\n");
        } catch (IOException e) {
            System.err.println("Logging failed: " + e.getMessage());
        }
    }
}
