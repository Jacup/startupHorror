package main.jobs;

import main.helpers.Randomizer;
import main.jobs.enums.DifficultyLevel;
import main.jobs.enums.TechStack;
import main.people.Client;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class Project extends ProjectTemplate {
    private static final int MAX_SPARE_DAYS = 10;
    private static final int PLN_PER_DAY_RATE = 1200;
    private static final int DEFAULT_PAYMENT_DEADLINE = 20;
    private static final Double DEFAULT_DEADLINE_PENALTY = 0.2;

    private final String name;
    private final Client client;
    private final DifficultyLevel difficultyLevel;
    private Integer workingDaysLeft;

    private final Integer deadlineDays;
    private final Double deadlinePenalty;
    private LocalDate actualDeadline;

    private boolean isFinished;
    private final Integer paymentDeadlineDays;
    private final Double payment;

    private final HashMap<TechStack, Integer> techStackAndWorkload;

    public Project(Client client) {
        this.name = generateRandomName();
        this.client = client;
        this.isFinished = false;
        this.difficultyLevel = generateDifficultyLevel();
        this.paymentDeadlineDays = generatePaymentDeadline();
        this.techStackAndWorkload = generateTechStack();
        this.workingDaysLeft = setInitWorkingDaysLeft();
        this.payment = generatePayment();
        this.deadlinePenalty = generateDeadlinePenalty();
        this.deadlineDays = generateDeadlineDays();
    }

    // public getters

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

    public Integer getWorkingDaysLeft() {
        return workingDaysLeft;
    }

    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public String toString() {
        return "Project name= " + name + ", difficulty= " + difficultyLevel + ", tech stack= " + techStackAndWorkload.toString();
    }


    // public methods

    public static Project generateRandomProject() {
        return new Project(new Client("asd", "asd", Client.ClientType.EASY));
    }

    public boolean makeProgress() {
        if (isFinished || workingDaysLeft <= 0) {
            System.out.println("There is no more work to do.");
            return false;
        }

        workingDaysLeft--;

        if (workingDaysLeft.equals(0)) {
            System.out.println("Congratulations! You have finished working on this project. Please return this to the client.");
            isFinished = true;
        } else System.out.println("Days left to finish: " + workingDaysLeft);

        return true;
    }


    // private methods, generators

    private String generateRandomName() {
        var name = availableProjectNames.get(Randomizer.generateRandomValue(availableProjectNames.size()));
        availableProjectNames.remove(name);
        return name;
    }

    private Double generateDeadlinePenalty() {
        return payment * DEFAULT_DEADLINE_PENALTY;
    }

    private Integer generatePaymentDeadline() {
        int value = DEFAULT_PAYMENT_DEADLINE;
        switch (client.getType()) {
            case EASY:
                if (Randomizer.draw(30)) {
                    value += 7;
                }
                break;
            case DEMANDING:
                break;
            case MTHRFCKR:
                int chance = Randomizer.generateRandomValue(100);
                if (chance < 30) {
                    value += 7;
                } else if (chance < 35) {
                    value += 30;
                }
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
        int finalRate = Randomizer.generateRandomValue((int) (basePayment * 0.8), (int) (basePayment * 1.2));

        return (double) finalRate;
    }

    private Integer generateDeadlineDays() {
        int daysNeeded = setInitWorkingDaysLeft();

        return Randomizer.generateRandomValue(daysNeeded, daysNeeded + MAX_SPARE_DAYS);
    }

    private DifficultyLevel generateDifficultyLevel() {
        return DifficultyLevel.values()[new Random().nextInt(DifficultyLevel.values().length)];
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

    private Integer setInitWorkingDaysLeft() {
        int sum = 0;
        var getValues = techStackAndWorkload.values();
        for (Integer value : getValues) {
            sum += value;
        }
        return sum;
    }
}
