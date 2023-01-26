package exercise;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

// BEGIN
class App {

    public static void main(String[] args) {
        Map<String, Object> data1 = new HashMap<>(
                Map.of("one", "eon", "two", "two", "four", true)
        );
        Map<String, Object> data2 = new HashMap<>(
                Map.of("two", "own", "zero", 4, "four", true)
        );

        System.out.println(genDiff(data1, data2));
    }

    public static LinkedHashMap<String, String> genDiff(Map<String, Object> data1, Map<String, Object> data2) {
        Map<String, String> result = new TreeMap<>();
        Map<String, Object> generalMap = new HashMap<>();
        generalMap.putAll(data1);
        generalMap.putAll(data2);
        for (String key : generalMap.keySet()) {
            if (!data1.containsKey(key) && data2.containsKey(key)) {
                result.put(key, "added");
            } else if (data1.containsKey(key) && !data2.containsKey(key)) {
                result.put(key, "deleted");
            } else if (data1.get(key).equals(data2.get(key))) {
                result.put(key, "unchanged");
            } else {
                result.put(key, "changed");
            }
        }
        return new LinkedHashMap<>(result);
    }
}
//END
