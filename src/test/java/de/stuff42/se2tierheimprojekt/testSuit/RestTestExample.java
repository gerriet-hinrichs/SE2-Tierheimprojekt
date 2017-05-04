package de.stuff42.se2tierheimprojekt.testSuit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import de.stuff42.se2tierheimprojekt.Application;
import de.stuff42.se2tierheimprojekt.configuration.TestApplicationInitializer;
import de.stuff42.se2tierheimprojekt.controller.AppStartController;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.get;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @SpringBootTest
@ContextConfiguration(classes = {Application.class},
    initializers = {TestApplicationInitializer.class})
//@WebAppConfiguration
public class RestTestExample {
  private RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

  /**
   * 
   */

  @LocalServerPort
  private int port;

  @Before
  public void setupConnection() {


    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;
    RestAssured.basePath = "";


  }



  @Before
  public void setup() throws Exception {
    requestSpecBuilder.setContentType(ContentType.JSON).addHeader("Accept",
        ContentType.JSON.getAcceptHeader());


    // requestSpecBuilder.setContentType(ContentType.HTML).addHeader("Accept",
    // ContentType.HTML.getAcceptHeader());
  }


  /**
   * Test welcher kontrolliert ob die Website ein HTML Dokument ausgibt.
   * 
   * @throws Exception
   */
  @Test
  public void beispielTest() throws Exception {
    // System.out.println(get("/test").contentType().contains(ContentType.JSON.toString()));
    // System.out.println(get().asString()); //nur zum experimentieren
    assert (get("/test").contentType().contains(ContentType.JSON.toString()));
  }


}


