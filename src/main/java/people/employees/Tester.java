package people.employees;

import gameplay.Game;
import helpers.console.Randomizer;
import jobs.Project;
import people.enums.Position;

public class Tester extends Employee {
   // to be refactored to read from  file
    private int BASE_SALARY;
    public int DAILY_TEST_AMOUNT;
    public Double SINGLE_TEST_VALUE;


    public Tester(String firstName, String lastName) {
        super(firstName, lastName, Position.TESTER);
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

}
