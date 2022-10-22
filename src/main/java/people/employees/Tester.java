package people.employees;

import gameplay.Game;
import helpers.Randomizer;
import jobs.Project;
import people.enums.Position;

import java.util.LinkedList;

public class Tester extends Employee {
   // to be refactored to read from  file
    private int BASE_SALARY;
    public int DAILY_TEST_AMOUNT;
    public Double SINGLE_TEST_VALUE;


    public Tester() {
        super("xd", "xd", Position.TESTER);
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
