package people;

import company.Company;

import lombok.Getter;
import lombok.Setter;

public abstract class Human {

    @Getter
    private final String firstName;

    @Getter
    private final String lastName;

    @Getter
    @Setter
    private Company company;

    public Human(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }
}
