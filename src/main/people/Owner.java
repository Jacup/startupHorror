package main.people;

import main.jobs.enums.TechStack;

import java.util.ArrayList;
import java.util.List;

public class Owner extends Human {
    private final ArrayList<TechStack> skills;

    public Owner(String firstName, String lastName) {
        super(firstName, lastName);
        this.skills = new ArrayList<>(List.of(
                TechStack.BACKEND,
                TechStack.DATABASE,
                TechStack.FRONTEND,
                TechStack.PRESTASHOP,
                TechStack.WORDPRESS));
    }

    public ArrayList<TechStack> getSkills() {
        return skills;
    }
}
