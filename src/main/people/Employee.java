package main.people;

import main.people.enums.Position;

public class Employee extends Human {

    private final Double salary;

    private final Position position;

    public Employee(String firstName, String lastName, Double salary, Position position) {
        super(firstName, lastName);
        this.salary = salary;
        this.position = position;
    }

    public static Employee generateRandomEmployee() {
        return new Employee("", "", 10.0, Position.DEVELOPER);
    }

    private String createEmailAddress() {
        return getFirstName().charAt(0) + getLastName() + "@firma.pl";          // to fix later
    }

    private Double getDefaultSalary(Position position) {
        double value;
        switch (position) {
            case DEVELOPER -> value = 15000.0;
            case TESTER -> value = 8000.0;
            case SALES -> value = 10000.0;
            default -> value = 5000.0;
        }
        return value;
    }

    public Position getPosition() {
        return position;
    }


}
