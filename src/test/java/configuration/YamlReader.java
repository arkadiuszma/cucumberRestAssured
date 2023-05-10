package configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@SuppressWarnings("unchecked")
public class YamlReader {
    public YamlReader() {
        this.getConfigFileValues();
    }

    private Map<String, Object> readConfigFile() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(new File("src/test/resources/config.yaml"), Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getConfigFileValues() {
        Map<String, Object> properties = (Map<String, Object>) readConfigFile().get("apiTestData");
        if (properties != null) {
            properties.forEach((key, value) -> System.setProperty(key, value.toString()));
        }
    }
}
