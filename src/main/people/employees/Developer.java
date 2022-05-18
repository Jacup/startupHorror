package main.people.employees;

import main.helpers.Randomizer;
import main.jobs.Project;
import main.jobs.enums.TechStack;
import main.people.enums.Position;
import main.people.enums.Seniority;

import java.util.Arrays;
import java.util.LinkedList;

import static java.lang.Double.parseDouble;

public class Developer extends Employee {
    private static final int BASE_SENIOR_SALARY = 12000;
    private static final int BASE_MID_SALARY = 5000;
    private static final int BASE_JUNIOR_SALARY = 3000;

    private final Seniority seniority;
    private final LinkedList<TechStack> skills;

    public Developer() {
        super(Position.DEVELOPER);
        this.seniority = generateSeniority();
        this.skills = generateSkills();
        setSalary(generateSalary());
    }


    // public methods

    public LinkedList<TechStack> getSkills() {
        return skills;
    }


    @Override
    public String toString() {
        return "Name: " + getName() + ", role: " + seniority + " " + position + ", salary: " + salary + ", skills: " + skills;
    }

    @Override
    public void goToWork(Project project) {
        System.out.println("WorkDev");
        if (isSick()) {
            System.out.println("Dev " + getName() + " is sick today.");
            return;
        }

        var workToDo = project.getWorkLeft();
        if (workToDo.isEmpty()) {
            return;
        }

        for (var tech : workToDo.keySet()) {
            if (this.skills.contains(tech) && workToDo.get(tech) > 0) {
                System.out.println("DEBUG: dev " + getName() + " worked on " + project.getName() + " in " + tech);
                System.out.println("Work left: " + project.getWorkLeft().get(tech));
                project.makeProgressByTech(tech);
                return;
            }
        }
    }

    // private methods

    private Seniority generateSeniority() {
        return Seniority.values()[Randomizer.generateRandomValue(Seniority.values().length)];
    }

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

    private Double generateSalary() {
        return getBaseSalary() * getSkillsMultiplier();
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
