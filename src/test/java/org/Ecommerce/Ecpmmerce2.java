package org.Ecommerce;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class Ecpmmerce2 {


    @Test
    public void test1()
    {

        RequestSpecification request= new RequestSpecBuilder().
                    setBaseUri("https://rahulshettyacademy.com").
                    setContentType(ContentType.JSON).build();

        LoginRequest2 loginrequest=new LoginRequest2();
        loginrequest.setUserEmail("jashishtbd@gmail.com");
        loginrequest.setUserPassword("Jashishtbd@12");


        RequestSpecification loginrequestspec=
                                                given().
                                                            relaxedHTTPSValidation().
                                                            spec(request).
                                                            body(loginrequest);
        LoginResponse2 loginresponse2Obj=

        loginrequestspec.
                         when().
                            post("api/ecom/auth/login").
                        then().
                                extract().response().as(LoginResponse2.class);

        String token=loginresponse2Obj.getToken();
        System.out.println(loginresponse2Obj.getToken());
        String userID=loginresponse2Obj.getUserId();
        System.out.println(loginresponse2Obj.getUserId());

        //Add product
        RequestSpecification addProductBaserequest= new RequestSpecBuilder().
                setBaseUri("https://rahulshettyacademy.com").
                addHeader("Authorization",token).
                build();

        RequestSpecification addproductrequest=

        given()
                .log().all().
                spec(addProductBaserequest).
                param("productName","qwerty").   //Use when we have forma paramerters
                param("productAddedBy",userID).
                param("productCategory","fashion").
                param("productSubCategory","shirts").
                param("productPrice","11500").
                param("productDescription","Addias Originals").
                param("productFor","women").
                multiPart("productImage",new File("C:\\Users\\HP\\IdeaProjects\\Restassuredproject1rahul\\src\\test\\java\\org\\Ecommerce\\Pic.png"));

        String addProductResponse=
        addproductrequest.
                when().
                    post("api/ecom/product/add-product").
                then().
                        log().all().
                        extract().response().asString();

        JsonPath jsonpth=new JsonPath(addProductResponse);
        String productID= jsonpth.get("productId");



        //Create order
        RequestSpecification addcratre= new RequestSpecBuilder().
                setBaseUri("https://rahulshettyacademy.com").
                addHeader("Authorization",token).
                setContentType(ContentType.JSON).
                build();

        OrderDetails2 orderdetail2=new OrderDetails2();
        orderdetail2.setCountry("Sri Lanka");
        orderdetail2.setProductOrderedId(productID);

        List<OrderDetails2> orderdetailslist=new ArrayList<>();
        orderdetailslist.add(orderdetail2);




        Orders2 orders2=new Orders2();
        orders2.setOrders(orderdetailslist);

        RequestSpecification orderrequests=
        given().
                log().all().
                spec(addcratre).
                body(orders2);

        String response=orderrequests.
                                    when().
                                            post("api/ecom/order/create-order").
                                    then().
                                            log().all().
                                            extract().response().asString();

        System.out.println(response);

    //Delete Product
        RequestSpecification deleterequest= new RequestSpecBuilder().
                setBaseUri("https://rahulshettyacademy.com").
                addHeader("Authorization",token).
                build();

        RequestSpecification deleteProdRequest=
        given().
                log().all().
                spec(deleterequest).
                pathParam("productID",productID);


        String deterequestresponse=
        deleteProdRequest.
                        when().
                        delete("api/ecom/product/delete-product/{productID}").
                    then().
                            log().all().
                            extract().response().asString();


        JsonPath js1=new JsonPath(deterequestresponse);
        Assert.assertEquals("Product Deleted Successfully",js1.get("message"));



    }




















}
