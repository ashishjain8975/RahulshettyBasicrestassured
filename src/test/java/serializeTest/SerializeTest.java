package serializeTest;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import pojo.AddPlace;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class SerializeTest {

    public static void main(String[] args)
    {

        baseURI="https://rahulshettyacademy.com";
        AddPlace p=new AddPlace();
        p.setAccuracy(50);
        p.setAddress("28,side layout,cohan 90");
        p.setLanguage("Malayalam");

        p.setPhone_number("5468464");
        p.setWebsite("https://rahulshettyacademy.com");
        p.setName("Rahul Shetty");

        List<String> mylist=new ArrayList<String>();
        mylist.add("shoe_park");
        mylist.add("shop");

        p.setTypes(mylist);

        Location l=new Location();
        l.setLat(56);
        l.setLat(78);

        p.setLocation(l);
       baseURI="https://rahulshettyacademy.com";


       Response res= given().
                queryParam("key","qaclick123").
                body(p).
                log().all().

                when().
                        post("/maps/api/place/add/json").
                then().
                        assertThat().
                        statusCode(200).extract().response();
String response=res.asString();
System.out.println(response);

    }


}
