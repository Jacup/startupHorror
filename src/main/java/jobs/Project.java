package jobs;

import lombok.Getter;
import lombok.Setter;
import main.helpers.Randomizer;
import main.jobs.enums.DifficultyLevel;
import main.jobs.enums.TechStack;

import java.time.LocalDate;
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
    private final Client client;

    @Getter
    private final DifficultyLevel difficultyLevel;

    @Getter
    private final Integer deadlineDays;

    @Getter
    private final Double deadlinePenalty;

    @Getter
    private LocalDate actualDeadline;

    @Getter
    private Double bugChance = 0.0;

    @Getter
    private boolean isFinished;

    @Getter
    @Setter
    private boolean developedByOwner;

    @Getter
    private final Integer paymentDelayDays;

    @Getter
    private final Double payment;

    @Getter
    private Double finalPayment;

    @Getter
    private final HashMap<TechStack, Integer> techStackAndWorkload;

    @Getter
    @Setter
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
        this.developedByOwner = false;
    }

    public Integer getEstimatedPaymentDate() {
        return DEFAULT_PAYMENT_DELAY;
    }

    public String getBugsChance(boolean asPercentage) {
        if (asPercentage) {
            return bugChance * 100 + "%";
//            return Game.decimalFormat.format(bugChance * 100) + "%";
        } else return bugChance.toString();
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

    public String toString(boolean showWorkLeft) {
        if (showWorkLeft)
            return "Project name= " + name + ", difficulty= " + difficultyLevel + ", tech stack= " + workLeft.toString();
        return toString();
    }

    public void setFinalPayment() {
        var penalty = 1 - DEFAULT_DEADLINE_PENALTY;
        finalPayment = isPenaltyAdded() ? (this.getPayment() * penalty) : this.getPayment();

        if (client.getType() == Client.ClientType.MTHRFCKR) {
            if (Randomizer.draw(1)) finalPayment = 0.0;
        }
    }

    public boolean isBugged() {
        if (bugChance > 0.75) return true;
        else if (bugChance > 0.5) return !Randomizer.draw(50);
        else if (bugChance > 0.25) return !Randomizer.draw(75);
        else if (bugChance > 0.10) return !Randomizer.draw(90);

        return false;
    }

    // public methods

    public static Project generateRandomProject(Client client) {
        return new Project(client);
    }

    public void setActualDeadline() {
        actualDeadline = Game.getGameDate().plusDays(deadlineDays);
    }

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

    public boolean isDeadlinePassed() {
        return Game.getGameDate().isAfter(actualDeadline);
    }

    public Integer daysAfterDeadline() {
        if (isDeadlinePassed()) return Game.getGameDate().compareTo(actualDeadline);

        return 0;
    }

    public boolean isPenaltyAdded() {
        var days = daysAfterDeadline();

        if (client.getType() == Client.ClientType.RELAXED) {
            if (days <= 7) return !Randomizer.draw(20);
        }

        return true;
    }

    // private methods, generators
    private String generateRandomName() {
        return availableProjectNames.get(Randomizer.generateRandomValue(availableProjectNames.size()));
    }

    private Integer generatePaymentDelay() {
        int paymentDelay = DEFAULT_PAYMENT_DELAY;
        switch (client.getType()) {
            case RELAXED:
                if (Randomizer.draw(30)) paymentDelay += 7;
                break;
            case DEMANDING:
                break;
            case MTHRFCKR:
                int chance = Randomizer.generateRandomValue(100);
                if (chance < 30) paymentDelay += 7;
                else if (chance < 35) paymentDelay += 30;
                break;
        }

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
