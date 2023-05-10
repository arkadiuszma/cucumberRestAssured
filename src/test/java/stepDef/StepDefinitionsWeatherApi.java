package stepDef;

import configuration.BaseTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.is;

public class StepDefinitionsWeatherApi extends BaseTest {
    private RequestSpecification requestSpec;
    private Response response;

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
                        .spec(requestSpec)
                .when()
                        .get();
    }

    @Then("Response should have status code {int}")
    public void validStatusCode(int statusCode) {
        Assertions.assertThat(statusCode).isEqualTo(response.getStatusCode());
    }

    @Then("the response should have content type {string}")
    public void thenTheResponseShouldHaveContentType(String contentType) {
        Assertions.assertThat(contentType).isEqualTo(response.getContentType());
    }

    @Then("the response should have a valid JSON schema")
    public void thenTheResponseShouldHaveAValidJsonSchema() {
        response.then().assertThat().body(matchesJsonSchema(new File(System.getProperty("jsonSchemaPath"))));
    }

    @Then("the response should have the expected city name {string}")
    public void thenTheResponseShouldHaveTheExpectedCityName(String city) {
        response.then().assertThat().body(System.getProperty("nameParam"), is(city));
    }
}

