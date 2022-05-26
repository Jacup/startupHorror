package main;

import main.helpers.Randomizer;
import main.jobs.Project;
import main.people.HumanTemplate;
import main.people.Owner;
import main.people.employees.Developer;
import main.people.employees.Employee;
import main.people.employees.Sales;
import main.people.enums.Position;

import java.util.LinkedList;

public class Company {
    public static final Double HIRE_COST = 2000.0;
    private static final Double FIRE_COST = 2000.0;
    private static final Integer TAXES_PERCENT = 30;

    private final String name;
    private final Owner owner;
    private Double cash;

    private LinkedList<Employee> hiredEmployees = new LinkedList<>();
    private LinkedList<Project> actualProjects = new LinkedList<>();

    private Integer daysSpendOnTaxes;
    private Double monthlyTax;

    public Company(String name) {
        this.cash = generateRandomCashAmount();
        this.name = name;
        this.owner = new Owner(HumanTemplate.getRandomFirstName(), HumanTemplate.getRandomLastName());
        this.daysSpendOnTaxes = 0;
        this.monthlyTax = 0.0;
    }

    public boolean goToTaxOffice() {
        this.daysSpendOnTaxes++;
        return true;
    }

    // company
    public String getName() {
        return name;
    }

    private Double generateRandomCashAmount() {
        return (double) Randomizer.generateRandomValue(10000, 20000);
    }

    private boolean haveEnoughCash(Double value) {
        return cash >= value;
    }

    public Double getCash() {
        return cash;
    }

    public void addCash(Double value) {
        cash += value;
    }

    // employees
    public boolean hireEmployee(Employee employee) {
        if (!haveEnoughCash(HIRE_COST)) {
            System.out.println("You can't hire employee now, because you don't have enough money");
            return false;
        }

        cash -= HIRE_COST;
        hiredEmployees.add(employee);
        Game.removeAvailableEmployee(employee);
        System.out.println("Congratulations! You have hired new " + employee.getPosition());

        return true;
    }

    public boolean fireEmployee(Employee employee) {
        if (!hiredEmployees.contains(employee)) {
            System.out.println("Something gone wrong... " + employee.getName() + " does not work here.");
            return false;
        }

        if (!haveEnoughCash(FIRE_COST)) {
            System.out.println("You can't fire employee now, because you don't have enough money");
            return false;
        }

        hiredEmployees.remove(employee);
        cash -= FIRE_COST;
        System.out.println(employee.getPosition() + " " + employee.getName() + " has been fired.");
        return true;
    }

    public LinkedList<Employee> getHiredEmployees() {
        return hiredEmployees;
    }

    public LinkedList<Developer> getHiredDevelopers() {
        var developers = new LinkedList<Developer>();

        for (Employee worker : hiredEmployees) {
            if (worker.isDeveloper()) developers.add((Developer) worker);
        }

        return developers;
    }

    public LinkedList<Sales> getHiredSales() {
        var sales = new LinkedList<Sales>();

        for (Employee worker : hiredEmployees) {
            if (worker.isSales()) sales.add((Sales) worker);
        }

        return sales;
    }

    public int getAmountOfEmployeesByType(Position position) {
        return (int) hiredEmployees.stream().filter((employee) -> employee.getPosition().equals(position)).count();
    }

    public void paySalaryToWorkers() {
        // later I could calculate salary per day, because if someone is hired at the end of the month, he shouldn't get full salary.
        for (var employee : hiredEmployees) {
            Double salary = employee.getSalary();

            if (this.cash > salary) {
                this.cash = cash - salary;
                employee.addCash(salary);
            } else {
                System.out.println("Whoops! It looks like you don't have enough money to pay salary to " + employee.getName());
                Game.lostGame();
            }
        }
    }

    public void performWork() {
        sendDevelopersToWork();
        sendSalesToWork();
    }

    private void sendDevelopersToWork() {
        var developers = getHiredDevelopers();
        var projects = getActualProjects();

        if (projects != null) {
            for (var developer : developers) {
                var validProject = developer.getValidProject(projects);
                if (validProject != null) developer.goToWork(validProject);
            }
        }
    }

    private void sendSalesToWork() {
        var sales = getHiredSales();

        for (var salesman : sales) {
            salesman.goToWork();
        }
    }

    // projects
    public boolean signNewProject(Project project) {
        actualProjects.add(project);
        project.setActualDeadline();
        System.out.println("\nCongratulations! Your have new project! \nDetails:");
        System.out.println(project);
        return true;
    }

    public LinkedList<Project> getActualProjects() {
        return actualProjects;
    }

    public boolean returnProject(Project project) {
        // TODO: support for non-working projects, along with implementing testers.
        if (!project.isFinished()) {
            System.out.println("This project is not finished yet.");
            return false;
        }

        project.setFinalPayment();
        actualProjects.remove(project);
        var paymentDate = Game.getGameDate().plusDays(project.getPaymentDelayDays());

        Game.addNewTransaction(paymentDate, project);
        monthlyTax += project.getFinalPayment() * 0.10;

        return true;
    }

    public Owner getOwner() {
        return owner;
    }

    public Integer getDaysSpendOnTaxes() {
        return daysSpendOnTaxes;
    }

    public void addDaySpendOnTaxes() {
        daysSpendOnTaxes++;
    }

    public void resetTaxDays() {
        daysSpendOnTaxes = 0;
    }

    public void payMonthlyTaxes() {
        if (cash < monthlyTax) {
         System.out.println("You don't have enough money to pax monthly taxes from your incomes");
         System.out.println("Your cash: " + cash + ". Taxes to pay this month: " + monthlyTax);
         Game.lostGame();
        }
        else {
            cash -= monthlyTax;
            System.out.println("Successfully paid " + monthlyTax + " monthly taxes.");
            monthlyTax = 0.0;
        }
    }
}
