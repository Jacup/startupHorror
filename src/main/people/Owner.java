package main.people;

import main.jobs.enums.TechStack;

import java.util.ArrayList;
import java.util.List;

public class Owner extends Human{

    private ArrayList<TechStack> abilities = new ArrayList<>();

    public Owner(String firstName, String lastName) {
        super(firstName, lastName);
        this.abilities = new ArrayList<>(List.of(
                TechStack.BACKEND,
                TechStack.DATABASE,
                TechStack.FRONTEND,
                TechStack.PRESTASHOP,
                TechStack.WORDPRESS));
    }






    // getters
    public ArrayList<TechStack> getAbilities() {
        return abilities;
    }
}
