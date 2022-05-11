package main.people;

public class Client extends Human{

    public enum Type {
        EASY, DEMANDING, MTHRFCKR
    }

    public Client(String firstName, String lastName) {
        super(firstName, lastName);
    }
}
