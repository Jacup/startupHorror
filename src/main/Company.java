package main;

import main.helpers.Randomizer;
import main.jobs.Project;
import main.people.Employee;

import java.util.ArrayList;
import java.util.LinkedList;

public class Company {

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
    private LinkedList<Employee> availableEmployees = new LinkedList<>();

    // JOBS
    private LinkedList<Project> availableProjects = new LinkedList<>();


    public Company(String name) {
        this.cash = generateRandomCashAmount();
        this.name = name;
        this.domain = name + ".pl";
    }

    private Double generateRandomCashAmount() {
        return (double) Randomizer.generateRandomValue(5000, 15000);
    }

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

    private boolean haveEnoughCash(Double value) {
        return cash >= value;
    }

    public String getDomain() {
        return domain;
    }

    public String getName() {
        return name;
    }
}
