package main;

import main.helpers.Randomizer;
import main.jobs.Project;
import main.people.HumanTemplate;
import main.people.Owner;
import main.people.employees.Employee;
import main.people.enums.Position;

import java.util.LinkedList;

public class Company {

    // costs
    public static final Double HIRE_COST = 2000.0;
    private static final Double FIRE_COST = 2000.0;
    private static final Integer TAXES_PERCENT = 30;

    // COMPANY RESOURCES
    private Double cash;

    // COMPANY DETAILS
    private final String name;
    private final Owner owner;

    // HUMAN RESOURCES
    private LinkedList<Employee> hiredEmployees = new LinkedList<>();


    // JOBS ETC
    private LinkedList<Project> actualProjects = new LinkedList<>();


    public Company(String name) {
        this.cash = generateRandomCashAmount();
        this.name = name;
        this.owner = createOwner();
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
    public Owner createOwner() {
        return new Owner(HumanTemplate.getRandomFirstName(), HumanTemplate.getRandomLastName());
    }

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

    public int getAmountOfEmployeesByType(Position position) {
        return (int) hiredEmployees.stream().filter((employee) -> employee.getPosition().equals(position)).count();
    }

    public void performWork() {
        var developers = hiredEmployees.stream().filter(Employee::isDeveloper).toList();
        var project = getActualProject();
        if (project != null) {
            developers.forEach(developer -> developer.goToWork(project));
        }

        //        var testers = hiredEmployees.stream().filter(Employee::isTester).toList();

//        LinkedList<Sales> salesList = new LinkedList<>();
//
//        for (var worker : hiredEmployees) {
//            if (worker.isSales()) salesList.add((Sales) worker);
//        }


//        for (var salesman : salesList) {
//            if (salesman.isSick()) continue;
//
//            var projectFound = salesman.findNewProject();
//            if (projectFound) Game.generateNewProject();
//        }
    }


    // projects
    public LinkedList<Project> getActualProjects() {
        return actualProjects;
    }

    private Project getActualProject() {
        for (var actualProject : actualProjects) {
            if (!actualProject.isFinished()) {
                return actualProject;
            }
        }

        return null;
    }

    public void setActualProjects(LinkedList<Project> actualProjects) {
        this.actualProjects = actualProjects;
    }

    public boolean returnProject(Project project) {
        if (!project.isFinished()) {
            System.out.println("This project is not finished yet.");
            return false;
        }

        createPayment(project, project.isPenaltyAdded());
        actualProjects.remove(project);
        return true;
    }

    private void createPayment(Project project, boolean isPenalty) {
        var penalty = 1 - Project.DEFAULT_DEADLINE_PENALTY;
        var paymentDate = Game.getGameDate().plusDays(project.getPaymentDelayDays());
        var cash = isPenalty ? (project.getPayment() * penalty) : project.getPayment();

        Game.addNewTransaction(paymentDate, cash);
    }

    public boolean signNewProject(Project project) {
        actualProjects.add(project);
        project.setActualDeadline();
        System.out.println("\nCongratulations! Your have new project! \nDetails:");
        System.out.println(project);
        return true;
    }

    public Owner getOwner() {
        return owner;
    }
}
