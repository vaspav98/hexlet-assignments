package exercise;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void testTake() {
        // BEGIN
        List<Integer> elements = new ArrayList<>(List.of(1, 3, 5, 6, 10));
        List<Integer> expected1 = new ArrayList<>(List.of(1, 3, 5));
        List<Integer> actual1 = App.take(elements, 3);
        assertThat(actual1).isEqualTo(expected1);

        List<Integer> expected2 = new ArrayList<>(List.of(1, 3, 5, 6, 10));
        List<Integer> actual2 = App.take(elements, 20);
        assertThat(actual2).isEqualTo(expected2);

        elements.clear();
        List<Integer> expected3 = new ArrayList<>();
        List<Integer> actual3 = App.take(elements, 3);
        assertThat(actual3).isEqualTo(expected3);
        // END
    }
}
