package main.jobs;

import main.Game;
import main.helpers.Randomizer;
import main.jobs.enums.DifficultyLevel;
import main.jobs.enums.TechStack;
import main.people.Client;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Project extends ProjectTemplate {
    private static final int MAX_SPARE_DAYS = 10;
    private static final int PLN_PER_DAY_RATE = 1200;
    private static final int DEFAULT_PAYMENT_DELAY = 10;
    public static final Double DEFAULT_DEADLINE_PENALTY = 0.2;

    private final String name;
    private final Client client;
    private final DifficultyLevel difficultyLevel;

    private final Integer deadlineDays;
    private final Double deadlinePenalty;
    private LocalDate actualDeadline;

    private boolean isFinished;
    private final Integer paymentDelayDays;
    private final Double payment;

    private final HashMap<TechStack, Integer> techStackAndWorkload;
    private HashMap<TechStack, Integer> workLeft;

    public Project(Client client) {
        this.name = generateRandomName();
        this.client = client;
        this.isFinished = false;
        this.difficultyLevel = DifficultyLevel.values()[Randomizer.generateRandomValue(DifficultyLevel.values().length)];
        this.paymentDelayDays = generatePaymentDelay();
        this.techStackAndWorkload = generateTechStack();
        this.workLeft = new HashMap<>(techStackAndWorkload);
        this.payment = generatePayment();
        this.deadlinePenalty = payment * DEFAULT_DEADLINE_PENALTY;
        this.deadlineDays = generateDeadlineDays();
    }

    // public getters and setters
    public String getName() {
        return name;
    }

    public Client getClient() {
        return client;
    }

    public Integer getDeadlineDays() {
        return deadlineDays;
    }

    public Double getDeadlinePenalty() {
        return deadlinePenalty;
    }

    public LocalDate getActualDeadline() {
        return actualDeadline;
    }

    public Integer getPaymentDelayDays() {
        return paymentDelayDays;
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

    public boolean isFinished() {
        return isFinished;
    }

    public HashMap<TechStack, Integer> getWorkLeft() {
        return workLeft;
    }

    public void setWorkLeft(HashMap<TechStack, Integer> workLeft) {
        this.workLeft = workLeft;
    }

    public int getDaysLeft() {
        var days = workLeft.values();
        var counter = 0;
        for (var day : days) {
            counter += day;
        }
        return counter;
    }

    @Override
    public String toString() {
        return "Project name= " + name + ", difficulty= " + difficultyLevel + ", tech stack= " + techStackAndWorkload.toString();
    }


    // public methods

    public static Project generateRandomProject() {
        return new Project(new Client("asd", "asd", Client.ClientType.EASY));
    }

    public void setActualDeadline() {
        actualDeadline = Game.getGameDate().plusDays(deadlineDays);
    }

    // TODO: add support by seniority + add support for msg list in header
    public void makeProgressByTech(TechStack tech) {
        var value = workLeft.get(tech);

        workLeft.replace(tech, value - 1);

        if (workLeft.get(tech) == 0) {
            workLeft.remove(tech);
            System.out.println("Finished work on " + tech + " in project. Removing from todo list.");
        }
        if (workLeft.isEmpty()) {
            isFinished = true;
            System.out.println("Finished work on " + getName() + " Return to client to get paid.");
        }
    }

    public boolean isDeadlinePassed() {
        return Game.getGameDate().isAfter(actualDeadline);
    }

    public Integer daysAfterDeadline() {
        if (isDeadlinePassed()) return Game.getGameDate().compareTo(actualDeadline);

        return 0;
    }

    public boolean isPenaltyAdded() {
        var days = daysAfterDeadline();

        if (client.getType() == Client.ClientType.EASY) {
            if (days <= 7) return !Randomizer.draw(20);
        }

        return true;
    }

    // private methods, generators
    private String generateRandomName() {
        var name = availableProjectNames.get(Randomizer.generateRandomValue(availableProjectNames.size()));
        availableProjectNames.remove(name);
        return name;
    }

    private Integer generatePaymentDelay() {
        int value = DEFAULT_PAYMENT_DELAY;
        switch (client.getType()) {
            case EASY:
                if (Randomizer.draw(30)) value += 7;
                break;
            case DEMANDING:
                break;
            case MTHRFCKR:
                int chance = Randomizer.generateRandomValue(100);
                if (chance < 30) value += 7;
                else if (chance < 35) value += 30;
                break;
        }

        return value;
    }

    private Double generatePayment() {
        int sumOfDaysNeeded = 0;
        var getValues = techStackAndWorkload.values();
        for (Integer value : getValues) {
            sumOfDaysNeeded += value;
        }

        int basePayment = sumOfDaysNeeded * PLN_PER_DAY_RATE;
        int finalRate = Randomizer.generateRandomValue((int) (basePayment * 0.7), (int) (basePayment * 1.3));

        return (double) finalRate;
    }

    private Integer generateDeadlineDays() {

        int daysNeeded = getDaysLeft();

        return Randomizer.generateRandomValue(daysNeeded, daysNeeded + MAX_SPARE_DAYS);
    }

    private HashMap<TechStack, Integer> generateTechStack() {
        int min, max;
        var DaysPerTech = 3;      // TODO temporary, idk how to handle this. maybe assign it with game difficulty lvl

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

            hashMap.put(techStack, DaysPerTech);
            availableTechStack.remove(techStack);
        }

        return hashMap;
    }
}
