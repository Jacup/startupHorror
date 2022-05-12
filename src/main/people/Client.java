package main.people;

public class Client extends Human {

    public ClientType getType() {
        return type;
    }

    public enum ClientType {
        EASY, DEMANDING, MTHRFCKR
    }

    private final ClientType type;

    public Client(String firstName, String lastName, ClientType type) {
        super(firstName, lastName);
        this.type = type;
    }

    public static Client generateRandomClient() {
        return new Client("", "", ClientType.EASY);
    }
}
