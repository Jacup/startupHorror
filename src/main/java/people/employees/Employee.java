package people.employees;

import helpers.Randomizer;
import lombok.Getter;
import lombok.Setter;
import people.Human;
import people.enums.Position;
import people.interfaces.Worker;

public abstract class Employee extends Human implements Worker {
    private static final int SICKNESS_CHANCE = 3;

    @Getter
    @Setter
    protected Position position;

    @Getter
    @Setter
    protected Double salary;
    private Double cash;

    public Employee(Position position) {
        super();
        this.position = position;
        this.cash = 0.0;
    }

    // public methods
    public static Employee generateRandomEmployee() {
        return switch (generatePosition()) {
            case DEVELOPER -> new Developer();
            case TESTER -> new Tester();
            case SALES -> new Sales();
        };
    }

    @Override
    public String toString() {
        return "Name: " + getFirstName() + " " + getLastName() + ", role: " + position + ", salary: " + salary;
    }

    public boolean isDeveloper() {
        return position == Position.DEVELOPER;
    }

    public boolean isTester() {
        return position == Position.TESTER;
    }

    public boolean isSales() {
        return position == Position.SALES;
    }

    public void addCash(Double amount) {
        this.cash += amount;
    }

    public boolean isSick() {
        return Randomizer.draw(SICKNESS_CHANCE);
    }

    // private methods
    private static Position generatePosition() {
        // 50% chance for devs, 25% chance for tester, 25% for sales
        var isDev = Randomizer.draw(50);
        if (isDev) return Position.DEVELOPER;

        if (Randomizer.draw(50)) return Position.TESTER;
        return Position.SALES;
    }
}
