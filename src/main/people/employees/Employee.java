package main.people.employees;

import main.helpers.Randomizer;
import main.people.Human;
import main.people.HumanTemplate;
import main.people.enums.Position;
import main.people.interfaces.Worker;

public abstract class Employee extends Human implements Worker {
    private static final int SICKNESS_CHANCE = 3;

    protected Position position;
    protected Double salary;

    public Employee(Position position) {
        super(HumanTemplate.getRandomFirstName(), HumanTemplate.getRandomLastName());
        this.position = position;
    }


    // public methods

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

    public Position getPosition() {
        return position;
    }

    public static Employee generateRandomEmployee() {
        return switch (generatePosition()) {
            case DEVELOPER -> new Developer();
            case TESTER -> new Tester();
            case SALES -> new Sales();
        };
    }

    public void setSalary(Double salary) {
        this.salary = salary;
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
