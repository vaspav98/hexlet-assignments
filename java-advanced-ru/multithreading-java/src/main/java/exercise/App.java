package exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

class App {
    private static final Logger LOGGER = Logger.getLogger("AppLogger");

    public static void main(String[] args) {
        int[] arg = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(getMinMax(arg));
    }
    // BEGIN
    public static Map<String, Integer> getMinMax(int[] numbers) {
        MinThread minThread = new MinThread(numbers);
        MaxThread maxThread = new MaxThread(numbers);
        minThread.start();
        LOGGER.info(minThread.getName() + " started");
        maxThread.start();
        LOGGER.info(maxThread.getName() + " started");

        try {
            minThread.join();
            LOGGER.info(minThread.getName() + " finished");
            maxThread.join();
            LOGGER.info(maxThread.getName() + " finished");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        int min = minThread.get();
        int max = maxThread.get();
        Map<String, Integer> result = new HashMap<>();
        result.put("min", min);
        result.put("max", max);
        return result;
    }
    // END
}
