package main;

import main.helpers.Randomizer;
import main.jobs.Project;
import main.people.Employee;
import main.people.enums.Position;

import java.util.LinkedList;

public class Company extends Game {

    // costs
    private static final Double EMPLOYEE_HIRE_COST = 2000.0;
    private static final Double EMPLOYEE_FIRE_COST = 2000.0;
    private static final Integer EMPLOYEE_TAXES_PERCENT = 30;

    // COMPANY RESOURCES
    private Double cash;

    // COMPANY DETAILS
    private final String name;
    private final String domain;

    // HUMAN RESOURCES
    private LinkedList<Employee> hiredEmployees = new LinkedList<>();

    // JOBS ETC
    private LinkedList<Project> actualProjects = new LinkedList<>();


    public Company(String name) {
        this.cash = generateRandomCashAmount();
        this.name = name;
        this.domain = name + ".pl";
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

    public String getDomain() {
        return domain;
    }

    // employees
    public void hireEmployee(Employee employee) {
        if (!availableEmployees.isEmpty()) {
            if (haveEnoughCash(EMPLOYEE_HIRE_COST)) {
                cash -= EMPLOYEE_HIRE_COST;
                hiredEmployees.add(employee);
                availableEmployees.remove(employee);
            } else {
                System.out.println("You can't hire employee now, because you don't have enough money");
            }
        } else {
            System.out.println("There is no one to hire. Spend some money on HR to find someone!");
        }
    }

    public void fireEmployee(Employee employee) {
        if (hiredEmployees.contains(employee)) {

        }
    }

    public LinkedList<Employee> getHiredEmployees() {
        return hiredEmployees;
    }

    public int getAmountOfEmployeesByType(Position position) {
        return (int) hiredEmployees.stream().filter((employee) -> employee.getPosition().equals(position)).count();
    }

    // projects

    public LinkedList<Project> getActualProjects() {
        return actualProjects;
    }

    public void setActualProjects(LinkedList<Project> actualProjects) {
        this.actualProjects = actualProjects;
    }

    public boolean returnProject() {
        return false;
    }

    public boolean signNewProject(Project project) {
        actualProjects.add(project);
        getAvailableProjects().remove(project);
        System.out.println("\nCongratulations! Your have new project! \nDetails:");
        System.out.println(project.toString());
        return true;
    }
}
