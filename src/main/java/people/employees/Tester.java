package people.employees;

import main.helpers.Randomizer;
import main.jobs.Project;
import main.people.enums.Position;

import java.util.LinkedList;

public class Tester extends Employee {
    private static final int BASE_SALARY = 4000;
    public static final int DAILY_TEST_AMOUNT = 3;
    public static final Double SINGLE_TEST_VALUE = 0.05;


    public Tester() {
        super(Position.TESTER);
        setSalary(generateSalary());
    }

    @Override
    public void goToWork(Project project) {
        var bugsChance = project.getBugChance();
        if (bugsChance < SINGLE_TEST_VALUE) {
            return;
        }

        project.removeBugs(SINGLE_TEST_VALUE);
        System.out.println(Game.TAB + "tested " + project.getName());
    }

    private Double generateSalary() {
        return (double) Randomizer.generateRandomValue((int) (BASE_SALARY * 0.8), (int) (BASE_SALARY * 1.8));
    }

    public Project getFirstValidProject(LinkedList<Project> projects) {
        for (Project project : projects) {
            var bugsChance = project.getBugChance();

            if (bugsChance >= SINGLE_TEST_VALUE) {
                return project;
            }
        }

        System.out.println(this.getName() + "don't have anything to test ");
        return null;
    }
}
