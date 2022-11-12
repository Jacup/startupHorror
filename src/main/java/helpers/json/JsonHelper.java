package helpers.json;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class JsonHelper<T> {



    public RandomEmployeesData getPreparedData() throws IOException, URISyntaxException {

        URI uri = URI.create(this.getClass().getResource("/helpers.json/preparedData.json").toString());

        FileReader reader = new FileReader(new File(uri));

        Jsonb jsonb = JsonbBuilder.create();
        RandomEmployeesData s = jsonb.fromJson(reader, RandomEmployeesData.class);

        return s;
    }
}
