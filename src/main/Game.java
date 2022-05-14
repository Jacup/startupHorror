package main;

import main.helpers.UserActions;
import main.jobs.Project;
import main.jobs.enums.DifficultyLevel;
import main.people.Client;
import main.people.Employee;
import main.people.enums.Position;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private static final int START_CLIENTS = 5;
    private static final int START_PROJECTS = 3;
    private static final int START_EMPLOYEES = 5;

    private static final String TAB = "   ";

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
            if (dayActivities()) {
                nextDay();
                UserActions.pressEnterKeyToContinue();
            }

        }
    }

    private void nextDay() {
        gameDay += 1;
        gameDate = gameDate.plusDays(1);
    }

    // each activity should return true if successful (nextDay++) or false if we're returning (action was not successful)
    private boolean dayActivities() {
        printHeader();

        System.out.println("What would you like to do today?");
        ArrayList<String> activities = new ArrayList<>(List.of("1. Sign a contract for a new project", "2. Try to find a new client", "3. Go programming!", "4. Go testing!", "5. Return the finished project to the client", "6. Hire a new employee", "7. Fire an employee", "8. Pay taxes"));

        for (var activity : activities) {
            System.out.println(TAB + activity);
        }
        System.out.print("Your choice: ");
        var choice = scanner.nextInt();
        switch (choice) {
            case 1:
                return contractMenu();
            case 2:
                break;
            case 3:
                break;
            case 5:
                returnContract();
        }

        return false;
    }

    // 1. Contract Menu
    private boolean contractMenu() {
        System.out.println("\nAvailable projects: ");
        for (int i = 1; i <= availableProjects.size(); i++)
            System.out.println(TAB + i + ". " + availableProjects.get(i - 1));
        System.out.println(TAB + 0 + ". Go back");

        var selectedProject = selectProjectToSignUp();
        if (selectedProject != null) {
            return company.signNewProject(selectedProject);
        }
        return false;
    }

    private Project selectProjectToSignUp() {
        System.out.print("Which project would you like to sign up? ");
        var choice = scanner.nextInt();
        if (choice == 0) return null;

        var selectedProject = availableProjects.get(choice - 1);
        var amountOfDevelopers = company.getAmountOfEmployeesByType(Position.DEVELOPER);

        if (amountOfDevelopers != 0) {
            return selectedProject;
        }

        if (selectedProject.getDifficultyLevel() == DifficultyLevel.HARD) {
            System.out.println("\nThis project is too hard for you, select something easier or hire more developers");
            selectProjectToSignUp();
        }
        return selectedProject;
    }


    private void returnContract() {
        var finishedProjects = company.getActualProjects().stream().filter((project) -> project.getHoursLeftToFinish().equals(0)).toList();
        if (finishedProjects.equals(0)) {
            var successful = company.returnProject();
            if (!successful) successfulProjects = +1;
        }


    }

    private void printHeader() {
        System.out.println("\n\n--------------------------------------------------------------------------------");
        System.out.println("> Your bank account: " + company.getCash() + "                       Today is " + gameDate + " -  Day " + gameDay);
        System.out.println("> Successful projects: " + successfulProjects + "\n");
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
