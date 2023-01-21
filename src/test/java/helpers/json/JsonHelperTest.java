package helpers.json;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class JsonHelperTest {

    @Test
    void checkIfJsonInputWorks() {
        try {
            new JsonHelper().getPreparedData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}