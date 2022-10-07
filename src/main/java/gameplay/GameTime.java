package gameplay;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class GameTime {

    @Getter
    private final LocalDate localDate = LocalDate.of(2022, 1, 1);

    @Getter
    @Setter
    private int gameDay = 1;

    public void incrementLocalDate() {
        var x = localDate.plusDays(1);
    }
}
