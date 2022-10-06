package people;

import lombok.Getter;

public abstract class Human {

    @Getter
    private final String firstName;

    @Getter
    private final String lastName;
    
    public Human(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }
}
