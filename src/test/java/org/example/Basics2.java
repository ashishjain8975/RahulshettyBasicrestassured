package org.example;
import Files.Payload;
import Files.ReusableClass;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics2 {

public static void main(String[] args) {
   RestAssured.baseURI = "https://rahulshettyacademy.com";

   given().
           log().all().
           queryParam("key","qaclick123").
           header("Content-Type","application/json").
           body("{\n" +
                   "  \"location\": {\n" +
                   "    \"lat\": -38.383494,\n" +
                   "    \"lng\": 33.427362\n" +
                   "  },\n" +
                   "  \"accuracy\": 50,\n" +
                   "  \"name\": \"New Frontline2 house\",\n" +
                   "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
                   "  \"address\": \"29, side layout, fohhoo 09\",\n" +
                   "  \"types\": [\n" +
                   "    \"tshirt park\",\n" +
                   "    \"shop\"\n" +
                   "  ],\n" +
                   "  \"website\": \"http://google.com\",\n" +
                   "  \"language\": \"French-IN\"\n" +
                   "}\n").
           when().
                  post("maps/api/place/add/json").
           then().
                 log().all().
                  assertThat().statusCode(200).
                  body("scope",equalTo("APP")).
                  header("server","Apache/2.4.41 (Ubuntu)");
}

@Test
public void test2()
{
   RestAssured.baseURI = "https://rahulshettyacademy.com";

   String response=
   given().
           log().all().
           queryParam("key","qaclick123").
           header("Content-Type","application/json").
           body(Payload.AddPlace()).
   when().
           post("maps/api/place/add/json").

   then().
           log().all().
           assertThat().statusCode(200).
           body("scope",equalTo("APP")).
           header("server","Apache/2.4.41 (Ubuntu)").
           extract().response().asString();

   System.out.println(response);

   JsonPath  js=new JsonPath(response);
   String placeId=js.getString("place_id");

   System.out.println("The place id is "+placeId);

   //Update place
String updateAddress="73350 Villghfhfghfghage walk, MX";
   given().
           log().all().
           queryParam("key","qaclick123").
           header("Content-Type","application/json").
            body("{\n" +
                    "\"place_id\":\""+placeId+"\",\n" +
                    "\"address\":\"73350 Villghfhfghfghage walk, MX\",\n" +
                    "\"key\":\"qaclick123\"\n" +
                    "}\n").
   when().
           put("maps/api/place/update/json").
   then().
           log().all().
           assertThat().
           statusCode(200).
           body("msg",equalTo("Address successfully updated"));


   //Get place

    String getResponse=
            given().
                    log().all().
                    queryParam("key","qaclick123").
                    queryParam("place_id",placeId).
           header("Content-Type","application/json").
   when().
           get("maps/api/place/get/json").
   then().
           log().all().
           assertThat().statusCode(200).
            extract().response().asString();

  JsonPath js2=new JsonPath(getResponse);
 // JsonPath js2 = ReusableClass.rawToJson(getResponse);
  System.out.println(js2);

  String addressActual =js2.getString("address");

   Assert.assertEquals(addressActual,updateAddress);






}

}
