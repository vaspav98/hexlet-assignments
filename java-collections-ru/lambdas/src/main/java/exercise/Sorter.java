package exercise;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

// BEGIN
class Sorter {

    public static void main(String[] args) {
        List<Map<String, String>> users = List.of(
                Map.of("name", "Vladimir Nikolaev", "birthday", "1991-12-27", "gender", "male"),
                Map.of("name", "Andrey Petrov", "birthday", "1989-11-23", "gender", "male"),
                Map.of("name", "Anna Sidorova", "birthday", "1996-09-09", "gender", "female"),
                Map.of("name", "John Smith", "birthday", "1989-03-11", "gender", "male"),
                Map.of("name", "Vanessa Vulf", "birthday", "1985-11-16", "gender", "female"),
                Map.of("name", "Alice Lucas", "birthday", "1986-01-01", "gender", "female"),
                Map.of("name", "Elsa Oscar", "birthday", "1970-03-10", "gender", "female"),
                Map.of("name", "Pavel Vasilev", "birthday", "1990-03-11", "gender", "male")
        );
        System.out.println(takeOldestMans(users));
    }

    public static List<String> takeOldestMans(List<Map<String, String>> users) {
        return users.stream()
                .filter(user -> user.get("gender").equals("male"))
                .sorted((user1, user2) -> user1.get("birthday").compareTo(user2.get("birthday")))
                .map(user -> user.get("name"))
                .collect(Collectors.toList());
    }
}
// END
