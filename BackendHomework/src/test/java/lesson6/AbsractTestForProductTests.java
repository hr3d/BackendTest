package lesson6;

import lesson6.api.ProductController;
import lesson6.utils.RetrofitUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.io.InputStream;

public class AbsractTestForProductTests {

    static ProductController productController;
    static SqlSession session = null;

    @BeforeAll
    static void beforeAll() throws IOException {
        productController = RetrofitUtils.getRetrofit()
                .create(ProductController.class);

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new
                SqlSessionFactoryBuilder().build(inputStream);
        session = sqlSessionFactory.openSession();

    }

    @AfterAll
    static void afterAll(){
        session.close();
    }

}
