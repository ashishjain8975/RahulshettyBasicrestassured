package org.example;

import Files.Payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

public class ComplexJsonPath {
    public static  void  main(String[] args) {

        JsonPath js = new JsonPath(Payload.CoursePrice());
        //Print no of courses

        int count = js.getInt("courses.size()");
        System.out.println(count);

        int purchaseamount=js.getInt("dashboard.purchaseAmount");
        System.out.println(purchaseamount);

        //Print title of first course

        String coursetitle=js.get("courses[0].title");
        System.out.println(coursetitle);

        //Print all course title and their respective price

        for(int i=0;i<count;i++)
        {
            System.out.println(js.get("courses["+i+"].title").toString());
            System.out.println(js.get("courses["+i+"].price").toString());
        }

        //Print all RPA course price
        System.out.println("Print copies sold for  RPA course  ");
        for(int i=0;i<count;i++)
        {
           String coursetitleinner=js.get("courses["+i+"].title");
           if (coursetitleinner.equalsIgnoreCase("RPA"))
           {
               //copies sold for RPA
               System.out.println(js.get("courses["+i+"].price").toString());
               break;
           }

        }

        //Sumvalidation
        System.out.println("Verify the Sumvalidation  ");
        int purchaseamountactual=0;
        for(int i=0;i<count;i++)
        {
           int price= js.get("courses["+i+"].copies");
           int copies= js.get("courses["+i+"].price");

            purchaseamountactual=purchaseamountactual+price*copies;

            }
        System.out.println(purchaseamountactual);
         Assert.assertEquals(purchaseamountactual,purchaseamount);

        }

}
