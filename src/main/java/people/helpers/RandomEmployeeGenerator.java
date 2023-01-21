package people.helpers;

import helpers.console.Randomizer;
import helpers.json.JsonHelper;
import helpers.json.RandomEmployeesData;
import people.employees.Developer;
import people.employees.Employee;
import people.employees.Sales;
import people.employees.Tester;

import java.io.IOException;
import java.net.URISyntaxException;

public class RandomEmployeeGenerator {

    private RandomEmployeesData randomEmployeesData;

    public Employee generateRandomEmployee() {
        Employee employee;
        int percentage = Randomizer.generateRandomValue(100);

        try {
            randomEmployeesData = new JsonHelper().getPreparedData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String randomName = randomEmployeesData.getFirstNames().get(Randomizer.generateRandomValue(0, randomEmployeesData.getFirstNames().size() -1));
        String randomSurname = randomEmployeesData.getLastNames().get(Randomizer.generateRandomValue(0, randomEmployeesData.getLastNames().size() -1));

        if(percentage > 50) {
            employee = new Developer(randomName, randomSurname);
        } else if(percentage > 25) {
            employee = new Tester(randomName, randomSurname);
        } else {
            employee = new Sales(randomName, randomSurname);
        }

        return employee;
    }
}
