package main.people.employees;

import main.helpers.Randomizer;
import main.people.enums.Position;

public class Tester extends Employee {
    private static final int BASE_SALARY = 4000;

    public Tester() {
        super(Position.TESTER);
        setSalary(generateSalary());
    }


    // public methods

    @Override
    public void goToWork() {
    }


    // private methods

    private Double generateSalary() {
        return (double) Randomizer.generateRandomValue((int) (BASE_SALARY * 0.8), (int) (BASE_SALARY * 1.8));
    }
}
