package people.employees;

import helpers.console.Randomizer;
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

    public Employee(String firstName, String lastName, Position position) {
        super(firstName, lastName);
        this.position = position;
    }

    @Override
    public String toString() {
        return "Name: " + getFirstName() + " " + getLastName() + ", role: " + position + ", salary: " + salary;
    }

    public boolean isSick() {
        return Randomizer.draw(SICKNESS_CHANCE);
    }

}
