package lesson5;

import lesson5.dto.Product;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetProductsTest extends AbsractTestForProductTests{

    @SneakyThrows
    @Test
    void getProductsPositiveTest() {

        Response<Product[]> response = productController.getProducts().execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body()[0].getId(), equalTo(1));
        assertThat(response.body()[0].getTitle(), equalTo("Milk"));
        assertThat(response.body()[0].getCategoryTitle(), equalTo("Food"));

    }

}
