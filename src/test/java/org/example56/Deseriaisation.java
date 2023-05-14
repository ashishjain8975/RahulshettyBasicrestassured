package org.example56;

import io.restassured.RestAssured;
import org.testng.annotations.Test;
import pojo2.AddPlace;
import pojo2.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Deseriaisation {

@Test
public void test1()
{
    RestAssured.baseURI="https://rahulshettyacademy.com";

    List<String> myList=new ArrayList<>();
    myList.add("shoe park");
    myList.add("shopp");

    Location objloc=new Location();
    objloc.setLat(34.56);
    objloc.setLng(57.63);


    AddPlace p=new AddPlace();
    p.setAccuracy(50);
    p.setAddress("Thie is  adddress");
    p.setLanguage("ENglisg");
    p.setPhone_number("546454646546");
    p.setWebsites("https://www.google.com");
    p.setName("Ashish");
    p.setTypes(myList);

    p.setLocation(objloc);

    String repsonse=
            given().
                    log().all().
                    queryParam("key","qaclick123").
                    body(p).
     when().
                    post("/maps/api/place/add/json").
      then().
                    statusCode(200).
                    log().all().
                    extract().response().asString();
    System.out.println("Response is as "+repsonse);

}

}
