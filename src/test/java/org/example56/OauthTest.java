package org.example56;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import pojo.Api;
import pojo.Course;
import pojo.GetCourses;
import pojo.WebAutomation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;

public class OauthTest {

    public static void main(String[] args) {


        //Perform below steps manually
     /* //  WebDriverManager.chromedriver().setup();
        WebDriver driver=new ChromeDriver();
        driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
        driver.findElement(By.xpath("//input[@type='email']")).sendKeys("jashishtbd@gmail.com");
        driver.findElement(By.xpath("//*[text()='Next']")).click();

       String url=driver.getCurrentUrl();*/
        //As google has restricted gmail access through automation,add this url as below manually by following above steps manually
        String url="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AfgeXvs4LzHbrQt0Ro1Ebe60o_TiYRRdBpiKNJstqBihl497j8PPbJnOseFwSBPg1uGo0Q&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
       String partialcode=url.split("code=")[1];
       String code=partialcode.split("&scope")[0];


       //To get access_token
        String accesstokenresponse=
        given().
                urlEncodingEnabled(false).
                queryParams("code",code).
                queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com").
                queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W").
                queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php").
                queryParams("grant_type","authorization_code").

                when().
                        log().all().
                        post("https://www.googleapis.com/oauth2/v4/token").
                asString();

        JsonPath js=new JsonPath(accesstokenresponse);
        String accesstoken=js.getString("access_token");









        String response2 =
                given().
                         queryParam("access_token", accesstoken).
                when().
                         get("https://rahulshettyacademy.com/getCourse.php").
                         asString();

        System.out.println(response2);

        //from pojo classes
        //we get object oj Java classes
        GetCourses gc=given().
                                queryParam("access_token",accesstoken).
                                expect().defaultParser(Parser.JSON).     //as we are expecting json response and not java object.If response header is application type Json this line can be avoided
                    when().
                            get("https://rahulshettyacademy.com/getCourse.php").
                            as(GetCourses.class);//Earlier we used asString.For pojo use as class

        System.out.println(gc.getLinkedIn());
        System.out.println(gc.getInstructor());

        System.out.println( gc.getCourses().getApi().get(1).getCourseTitle());

        //without index
        List<Api> apicourse=gc.getCourses().getApi();
        for (int i=0;i<apicourse.size();i++)
        {
            if (apicourse.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
            {
                System.out.println(apicourse.get(i).getPrice());
            }
        }
        String[] coursetitle={"Selenium Webdriver Java","Cypress","Protractor"};
        List<WebAutomation> coursesfrom=gc.getCourses().getWebAutomation();
        ArrayList<String> actuallist=new ArrayList<String>();


        for (int i=0;i<coursesfrom.size();i++)
        {
                System.out.println(coursesfrom.get(i).getCourseTitle());
                actuallist.add(coursesfrom.get(i).getCourseTitle());

        }
        List<String> expected=Arrays.asList(coursetitle);
        Assert.assertEquals(actuallist,expected);


    }









}
