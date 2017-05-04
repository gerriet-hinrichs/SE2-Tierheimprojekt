package de.stuff42.se2tierheimprojekt.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockTests {

    /***
     * Testing only a single mock service.
     */
    @Test
    public void mockServiceTest() {

        Logger logger = LoggerFactory.getLogger(this.getClass());

        logger.info("Create Object:");
        logger.info("-------------------------------");
        MockService mockService = new MockService();
        logger.info("");

        logger.info("Call Service function:");
        logger.info("-------------------------------");
        String greeting = mockService.sayHello("Duke");
        logger.info("");

        logger.info("Check returned value:");
        logger.info("-------------------------------");
        assertThat(greeting).isEqualTo("Hello World, Duke");
        logger.info("");
    }
}
