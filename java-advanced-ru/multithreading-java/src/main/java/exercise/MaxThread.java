package exercise;

import java.util.Arrays;

// BEGIN
public class MaxThread extends Thread {

    private int[] numbers;
    private int max;

    public MaxThread(int[] numbers) {
        this.numbers = numbers;
    }

    @Override
    public void run() {
        max = Arrays.stream(numbers)
                .max()
                .orElse(0);
    }

    public int get() {
        return max;
    }
}
// END
