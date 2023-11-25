package exercise;

import java.util.Arrays;

// BEGIN
public class MinThread extends Thread {

    private int[] numbers;
    private int min;

    public MinThread(int[] numbers) {
        this.numbers = numbers;
    }

    @Override
    public void run() {
        min = Arrays.stream(numbers)
                .min()
                .orElse(0);
    }

    public int get() {
        return min;
    }
}
// END
