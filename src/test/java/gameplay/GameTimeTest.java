package gameplay;

import org.junit.jupiter.api.*;

import java.time.LocalDate;

class GameTimeTest {
    static GameTime gameTime;

    @BeforeEach
    void initializeComponent() {
        gameTime = new GameTime();
    }

    @Test
    void checkDefaultDateValue() {

       LocalDate date = gameTime.getLocalDate();

        Assertions.assertAll(
                () -> Assertions.assertEquals(2022, date.getYear()),
                () -> Assertions.assertEquals(1, date.getMonth().getValue()),
                () -> Assertions.assertEquals(1, date.getDayOfMonth())
        );
    }

    @Test
    void checkDateAfterAddingNewDate() {
        LocalDate date = gameTime.getLocalDate();
        int defaultDayOfMonth = date.getDayOfMonth();

        gameTime.nextDay();

        Assertions.assertNotEquals(defaultDayOfMonth, gameTime.getLocalDate().getDayOfMonth(), "Days amount should increase.");
    }

}