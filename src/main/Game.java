package main;

import main.helpers.Console;
import main.helpers.Randomizer;
import main.helpers.UserActions;
import main.jobs.Project;
import main.jobs.enums.DifficultyLevel;
import main.people.Client;
import main.people.enums.Position;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

public class Game {
    private static final int START_CLIENTS = 5;
    private static final int START_PROJECTS = 3;

    public static final String TAB = "    ";

    private static LinkedList<Client> availableClients;
    private static LinkedList<Project> availableProjects;

    private static final HashMap<LocalDate, Project> projectTransactions = new HashMap<>();
    private static int successfulProjects;
    private static int gameDay;
    private static LocalDate gameDate;

    private final Scanner scanner;
    private final GameHr gameHr;
    private Company company;

    public Game() {
        this.scanner = new Scanner(System.in);
        gameHr = new GameHr();
        availableClients = new LinkedList<>();
        availableProjects = new LinkedList<>();
        successfulProjects = 0;
        gameDate = LocalDate.of(2022, 1, 1);
        gameDay = 1;
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
        gameHr.generateEmployees();
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

            if (!project.isDevelopedByOwner() && project.getDifficultyLevel().equals(DifficultyLevel.HARD)) successfulProjects++;
        }
    }

    private void monthlyRoutines() {
        if (gameDate.getDayOfMonth() == gameDate.lengthOfMonth()) {
            // tax office
            if (company.getDaysSpendOnTaxes() < 2) {
                System.out.println("The tax office shuts down your business because you fail to comply with tax obligations.");
                Game.lostGame();
            }
            company.resetTaxDays();

            // salaries
            company.paySalaryToWorkers();

            // monthly tax
            company.payMonthlyTaxes();
        }
    }

    private static void nextDay() {
        gameDay += 1;
        gameDate = gameDate.plusDays(1);
    }

    /**
     * @return true if activity was successful and next day should begin. False, if user haven't performed action that day.
     */
    private boolean dayActivities() {
        printHeader();

        System.out.println("What would you like to do today?");
        ArrayList<String> activities = new ArrayList<>(List.of("1. Sign a contract for a new project", "2. Try to find a new client", "3. Go programming!", "4. Go testing!", "5. Return the finished project to the client", "6. HR operations", "7. Fire an employee", "8. Go to tax office", "9. Go to sleep", "0. Exit game"));

        Console.printList(activities);

        var choice = UserActions.getUserInputByte(activities.size(), true);

        switch (choice) {
            case 1:
                return contractMenu();
            case 2:
                return searchClients();
            case 3:
                return goProgramming();
            case 5:
                return returnContract();
            case 6:
                return gameHr.menu(company);
            case 8:
                return goToTaxOffice();
            case 9:
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
        Console.printProjects(availableProjects);
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
                UserActions.pressEnterKeyToContinue();
                return false;
            }
        }

        return true;
    }



    /**
     * 2. Clients Menu
     *
     * @return true if day is ended.
     */
    private boolean searchClients() {
        if (company.getOwner().makeProgressOnFindingClients()) {
            System.out.println("Congratulations, you have found new client with available project!");
        }

        return true;
    }

    /**
     * 3. Programming Menu
     *
     * @return true if day is ended.
     */
    private boolean goProgramming() {
        var owner = company.getOwner();
        var projectsForOwner = owner.getProjectsForOwner(company);
        if (projectsForOwner == null || projectsForOwner.isEmpty()) {
            System.out.println("You don't have any project to work on.");
            UserActions.pressEnterKeyToContinue();
            return false;
        }

        Console.printProjects(projectsForOwner, true);
        var choice = UserActions.getUserInputByte(projectsForOwner.size());
        if (choice == 0) return false;

        var chosenProject = projectsForOwner.get(choice - 1);
        if (chosenProject == null) return false;

        return owner.goProgramming(chosenProject);
    }

    /**
     * 5. return project menu
     *
     * @return true if day is ended and returned project successfully.
     */
    private boolean returnContract() {
        var finishedProjects = company.getActualProjects().stream().filter(Project::isFinished).toList();

        if (finishedProjects.size() == 0) {
            System.out.println("You don't have any ready to return projects. Go programming or hire devs!");
            UserActions.pressEnterKeyToContinue();
            return false;
        }

        System.out.println("Which project would you like to return? ");
        Console.printProjects(finishedProjects);

        var choice = UserActions.getUserInputByte(finishedProjects.size());
        if (choice == 0) return false;

        var chosenProject = finishedProjects.get(choice - 1);

        return company.returnProject(chosenProject);
    }

    /**
     * 8. Taxes menu
     *
     * @return true if day is ended and taxes was paid successfully.
     */
    private boolean goToTaxOffice() {
        if (!company.goToTaxOffice()) {
            return false;
        }

        System.out.println("Successful day in tax office. Good job!");
        return true;
    }

    private void printHeader() {
        var actualProjects = company.getActualProjects();
        System.out.println("\n\n");

        // cash
        String cashMsg = "Your cash: " + company.getCash();
        String dateMsg = gameDate.getDayOfWeek().toString() + ", " + gameDate + " - Day " + gameDay;

        System.out.println("-".repeat(80));
        System.out.println(cashMsg + " ".repeat(80 - cashMsg.length() - dateMsg.length()) + dateMsg);

        // taxes
        String taxes = "Days spend on taxes in " + getGameDate().getMonth().toString().toLowerCase() + ": " + company.getDaysSpendOnTaxes();
        System.out.println(taxes);

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
                    System.out.println(TAB + project.getName() + ", work left: " + project.getWorkLeft() + ", Deadline: " + project.getActualDeadline());
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
        var index = Randomizer.generateRandomValue(availableClients.size());
        var client = availableClients.get(index);

        for (int i = 0; i < START_PROJECTS; i++) {
            availableProjects.add(Project.generateRandomProject(client));
        }
    }

    public static void generateNewProject() {
        var index = Randomizer.generateRandomValue(availableProjects.size());
        var client = availableClients.get(index);

        availableProjects.add(Project.generateRandomProject(client));
    }

    public static void generateNewProject(boolean newClient) {
        if (newClient) {
            var client = Client.generateRandomClient();
            availableClients.add(client);
            availableProjects.add(Project.generateRandomProject(client));
        } else {
            generateNewProject();
        }
    }

    public static void addNewTransaction(LocalDate date, Project project) {
        projectTransactions.put(date, project);
    }
}
