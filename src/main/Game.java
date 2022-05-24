package main;

import main.helpers.UserActions;
import main.jobs.Project;
import main.jobs.enums.DifficultyLevel;
import main.jobs.enums.TechStack;
import main.people.Client;
import main.people.Owner;
import main.people.employees.Employee;
import main.people.enums.Position;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

import static main.Company.HIRE_COST;

public class Game {
    /**
     * Global game settings. Could be replaced in future with difficulty level, changed by user.
     */
    private static final int START_CLIENTS = 5;
    private static final int START_PROJECTS = 3;
    private static final int START_EMPLOYEES = 10;
    private static final String TAB = "   ";

    private static LinkedList<Client> availableClients;
    private static LinkedList<Project> availableProjects;
    private static LinkedList<Employee> availableEmployees;

    private static HashMap<LocalDate, Project> projectTransactions = new HashMap<>();
    private static int successfulProjects;
    private static int gameDay;
    private static LocalDate gameDate;

    private final Scanner scanner;
    private Company company;

    public Game() {
        this.scanner = new Scanner(System.in);
        availableClients = new LinkedList<>();
        availableProjects = new LinkedList<>();
        availableEmployees = new LinkedList<>();
        successfulProjects = 0;
        gameDate = LocalDate.now();
        gameDay = 1;
    }

    public static LinkedList<Project> getAvailableProjects() {
        return availableProjects;
    }

    public static LinkedList<Employee> getAvailableEmployees() {
        return availableEmployees;
    }

    public static LocalDate getGameDate() {
        return gameDate;
    }

    public static void lostGame() {
        System.out.println("\n\n\nSorry. You lost. ");
        UserActions.pressEnterKeyToContinue();
        System.exit(1);
    }

    public void setup() {
        company = createCompany();

        generateEmployees();
        generateClients();
        generateProjects();
    }

    public void play() {
        while (gameIsContinued()) {
            if (dayActivities()) {
                routines();
                if (isWorkDay(gameDate)) company.performWork();
                nextDay();
                UserActions.pressEnterKeyToContinue();
            }
        }
    }

    private boolean gameIsContinued() {
        if (successfulProjects >= 3) {
            System.out.println("\n\n\nCongratulations. You win");
            return false;
        }

        return true;
    }

    private static boolean isWorkDay(LocalDate day) {
        var dayOfWeek = day.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

    private void routines() {
        dailyRoutines();
        monthlyRoutines();
    }

    private void dailyRoutines() {
        getPaymentsForProjects();
    }

    private void getPaymentsForProjects() {
        if (projectTransactions.containsKey(gameDate)) {
            var project = projectTransactions.get(gameDate);
            company.addCash(project.getFinalPayment());

            if (!project.isDevelopedByOwner()) successfulProjects++;
        }
    }

    private void monthlyRoutines() {
        if (gameDate.getDayOfMonth() == gameDate.lengthOfMonth()) {
            company.paySalaryToWorkers();
        }
    }

    private static void nextDay() {
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

        var choice = UserActions.getUserInputByte(activities.size(), true);

        switch (choice) {
            case 1:
                return contractMenu();
            case 2:
                break;
            case 3:
                return goProgramming();
            case 5:
                return returnContract();
            case 6:
                return hireEmployee();
            case 7:
                return fireEmployee();
            case 8:
                return true;
            case 0:
                exitGame();
                break;
        }

        return false;
    }

    private static void exitGame() {
        System.out.println("Closing app. . . ");
    }

    private static void printList(List<String> options) {
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
        if (project != null && project.getDifficultyLevel() == DifficultyLevel.HARD) {
            var amountOfDevelopers = company.getAmountOfEmployeesByType(Position.DEVELOPER);

            if (amountOfDevelopers == 0) {
                System.out.println("You have selected hard project, but you don't have any developers.");
                return false;
            }
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

        var choice = UserActions.getUserInputByte(projectsForOwner.size());
        if (choice == 0) return false;

        var chosenProject = projectsForOwner.get(choice - 1);

        var workToDo = chosenProject.getWorkLeft();
        if (workToDo == null || workToDo.isEmpty()) {
            return false;
        }

        for (var tech : workToDo.keySet()) {
            if (company.getOwner().getSkills().contains(tech) && workToDo.get(tech) > 0) {
                chosenProject.makeProgressByTech(tech);
                chosenProject.setDevelopedByOwner(true);
                return true;
            }
        }

        return false;
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
        // if one of tech is missing, owner cannot work on this??? to fix
        var techstack = project.getTechStackAndWorkload().keySet().stream().toList();

        return techstack.stream().filter(element -> !owner.getSkills().contains(element)).toList();
    }

    /**
     * 5. return project menu
     *
     * @return true if day is ended and returned project successfully.
     */    private boolean returnContract() {
        var finishedProjects = company.getActualProjects().stream().filter(Project::isFinished).toList();

        if (finishedProjects.size() == 0) {
            System.out.println("You don't have any ready to return projects. Go programming or hire devs!");
            UserActions.pressEnterKeyToContinue();
            return false;
        }

        System.out.println("Which project would you like to return? ");
        printProjects(finishedProjects);

        var choice = UserActions.getUserInputByte(finishedProjects.size());
        if (choice == 0) return false;

        var chosenProject = finishedProjects.get(choice - 1);

        return company.returnProject(chosenProject);
    }

    /**
     * 6. hire employees menu
     *
     * @return true if day is ended and hiring was successfully.
     */
    private boolean hireEmployee() {
        if (availableEmployees.size() == 0) {
            System.out.println("No one wants to work for you. Advertise your company at job boards.");
            UserActions.pressEnterKeyToContinue();
            return false;
        }
        System.out.println("Cost of hiring new employee: " + HIRE_COST + "\n");


        printEmployees(availableEmployees);
        var choice = UserActions.getUserInputByte(availableEmployees.size());
        if (choice == 0) return false;

        var chosenEmployee = availableEmployees.get(choice - 1);

        return company.hireEmployee(chosenEmployee);
    }

    /**
     * 7. Fire employees menu
     *
     * @return true if day is ended and firing was successfully.
     */
    private boolean fireEmployee() {
        var employees = company.getHiredEmployees();
        if (employees.size() == 0) {
            System.out.println("You don't have any employees to fire.");
            UserActions.pressEnterKeyToContinue();
            return false;
        }

        printEmployees(employees);
        var choice = UserActions.getUserInputByte(employees.size());
        if (choice == 0) return false;

        var chosenEmployee = employees.get(choice - 1);
        return company.fireEmployee(chosenEmployee);

    }

    private void printEmployees(List<Employee> employees) {
        for (int i = 1; i <= employees.size(); i++)
            System.out.println(TAB + i + ". " + employees.get(i - 1));
        System.out.println(TAB + 0 + ". Go back");
    }

    private void printHeader() {
        var actualProjects = company.getActualProjects();
        System.out.println("\n\n");

        String cashMsg = "Your cash: " + company.getCash();
        String dateMsg = gameDate.getDayOfWeek().toString() +  ", " + gameDate + " - Day " + gameDay;

        System.out.println("-".repeat(80));
        System.out.println(cashMsg + " ".repeat(80 - cashMsg.length() - dateMsg.length()) + dateMsg);

        if (successfulProjects > 0) {
            System.out.println("Successful projects: " + successfulProjects);
        } else {
            System.out.println();
        }

        if (!actualProjects.isEmpty()) {
            var completedProjects = actualProjects.stream().filter(Project::isFinished).toList();
            var currentProjects = actualProjects.stream().filter(Predicate.not(Project::isFinished)).toList();

            if (!completedProjects.isEmpty()) {
                System.out.println("Projects ready to return:");

                for (var project : completedProjects) {
                    System.out.println(TAB + project.getName() + ", Deadline: " + project.getActualDeadline());
                }
            }

            if (!currentProjects.isEmpty()) {
                System.out.println("Current projects: ");

                for (var project : actualProjects) {
                    System.out.println(TAB + project.getName() + ", work left: " + project.getWorkLeft()
                            + ", Deadline: " + project.getActualDeadline());
                }
            }
        }

        System.out.println("-".repeat(80));
        System.out.println();
    }

    public Company createCompany() {
        System.out.println("\nPlease enter name of your startup: ");
        var companyName = scanner.nextLine();

        return new Company(companyName);
    }

    private static void generateClients() {
        for (int i = 0; i < START_CLIENTS; i++) {
            availableClients.add(Client.generateRandomClient());
        }
    }

    private static void generateProjects() {
        for (int i = 0; i < START_PROJECTS; i++) {
            availableProjects.add(Project.generateRandomProject());
        }
    }

    public static void generateNewProject() {
        availableProjects.add(Project.generateRandomProject());
    }

    private static void generateEmployees() {
        for (int i = 0; i < START_EMPLOYEES; i++) {
            availableEmployees.add(Employee.generateRandomEmployee());
        }
    }

    public static void removeAvailableEmployee(Employee employee) {
        availableEmployees.remove(employee);
    }

    public static void addNewTransaction(LocalDate date, Project project) {
        projectTransactions.put(date, project);

    }


}
