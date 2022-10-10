package people;

import helpers.Randomizer;
import lombok.Getter;

public class Client extends Human {

    @Getter
    private final ClientType type;

    public enum ClientType {
        RELAXED, DEMANDING, MTHRFCKR
    }

    public Client() {
        super();
        this.type = ClientType.values()[Randomizer.generateRandomValue(ClientType.values().length)];
    }

    public static Client generateRandomClient() {
        return new Client();
    }
}
