package org.Ecommerce;
import Files.Payload;
import Files.ReusableClass;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.OrderDetails;
import pojo.Orders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class EcommerceAPI {


    public static void main(String args[]) {


        RequestSpecification req=new RequestSpecBuilder().
                                 setBaseUri("https://rahulshettyacademy.com").
                                 setContentType(ContentType.JSON).build();

        LoginRequest login=new LoginRequest();
        login.setUserEmail("jashishtbd@gmail.com");
        login.setUserPassword("Jashishtbd@12");

        RequestSpecification reqlogin=given().
                                        log().all().
                                        spec(req).
                                        body(login);

        //Login
        LoginResponse loginresobj=reqlogin.
                when().
                        post("/api/ecom/auth/login").
                then().
                        extract().response().as(LoginResponse.class);
        System.out.println(loginresobj.getToken());
        String token=loginresobj.getToken();
        String userID=loginresobj.getUserId();
        System.out.println(loginresobj.getUserId());


        //Add product

        RequestSpecification addproduct=new RequestSpecBuilder().
                setBaseUri("https://rahulshettyacademy.com").
                addHeader("Authorization",token).
                build();

        RequestSpecification reqaddProduct= given().
                log().all().spec(addproduct).
                param("productName","Shirts new Raymond").
                param("productAddedBy","6309977fc4d0c51f4f10bbdd").
                param("productCategory","fashion").
                param("productSubCategory","shirts").
                param("productPrice","11500").
                param("productDescription","Addias Originals").
                param("productFor","women").
                multiPart("productImage",new File("C:\\Users\\HP\\Desktop\\Notes\\Udemy\\api\\RahulShetty\\productImage_1650649488046.jpg"));

                //here not using pojo
       String  addProductresponse=
               reqaddProduct.
                       when().
                            post("api/ecom/product/add-product").
                    then().
                            log().all().
                            extract().response().asString();
        JsonPath js=new JsonPath(addProductresponse);
        String productID=js.get("productId");


        //Create order for product
        RequestSpecification createOrderBasereq=new RequestSpecBuilder().
                    setBaseUri("https://rahulshettyacademy.com").
                    addHeader("Authorization",token).
                    setContentType(ContentType.JSON).build();

        OrderDetails orderdetail=new OrderDetails();
        orderdetail.setCountry("India");
        orderdetail.setProductOrderedId(productID);

        List<OrderDetails> orderdetailist=new ArrayList<OrderDetails>();
        orderdetailist.add(orderdetail);

        Orders order=new Orders();
        order.setOrders(orderdetailist);

       RequestSpecification createorderreq= given().
                log().all().
                spec(createOrderBasereq).body(order);

        String responseaddorder=createorderreq.
                when().
                        post("api/ecom/order/create-order").
                then().
                        log().all().extract().response().asString();

        System.out.println(responseaddorder);


        //Delete

        RequestSpecification deletebasereq=new RequestSpecBuilder().
                setBaseUri("https://rahulshettyacademy.com").
                addHeader("Authorization",token).
                setContentType(ContentType.JSON).build();

        RequestSpecification deleteProdReq=given().
                                                relaxedHTTPSValidation().//to use if ssl certificate validation fails
                                                log().all().
                                                spec(deletebasereq).
                                                pathParam("productOrderId",productID);


            String deleteprodResponse=deleteProdReq.
                         when().
                                delete("api/ecom/product/delete-product/{productOrderId}").
                         then().
                                log().all().
                                extract().response().asString();
            JsonPath js1=new JsonPath(deleteprodResponse);
            Assert.assertEquals(js1.get("message"),"Product Deleted Successfully");




    }
}
