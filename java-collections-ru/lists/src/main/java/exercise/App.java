package exercise;

import java.util.ArrayList;
import java.util.List;

// BEGIN
public class App {

    public static void main(String[] args) {
        System.out.println(scrabble("evvwefvnowr", "word"));
    }

    public static boolean scrabble(String characterSet, String word) {
        characterSet = characterSet.toLowerCase();
        word = word.toLowerCase();
        List<String> list = new ArrayList<>();
        for (var i = 1; i <= characterSet.length(); i++) {
            list.add(characterSet.substring((i - 1), i));
        }
        for (var i = 1; i <= word.length(); i++) {
            if (list.contains(word.substring((i - 1), i))) {
                list.remove(word.substring((i - 1), i));
            } else {
                return false;
            }
        }
        return true;
    }
}
//END
