package lesson4;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class ClassifyCuisine extends AbstractTest {

    @Test
    void getCuisineForFalafelBurgers() {

        ResponseForClassifyCuisine responseForClassifyCuisine = given()
                .queryParam("language", "en")
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","Falafel Burgers")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .then()
                .extract()
                .response()
                .body()
                .as(ResponseForClassifyCuisine.class);
        assertThat(responseForClassifyCuisine.getCuisine(), equalTo("American"));
        assertThat(responseForClassifyCuisine.getConfidence(), greaterThanOrEqualTo(0.1f));

    }

    @Test
    void getCuisineForFalafelBurgersInGerman() {

        ResponseForClassifyCuisine responseForClassifyCuisine = given()
                .queryParam("language", "de")
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","Falafel Burgers")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .then()
                .extract()
                .response()
                .body()
                .as(ResponseForClassifyCuisine.class);
        assertThat(responseForClassifyCuisine.getCuisine(), equalTo("American"));
        assertThat(responseForClassifyCuisine.getConfidence(), greaterThanOrEqualTo(0.1f));

    }

    @Test
    void checkRequestWithoutParameters() {

        ResponseForClassifyCuisine responseForClassifyCuisine = given()
                .contentType("application/x-www-form-urlencoded")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .then()
                .extract()
                .response()
                .body()
                .as(ResponseForClassifyCuisine.class);
        assertThat(responseForClassifyCuisine.getConfidence(), equalTo(0.0f));

    }

    @Test
    void getCuisineForJapaneseSushi() {

        ResponseForClassifyCuisine responseForClassifyCuisine = given()
                .queryParam("language", "en")
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","Japanese Sushi")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .then()
                .extract()
                .response()
                .body()
                .as(ResponseForClassifyCuisine.class);
        assertThat(responseForClassifyCuisine.getCuisine(), equalTo("Japanese"));
        assertThat(responseForClassifyCuisine.getConfidence(), greaterThanOrEqualTo(0.1f));

    }

    @Test
    void getCuisineForButtermilkCornbreadAndSageStuffing() {

        ResponseForClassifyCuisine responseForClassifyCuisine = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","Buttermilk Cornbread and Sage Stuffing")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .then()
                .extract()
                .response()
                .body()
                .as(ResponseForClassifyCuisine.class);
        assertThat(responseForClassifyCuisine.getCuisine(), equalTo("Southern"));
        assertThat(responseForClassifyCuisine.getConfidence(), greaterThanOrEqualTo(0.1f));

    }

}