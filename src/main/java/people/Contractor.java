package people;


import people.enums.Position;
import people.enums.Seniority;

public class Contractor extends Human{

    private final Position position;

    private final Seniority seniority;


    public Contractor(String firstName, String lastName, Position position, Seniority seniority) {
        super();
        this.position = position;
        this.seniority = seniority;
    }
}
