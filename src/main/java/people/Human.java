package people;

import gameplay.Company;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public abstract class Human {

    @Getter
    private final String firstName;

    @Getter
    private final String lastName;

    @Getter
    @Setter
    private Company company;

    public String getName() {
        return firstName + " " + lastName;
    }
}
