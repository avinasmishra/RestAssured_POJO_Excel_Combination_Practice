package Utiles;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

public class ReadDataMapper {
    public String getPath(String sourceString) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<?, ?> map = mapper.readValue(Paths.get("src/test/resources/data_mapper.json").toFile(), Map.class);
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                if (entry.getKey().toString().equalsIgnoreCase(sourceString)) {
                    return (String) entry.getValue();
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return "";
    }
}
