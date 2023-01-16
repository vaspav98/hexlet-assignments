package exercise;

import java.util.HashMap;
import java.util.Map;

// BEGIN
class App {
    public static final String SENTENCE = "the java is the best programming language java";
    public static final String SENTENCE2 = "";

    public static void main(String[] args) {
        System.out.println(toString(getWordCount(SENTENCE2)));

    }

    public static Map<String, Integer> getWordCount(String sentence) {
        String[] arrayOfWords = sentence.split(" ");
        Map<String, Integer> map = new HashMap<>();
        if (sentence.equals("")) {
            return map;
        }
        for (String word : arrayOfWords) {
            if (map.containsKey(word)) {
                map.put(word, map.get(word) + 1);
            } else {
                map.put(word, 1);
            }
        }
        return map;
    }

    public static String toString(Map<String, Integer> map) {
        if (map.isEmpty()) {
            return "{}";
        }
        var result = new StringBuilder();
        result.append("{");
        for (String key : map.keySet()) {
            result.append("\n  ").append(key).append(": ").append(map.get(key));
        }
        result.append("\n}");
        return result.toString();
    }
}
//END
