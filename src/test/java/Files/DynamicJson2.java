package Files;
import io.restassured.RestAssured;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class DynamicJson2 {

    @Test
    public void test1() throws IOException {
        baseURI = "http://216.10.245.166";

        String response =
                given()
                        .header("Content-Type", "application/json")
                      //  .body(Payload.audiobook("sdfjsd", "asilenew")).
                        .body(new String(Files.readAllBytes(Paths.get("C:\\Users\\HP\\IdeaProjects\\Restassuredproject1rahul\\src\\test\\java\\Files\\Bookdata.json")))).

                        when().
                        post("/Library/Addbook.php").

                        then().
                        log().all().
                        assertThat().statusCode(200).
                        extract().response().asString();


        JsonPath js = ReusableClass.rawToJson(response);

        String id = js.get("ID");
        System.out.println("The id is " + id);


    }


    @Test(dataProvider = "BooksData")
    public void test2(String isbn,String isel) {
        baseURI = "http://216.10.245.166";

        String response =
                given()
                        .header("Content-Type", "application/json")
                        .body(Payload.audiobook(isbn, isel)).
                when().

                        post("/Library/Addbook.php").

                then() .
                        log().all().
                        assertThat().statusCode(200).
                        extract().response().asString();


        JsonPath js = ReusableClass.rawToJson(response);

        String id = js.get("ID");
        System.out.println("The id is " + id);


    }

    @DataProvider(name="BooksData")
    public Object[][] getdata()
    {
       return new Object[][] {
                {"sfssd43","45sdfs345"},
                {"ss4w353t","sdf4tsdfsd"},
                {"sdf23532","ss5353444"}
       };

    }

}
