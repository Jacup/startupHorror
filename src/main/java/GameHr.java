import main.helpers.Console;
import main.helpers.UserActions;
import main.java.Company;
import main.people.employees.Employee;
import main.people.enums.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static main.java.Company.HIRE_COST;


public class GameHr {
    private static final int START_EMPLOYEES = 10;
    private static LinkedList<Employee> availableEmployees;

    private Company company;

    public GameHr() {
        availableEmployees = new LinkedList<>();
    }

    public void generateEmployees() {
        for (int i = 0; i < START_EMPLOYEES; i++) {
            availableEmployees.add(Employee.generateRandomEmployee());
        }
    }

    public static void removeAvailableEmployee(Employee employee) {
        availableEmployees.remove(employee);
    }

    public boolean menu(Company company) {
        this.company = company;

        System.out.println("HR operations?");
        ArrayList<String> activities = new ArrayList<>(List.of(
                "1. Show hired employees",
                "2. Hire a new employee",
                "3. Fire an employee",
                "4. Post the job offer to find more employees",
                "0. Return to main menu"));

        Console.printList(activities);
        var choice = UserActions.getUserInputByte(activities.size(), true);

        return switch (choice) {
            case 1 -> showHiredEmployees();
            case 2 -> hireEmployee();
            case 3 -> fireEmployee();
            case 4 -> postAJobOffer();
            default -> false;
        };
    }

    /**
     * 1. show hired employees
     *
     * @return false, because showing hired employees does not take whole day.
     */
    private boolean showHiredEmployees() {
        var employees = company.getHiredEmployees();

        if (employees.isEmpty()) {
            System.out.println("You don't have any employees.");
            UserActions.pressEnterKeyToContinue();
            return false;
        }

        printEmployees(employees);

        UserActions.pressEnterKeyToContinue();
        return false;
    }

    /**
     * 2. hire employees menu
     *
     * @return true if day is ended and hiring was successfully.
     */
    private boolean hireEmployee() {
        if (availableEmployees.isEmpty()) {
            System.out.println("No one wants to work for you. Advertise your company at job boards.");
            UserActions.pressEnterKeyToContinue();
            return false;
        }
        System.out.println("Cost of hiring new employee: " + HIRE_COST + "\n");
        printEmployees(availableEmployees);

        var choice = UserActions.getUserInputByte(availableEmployees.size());
        if (choice == 0) return false;

        var chosenEmployee = availableEmployees.get(choice - 1);

        return company.hireEmployee(chosenEmployee);
    }

    /**
     * 3. Fire employees menu
     *
     * @return true if day is ended and firing was successfully.
     */
    private boolean fireEmployee() {
        var employees = company.getHiredEmployees();
        if (employees.isEmpty()) {
            System.out.println("You don't have any employees to fire.");
            UserActions.pressEnterKeyToContinue();
            return false;
        }

        printEmployees(employees);
        var choice = UserActions.getUserInputByte(employees.size());
        if (choice == 0) return false;

        var chosenEmployee = employees.get(choice - 1);
        return company.fireEmployee(chosenEmployee);
    }

    /**
     * 4. job offer menu
     *
     * @return true if day is ended and posting a job offer was successful.
     */
    private boolean postAJobOffer() {
        System.out.println("What employee do you want do recruit?");
        var positions = Arrays.stream(Position.values()).toList();
        printPositions(positions);
        UserActions.getUserInputByte(positions.size());

        return false;
    }

    private void printEmployees(List<Employee> employees) {
        for (int i = 1; i <= employees.size(); i++)
            System.out.println(Game.TAB + i + ". " + employees.get(i - 1));
        System.out.println(Game.TAB + 0 + ". Go back");
    }

    private void printPositions(List<Position> positions) {
        for (int i = 1; i <= positions.size(); i++)
            System.out.println(Game.TAB + i + ". " + positions.get(i - 1));
        System.out.println(Game.TAB + 0 + ". Go back");
    }
}