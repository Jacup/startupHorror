package people;

import people.enums.Position;

import java.time.LocalDate;

public class Employee extends Human {

    private final String mail;
    private final Double salary;

    private final Position position;

    public Employee(String firstName, String lastName, LocalDate birthday, String mail, Double salary, Position position) {
        super(firstName, lastName, birthday);
        this.mail = mail;
        this.salary = salary;
        this.position = position;
    }

    public Employee(String firstName, String lastName, LocalDate birthday, String mail, Double salary, Position position) {
        super(firstName, lastName, birthday);
        this.mail = mail;
        this.salary = salary;
        this.position = position;
    }
}
