package org.example;

import Files.Payload;
import Files.ReusableClass;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;//for given method
import static org.hamcrest.Matchers.*;//for equal to method

public class Basics {

    public static void main(String[] args) {
        //given All input details
        //when submit the details
        //then validate the response
        RestAssured.baseURI="https://rahulshettyacademy.com";

        //Create new Place using POST
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

        System.out.println("The respomse is as "+response);

        JsonPath js=new JsonPath(response);//for parsing Jaon convert string to Json
        String placeid=js.getString("place_id");
        System.out.println("The palce id is "+placeid);


        //Update Place using PUT
        String newaddress="70 Nallasopara2 walk";
        given().
                log().all().
                queryParam("key","qaclick123").
                header("Content-Type","application/json").
                body("{\n" +
                        "\"place_id\":\""+placeid+"\",\n" +
                        "\"address\":\""+newaddress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n").
                when().
                        put("maps/api/place/update/json").
                then().
                        log().all().
                        assertThat().
                        statusCode(200).
                        body("msg",equalTo("Address successfully updated"));



        //Get Place
        String getplaceholderresponse=
                given().
                    log().all().
                    queryParam("key","qaclick123").
                    queryParam("place_id",placeid).
                when().
                        get("maps/api/place/get/json").
                then().
                    assertThat().
                        log().all().
                        statusCode(200)
                        .extract().response().asString();

        JsonPath json1= ReusableClass.rawToJson(getplaceholderresponse);
        String actualAddress=json1.getString("address");

        System.out.println("Actual adrress is "+actualAddress);
        Assert.assertEquals(actualAddress,newaddress);

    }

}
