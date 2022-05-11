package helpers;

import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {

    public static int generateRandomValue(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);

    }
}
