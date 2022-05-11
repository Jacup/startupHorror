package main.helpers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomizerTest {

    @Test
    @DisplayName("Returned string shouldn't be lower or higher that provided min/max values")
    void generateRandomValue() {
        assertTrue(10 <= Randomizer.generateRandomValue(10, 20));
        assertTrue(20 >= Randomizer.generateRandomValue(10, 20));
        assertEquals(10, Randomizer.generateRandomValue(10, 10));
    }
}