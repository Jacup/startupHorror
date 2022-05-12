package main.helpers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomizerTest {

    @Test
    @DisplayName("Returned string shouldn't be lower or higher that provided min/max values")
    void generateRandomValue() {
        for (int i = 0; i < 20; i++) {
            assertTrue(99 <= Randomizer.generateRandomValue(99, 101));
            assertTrue(101 >= Randomizer.generateRandomValue(10, 20));
        }
        assertEquals(10, Randomizer.generateRandomValue(10, 10));
    }
}