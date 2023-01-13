package lesson6;

import lesson6.dto.Product;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetProductById extends AbsractTestForProductTests {


    db.dao.ProductsMapper productsMapper = session.getMapper(db.dao.ProductsMapper.class);
    db.model.ProductsExample example = new db.model.ProductsExample();

    @SneakyThrows
    @Test
    @Tag("positiveTest")
    void getProductByIdPositiveTest() {

        Response<Product> response = productController.getProductById(2).execute();

            assertThat(response.isSuccessful(), CoreMatchers.is(true));

            example.createCriteria().andIdEqualTo(2L);
            List<db.model.Products> list = productsMapper.selectByExample(example);
            assertThat(response.body().getId(), equalTo(list.get(0).getId()));
            assertThat(response.body().getTitle(), equalTo(list.get(0).getTitle()));
            assertThat(response.body().getCategoryTitle(), equalTo("Food"));

    }

    @SneakyThrows
    @Test
    @Tag("negativeTest")
    void getProductByInvalidId() {

        Response<Product> response = productController.getProductById(999).execute();

            assertThat(response.isSuccessful(), CoreMatchers.is(false));

            example.createCriteria().andIdEqualTo(999L);
            assertThat(productsMapper.selectByExample(example).size(), equalTo(0));

    }


}
