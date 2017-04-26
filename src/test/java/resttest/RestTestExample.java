package resttest;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class RestTestExample {
    RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
    
    @BeforeClass
    public static void setupConnection() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "";     
    }
     
     
    @Before
    public void setup() throws Exception {
        //requestSpecBuilder.setContentType(ContentType.JSON).addHeader("Accept", ContentType.JSON.getAcceptHeader());
    	requestSpecBuilder.setContentType(ContentType.HTML).addHeader("Accept", ContentType.HTML.getAcceptHeader());
    }
    
    //Nachfolgende Test sind nicht umbedingt sinnvoll sondern dienen hauptsächlich den Zweck mit RESTAssured zu experimentieren
    
    /**
     * Test welcher kontrolliert ob die Website ein HTML Dokument ausgibt.
     * @throws Exception
     */
    @Test
    public void testHTML () throws Exception {
    	//System.out.println(get().asString()); //nur zum experimentieren
    	assert(when().get().contentType().toString().contains(ContentType.HTML.toString()));
    }
    
    
}
