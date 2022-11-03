package Utils;

import io.restassured.RestAssured;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class JiraCreation {

    @BeforeMethod
    public void baseuriset()
    {
        RestAssured.baseURI="https://jashish.atlassian.net";

    }

    @Test
    public void jiralogin()
    {



        given().
                auth().preemptive().
                basic("jashishtbd@gmail.com","ggzXtgWEiDmhWuZdqZ4t3006").
                when().
                        get("rest/api/3/issue/EZ-1").
                then().
                        log().all().
                        assertThat().statusCode(200);

    }



    @Test
    public void createbuginjira()
    {


        RestAssured.baseURI="https://jashish.atlassian.net";
        given().
                auth().preemptive().
                basic("jashishtbd@gmail.com","ggzXtgWEiDmhWuZdqZ4t3006").
                header("Content-Type","application/json").
                body("{\n" +
                        "    \"fields\": {\n" +
                        "       \"project\":\n" +
                        "       {\n" +
                        "          \"key\": \"EZ\"\n" +
                        "       },\n" +
                        "       \"summary\": \"This is bug cretaed by Ashish from Intellij\",\n" +
                        "       \"issuetype\": {\n" +
                        "          \"name\": \"Bug\"\n" +
                        "       }\n" +
                        "   }\n" +
                        "}").
                when().
                        post("rest/api/3/issue/").
                then().
                    log().all().
                    assertThat().statusCode(201);

    }


}
