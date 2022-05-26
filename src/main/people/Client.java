package main.people;

import main.helpers.Randomizer;

public class Client extends Human {

    private final ClientType type;

    public enum ClientType {
        RELAXED, DEMANDING, MTHRFCKR
    }

    public Client() {
        super(HumanTemplate.getRandomFirstName(), HumanTemplate.getRandomLastName());
        this.type = ClientType.values()[Randomizer.generateRandomValue(ClientType.values().length)];
    }

    // public methods

    public ClientType getType() {
        return type;
    }

    public static Client generateRandomClient() {
        return new Client();
    }

    // private methods


}
