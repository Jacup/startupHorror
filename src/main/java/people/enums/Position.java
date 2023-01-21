package people.enums;

import lombok.Getter;

public enum Position {
    DEVELOPER(50),
    TESTER(25),
    SALES(25);

    @Getter
    int chance;

    Position(int chance){
        this.chance = chance;
    }
}
