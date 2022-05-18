package main.people.employees;

import main.helpers.Randomizer;
import main.jobs.Project;
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
        projectFindingIndicator++;
        System.out.println("DEBUG: sales " + this.getName() + " worked today. " + "project finding: " + projectFindingIndicator);

        return projectFindingIndicator % 2 == 0;
    }


    // private methods

    private Double generateSalary() {
        return (double) Randomizer.generateRandomValue((int) (BASE_SALARY * 0.8), BASE_SALARY * 2);
    }

    @Override
    public void goToWork(Project project) {


    }
}
