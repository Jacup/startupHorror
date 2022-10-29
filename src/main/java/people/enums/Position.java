package people.enums;

import lombok.Getter;

public enum Position {
    DEVELOPER(25), TESTER, SALES;

    @Getter
    int chance;

    Position(int chance){
        this.chance = chance;
    }
}
