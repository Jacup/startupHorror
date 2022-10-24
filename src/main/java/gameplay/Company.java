package gameplay;

import helpers.Randomizer;
import jobs.Project;

import lombok.Getter;

import people.employees.Developer;
import people.employees.Employee;
import people.employees.Sales;
import people.employees.Tester;
import people.enums.Position;

import java.util.LinkedList;
import java.util.stream.Collectors;
public class Company {
    private static final double DAILY_TEST_AMOUNT = 0.05;
    @Getter
    private final String name;

    @Getter
    private Double cash;

    @Getter
    private final LinkedList<Employee> hiredEmployees = new LinkedList<>();
    private final LinkedList<Project> actualProjects = new LinkedList<>();

    private Integer daysSpendOnTaxes;

    public Company(String name) {
        this.cash = generateRandomCashAmount();
        this.name = name;
        this.daysSpendOnTaxes = 0;
    }

    private Double generateRandomCashAmount() {
        return (double) Randomizer.generateRandomValue(10000, 20000);
    }

    public LinkedList<Developer> getHiredDevelopers() {
        var developers = new LinkedList<Developer>();

        for (Employee worker : hiredEmployees.stream().filter(e -> e.getPosition() == Position.DEVELOPER).collect(Collectors.toList())) {
            developers.add((Developer) worker);
        }

        return developers;
    }

    public LinkedList<Tester> getHiredTesters() {
        var testers = new LinkedList<Tester>();

        for (Employee worker : hiredEmployees.stream().filter(e -> e.getPosition() == Position.TESTER).collect(Collectors.toList())) {
            testers.add((Tester) worker);
        }

        return testers;
    }

    public LinkedList<Sales> getHiredSales() {
        var sales = new LinkedList<Sales>();

        for (Employee worker : hiredEmployees.stream().filter(e -> e.getPosition() == Position.SALES).collect(Collectors.toList())) {
            sales.add((Sales) worker);
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

}
