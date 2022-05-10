package people;

import java.time.LocalDate;

public abstract class Human {
    private final String firstName;
    private final String lastName;

    private final LocalDate Birthday;


    public Human(String firstName, String lastName, LocalDate birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        Birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
