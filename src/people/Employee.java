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

    public Employee(String firstName, String lastName, LocalDate birthday, Position position) {
        super(firstName, lastName, birthday);
        this.mail = createEmailAddress();
        this.salary = getDefaultSalary(position);
        this.position = position;
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

    public String getMail() {
        return mail;
    }

    public Position getPosition() {
        return position;
    }
}
