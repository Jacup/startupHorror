package main;

import people.Employee;
import people.enums.Position;

import java.time.LocalDate;
import java.util.ArrayList;

public class Company {

    // costs
    private static final Double EMPLOYEE_HIRE_COST = 2000.0;
    private static final Double EMPLOYEE_FIRE_COST = 2000.0;
    private static final Integer EMPLOYEE_TAXES_PERCENT = 30;

    // COMPANY RESOURCES
    private Double cash;

    private final String domain;
    private ArrayList<Employee> hiredEmployees = new ArrayList<>();
    private ArrayList<Employee> availableEmployees = new ArrayList<>();

    public Company(Double cash, String domain) {
        this.cash = cash;
        this.domain = domain;
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

}
