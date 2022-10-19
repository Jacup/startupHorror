package gameplay;

import helpers.Console;
import helpers.Randomizer;
import helpers.UserActions;
import java.time.LocalDate;
import java.util.*;
import jobs.Project;
import jobs.enums.DifficultyLevel;
import lombok.Singular;
import people.Client;

public class Game {
    public static final String TAB = "    ";

    private static final int START_CLIENTS = 5;
    private static final int START_PROJECTS = 3;
    private static final HashMap<LocalDate, Project> projectTransactions = new HashMap<>();

    private static LinkedList<Client> availableClients;
    private static LinkedList<Project> availableProjects;

    private static int successfulProjects;

    private DifficultyLevel difficultyLvl;
    private final Console console = new Console();

    private final Scanner scanner;
    private final GameHr gameHr;
    private static GameTime gameTime;

    @Singular
    private List<Player> players;

    private Company company;

    public Game() {
        this.scanner = new Scanner(System.in);
        gameHr = new GameHr();
        availableClients = new LinkedList<>();
        availableProjects = new LinkedList<>();
        successfulProjects = 0;
        gameTime = new GameTime();
    }

    public static LocalDate getGameDate() {
        return gameTime.getLocalDate();
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
            if (true) {
                console.printDayActivities();
                routines();
                if (gameTime.isWorkDay()) company.performWork();
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

    private void routines() {
        dailyRoutines();
        monthlyRoutines();
    }

    private void dailyRoutines() {
        getPaymentsForProjects();
    }

    private void getPaymentsForProjects() {
        if (projectTransactions.containsKey(gameTime.getLocalDate())) {
            var project = projectTransactions.get(gameTime.getLocalDate());
            company.addCash(project.getFinalPayment());

            if (!project.isDevelopedByOwner() && project.getDifficultyLevel().equals(DifficultyLevel.HARD))
                successfulProjects++;
        }
    }

    private void monthlyRoutines() {
        if (gameTime.getGameDay() == gameTime.getLocalDate().lengthOfMonth()) {
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
        gameTime.setGameDay(gameTime.getGameDay() + 1);
        gameTime.nextDay();
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
}
