package org.example56;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.WebAutomation;
import pojo2.API2;
import pojo2.GetCourses2;
import pojo2.WebAutomation2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class Oauth2 {

    @Test
    public void test1()
    {

        String[] courseTitleExpected={"Selenium Webdriver Java","Cypress","Protractor"};

        //https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php
         String url="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AbUR2VNtD1TSU5nzkOcMGin5gqxXpBuJ7p80hgaGq9PfjFkwqUr4AoboBkgIPqgjEVYd4g&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
                 String partialurl=url.split("code=")[1];
         String actualcode= partialurl.split("&scope")[0];

String accessTokenResponse=
        given().
                urlEncodingEnabled(false).
                log().all().
                queryParam("code",actualcode).
                queryParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com").
                queryParam("client_secret","erZOWM9g3UtwNRj340YYaK_W").
                queryParam("redirect_uri","https://rahulshettyacademy.com/getCourse.php").
                queryParam("grant_type","authorization_code").
                when().log().all().
                post("https://www.googleapis.com/oauth2/v4/token").asString();

        JsonPath js=new JsonPath(accessTokenResponse);
       String accesstokebn= js.getString("access_token");

        String response=given().
                queryParam("access_token",accesstokebn).
                when().
                get("https://rahulshettyacademy.com/getCourse.php").asString();

        System.out.println("Response is as "+response);




        GetCourses2 classresponse2=
                given().
                        queryParam("access_token",accesstokebn).
                        expect().defaultParser(Parser.JSON).//if response header is application.json this can be avoided
                when().
                        get("https://rahulshettyacademy.com/getCourse.php").
                        as(GetCourses2.class);

        System.out.println("The linked in is "+classresponse2.getLinkedIn());


        System.out.println("Response istrucutor as "+classresponse2.getInstructor());
        System.out.println("Course title is "+classresponse2.getCourses().getAPI().get(1).getCourseTitle());

        List<API2> apicourses =classresponse2.getCourses().getAPI();

        for (int i=0;i<apicourses.size();i++)
        {
            if(apicourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
            {
              System.out.println("Price is "+apicourses.get(i).getPrice());

            }

        }
        ArrayList<String> arl=new ArrayList<String>();

           List<WebAutomation2> wc= classresponse2.getCourses().getWebAutomation();
            for(int i=0;i<wc.size();i++)
            {
                System.out.println(wc.get(i).getCourseTitle());
                arl.add(wc.get(i).getCourseTitle());
            }

            List<String> arrayListexpected= Arrays.asList(courseTitleExpected);
            Assert.assertTrue(arl.equals(arrayListexpected));


    }
















}
