package helpers.json;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.List;

public class RandomEmployeesData implements Serializable {

    @Getter
    @Setter
    private List<String> firstNames;

    @Getter
    @Setter
    private List<String> lastNames;

    public RandomEmployeesData deserialize() throws IOException, URISyntaxException {
        return new JsonHelper<RandomEmployeesData>().getPreparedData();
    }


}
