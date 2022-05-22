package main.people.employees;

import main.Game;
import main.helpers.Randomizer;
import main.jobs.Project;
import main.people.enums.Position;

public class Sales extends Employee {
    private static final int BASE_SALARY = 5000;

    private int projectFindingIndicator;
    private int projectsFound;

    public Sales() {
        super(Position.SALES);
        setSalary(generateSalary());
        this.projectFindingIndicator = 0;
        this.projectsFound = 0;
    }

    // public methods
    public void findNewProject() {
        projectFindingIndicator++;
        System.out.println("DEBUG: sales " + this.getName() + " worked today. " + "project finding: " + projectFindingIndicator);

        if (projectFindingIndicator == 4) {
            Game.generateNewProject();
            projectsFound++;
            projectFindingIndicator = 0;
            System.out.println("DEBUG: sales " + this.getName() + " found new available project.");
        }
    }

    public void goToWork() {
        System.out.println("WorkSales");
        if (isSick()) {
            System.out.println("Salesman " + getName() + " is sick today.");
            return;
        }

        findNewProject();
    }

    // private methods
    private Double generateSalary() {
        return (double) Randomizer.generateRandomValue((int) (BASE_SALARY * 0.8), BASE_SALARY * 2);
    }

    @Override
    public void goToWork(Project project) {

    }
}
