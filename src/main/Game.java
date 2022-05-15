package main;

import main.helpers.UserActions;
import main.jobs.Project;
import main.jobs.enums.DifficultyLevel;
import main.jobs.enums.TechStack;
import main.people.Client;
import main.people.Employee;
import main.people.Owner;
import main.people.enums.Position;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Game {
    /**
     * Global settings. Could be replaced in future with difficulty level, changed by user.
     */
    private static final int START_CLIENTS = 5;
    private static final int START_PROJECTS = 3;
    private static final int START_EMPLOYEES = 5;

    private static final String TAB = "   ";
    protected final LinkedList<Client> availableClients;
    protected final LinkedList<Project> availableProjects;
    protected final LinkedList<Employee> availableEmployees;
    private final Scanner scanner;
    protected int successfulProjects;
    private int gameDay;
    private LocalDate gameDate;
    private Company company;


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

    /**
     * todo later: divide into sections/submenus: HR, Contracts, etc.
     *
     * @return true if activity was successful and next day should begin. False, if user haven't performed action that day.
     */
    private boolean dayActivities() {
        printHeader();

        System.out.println("What would you like to do today?");
        ArrayList<String> activities = new ArrayList<>(List.of("1. Sign a contract for a new project", "2. Try to find a new client", "3. Go programming!", "4. Go testing!", "5. Return the finished project to the client", "6. Hire a new employee", "7. Fire an employee", "8. Pay taxes", "0. Exit game"));

        printList(activities);

        var choice = UserActions.getUserInputByte(activities.size());

        switch (choice) {
            case 1:
                return contractMenu();
            case 2:
                break;
            case 3:
                return goProgramming();
            case 5:
                returnContract();
                break;
            case 8:
                System.out.println("8888");
                break;
            case 0:
                exitGame();
                break;
        }

        return false;
    }

    private static void exitGame() {
        System.out.println("You lost! Closing app. . . ");
    }

    private void printList(List<String> options) {
        for (var option : options) {
            System.out.println(TAB + option);
        }
    }

    /**
     * 1. Contract Menu
     *
     * @return true if day is ended.
     */
    private boolean contractMenu() {
        if (availableProjects.size() == 0) {
            System.out.println("You don't have any available projects. You have to find new clients.");
            UserActions.pressEnterKeyToContinue();
            return false;
        }

        System.out.println("\nAvailable projects: ");
        printProjects(availableProjects);
        System.out.println("Which project would you like to sign up? ");

        var selectedProject = selectProject();
        if (selectedProject == null) return false;

        availableProjects.remove(selectedProject);
        return company.signNewProject(selectedProject);
    }

    private Project selectProject() {
        byte userChoice = UserActions.getUserInputByte(availableProjects.size());
        if (userChoice == 0) return null;

        Project selectedProject = null;
        try {
            selectedProject = availableProjects.get(userChoice - 1);
        } catch (Exception e) {
            System.out.println("Something gone wrong... ");
            selectProject();
        }

        if (validateProject(selectedProject)) return selectedProject;
        else return null;
    }

    private boolean validateProject(Project project) {
        var amountOfDevelopers = company.getAmountOfEmployeesByType(Position.DEVELOPER);

        if (amountOfDevelopers != 0) return false;

        if (project != null && project.getDifficultyLevel() == DifficultyLevel.HARD) {
            System.out.println("\nThis project is too hard for you, select something easier or hire more developers");
            selectProject();
        }

        return true;
    }

    private void printProjects(List<Project> projects) {
        for (int i = 1; i <= projects.size(); i++)
            System.out.println(TAB + i + ". " + projects.get(i - 1));
        System.out.println(TAB + 0 + ". Go back");
    }

    // 2. Clients

    // 3. Working

    private boolean goProgramming() {
        var projectsForOwner = findAvailableProjectsForOwner();

        if (projectsForOwner == null || projectsForOwner.size() == 0) {
            System.out.println("You don't have any project to work on.");
            UserActions.pressEnterKeyToContinue();
            return false;
        }

        printProjects(projectsForOwner);

        var choice = scanner.nextInt();
        if (choice == 0) return false;

        return projectsForOwner.get(choice).makeProgress();
    }

    private ArrayList<Project> findAvailableProjectsForOwner() {
        var actualProjects = company.getActualProjects();

        if (actualProjects.size() == 0) return null;

        ArrayList<Project> availableProjectsForOwner = new ArrayList<>();

        for (var project : actualProjects) {
            var missingSkills = LookForMissingSkills(project, company.getOwner());

            if (missingSkills.size() != 0) {
                System.out.println("Sorry, but you cannot work on " + project.getName() + ", because you don't know " + missingSkills);
            } else {
                availableProjectsForOwner.add(project);
            }
        }

        return availableProjectsForOwner;
    }

    private List<TechStack> LookForMissingSkills(Project project, Owner owner) {
        var techstack = project.getTechStackAndWorkload().keySet().stream().toList();

        return techstack.stream().filter(element -> !owner.getAbilities().contains(element)).toList();

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
