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
        int count = 4;

        assertThat(App.take(elements, count))
                .isNotEmpty()
                .hasSize(count)
                .contains(6, 3);

        count = 20;

        assertThat(App.take(elements, count))
                .isNotEmpty()
                .hasSizeLessThanOrEqualTo(elements.size())
                .contains(6);

        elements.clear();

        assertThat(App.take(elements, count))
                .isEmpty();
        // END
    }
}
