package gameplay;

import company.Company;
import helpers.console.Console;
import helpers.console.UserActions;
import jobs.enums.DifficultyLevel;

public class Game {
    public static final String TAB = "    ";

    private static int successfulProjects;

    private DifficultyLevel difficultyLvl;

    private final Console console = new Console();

    private static GameTime gameTime;

    private Company company;

    public Game() {
        successfulProjects = 0;
        gameTime = new GameTime();
        company = createCompany();
    }

    public static void lostGame() {
        System.out.println("\n\n\nSorry. You lost. ");
        UserActions.pressEnterKeyToContinue();
        System.exit(1);
    }

    public void play() {
        while (gameIsContinued()) {
            if (true) {
                console.printDayActivities();
                routines();
                if (gameTime.isWorkDay()) company.performWork();
                gameTime.nextDay();
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
        monthlyRoutines();
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
            company.getCompanyHrOperations().paySalaryToWorkers();

            //there's no method now
            // monthly tax
            //company.getCompanyHrOperations().
        }
    }

    public Company createCompany() {
        System.out.println("\nPlease enter name of your startup: ");

        String companyName = UserActions.getUserInputString();
        return new Company(companyName);
    }
}
