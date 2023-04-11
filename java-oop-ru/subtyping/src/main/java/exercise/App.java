package exercise;

import java.util.Map;
// BEGIN
public class App {

    public static void swapKeyValue(KeyValueStorage storage) {
        Map<String, String> map = storage.toMap();
        for (Map.Entry<String, String> pair : map.entrySet()) {
            String newKey = pair.getValue();
            String newValue = pair.getKey();
            storage.unset(pair.getKey());
            storage.set(newKey, newValue);
        }
    }
}
// END
