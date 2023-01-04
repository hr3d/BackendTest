package lesson4;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class ComplexSearch extends AbstractTest {

    @Test
    void getSearchBurgers() {
        ResponseForComplexSearch responseForComplexSearch = given()
                .queryParam("titleMatch", "burger")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .extract()
                .response()
                .body()
                .as(ResponseForComplexSearch.class);
        assertThat(responseForComplexSearch.getOffset(), equalTo(0));
        assertThat(responseForComplexSearch.getNumber(), equalTo(10));
        assertThat(responseForComplexSearch.getTotalResults(), not(0));

    }

    @Test
    void getSearchFalafelBurgers() {

        ResponseForComplexSearch responseForComplexSearch = given()
                .queryParam("titleMatch", "Falafel Burgers")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .extract()
                .response()
                .body()
                .as(ResponseForComplexSearch.class);
        assertThat(responseForComplexSearch.getOffset(), equalTo(0));
        assertThat(responseForComplexSearch.getNumber(), equalTo(10));
        assertThat(responseForComplexSearch.getTotalResults(), equalTo(1));
        assertThat(responseForComplexSearch.getResults().get(0).getTitle(), equalTo("Falafel Burgers"));
        assertThat(responseForComplexSearch.getResults().get(0).getId(), equalTo(642540));

    }

    @Test
    void getSearchByCalories() {

        ResponseForComplexSearch responseForComplexSearch = given()
                .queryParam("minCalories", "200")
                .queryParam("maxCalories", "600")
                .queryParam("number", "15")
                .queryParam("offset", "2")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .extract()
                .response()
                .body()
                .as(ResponseForComplexSearch.class);
        assertThat(responseForComplexSearch.getOffset(), equalTo(2));
        assertThat(responseForComplexSearch.getNumber(), equalTo(15));
        assertThat(responseForComplexSearch.getTotalResults(), not(0));
        assertThat(responseForComplexSearch.getResults().get(0).getNutrition().getNutrients().get(0).getName(), equalTo("Calories"));
        assertThat(responseForComplexSearch.getResults().get(0).getNutrition().getNutrients().get(0).getAmount(), allOf(greaterThanOrEqualTo(200f), lessThanOrEqualTo(600f)));

    }

    @Test
    void getSearchByIngredientsAndThenByReceivedTitle() {

        ResponseForComplexSearch responseForComplexSearch = given()
                .queryParam("includeIngredients", "Egg,milk,cheese")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .extract()
                .response()
                .body()
                .as(ResponseForComplexSearch.class);
        assertThat(responseForComplexSearch.getOffset(), equalTo(0));
        assertThat(responseForComplexSearch.getNumber(), equalTo(10));
        assertThat(responseForComplexSearch.getTotalResults(), not(0));

        int MyTestID1 = responseForComplexSearch.getResults().get(0).getId();
        String MyTestTitle1 = responseForComplexSearch.getResults().get(0).getTitle();

        ResponseForComplexSearch responseForComplexSearch2 = given()
                .queryParam("titleMatch", MyTestTitle1)
                .queryParam("addRecipeInformation", true)
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .extract()
                .response()
                .body()
                .as(ResponseForComplexSearch.class);
        assertThat(responseForComplexSearch2.getOffset(), equalTo(0));
        assertThat(responseForComplexSearch2.getNumber(), equalTo(10));
        assertThat(responseForComplexSearch2.getTotalResults(), equalTo(1));
        assertThat(responseForComplexSearch2.getResults().get(0).getTitle(), equalTo(MyTestTitle1));
        assertThat(responseForComplexSearch2.getResults().get(0).getId(), equalTo(MyTestID1));
        assertThat(responseForComplexSearch2.getResults().get(0).getVegan(), equalTo(false));

    }

    @Test
    void getSearchWithSortByCalories() {

        ResponseForComplexSearch responseForComplexSearch = given()
                .queryParam("sort", "calories")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .extract()
                .response()
                .body()
                .as(ResponseForComplexSearch.class);
        assertThat(responseForComplexSearch.getOffset(), equalTo(0));
        assertThat(responseForComplexSearch.getNumber(), equalTo(10));
        assertThat(responseForComplexSearch.getResults().get(0).getNutrition().getNutrients().get(0).getName(), equalTo("Calories"));

        float amount1 = responseForComplexSearch.getResults().get(0).getNutrition().getNutrients().get(0).getAmount();
        float amount2 = responseForComplexSearch.getResults().get(1).getNutrition().getNutrients().get(0).getAmount();
        float amount3 = responseForComplexSearch.getResults().get(2).getNutrition().getNutrients().get(0).getAmount();
        assertThat(amount1, greaterThanOrEqualTo(amount2));
        assertThat(amount2, greaterThanOrEqualTo(amount3));

    }

}