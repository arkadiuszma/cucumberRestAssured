package stepDef;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;


import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.is;


public class StepDefinitionsWeatherApi {
    WeatherApiDataProvider p = new WeatherApiDataProvider();
    private RequestSpecification requestSpec;
    private Response response;
    private final String BaseUrl = p.getBaseUrl();
    private final String apiKey = p.getApiKey();
    private final String appIdParam = p.getAppIdParam();

    @Given("Request specification with the city name {string}")
    public void givenRequestSpecificationWithCityName(String city) {
        requestSpec = getRequestSpecByCityName(city);
    }

    @Given("Request specification with the city ID {int}")
    public void givenRequestSpecificationWithCityId(int id) {
        requestSpec = getRequestSpecByCityId(id);
    }

    @When("Send a GET request")
    public void sendGetRequest() {
        response =
                given()
                        .spec(requestSpec).
                when()
                        .get();
    }

    @Then("Response should have status code {int}")
    public void validStatusCode(int statusCode) {
        Assertions.assertThat(statusCode).isEqualTo(response.getStatusCode());
    }

    @Then("Response should have a valid JSON schema")
    public void thenTheResponseShouldHaveAValidJsonSchema() {
        response.then().assertThat().body(matchesJsonSchema(new File(p.getJsonSchemaPath())));
    }

    @Then("Response should have the expected city name {string}")
    public void thenTheResponseShouldHaveTheExpectedCityName(String city) {
        response.then().assertThat().body(p.getNameParam(), is(city));
    }

    private RequestSpecification getRequestSpecByCityName(String city) {
        return new RequestSpecBuilder()
                .setBaseUri(BaseUrl)
                .addQueryParam(p.getCityParam(), city)
                .addQueryParam(appIdParam, apiKey)
                .setContentType(ContentType.JSON)
                .build();
    }

    private RequestSpecification getRequestSpecByCityId(int id) {
        return new RequestSpecBuilder()
                .setBaseUri(BaseUrl)
                .addQueryParam(p.getIdParam(), id)
                .addQueryParam(appIdParam, apiKey)
                .setContentType(ContentType.JSON)
                .build();
    }

    @Before
    public static void setup() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
}

