package people.employees;

import gameplay.Game;
import jobs.Project;
import people.enums.Position;

public class Tester extends Employee {
    public Double SINGLE_TEST_VALUE;


    public Tester(String firstName, String lastName) {
        super(firstName, lastName, Position.TESTER);
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

}
