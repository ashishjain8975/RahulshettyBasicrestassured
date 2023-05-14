package serializeTest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import pojo2.AddPlace;
import pojo2.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SpecBuilder3 {



    @Test
    public void test1()
    {
    //    RestAssured.baseURI="https://rahulshettyacademy.com";

        List<String> myList=new ArrayList<>();
        myList.add("shoe park");
        myList.add("shopp");

        pojo2.Location objloc=new Location();
        objloc.setLat(34.56);
        objloc.setLng(57.63);



        pojo2.AddPlace p=new AddPlace();
        p.setAccuracy(50);
        p.setAddress("Thie is  adddress");
        p.setLanguage("ENglisg");
        p.setPhone_number("546454646546");
        p.setWebsites("https://www.google.com");
        p.setName("Ashish");
        p.setTypes(myList);

        p.setLocation(objloc);

        RequestSpecification req=
                new RequestSpecBuilder().
                        setBaseUri("https://rahulshettyacademy.com").
                        addQueryParam("qaclick123").
                        setContentType(ContentType.JSON).build();





        RequestSpecification reqestehen=
                given().
                        spec(req).
                        body(p);


        ResponseSpecification responseSpecific =new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).build();



        Response response=
                reqestehen.
        when().
                        post("/maps/api/place/add/json").
         then().
                        spec(responseSpecific).
                        extract().response();
        System.out.println("Response is as "+response.asString());


    }

}
