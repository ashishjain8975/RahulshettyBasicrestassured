package Files;

import io.restassured.RestAssured;
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

public class DynamicJson {



@Test(dataProvider ="BooksData")
    public void addBook(String ibsnp,String idp) throws IOException {

    RestAssured.baseURI="https://rahulshettyacademy.com";

    String response=
            given().
                   header("Content-Type","application/json").
                    queryParam("key","qaclick123").
            body(Payload.audiobook(ibsnp,idp)).
                    //To get Json from file instead of a method
                //  body(new String(Files.readAllBytes(Paths.get("C:\\Users\\HP\\IdeaProjects\\Restassuredproject1rahul\\src\\test\\java\\Files\\Bookdata.json")))).
            when().
                    post("Library/Addbook.php").
            then().
                    assertThat().statusCode(200).
                    extract().response().asString();

    System.out.println(response);
            JsonPath js=ReusableClass.rawToJson(response);
            String id=js.get("ID");
            System.out.println(id);


}



        @DataProvider(name="BooksData")
        public Object[][] getData()
        {
           return new Object[][] {{"adfkjhddsjgssa","8454"},
                   {"asdfgfdjhdfdkgdsda","8357984"},
                   {"asdiydiuyigdffda","825454"},
                   {"asddudiodffda","82iuy54"},
                   {"asdiyiufdsgdgyigdffda","825454"},};
        }
}
