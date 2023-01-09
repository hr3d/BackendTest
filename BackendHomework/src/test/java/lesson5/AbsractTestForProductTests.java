package lesson5;

import lesson5.api.ProductController;
import lesson5.utils.RetrofitUtils;
import org.junit.jupiter.api.BeforeAll;

public class AbsractTestForProductTests {

    static ProductController productController;

    @BeforeAll
    static void beforeAll() {
        productController = RetrofitUtils.getRetrofit()
                .create(ProductController.class);
    }

}
