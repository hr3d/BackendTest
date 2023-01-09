package lesson5;

import lesson5.dto.Product;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetProductById extends AbsractTestForProductTests {

    @SneakyThrows
    @Test
    @Tag("positiveTest")
    void getProductByIdPositiveTest() {

        Response<Product> response = productController.getProductById(2).execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getId(), equalTo(2));
        assertThat(response.body().getTitle(), equalTo("Bread"));
        assertThat(response.body().getCategoryTitle(), equalTo("Food"));

    }

    @SneakyThrows
    @Test
    @Tag("negativeTest")
    void getProductByInvalidId() {

        Response<Product> response = productController.getProductById(10).execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(false));

    }


}
