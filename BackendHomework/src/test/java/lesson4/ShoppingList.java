package lesson4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lesson4.requestAddItem.RequestAddItem;
import lesson4.responseAddItem.ResponseAddItem;
import lesson4.responseDeleteItem.ResponseDeleteItem;
import lesson4.responseShoppingList.ResponseForShoppingList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class ShoppingList extends AbstractTestForShoppingList{

    //private static String password = "lateharvestzinfandelwith45eggrollwrappers";
    private static int currentItemID;

    @Test
    void generateShoppingListThenAddAndDeleteItem() throws JsonProcessingException {
        ResponseForShoppingList generateShoppingList = given()
                .pathParam("start-date", "2022-12-25")
                .pathParam("end-date", "2022-12-31")
                .when()
                .post(getBaseUrl() + "mealplanner/{username}/shopping-list/{start-date}/{end-date}")
                .then()
                .extract()
                .response()
                .body()
                .as(ResponseForShoppingList.class);
        assertThat(generateShoppingList.getCost(), equalTo(0.0f));

        RequestAddItem requestAddItemMilk = new RequestAddItem();
        requestAddItemMilk.setItem("1 bottle of milk");
        requestAddItemMilk.setAisle("Dairy products");
        requestAddItemMilk.setParse(true);

        ObjectMapper mapper = new ObjectMapper();

        ResponseAddItem addItem = given()
                .body(mapper.writeValueAsString(requestAddItemMilk)).when()
                .post(getBaseUrl() + "mealplanner/{username}/shopping-list/items")
                .then()
                .extract()
                .response()
                .body()
                .as(ResponseAddItem.class);
        currentItemID = addItem.getId();
        assertThat(addItem.getName(), equalTo("milk"));
        assertThat(addItem.getAisle(), equalTo("Dairy products"));
        assertThat(addItem.getCost(), greaterThanOrEqualTo(0.01f));

        ResponseForShoppingList getShoppingList = given()
                .when()
                .get(getBaseUrl() + "mealplanner/{username}/shopping-list")
                .then()
                .extract()
                .response()
                .body()
                .as(ResponseForShoppingList.class);
        assertThat(getShoppingList.getAisles().get(0).getAisle(), equalTo("Dairy products"));
        assertThat(getShoppingList.getAisles().get(0).getItems().get(0).getId(), equalTo(currentItemID));
        assertThat(getShoppingList.getAisles().get(0).getItems().get(0).getName(), greaterThanOrEqualTo("milk"));
        assertThat(getShoppingList.getCost(), greaterThanOrEqualTo(0.01f));

    }

    @AfterEach
    void tearDown() {
        ResponseDeleteItem deleteItem = given()
                .pathParam("id", currentItemID)
                .when()
                .delete(getBaseUrl() + "mealplanner/{username}/shopping-list/items/{id}")
                .then()
                .extract()
                .response()
                .body()
                .as(ResponseDeleteItem.class);
        assertThat(deleteItem.getStatus(), equalTo("success"));

    }

}

//        {
//        "status": "success",
//        "username": "testmailforgb",
//        "spoonacularPassword": "lateharvestzinfandelwith45eggrollwrappers",
//        "hash": "263f83a4a150e1ab24a7434f6a30c8d1d64f80e6"
//        }