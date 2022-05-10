package people;

import java.time.LocalDate;

public abstract class Human {
    private final String firstName;
    private final String lastName;


    public Human(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
