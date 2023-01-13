package lesson6;

import lesson6.api.CategoryController;
import lesson6.dto.CategoryResponse;
import lesson6.dto.Product;
import lesson6.utils.RetrofitUtils;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

public class CategoryTest {

    static CategoryController categoryController;
    static SqlSession session = null;

    @BeforeAll
    static void beforeAll() throws IOException {

        categoryController = RetrofitUtils.getRetrofit().create(CategoryController.class);

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new
                SqlSessionFactoryBuilder().build(inputStream);
        session = sqlSessionFactory.openSession();

    }

    @SneakyThrows
    @Test
    void getCategoryByIdPositiveTest() {

        Response<CategoryResponse> response = categoryController.getCategory(1).execute();

            assertThat(response.isSuccessful(), CoreMatchers.is(true));

        db.dao.CategoriesMapper categoriesMapper = session.getMapper(db.dao.CategoriesMapper.class);
        db.model.CategoriesExample example = new db.model.CategoriesExample();

            example.createCriteria().andIdEqualTo(1L);
            List<db.model.Categories> list = categoriesMapper.selectByExample(example);
            assertThat(response.body().getId().longValue(), equalTo(list.get(0).getId()));
            assertThat(response.body().getTitle(), equalTo(list.get(0).getTitle()));

        db.dao.ProductsMapper productsMapper = session.getMapper(db.dao.ProductsMapper.class);
        db.model.ProductsExample example2 = new db.model.ProductsExample();

        List <Product> listFromResponse = response.body().getProducts();
        example2.createCriteria().andCategory_idEqualTo(1L);
        List <db.model.Products> listFromDB = productsMapper.selectByExample(example2);

        assertThat(listFromResponse.size(), equalTo(listFromDB.size()));

        for (int i = 0; i < listFromResponse.size(); i++){
            assertThat(listFromResponse.get(i).getId(), equalTo(listFromDB.get(i).getId()));
            assertThat(listFromResponse.get(i).getTitle(), equalTo(listFromDB.get(i).getTitle()));
            assertThat(listFromResponse.get(i).getPrice(), equalTo(listFromDB.get(i).getPrice()));
        }

    }

    @AfterAll
    static void afterAll(){
        session.close();
    }

}

//    public static void main( String[] args ) throws IOException {
//        SqlSession session = null;
//        try {
//            String resource = "mybatis-config.xml";
//            InputStream inputStream = Resources.getResourceAsStream(resource);
//            SqlSessionFactory sqlSessionFactory = new
//                    SqlSessionFactoryBuilder().build(inputStream);
//            session = sqlSessionFactory.openSession();
//            db.dao.CategoriesMapper categoriesMapper = session.getMapper(db.dao.CategoriesMapper.class);
//            db.model.CategoriesExample example = new db.model.CategoriesExample();
//
//            example.createCriteria().andIdEqualTo(1);
//            assertThat(response.body().getId(), equalTo(1));
//            System.out.println(categoriesMapper.countByExample(example));
//
//            db.model.Categories categories = new db.model.Categories();
//            categories.setTitle("test");
//            categoriesMapper.insert(categories);
//            session.commit();
//
//            db.model.CategoriesExample example2 = new db.model.CategoriesExample();
//            example2.createCriteria().andTitleLike("%test%");
//            List<db.model.Categories> list2 = categoriesMapper.selectByExample(example2);
//            db.model.Categories categories2 = list2.get(0);
//            categories2.setTitle("test100");
//            categoriesMapper.updateByPrimaryKey(categories2);
//            session.commit();
//
//            categoriesMapper.deleteByPrimaryKey(categories2.getId());
//            session.commit();
//
//        } finally {
//            session.close();
//        }
//
//
//    }