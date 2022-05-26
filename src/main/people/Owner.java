package main.people;

import main.Game;
import main.jobs.enums.TechStack;

import java.util.ArrayList;
import java.util.List;

public class Owner extends Human {
    private final ArrayList<TechStack> skills;
    private int findClientProgress;
    private int projectsFound;

    public Owner(String firstName, String lastName) {
        super(firstName, lastName);
        this.skills = new ArrayList<>(List.of(TechStack.BACKEND, TechStack.DATABASE, TechStack.FRONTEND, TechStack.PRESTASHOP, TechStack.WORDPRESS));
        this.findClientProgress = 0;
        this.projectsFound = 0;
    }

    public ArrayList<TechStack> getSkills() {
        return skills;
    }

    public int getFindClientProgress() {
        return findClientProgress;
    }

    public boolean makeProgressOnFindingClients() {
        findClientProgress++;

        if (findClientProgress == 4) {
            Game.generateNewProject();
            projectsFound++;
            findClientProgress = 0;
            return true;
        }
        return false;
    }
}
