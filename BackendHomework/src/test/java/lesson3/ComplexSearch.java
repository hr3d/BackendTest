package lesson3;

import io.restassured.path.json.JsonPath;
import lesson3.AbstractTest;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class ComplexSearch extends AbstractTest {

    @Test
    void getSearchBurgers() {

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("titleMatch", "burger")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response.get("offset"), equalTo(0));
        assertThat(response.get("number"), equalTo(10));
        assertThat(response.get("totalResults"), not(0));

    }

    @Test
    void getSearchFalafelBurgers() {

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("titleMatch", "Falafel Burgers")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response.get("offset"), equalTo(0));
        assertThat(response.get("number"), equalTo(10));
        assertThat(response.get("totalResults"), equalTo(1));
        assertThat(response.get("results[0].title"), equalTo("Falafel Burgers"));
        assertThat(response.get("results[0].id"), equalTo(642540));

    }

    @Test
    void getSearchByCalories() {

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("minCalories", "200")
                .queryParam("maxCalories", "600")
                .queryParam("number", "15")
                .queryParam("offset", "2")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response.get("offset"), equalTo(2));
        assertThat(response.get("number"), equalTo(15));
        assertThat(response.get("totalResults"), not(0));
        assertThat(response.get("results[0].nutrition.nutrients[0].name"), equalTo("Calories"));
        assertThat(response.get("results[0].nutrition.nutrients[0].amount"), allOf(greaterThanOrEqualTo(200f), lessThanOrEqualTo(600f)));

    }

    @Test
    void getSearchByIngredientsAndThenByReceivedTitle() {

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("includeIngredients", "Egg,milk,cheese")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response.get("offset"), equalTo(0));
        assertThat(response.get("number"), equalTo(10));
        assertThat(response.get("totalResults"), not(0));

        int MyTestID1 = response.get("results[0].id");
        String MyTestTitle1 = response.get("results[0].title");

        JsonPath response2 = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("titleMatch", MyTestTitle1)
                .queryParam("addRecipeInformation", true)
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response2.get("offset"), equalTo(0));
        assertThat(response2.get("number"), equalTo(10));
        assertThat(response2.get("totalResults"), equalTo(1));
        assertThat(response2.get("results[0].title"), equalTo(MyTestTitle1));
        assertThat(response2.get("results[0].id"), equalTo(MyTestID1));
        assertThat(response2.get("results[0].vegan"), equalTo(false));

    }

    @Test
    void getSearchWithSortByCalories() {

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("sort", "calories")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response.get("offset"), equalTo(0));
        assertThat(response.get("number"), equalTo(10));
        assertThat(response.get("results[0].nutrition.nutrients[0].name"), equalTo("Calories"));

        float amount1 = response.get("results[0].nutrition.nutrients[0].amount");
        float amount2 = response.get("results[1].nutrition.nutrients[0].amount");
        float amount3 = response.get("results[2].nutrition.nutrients[0].amount");
        assertThat(amount1, greaterThanOrEqualTo(amount2));
        assertThat(amount2, greaterThanOrEqualTo(amount3));

    }

}