package people.employees;

import gameplay.Game;
import helpers.Randomizer;
import jobs.Project;
import jobs.enums.TechStack;
import lombok.Getter;
import lombok.ToString;
import people.enums.Position;
import people.enums.Seniority;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Double.parseDouble;

@ToString
public class Developer extends Employee {
    private static final int BASE_SENIOR_SALARY = 12000;
    private static final int BASE_MID_SALARY = 5000;
    private static final int BASE_JUNIOR_SALARY = 3000;

    private final Seniority seniority;

    @Getter
    private final LinkedList<TechStack> skills;

    public Developer() {
        super(Position.DEVELOPER);
        this.seniority = Seniority.values()[Randomizer.generateRandomValue(Seniority.values().length)];
        this.skills = generateSkills();
        setSalary(getBaseSalary() * getSkillsMultiplier());
    }

    @Override
    public void goToWork(Project project) {
        var workToDo = project.getWorkLeft();
        if (workToDo == null || workToDo.isEmpty()) {
            return;
        }

        int dailyWork;
        switch (this.seniority) {
            case SENIOR -> dailyWork = Randomizer.draw(25) ? 2 : 1;
            case JUNIOR -> dailyWork = Randomizer.draw(20) ? 0 : 1;
            default -> dailyWork = 1;
        }

        for (var tech : workToDo.keySet()) {
            for (int i = 0; i < dailyWork; i++) {
                if (this.skills.contains(tech) && workToDo.get(tech) > 0) {
                    System.out.println(Game.TAB + "worked on " + project.getName() + " in " + tech);
                    project.makeProgressByTech(tech, this.seniority);
                    return;
                }
            }
        }
    }

    public Project getFirstValidProject(List<Project> projects) {
        for (Project project : projects) {
            var workToDo = project.getWorkLeft();

            for (TechStack tech : workToDo.keySet()) {
                if (this.skills.contains(tech) && workToDo.get(tech) > 0) {
                    return project;
                }

            }
        }

        return null;
    }

    // private methods
    private LinkedList<TechStack> generateSkills() {
        var availableSkills = new LinkedList<>(Arrays.asList(TechStack.class.getEnumConstants()));
        var list = new LinkedList<TechStack>();
        int min, max;

        switch (seniority) {
            case SENIOR -> {
                min = 4;
                max = 6;
            }
            case MID -> {
                min = 3;
                max = 5;
            }
            case JUNIOR -> {
                min = 1;
                max = 3;
            }
            default -> {
                min = 1;
                max = TechStack.values().length;
            }
        }
        int amountOfSkills = Randomizer.generateRandomValue(min, max);

        for (int i = 0; i < amountOfSkills; i++) {
            var skill = availableSkills.get(Randomizer.generateRandomValue(availableSkills.size()));

            list.add(skill);
            availableSkills.remove(skill);
        }

        return list;
    }

    private int getBaseSalary() {
        return switch (seniority) {
            case SENIOR -> BASE_SENIOR_SALARY;
            case MID -> BASE_MID_SALARY;
            case JUNIOR -> BASE_JUNIOR_SALARY;
        };
    }

    private Double getSkillsMultiplier() {
        String str = Integer.toString(skills.size());
        String multiplier = "1." + str;

        return parseDouble(multiplier);
    }

}
