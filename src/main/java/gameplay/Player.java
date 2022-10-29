package gameplay;

import company.Company;
import lombok.Getter;
import people.Human;

public class Player extends Human {

    @Getter
    private final Company company;

    public Player(Company company) {
        super("xd", "xd");
        this.company = company;
    }
}
