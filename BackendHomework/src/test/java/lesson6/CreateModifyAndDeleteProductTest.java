package lesson6;

import lesson6.dto.Product;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateModifyAndDeleteProductTest extends AbsractTestForProductTests {

    Product product = null;
    Product modifiedProduct = null;
    String titleMyProduct = "MyIphone";
    String categoryMyProduct = "Electronic";
    int priceMyProduct = 15000;
    int newPriceMyProduct = 10000;
    long id;
    db.dao.ProductsMapper productsMapper = session.getMapper(db.dao.ProductsMapper.class);
    db.model.ProductsExample example = new db.model.ProductsExample();

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

        assertThat(response.isSuccessful(), CoreMatchers.is(true));

        id = response.body().getId();

        example.createCriteria().andIdEqualTo(id);
        List<db.model.Products> list = productsMapper.selectByExample(example);

            assertThat(list.get(0).getId(), equalTo(id));
            assertThat(list.get(0).getTitle(), equalTo(titleMyProduct));
            assertThat(list.get(0).getCategory_id(), equalTo(2L));
            assertThat(list.get(0).getPrice(), equalTo(priceMyProduct));

        session.commit();

        modifiedProduct = new Product()
                .withId(id)
                .withTitle(titleMyProduct)
                .withCategoryTitle(categoryMyProduct)
                .withPrice(newPriceMyProduct);

        Response <Product> response2 = productController.modifyProduct(modifiedProduct)
                .execute();
            assertThat(response2.isSuccessful(), CoreMatchers.is(true));

        example.createCriteria().andIdEqualTo(id);
        List<db.model.Products> list2 = productsMapper.selectByExample(example);

            assertThat(list2.get(0).getId(), equalTo(id));
            assertThat(list2.get(0).getTitle(), equalTo(titleMyProduct));
            assertThat(list2.get(0).getCategory_id(), equalTo(2L));
            assertThat(list2.get(0).getPrice(), equalTo(newPriceMyProduct));

        session.commit();

    }

    @SneakyThrows
    @AfterEach
    void tearDown() {
        Response<ResponseBody> response = productController.deleteProduct(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));

        example.createCriteria().andIdEqualTo(id);
        assertThat(productsMapper.selectByExample(example).size(), equalTo(0));

    }

}
