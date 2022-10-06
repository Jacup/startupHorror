package people;

import helpers.Randomizer;

import java.util.ArrayList;
import java.util.List;

// temporary, to be refactored into some additional dictionary, maybe even in .txt or other .csv file
public abstract class HumanTemplate {
    protected static final ArrayList<String> firstNames = new ArrayList<>(List.of("James", "Robert", "John", "Michael", "David", "William", "Richard", "Joseph", "Thomas", "Charles", "Mary", "Patricia", "Jennifer", "Linda", "Elizabeth", "Barbara", "Susan", "Jessica", "Sarah", "Karen"));
    protected static final ArrayList<String> lastNames = new ArrayList<>(List.of("Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Martin"));

    public static String getRandomFirstName() {
        var val = Randomizer.generateRandomValue(firstNames.size());
        return firstNames.get(val);
    }

    public static String getRandomLastName() {
        var val = Randomizer.generateRandomValue(lastNames.size());
        return lastNames.get(val);
    }
}
