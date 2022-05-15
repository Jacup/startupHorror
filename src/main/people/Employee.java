package main.people;

import main.helpers.Randomizer;
import main.jobs.enums.TechStack;
import main.people.enums.Position;
import main.people.enums.Seniority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Employee extends Human {

    private final Position position;
    private final Seniority seniority;

    private final LinkedList<TechStack> skills;
    private final Double salary;


    public Employee() {
        super(HumanTemplate.getRandomFirstName(), HumanTemplate.getRandomLastName());
        this.position = generatePosition();
        this.seniority = this.isDeveloper() ? generateSeniority() : null;
        this.skills = this.isDeveloper() ? generateSkills() : null;
        this.salary = generateSalary();
    }


    // generators

    private Position generatePosition() {
        return Position.values()[Randomizer.generateRandomValue(Position.values().length)];
    }

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
                max = 4;
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
        Double salary = 0.0;
        return salary;
    }


    public boolean isDeveloper() {
        return position == Position.DEVELOPER;
    }


    public static Employee generateRandomEmployee() {
        return new Employee();
    }

    public String createEmailAddress() {
        return getFirstName().charAt(0) + getLastName() + "@firma.pl";          // to fix later
    }

    private Double getDefaultSalary(Position position) {
        double value;
        switch (position) {
            case DEVELOPER -> value = 15000.0;
            case TESTER -> value = 8000.0;
            case SALES -> value = 10000.0;
            default -> value = 5000.0;
        }
        return value;
    }

    public Position getPosition() {
        return position;
    }


}
