package people;

import gameplay.Company;
import lombok.Getter;
import lombok.Setter;

public abstract class Human {

    @Getter
    private String firstName;

    @Getter
    private String lastName;

    @Getter
    @Setter
    private Company company;

    public Human() {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }
}
