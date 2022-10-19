package gameplay;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class GameTime {

    @Getter
    private LocalDate localDate = LocalDate.of(2022, 1, 1);

    @Getter
    @Setter
    private int gameDay = 1;

    public void nextDay() {
        localDate = localDate.plusDays(1);
        gameDay++;
    }

    public boolean isWorkDay() {
        return localDate.getDayOfWeek() != DayOfWeek.SATURDAY && localDate.getDayOfWeek() != DayOfWeek.SUNDAY;
    }
}
