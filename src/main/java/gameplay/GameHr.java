package gameplay;

import people.employees.Employee;

import java.util.LinkedList;

public class GameHr {
    private static final int START_EMPLOYEES = 10;
    private static LinkedList<Employee> availableEmployees;

    public GameHr() {
        availableEmployees = new LinkedList<>();
    }

    public void generateEmployees() {
        for (int i = 0; i < START_EMPLOYEES; i++) {
            availableEmployees.add(Employee.generateRandomEmployee());
        }
    }
}