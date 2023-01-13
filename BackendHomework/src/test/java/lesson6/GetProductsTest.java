package lesson6;

import lesson6.dto.Product;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetProductsTest extends AbsractTestForProductTests {

    @SneakyThrows
    @Test
    void getProductsPositiveTest() {

        Response<Product[]> response = productController.getProducts().execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(true));

        db.dao.ProductsMapper productsMapper = session.getMapper(db.dao.ProductsMapper.class);
        db.model.ProductsExample example = new db.model.ProductsExample();

        List<db.model.Products> list = productsMapper.selectByExample(example);

        for (int i = 0; i < response.body().length; i++) {
            assertThat(response.body()[i].getId(), equalTo(list.get(i).getId()));
            assertThat(response.body()[i].getTitle(), equalTo(list.get(i).getTitle()));
            assertThat(response.body()[i].getPrice(), equalTo(list.get(i).getPrice()));
        }

    }

}
