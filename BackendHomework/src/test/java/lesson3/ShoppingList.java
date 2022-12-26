package lesson3;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class ShoppingList extends AbstractTest{

    private static String username = "testmailforgb";
    private static String hash = "263f83a4a150e1ab24a7434f6a30c8d1d64f80e6";
    //private static String password = "lateharvestzinfandelwith45eggrollwrappers";
    private static int currentItemID;

    @Test
    void generateShoppingListThenAddAndDeleteItem() {
        JsonPath generateShoppingList = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("hash", hash)
                .pathParam("username", username)
                .pathParam("start-date", "2022-12-25")
                .pathParam("end-date", "2022-12-31")
                .when()
                .post(getBaseUrl() + "mealplanner/{username}/shopping-list/{start-date}/{end-date}")
                .body()
                .jsonPath();
        assertThat(generateShoppingList.get("cost"), equalTo(0.0f));

        JsonPath addItem = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("hash", hash)
                .pathParam("username", username)
                .body("{\n"
                        + " \"item\": \"1 bottle of milk\",\n"
                        + " \"aisle\": \"Dairy products\",\n"
                        + " \"parse\": true,\n"
                        + "}")
                .when()
                .post(getBaseUrl() + "mealplanner/{username}/shopping-list/items")
                .body()
                .jsonPath();
        currentItemID = addItem.get("id");
        assertThat(addItem.get("name"), equalTo("milk"));
        assertThat(addItem.get("aisle"), equalTo("Dairy products"));
        assertThat(addItem.get("cost"), greaterThanOrEqualTo(0.01f));

        JsonPath getShoppingList = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("hash", hash)
                .pathParam("username", username)
                .when()
                .get(getBaseUrl() + "mealplanner/{username}/shopping-list")
                .body()
                .jsonPath();
        assertThat(getShoppingList.get("aisles[0].aisle"), equalTo("Dairy products"));
        assertThat(getShoppingList.get("aisles[0].items[0].id"), equalTo(currentItemID));
        assertThat(getShoppingList.get("aisles[0].items[0].name"), greaterThanOrEqualTo("milk"));
        assertThat(getShoppingList.get("cost"), greaterThanOrEqualTo(0.01f));

    }

    @AfterEach
    void tearDown() {
        JsonPath deleteItem = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("hash", hash)
                .pathParam("username", username)
                .pathParam("id", currentItemID)
                .when()
                .delete(getBaseUrl() + "mealplanner/{username}/shopping-list/items/{id}")
                .body()
                .jsonPath();
        assertThat(deleteItem.get("status"), equalTo("success"));

    }

}

//        {
//        "status": "success",
//        "username": "testmailforgb",
//        "spoonacularPassword": "lateharvestzinfandelwith45eggrollwrappers",
//        "hash": "263f83a4a150e1ab24a7434f6a30c8d1d64f80e6"
//        }