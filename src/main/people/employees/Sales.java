package main.people.employees;

import main.helpers.Randomizer;
import main.people.enums.Position;

public class Sales extends Employee {
    private static final int BASE_SALARY = 5000;
    private int projectFindingIndicator;

    public Sales() {
        super(Position.SALES);
        setSalary(generateSalary());
    }


    // public methods

    public boolean findNewProject() {
        if (position == Position.SALES) {
            projectFindingIndicator++;

            return projectFindingIndicator % 5 == 0;
        }

        return false;
    }


    // private methods

    private Double generateSalary() {
        return (double) Randomizer.generateRandomValue((int) (BASE_SALARY * 0.8), BASE_SALARY * 2);
    }

    @Override
    public void goToWork() {

    }
}
