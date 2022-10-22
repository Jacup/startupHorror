package people.employees;

import gameplay.Game;
import helpers.Randomizer;
import jobs.Project;
import people.enums.Position;

public class Sales extends Employee {
    private static final int BASE_SALARY = 5000;

    private int projectFindingProgress;
    private int projectsFound;

    public Sales() {
        super("xd", "xd", Position.SALES);
        setSalary(generateSalary());
        this.projectFindingProgress = 0;
        this.projectsFound = 0;
    }

    // public methods
    public void findNewProject() {
        projectFindingProgress++;
        System.out.println("DEBUG: sales " + this.getName() + " worked today. " + "project finding: " + projectFindingProgress);

        if (projectFindingProgress == 4) {
            Game.generateNewProject(true);
            projectsFound++;
            projectFindingProgress = 0;
            System.out.println("DEBUG: sales " + this.getName() + " found new client and available project.");
        }
    }

    // private methods
    private Double generateSalary() {
        return (double) Randomizer.generateRandomValue((int) (BASE_SALARY * 0.8), BASE_SALARY * 2);
    }

    @Override
    public void goToWork(Project project) {
        System.out.println("WorkSales");
        if (isSick()) {
            System.out.println("Salesman " + getName() + " is sick today.");
            return;
        }

        findNewProject();
    }
}
