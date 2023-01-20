package people.helpers;

import helpers.console.Randomizer;
import helpers.json.RandomEmployeesData;
import people.employees.Employee;
import people.enums.Position;

import java.util.Arrays;

public class RandomEmployeeGenerator {

    private RandomEmployeesData randomEmployeesData;

    public RandomEmployeeGenerator() {
        randomEmployeesData = new RandomEmployeesData();
    }

    public Employee generateRandomEmployee() {
        int percentage = Randomizer.generateRandomValue(100);

        Arrays.stream(Position.values()).sorted()
        return ;
    }
}
