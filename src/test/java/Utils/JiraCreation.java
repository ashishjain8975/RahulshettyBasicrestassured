package Utils;

import io.restassured.RestAssured;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class JiraCreation {

    @BeforeMethod
    public void baseuriset()
    {
        RestAssured.baseURI="https://jashish.atlassian.net";

    }

    @Test
    public void jiralogin()
    {
        given().
               // header("Authorization","Basic ATATT3xFfGF06OqeKRT_wigj3op4WYNl47VpR_8giISfz8rjMLpiBwnU_mUNSLtxwPPowv8fWnkSBvALlFIxKo67PFS4HZLmnidPupGWIX9-aJiMffHSNEzVTrDibPypfvPnfcQDGXOPB9yNOe0QIz449_f7AyDbc9NacNt0bZPu4OhdWy34I2A=FE7AC279").
                auth().preemptive().
                basic("jashishtbd@gmail.com","ATATT3xFfGF07G_yvXhpAL4s29akuCt6IBVk0ja--F8K87Y2lH91jvVMTPN4TaUaqb1BoNGaWk8cXI-k3LU9vOLu_59SXOG1d37BEX8e6ZajjcK0VKnskuaIccdEt1hkacICZQka8gVQqEOhC_pxvKJ20BY2DTv-vxMFJrldNS1MIPI3PAJn8f4=D61AF853").
                when().
                        get("rest/api/3/issue/EZ-9").
                then().
                        log().all().
                        assertThat().statusCode(200);

    }



    @Test
    public void createbuginjira()
    {


        RestAssured.baseURI="https://jashish.atlassian.net";
        given().
               auth().preemptive().
                basic("jashishtbd@gmail.com","ATATT3xFfGF07G_yvXhpAL4s29akuCt6IBVk0ja--F8K87Y2lH91jvVMTPN4TaUaqb1BoNGaWk8cXI-k3LU9vOLu_59SXOG1d37BEX8e6ZajjcK0VKnskuaIccdEt1hkacICZQka8gVQqEOhC_pxvKJ20BY2DTv-vxMFJrldNS1MIPI3PAJn8f4=D61AF853").

              //  header("Authorization","Basic ATATT3xFfGF06OqeKRT_wigj3op4WYNl47VpR_8giISfz8rjMLpiBwnU_mUNSLtxwPPowv8fWnkSBvALlFIxKo67PFS4HZLmnidPupGWIX9-aJiMffHSNEzVTrDibPypfvPnfcQDGXOPB9yNOe0QIz449_f7AyDbc9NacNt0bZPu4OhdWy34I2A=FE7AC279").

                header("Content-Type","application/json").
                body("{\n" +
                        "    \"fields\": {\n" +
                        "       \"project\":\n" +
                        "       {\n" +
                        "          \"key\": \"EZ\"\n" +
                        "       },\n" +
                        "       \"summary\": \"This is bug new from API  from Intellij\",\n" +
                        "       \"issuetype\": {\n" +
                        "          \"name\": \"Bug\"\n" +
                        "       }\n" +
                        "   }\n" +
                        "}").
                when().
                        post("rest/api/3/issue/").
                then().
                    log().all().
                    assertThat().statusCode(201);

    }

    @Test
    public void updateBug()
    {

        RestAssured.baseURI="https://jashish.atlassian.net";
        given().
                log().all().
                auth().preemptive().
                basic("jashishtbd@gmail.com","ATATT3xFfGF07G_yvXhpAL4s29akuCt6IBVk0ja--F8K87Y2lH91jvVMTPN4TaUaqb1BoNGaWk8cXI-k3LU9vOLu_59SXOG1d37BEX8e6ZajjcK0VKnskuaIccdEt1hkacICZQka8gVQqEOhC_pxvKJ20BY2DTv-vxMFJrldNS1MIPI3PAJn8f4=D61AF853").
                header("Content-Type","application/json").
                pathParam("key","EZ-10").
                body("{\n" +
                       "    \"body\": \"This is a second comment from IntelliJ\"\n" +
                       "}").

        when().
                post("rest/api/2/issue/{key}/comment").

        then().
                log().all().
                assertThat().statusCode(201);

    }


    @Test
    public void UpdateFileInBug()
    {

        RestAssured.baseURI="https://jashish.atlassian.net";
        given().
                log().all().
                auth().preemptive().
                basic("jashishtbd@gmail.com","ATATT3xFfGF07G_yvXhpAL4s29akuCt6IBVk0ja--F8K87Y2lH91jvVMTPN4TaUaqb1BoNGaWk8cXI-k3LU9vOLu_59SXOG1d37BEX8e6ZajjcK0VKnskuaIccdEt1hkacICZQka8gVQqEOhC_pxvKJ20BY2DTv-vxMFJrldNS1MIPI3PAJn8f4=D61AF853").
                header("Content-Type","multipart/form-data").
                header("X-Atlassian-Token","nocheck").

                pathParam("key","EZ-10").
                body("{\n" +
                        "    \"body\": \"This is a second comment from IntelliJ\"\n" +
                        "}").
                multiPart("file",new File("C:\\Users\\HP\\IdeaProjects\\Restassuredproject1rahul\\src\\test\\java\\Utils\\Screenshot .png")).
        when().
                post("rest/api/2/issue/{key}/attachments").
        then().
                log().all().
                assertThat().statusCode(200);

    }

    @Test
    public void getCommentfromJson()
    {

        RestAssured.baseURI="https://jashish.atlassian.net";
        //Post a  comment
        String newComments="This is validation commnet required";
        String response=given().
                log().all().
                auth().preemptive().
                basic("jashishtbd@gmail.com","ATATT3xFfGF07G_yvXhpAL4s29akuCt6IBVk0ja--F8K87Y2lH91jvVMTPN4TaUaqb1BoNGaWk8cXI-k3LU9vOLu_59SXOG1d37BEX8e6ZajjcK0VKnskuaIccdEt1hkacICZQka8gVQqEOhC_pxvKJ20BY2DTv-vxMFJrldNS1MIPI3PAJn8f4=D61AF853").
                header("Content-Type","application/json").
                pathParam("key","EZ-10").
                queryParam("fields","comment").
                body("{\n" +
                        "    \"body\": \""+newComments+"\"\n" +
                        "}").
      when()      .
                post("rest/api/2/issue/{key}/comment").
                then().
                log().all().
                assertThat().statusCode(201).extract().asString();


        JsonPath js1= new JsonPath(response);

        System.out.println("The filetrerd result is "+response);
        String commentID= js1.get("id").toString();


        //Fetch all commnet id and verify if new comment id is there in it
        String getResponse=given().
                log().all().
                auth().preemptive().
                basic("jashishtbd@gmail.com","ATATT3xFfGF07G_yvXhpAL4s29akuCt6IBVk0ja--F8K87Y2lH91jvVMTPN4TaUaqb1BoNGaWk8cXI-k3LU9vOLu_59SXOG1d37BEX8e6ZajjcK0VKnskuaIccdEt1hkacICZQka8gVQqEOhC_pxvKJ20BY2DTv-vxMFJrldNS1MIPI3PAJn8f4=D61AF853").
                header("Content-Type","application/json").
                pathParam("key","EZ-10").
                queryParam("fields","comment").
                body("{\n" +
                        "    \"body\": \""+newComments+"\"\n" +
                        "}").
                when()      .
                get("rest/api/2/issue/{key}").
                then().
                log().all().
                assertThat().statusCode(200).extract().asString();
        JsonPath js2= new JsonPath(getResponse);


        int commentsSize=js2.getInt("fields.comment.comments.size()");

        for (int i=0;i<commentsSize;i++)
        {
           String commentID2= js2.get("fields.comment.comments["+i+"].id").toString();
           if(commentID2.equalsIgnoreCase(commentID))
           {

            String message=js2.get("fields.comment.comments["+i+"].body").toString();
            Assert.assertEquals(message,newComments);

           }
        }
    }

    @Test
    public void relaxedhTTPSvalidation()
    {

        RestAssured.baseURI="https://jashish.atlassian.net";
        given().
                relaxedHTTPSValidation().
                log().all().
                auth().preemptive().
                basic("jashishtbd@gmail.com","ATATT3xFfGF07G_yvXhpAL4s29akuCt6IBVk0ja--F8K87Y2lH91jvVMTPN4TaUaqb1BoNGaWk8cXI-k3LU9vOLu_59SXOG1d37BEX8e6ZajjcK0VKnskuaIccdEt1hkacICZQka8gVQqEOhC_pxvKJ20BY2DTv-vxMFJrldNS1MIPI3PAJn8f4=D61AF853").
                header("Content-Type","application/json").
                pathParam("key","EZ-10").
                body("{\n" +
                        "    \"body\": \"This is a second comment from IntelliJ\"\n" +
                        "}").
                when().
                post("rest/api/2/issue/{key}/comment").
                then().
                log().all().
                assertThat().statusCode(201);

    }


}
