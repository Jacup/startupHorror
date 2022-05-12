package main.jobs;

import main.helpers.Randomizer;
import main.jobs.enums.DifficultyLevel;
import main.jobs.enums.TechStack;
import main.people.Client;

import java.time.LocalDate;
import java.util.*;

public class Project {
    private static final int MAX_SPARE_DAYS = 8;
    private static final int PLN_PER_HOUR_RATE = 150;
    private static final int DEFAULT_PAYMENT_DEADLINE = 20;

    private final String name;
    private final Client client;
    private final DifficultyLevel difficultyLevel;

    private final LocalDate deadline;
    private final Double deadlinePenalty;

    private final Integer paymentDeadlineDays;
    private final Double payment;

    private HashMap<TechStack, Integer> techStackAndWorkload = new HashMap<>();

    public Project(String name, Client client, LocalDate deadline, Double deadlinePenalty, Integer paymentDeadlineDays, Double payment) {
        this.name = name;
        this.client = client;
        this.difficultyLevel = generateDifficultyLevel();
        this.deadlinePenalty = deadlinePenalty;
        this.paymentDeadlineDays = generatePaymentDeadline();
        this.techStackAndWorkload = generateTechStack();
        this.payment = generatePayment();
        this.deadline = generateDeadline();
    }

    private Integer generatePaymentDeadline() {
        int value = DEFAULT_PAYMENT_DEADLINE;
        switch (client.getType()) {
            case EASY:
                if (Randomizer.draw(30)) {
                    value = value + 7;
                }
                break;
            case DEMANDING:
                break;
            case MTHRFCKR:
                int chance = Randomizer.generateRandomValue(100);
                if (chance < 30) {
                    value = value + 7;
                } else if (chance < 35) {
                    value = value + 30;
                }
                break;
        }
        return value;
    }

    private Double generatePayment() {
        int sumOfHoursNeeded = 0;
        var getValues = techStackAndWorkload.values();
        for (Integer value : getValues) {
            sumOfHoursNeeded = sumOfHoursNeeded + value;
        }
        int basePayment = sumOfHoursNeeded * PLN_PER_HOUR_RATE;

        int finalRate = Randomizer.generateRandomValue((int) (basePayment * 0.8), (int) (basePayment * 1.2));
        return (double) finalRate;
    }

    LocalDate generateDeadline() {
        int sumOfHoursNeeded = 0;
        var getValues = techStackAndWorkload.values();
        for (Integer value : getValues) {
            sumOfHoursNeeded = sumOfHoursNeeded + value;
        }
        int daysNeeded = (int) Math.ceil(sumOfHoursNeeded / 8);
        var randomDeadlineDays = Randomizer.generateRandomValue(daysNeeded, daysNeeded + MAX_SPARE_DAYS);

        return LocalDate.now().plusDays(randomDeadlineDays);
    }

    public String getName() {
        return name;
    }

    public Client getClient() {
        return client;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public Double getDeadlinePenalty() {
        return deadlinePenalty;
    }

    public Integer getPaymentDeadlineDays() {
        return paymentDeadlineDays;
    }

    public Double getPayment() {
        return payment;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public HashMap<TechStack, Integer> getTechStackAndWorkload() {
        return techStackAndWorkload;
    }

    private DifficultyLevel generateDifficultyLevel() {
        return DifficultyLevel.values()[new Random().nextInt(DifficultyLevel.values().length)];
    }

    private HashMap<TechStack, Integer> generateTechStack() {
        int min, max;
        var hoursPerTech = 30;      // temporary, idk how to handle this.

        var availableTechStack = new LinkedList<>(Arrays.asList(TechStack.class.getEnumConstants()));
        var hashMap = new HashMap<TechStack, Integer>();

        switch (difficultyLevel) {
            case EASY -> {
                min = 1;
                max = 1;
            }
            case MEDIUM -> {
                min = 2;
                max = 3;
            }
            case HARD -> {
                min = 3;
                max = 5;
            }
            default -> {
                min = 1;
                max = TechStack.values().length;
            }
        }

        int amountOfTechStacks = Randomizer.generateRandomValue(min, max);

        for (int i = 0; i < amountOfTechStacks; i++) {
            TechStack techStack = availableTechStack.get(Randomizer.generateRandomValue(availableTechStack.size()));

            hashMap.put(techStack, hoursPerTech);
            availableTechStack.remove(techStack);
        }

        return hashMap;
    }

    public static Project generateRandomProject() {
        return new Project("dupa", new Client("asd", "asd", Client.ClientType.EASY), LocalDate.now(), 5000.0, 60, 10000.0);
    }
}
