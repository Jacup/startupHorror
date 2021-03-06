package main.people;

import main.Company;
import main.Game;
import main.jobs.Project;
import main.jobs.enums.TechStack;
import main.people.enums.Seniority;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static main.people.employees.Tester.SINGLE_TEST_VALUE;

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

    public boolean makeProgressOnFindingClients() {
        findClientProgress++;

        if (findClientProgress == 4) {
            Game.generateNewProject(true);
            projectsFound++;
            findClientProgress = 0;
            return true;
        }
        return false;
    }

    public ArrayList<Project> getProjectsForOwnerToProgram(Company company) {
        var actualProjects = company.getActualProjects().stream().filter(project -> !project.isFinished()).toList();
        if (actualProjects.size() == 0) return null;

        var list = new ArrayList<Project>();

        for (var project : actualProjects) {
            var candidate = company.getOwner().checkProject(project);
            if (candidate != null) list.add(candidate);
        }

        return list;
    }

    private Project checkProject(Project project) {
        var workToDo = project.getWorkLeft();

        for (TechStack tech : workToDo.keySet()) {
            if (this.skills.contains(tech) && workToDo.get(tech) > 0) {
                return project;
            }
        }

        return null;
    }

    public Project getFirstValidProjectToTest(LinkedList<Project> projects) {
        for (Project project : projects) {
            var bugsChance = project.getBugsChance();

            if (bugsChance >= SINGLE_TEST_VALUE) {
                return project;
            }
        }

        return null;
    }


    public boolean goProgramming(Project project) {
        var workToDo = project.getWorkLeft();
        if (workToDo == null || workToDo.isEmpty()) return false;

        for (var tech : workToDo.keySet()) {
            if (this.skills.contains(tech) && workToDo.get(tech) > 0) {
                project.makeProgressByTech(tech, Seniority.MID);
                project.setDevelopedByOwner(true);

                return true;
            }
        }

        return false;
    }

    public boolean goTesting(Project project) {
        var bugsChance = project.getBugsChance();
        if (bugsChance < SINGLE_TEST_VALUE) {
            return false;
        }

        project.removeBugs(SINGLE_TEST_VALUE);
        return true;
    }
}
