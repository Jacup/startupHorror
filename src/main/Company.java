package main;

import main.helpers.Randomizer;
import main.jobs.Project;
import main.people.Employee;
import main.people.HumanTemplate;
import main.people.Owner;
import main.people.enums.Position;

import java.util.LinkedList;

public class Company extends Game {

    // costs
    public static final Double HIRE_COST = 2000.0;
    private static final Double FIRE_COST = 2000.0;
    private static final Integer TAXES_PERCENT = 30;

    // COMPANY RESOURCES
    private Double cash;

    // COMPANY DETAILS
    private final String name;
    private final String domain;
    private final Owner owner;

    // HUMAN RESOURCES
    private LinkedList<Employee> hiredEmployees = new LinkedList<>();


    // JOBS ETC
    private LinkedList<Project> actualProjects = new LinkedList<>();


    public Company(String name) {
        this.cash = generateRandomCashAmount();
        this.name = name;
        this.domain = name + ".pl";
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

    public String getDomain() {
        return domain;
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
        availableEmployees.remove(employee);
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

    public void goToWork() {
        Project projectToWorkOn = null;

        for (var actualProject : actualProjects) {
            if (!actualProject.isFinished()) {
                projectToWorkOn = actualProject;
                break;
            }
        }


        if (projectToWorkOn == null) {
            System.out.println("sda");
        } else {

            boolean newProjectFound = false;

            for (var employee : hiredEmployees) {
                switch (employee.getPosition()) {
                    case DEVELOPER -> {
                        projectToWorkOn.makeProgressByEmployee();
                    }
                    case TESTER -> {
                    }
                    case SALES -> {
                        newProjectFound = employee.findNewProject();
                    }
                }


                if (newProjectFound) {
                    availableProjects.add(Project.generateRandomProject());
                }

            }
        }

    }


    // projects
    public LinkedList<Project> getActualProjects() {
        return actualProjects;
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
        return true;
    }

    private void createPayment(Project project, boolean isPenalty) {
        var penalty = 1 - Project.DEFAULT_DEADLINE_PENALTY;
        var paymentDate = getGameDate().plusDays(project.getPaymentDelayDays());
        var cash = isPenalty ? (project.getPayment() * penalty) : project.getPayment();

        dailyTransactions.put(paymentDate, cash);
    }

    public boolean signNewProject(Project project) {
        actualProjects.add(project);
        project.setActualDeadline();
        System.out.println("\nCongratulations! Your have new project! \nDetails:");
        System.out.println(project.toString());
        return true;
    }

    public Owner getOwner() {
        return owner;
    }
}
