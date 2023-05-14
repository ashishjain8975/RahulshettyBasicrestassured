package org.example;

import Files.Payload;
import Files.ReusableClass;
import io.restassured.path.json.JsonPath;

import Files.Payload;
import Files.ReusableClass;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ComplexJson2 {

    @Test
    public void test1() {

        JsonPath js = ReusableClass.rawToJson(Payload.CoursePrice());

        int count = js.getInt("courses.size()");
        System.out.println("The count is " + count);

        int totalAmt = js.getInt("dashboard.purchaseAmount");
        System.out.println("total amt is " + totalAmt);

        String titleFirst = js.getString("courses[0].title");
        System.out.println("Titlw is " + titleFirst);

        //Print all courses
        String courseTitle;
        for (int i = 0; i < count; i++) {
            courseTitle = js.getString("courses[" + i + "].title");
            System.out.println(courseTitle);

            int coursePrice = js.getInt("courses[" + i + "].price");
            System.out.println(coursePrice);

        }

        System.out.println("Print no of copies sold by RPA");
        for (int i = 0; i < count; i++) {
            courseTitle = js.getString("courses[" + i + "].title");
            if (courseTitle.equalsIgnoreCase("RPA")) {
                int copies = js.getInt("courses[" + i + "].copies");
                System.out.println(copies);
                break;
            }

        }

        System.out.println("Validate sum of prices");
        count = js.getInt("courses.size()");
        System.out.println("The count is " + count);
        int sum = 0;
        for (int i = 0; i < count; i++) {
            int price = js.getInt("courses[" + i + "].price");
            int copies = js.getInt("courses[" + i + "].copies");
            int amount = price * copies;
            System.out.println("Teh amount is " + amount);
            sum = sum + amount;
        }

        System.out.println("The sum is " + sum);

        int purchaseAmt = js.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(sum, purchaseAmt);


    }
}
