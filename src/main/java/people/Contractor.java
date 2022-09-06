package people;

import main.people.enums.Position;
import main.people.enums.Seniority;

public class Contractor extends Human{

    private final Position position;

    private final Seniority seniority;


    public Contractor(String firstName, String lastName, Position position, Seniority seniority) {
        super(firstName, lastName);
        this.position = position;
        this.seniority = seniority;
    }
}
