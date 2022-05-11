package main.jobs;

import main.helpers.Randomizer;
import main.jobs.enums.DifficultyLevel;
import main.jobs.enums.TechStack;
import main.people.Client;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Random;

public class Project {
    private static final int MAX_SPARE_DAYS = 8;

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
        this.paymentDeadlineDays = paymentDeadlineDays;
        this.payment = payment;
        this.techStackAndWorkload = generateTechStack();
        this.deadline = generateDeadline();
    }

    LocalDate generateDeadline() {
        int sumOfHoursNeeded = 0;
        var getValues = techStackAndWorkload.values();
        for (Integer value : getValues) {
            sumOfHoursNeeded = sumOfHoursNeeded + value;
        }
        int daysNeeded = (int) Math.ceil(sumOfHoursNeeded/8);
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
        int min = 0, max = TechStack.values().length;
        int hoursPerTech = 30;      // temporary, idk how to handle this.
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
        }
        int amountOfTechStacks = Randomizer.generateRandomValue(min, max);
        HashMap<TechStack, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < amountOfTechStacks; i++) {
            var techStack = TechStack.values()[new Random().nextInt(TechStack.values().length)];
            hashMap.put(techStack, hoursPerTech);
        }
        return hashMap;
    }

    public static Project generateRandomProject() {
        return new Project("dupa", new Client("asd", "asd"), LocalDate.now(), 5000.0, 60, 10000.0);
    }
}
