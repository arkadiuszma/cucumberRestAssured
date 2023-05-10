package stepDef;

import configuration.YamlReader;

public class WeatherApiDataProvider {
    public WeatherApiDataProvider() {
        new YamlReader();
    }

    protected String getCityParam() {
        return System.getProperty("cityParam");
    }

    protected String getIdParam() {
        return System.getProperty("idParam");
    }

    protected String getAppIdParam() {
        return System.getProperty("appIdParam");
    }

    protected String getNameParam() {
        return System.getProperty("nameParam");
    }

    protected String getJsonSchemaPath() {
        return System.getProperty("jsonSchemaPath");
    }

    protected String getBaseUrl() {
        return System.getProperty("baseUrl");
    }

    protected String getApiKey() {
        return System.getProperty("apiKey");
    }
}
