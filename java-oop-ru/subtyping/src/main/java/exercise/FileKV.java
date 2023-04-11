package exercise;

import java.util.HashMap;
import java.util.Map;

// BEGIN
public class FileKV implements KeyValueStorage {

    private String filePath;

    public FileKV(String filePath, Map<String, String> map) {
        this.filePath = filePath;
        Map<String, String> startMap = new HashMap<>(map);

        String content = Utils.serialize(startMap);
        Utils.writeFile(this.filePath, content);
    }

    public void set(String key, String value) {
        String content = Utils.readFile(filePath);
        Map<String, String> data = Utils.unserialize(content);

        data.put(key, value);

        String newContent = Utils.serialize(data);
        Utils.writeFile(filePath, newContent);
    }

    public void unset(String key) {
        String content = Utils.readFile(filePath);
        Map<String, String> data = Utils.unserialize(content);

        data.remove(key);

        String newContent = Utils.serialize(data);
        Utils.writeFile(filePath, newContent);
    }

    public String get(String key, String defaultValue) {
        String content = Utils.readFile(filePath);
        Map<String, String> data = Utils.unserialize(content);

        return data.getOrDefault(key, defaultValue);
    }

    public Map<String, String> toMap() {
        String content = Utils.readFile(filePath);
        return Utils.unserialize(content);
    }
}
// END
