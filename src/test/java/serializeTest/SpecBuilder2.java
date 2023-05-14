package serializeTest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class SpecBuilder2 {

    public static void main(String[] args)
    {

        baseURI="https://rahulshettyacademy.com";
        //Serialiazation
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
        l.setLng(78);


       RequestSpecification req= new RequestSpecBuilder().
                setBaseUri("https://rahulshettyacademy.com").
                addQueryParam("key","qaclick123").
                setContentType(ContentType.JSON).build();


        p.setLocation(l);
       baseURI="https://rahulshettyacademy.com";



       Response res= given().
                            log().all().
                   // queryParam("key","qaclick123").
                           spec(req).
                body(p).
                log().all().

                when().
                        post("/maps/api/place/add/json").
                then().
                        assertThat().
                        statusCode(200).extract().response();
String response=res.asString();
System.out.println(response);



        //If want to divide

    }


}
