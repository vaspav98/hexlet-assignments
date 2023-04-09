package exercise;

import java.util.List;
import java.util.stream.Collectors;

// BEGIN
public class App {

    public static List<String> buildApartmentsList(List<Home> homes, int n) {
        return homes.stream()
                .sorted((h1, h2) -> h1.compareTo(h2))
                .limit(n)
                .map(home -> home.toString())
                .collect(Collectors.toList());
    }
}
// END
