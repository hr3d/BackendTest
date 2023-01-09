package lesson5;

import lesson5.dto.Product;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateModifyAndDeleteProductTest extends AbsractTestForProductTests {

    Product product = null;
    Product modifiedProduct = null;
    String titleMyProduct = "MyIphone";
    String categoryMyProduct = "Electronic";
    int priceMyProduct = 15000;
    int newPriceMyProduct = 10000;
    int id;

    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(titleMyProduct)
                .withCategoryTitle(categoryMyProduct)
                .withPrice(priceMyProduct);
    }

    @Test
    void createAndModifyProductTest() throws IOException {
        Response <Product> response = productController.createProduct(product)
                .execute();
        id = response.body().getId();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getTitle(), equalTo(titleMyProduct));
        assertThat(response.body().getCategoryTitle(), equalTo(categoryMyProduct));
        assertThat(response.body().getPrice(), equalTo(priceMyProduct));

        modifiedProduct = new Product()
                .withId(id)
                .withTitle(titleMyProduct)
                .withCategoryTitle(categoryMyProduct)
                .withPrice(newPriceMyProduct);

        Response <Product> response2 = productController.modifyProduct(modifiedProduct)
                .execute();
        assertThat(response2.isSuccessful(), CoreMatchers.is(true));
        assertThat(response2.body().getId(), equalTo(id));
        assertThat(response2.body().getTitle(), equalTo(titleMyProduct));
        assertThat(response2.body().getCategoryTitle(), equalTo(categoryMyProduct));
        assertThat(response2.body().getPrice(), equalTo(newPriceMyProduct));


    }

    @SneakyThrows
    @AfterEach
    void tearDown() {
        Response<ResponseBody> response = productController.deleteProduct(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

}
