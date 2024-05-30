package Pojo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Utilities {

    public static RequestSpecification Request() throws IOException {
        RequestSpecification res = new RequestSpecBuilder().setBaseUri(globalProperties("Base_URL")).
                setContentType(ContentType.JSON).build();
        return res;
    }


    public static String globalProperties(String key) throws IOException {

        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(".\\src\\test\\java\\Pojo\\Global.properties");
        prop.load(fis);
        return prop.getProperty(key);
    }

    public static String authBody(String clientName, String clientEmail) {
        return "{\n" +
                "   \"clientName\": \"" + clientName + "\",\n" +
                "   \"clientEmail\": \"" + clientEmail + "\"\n" +
                "}";
    }

    public static String createNewOrderBody(String toolId, String customerName) {
        return "{\n" +
                "    \"toolId\": " + toolId + ",\n" +
                "    \"customerName\": \"" + customerName + "\"\n" +
                "}";
    }
}
