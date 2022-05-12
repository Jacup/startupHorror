package main.helpers;

import main.jobs.enums.TechStack;

import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {

    public static int generateRandomValue(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static int generateRandomValue(int max) {
        return ThreadLocalRandom.current().nextInt(max);
    }

    public static boolean draw(int percentage) {
        return ThreadLocalRandom.current().nextInt(100) <= percentage;
    }

}
