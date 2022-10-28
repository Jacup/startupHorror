package jobs;

import helpers.console.Randomizer;
import jobs.enums.DifficultyLevel;
import jobs.enums.TechStack;
import lombok.Getter;
import lombok.Setter;
import people.Contractor;
import people.enums.Seniority;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Project extends ProjectTemplate {
    private static final int MAX_SPARE_DAYS = 10;
    private static final int PLN_PER_DAY_RATE = 1200;
    private static final int DEFAULT_PAYMENT_DELAY = 10;
    public static final Double DEFAULT_DEADLINE_PENALTY = 0.2;

    @Getter
    private final String name;

    @Getter
    private final DifficultyLevel difficultyLevel;

    @Getter
    private final Contractor contractor;

    @Getter
    private final Integer deadlineDays;

    @Getter
    private final Double deadlinePenalty;

    @Getter
    private Double bugChance = 0.0;

    @Getter
    private boolean isFinished;

    @Getter
    private final Integer paymentDelayDays;

    @Getter
    private final Double payment;

    @Getter
    private final HashMap<TechStack, Integer> techStackAndWorkload;

    @Getter
    @Setter
    private HashMap<TechStack, Integer> workLeft;

    public Project(Contractor contractor) {
        this.name = generateRandomName();
        this.isFinished = false;
        this.difficultyLevel = DifficultyLevel.values()[Randomizer.generateRandomValue(DifficultyLevel.values().length)];
        this.paymentDelayDays = generatePaymentDelay();
        this.contractor = contractor;
        this.techStackAndWorkload = generateTechStack();
        this.workLeft = new HashMap<>(techStackAndWorkload);
        this.payment = generatePayment();
        this.deadlinePenalty = payment * DEFAULT_DEADLINE_PENALTY;
        this.deadlineDays = generateDeadlineDays();
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

    public void makeProgressByTech(TechStack tech, Seniority seniority) {
        var value = workLeft.get(tech);

        workLeft.replace(tech, value - 1);

        switch (seniority) {
            case SENIOR, MID -> {
                if ((bugChance + 0.05) <= 1.0) {
                    bugChance += 0.05;
                }
            }
            case JUNIOR -> {

                if ((bugChance + 0.1) <= 1.0) {
                    bugChance += 0.1;
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + seniority);
        }

        if (workLeft.get(tech) == 0) {
            workLeft.remove(tech);
        }

        if (workLeft.isEmpty()) {
            isFinished = true;
        }
    }

    public void removeBugs(Double singleTestValue) {
        if ((bugChance < singleTestValue) || ((bugChance - singleTestValue) < 0.0))
            throw new IllegalStateException("Unexpected value; " + bugChance + " cannot be lower than " + singleTestValue);
        bugChance -= singleTestValue;
    }

    // private methods, generators
    private String generateRandomName() {
        return availableProjectNames.get(Randomizer.generateRandomValue(availableProjectNames.size()));
    }

    private Integer generatePaymentDelay() {
        int paymentDelay = DEFAULT_PAYMENT_DELAY;
        if(Randomizer.draw(contractor.getContractorType().getDelayPaymentWeekChance()))
            paymentDelay += 7;
        //tbd

        return paymentDelay;
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
