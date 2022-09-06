package main.people;

import lombok.Getter;
import main.helpers.Randomizer;

public class Client extends Human {

    @Getter
    private final ClientType type;

    public enum ClientType {
        RELAXED, DEMANDING, MTHRFCKR
    }

    public Client() {
        super(HumanTemplate.getRandomFirstName(), HumanTemplate.getRandomLastName());
        this.type = ClientType.values()[Randomizer.generateRandomValue(ClientType.values().length)];
    }

    public static Client generateRandomClient() {
        return new Client();
    }
}
