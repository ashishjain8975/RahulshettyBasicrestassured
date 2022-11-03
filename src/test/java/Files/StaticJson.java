package Files;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class StaticJson {
    @Test

    public void addBook() throws IOException



    {



        RestAssured.baseURI="https://rahulshettyacademy.com";

        String resp=given().

                header("Content-Type","application/json").

                body(GenerateStringFromResource("C:\\Users\\HP\\IdeaProjects\\Restassuredproject1rahul\\src\\test\\java\\Files\\Bookdata.json")).
                log().all().
                when().

                post("/Library/Addbook.php").

                then().assertThat().statusCode(200).

                extract().response().asString();
        System.out.println(resp);

        JsonPath js= ReusableClass.rawToJson(resp);

        String id=js.get("id");

        System.out.println(id);



        //deleteBOok

    }

    public static String GenerateStringFromResource(String path) throws IOException {

       // import java.nio.file.Files;
       //import java.nio.file.Paths;
        return new String(Files.readAllBytes(Paths.get(path)));//imported io.nio.files

    }
}
