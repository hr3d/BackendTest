package lesson5;

import lesson5.api.CategoryController;
import lesson5.dto.CategoryResponse;
import lesson5.utils.RetrofitUtils;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CategoryTest {

    static CategoryController categoryController;
    @BeforeAll
    static void beforeAll() {
        categoryController = RetrofitUtils.getRetrofit().create(CategoryController.class);
    }

    @SneakyThrows
    @Test
    void getCategoryByIdPositiveTest() {

        Response<CategoryResponse> response = categoryController.getCategory(1).execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getId(), equalTo(1));
        assertThat(response.body().getTitle(), equalTo("Food"));
        response.body().getProducts().forEach(product ->
                assertThat(product.getCategoryTitle(), equalTo("Food")));

    }

}