package main;

import main.jobs.Project;
import main.people.Client;
import main.people.Employee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private static final int START_CLIENTS = 5;
    private static final int START_PROJECTS = 3;
    private static final int START_EMPLOYEES = 5;

    protected int successfulProjects;

    private int gameDay;
    private LocalDate gameDate;

    private final Scanner scanner;
    private Company company;

    private final LinkedList<Client> availableClients;
    private final LinkedList<Project> availableProjects;
    protected final LinkedList<Employee> availableEmployees;


    public Game() {
        this.availableClients = new LinkedList<>();
        this.scanner = new Scanner(System.in);
        this.availableProjects = new LinkedList<>();
        this.availableEmployees = new LinkedList<>();
        this.successfulProjects = 0;
        this.gameDate = LocalDate.now();
        this.gameDay = 1;


    }

    public void play() {
        while (successfulProjects < 3) {
            dayActivities();
            nextDay();
        }
    }

    private void nextDay() {
        gameDay += 1;
        gameDate = gameDate.plusDays(1);
    }

    private void dayActivities() {
        printHeader();
        System.out.println("What would you like to do today?");
        ArrayList<String> activities = new ArrayList<>(List.of(
                "1. Sign a contract for a new project",
                "2. Try to find a new client",
                "3. Go programming!",
                "4. Go testing!",
                "5. Return the finished project to the client",
                "6. Hire a new employee",
                "7. Fire an employee",
                "8. Pay taxes"
        ));
        var choice = scanner.nextInt();
        switch (choice) {
            case 1:
                newContract();
                break;
            case 2:
                break;
            case 3:
                break;
            case 5:
                returnContract();

        }


    }

    private void newContract() {
    }

    private void returnContract() {
        var finishedProjects = company.getActualProjects().stream()
                .filter((project) -> project.getHoursLeftToFinish().equals(0)).toList();
        if (finishedProjects.equals(0)) {
            var successful = company.returnProject();
            if (!successful) successfulProjects = +1;
        }

    }

    private void printHeader() {
        System.out.println("\n\n-----------------------------------------------------------");
        System.out.println("Day " + gameDay + "                                   Today is "+ gameDate);
        System.out.println("Your bank account: " + company.getCash());
        System.out.println("Successful projects: " + successfulProjects);

    }

    public void setup() {
        company = createCompany();

        generateEmployees();
        generateClients();
        generateProjects();
    }

    private Company createCompany() {
        System.out.println("\nPlease enter name of your startup: ");
        var companyName = scanner.nextLine();

        return new Company(companyName);
    }

    private void generateClients() {
        for (int i = 0; i < START_CLIENTS; i++) {
            availableClients.add(Client.generateRandomClient());
        }
    }

    private void generateProjects() {
        for (int i = 0; i < START_PROJECTS; i++) {
            availableProjects.add(Project.generateRandomProject());
        }
    }

    private void generateEmployees() {
        for (int i = 0; i < START_EMPLOYEES; i++) {
            availableEmployees.add(Employee.generateRandomEmployee());
        }
    }

    public LinkedList<Project> getAvailableProjects() {
        return availableProjects;
    }

    public LinkedList<Employee> getAvailableEmployees() {
        return availableEmployees;
    }
}
