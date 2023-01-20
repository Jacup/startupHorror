package people.helpers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import people.employees.Employee;

import static org.junit.jupiter.api.Assertions.*;

class RandomEmployeeGeneratorTest {

    static RandomEmployeeGenerator randomEmployeeGenerator;

    @BeforeAll
    static void initialize() {
        randomEmployeeGenerator = new RandomEmployeeGenerator();
    }

    @RepeatedTest(10)
    void checkIfRandomEmployeeIsNotNull() {
        //given
        //when
        Employee e = randomEmployeeGenerator.generateRandomEmployee();
        //then
        System.out.println(e);
        Assertions.assertNotNull(e);
    }
}