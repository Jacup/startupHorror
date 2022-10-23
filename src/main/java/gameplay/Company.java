package gameplay;

import helpers.Randomizer;
import jobs.Project;

import lombok.Getter;

import people.employees.Developer;
import people.employees.Employee;
import people.employees.Sales;
import people.employees.Tester;

import java.util.LinkedList;

public class Company {
    private static final double DAILY_TEST_AMOUNT = 0.05;

    @Getter
    private final String name;

    @Getter
    private Double cash;

    private final LinkedList<Employee> hiredEmployees = new LinkedList<>();
    private final LinkedList<Project> actualProjects = new LinkedList<>();

    private Integer daysSpendOnTaxes;
    private Double monthlyTax;

    public Company(String name) {
        this.cash = generateRandomCashAmount();
        this.name = name;
        this.daysSpendOnTaxes = 0;
        this.monthlyTax = 0.0;
    }

    private Double generateRandomCashAmount() {
        return (double) Randomizer.generateRandomValue(10000, 20000);
    }

    public LinkedList<Developer> getHiredDevelopers() {
        var developers = new LinkedList<Developer>();

        for (Employee worker : hiredEmployees) {
            if (worker.isDeveloper()) developers.add((Developer) worker);
        }

        return developers;
    }

    public LinkedList<Tester> getHiredTesters() {
        var testers = new LinkedList<Tester>();

        for (Employee worker : hiredEmployees) {
            if (worker.isTester()) testers.add((Tester) worker);
        }

        return testers;
    }

    public LinkedList<Sales> getHiredSales() {
        var sales = new LinkedList<Sales>();

        for (Employee worker : hiredEmployees) {
            if (worker.isSales()) sales.add((Sales) worker);
        }

        return sales;
    }

    public void paySalaryToWorkers() {
        // TODO: calculate salary per day, because if someone is hired at the end of the month, he shouldn't get full salary.
        for (var employee : hiredEmployees) {
            Double salary = employee.getSalary();

            if (this.cash > salary) {
                this.cash = cash - salary;
            } else {
                System.out.println("Whoops! It looks like you don't have enough money to pay salary to " + employee.getName());
                Game.lostGame();
            }
        }
    }

    public void performWork() {
        sendDevelopersToWork();
        sendTestersToWork();
        sendSalesToWork();
    }

    private void sendDevelopersToWork() {
        var developers = getHiredDevelopers();
        var projects = getActualProjects();

        if (projects != null) {
            for (var developer : developers) {
                System.out.println("> Developer " + developer.getName() + ":");
                if (developer.isSick()) {
                    System.out.println(Game.TAB + getName() + " is sick today.");
                    continue;
                }

                var validProject = developer.getFirstValidProject(projects);
                if (validProject != null) developer.goToWork(validProject);
            }
            System.out.println();
        }
    }

    private void sendTestersToWork() {
        var testers = getHiredTesters();
        if (testers == null) return;

        var projects = getActualProjects();
        if (projects == null) return;

        for (var tester : testers) {
            System.out.println("> Tester " + tester + ":");
            if (tester.isSick()) {
                System.out.println(Game.TAB + getName() + " is sick today.");
                continue;
            }

            for (int i = 0; i < DAILY_TEST_AMOUNT; i++) {

                var validProject = tester.getFirstValidProject(projects);
                if (validProject != null) tester.goToWork(validProject);
            }
        }
    }

    private void sendSalesToWork() {
        var sales = getHiredSales();

        for (var salesman : sales) {
            salesman.goToWork(actualProjects.getFirst());
        }
    }

    public LinkedList<Project> getActualProjects() {
        return actualProjects;
    }

    public Integer getDaysSpendOnTaxes() {
        return daysSpendOnTaxes;
    }

    public void resetTaxDays() {
        daysSpendOnTaxes = 0;
    }

    public void payMonthlyTaxes() {
        if (cash < monthlyTax) {
            System.out.println("You don't have enough money to pax monthly taxes from your incomes");
            System.out.println("Your cash: " + cash + ". Taxes to pay this month: " + monthlyTax);
            Game.lostGame();
        } else {
            cash -= monthlyTax;
            System.out.println("Successfully paid " + monthlyTax + " monthly taxes.");
            monthlyTax = 0.0;
        }
    }
}
