package org.example;

import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class DownlaodFile {

    @Test
    public void test1() throws IOException
    {

        byte[] bytesfile=given().
                baseUri("https://raw.githubusercontent.com").
                log().all().
        when().
                get("/appium/appium/master/packages/appium/sample-code/apps/ApiDemos-debug.apk").

        then().
                log().all().
                extract().
                response().asByteArray();

        OutputStream os=new FileOutputStream(new File("ApiDemos-debug.apk"));
        os.write(bytesfile);
        os.close();

    }

}
