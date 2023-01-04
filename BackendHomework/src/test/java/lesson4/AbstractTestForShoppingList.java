package lesson4;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AbstractTestForShoppingList {

    static Properties prop = new Properties();
    private static InputStream configFile;
    private static String apiKey;
    private static String hash;
    private static String username;
    private static String baseUrl;

    protected static RequestSpecification requestSpecification;
    protected static ResponseSpecification responseSpecification;

    @BeforeAll
    static void initTest() throws IOException {

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        configFile = new FileInputStream("src/main/resources/my.properties");
        prop.load(configFile);

        apiKey = prop.getProperty("apiKey");
        hash = "263f83a4a150e1ab24a7434f6a30c8d1d64f80e6";
        username = "testmailforgb";
        baseUrl = prop.getProperty("base_url");

    }

    @BeforeEach
    void beforeTest() {

        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", apiKey)
                .addQueryParam("hash", hash)
                .addPathParam("username", username)
                .log(LogDetail.ALL)
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectResponseTime(Matchers.lessThan(5000L))
                .build();

        RestAssured.requestSpecification = requestSpecification;
        RestAssured.responseSpecification = responseSpecification;

    }

    public static String getBaseUrl() {
        return baseUrl;
    }

}
