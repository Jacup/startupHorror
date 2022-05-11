package main;

import main.people.Employee;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Company {

    // costs
    private static final Double EMPLOYEE_HIRE_COST = 2000.0;
    private static final Double EMPLOYEE_FIRE_COST = 2000.0;
    private static final Integer EMPLOYEE_TAXES_PERCENT = 30;

    // COMPANY RESOURCES
    private Double cash;


    private final String name;
    private final String domain;
    private ArrayList<Employee> hiredEmployees = new ArrayList<>();
    private ArrayList<Employee> availableEmployees = new ArrayList<>();

    public Company(String name) {
        this.cash = generateRandomCashAmount();
        this.name = name;
        this.domain = name + ".pl";
    }

    private Double generateRandomCashAmount() {
        return (double) ThreadLocalRandom.current().nextInt(5000, 10000 + 1);
    }

    public String getDomain() {
        return domain;
    }

    public void hireEmployee(Employee employee) {
        if (!availableEmployees.isEmpty()) {
            if (haveEnoughCash(EMPLOYEE_HIRE_COST)) {
                cash -= EMPLOYEE_HIRE_COST;
                hiredEmployees.add(employee);
            } else {
                System.out.println("You can't hire employee now, because you don't have enough money");
            }
        } else {
            System.out.println("There is no one to hire. Spend some money on HR to find someone!");
        }
    }

    private boolean haveEnoughCash(Double value) {
        return cash >= value;
    }

    public String getName() {
        return name;
    }
}
