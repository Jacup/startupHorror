package helpers.json;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class RandomEmployeesDataTest {

    static RandomEmployeesData randomEmployeesData;

    @BeforeAll
    static void initialize() {
        randomEmployeesData = new RandomEmployeesData();
    }

    @Test
    void isDeserializationOk(){
        try {
            randomEmployeesData.deserialize();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}