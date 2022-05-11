package main;

import main.jobs.Project;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final int START_PROJECTS = 3;

    private static final ArrayList<Project> availableProjects = new ArrayList();

    public static void main(String[] args) {
        initializeData();

        startGame();
    }

    private static void initializeData() {
        generateEmployees();
        for (int i = 0; i < START_PROJECTS; i++) {
            availableProjects.add(Project.generateRandomProject());
        }
    }

    private static void startGame() {
        // creates user company
        System.out.println("Enter your startup name:\n");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();


        createCompany(name);

    }

    private static void createCompany(String name) {
        main.Company company = new main.Company(name);
    }

    private static void generateEmployees() {
        // generates random 3 employees to hire at start
    }
}
