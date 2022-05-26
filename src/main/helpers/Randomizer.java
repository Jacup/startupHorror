package main.helpers;

import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {

    public static int generateRandomValue(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static int generateRandomValue(int max) {
        return ThreadLocalRandom.current().nextInt(max);
    }

    /**
     * @param percentage Percentage to draw. For example, if there is 25% chance for something, set percentage to 25.
     * @return true if percentage is hit.
     */
    public static boolean draw(int percentage) {
        return ThreadLocalRandom.current().nextInt(100) <= percentage;
    }

}
