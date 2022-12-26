package lesson3;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class ClassifyCuisine extends AbstractTest {

    @Test
    void getCuisineForFalafelBurgers() {

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("language", "en")
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","Falafel Burgers")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("American"));
        assertThat(response.get("confidence"), greaterThanOrEqualTo(0.1f));

    }

    @Test
    void getCuisineForFalafelBurgersInGerman() {

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("language", "de")
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","Falafel Burgers")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("American"));
        assertThat(response.get("confidence"), greaterThanOrEqualTo(0.1f));

    }

    @Test
    void checkRequestWithoutParameters() {

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("confidence"), equalTo(0.0f));

    }

    @Test
    void getCuisineForJapaneseSushi() {

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("language", "en")
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","Japanese Sushi")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("Japanese"));
        assertThat(response.get("confidence"), greaterThanOrEqualTo(0.1f));

    }

    @Test
    void getCuisineForButtermilkCornbreadAndSageStuffing() {

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","Buttermilk Cornbread and Sage Stuffing")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("Southern"));
        assertThat(response.get("confidence"), greaterThanOrEqualTo(0.1f));

    }

}