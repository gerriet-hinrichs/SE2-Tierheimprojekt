/*
 * Application to help putting pets from animal shelter across.
 * Copyright (C) 2017
 *     Felix Koch <felix.koch@haw-hamburg.de>,
 *     Kristian Exss <Kristian.Exss@HAW-Hamburg.de>,
 *     Adrian Bostelmann <Adrian.Bostelmann@HAW-Hamburg.de>,
 *     Karsten Boehringer <Karsten.Boehringer@HAW-Hamburg.de>,
 *     Gehui Xu <Gehui.Xu@HAW-Hamburg.de>,
 *     Gerriet Hinrichs <gerriet.hinrichs@haw-hamburg.de>.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.stuff42.se2tierheimprojekt.unsorted;

import de.stuff42.se2tierheimprojekt.Application;
import de.stuff42.se2tierheimprojekt.configuration.TestApplicationInitializer;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
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


