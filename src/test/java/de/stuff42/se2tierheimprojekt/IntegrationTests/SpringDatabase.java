package de.stuff42.se2tierheimprojekt.IntegrationTests;

import de.stuff42.se2tierheimprojekt.Application;
import de.stuff42.se2tierheimprojekt.configuration.TestApplicationInitializer;
import de.stuff42.se2tierheimprojekt.db.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {Application.class}, initializers = {TestApplicationInitializer.class})
@Transactional
public class SpringDatabase {

    @Autowired
    private FakeTable fakeTable;

    @Autowired
    private OtherFakeTable otherFakeTable;

    private Logger logger;

    @Before
    public void setUp(){
        this.logger = LoggerFactory.getLogger(this.getClass());
        logger.info("");
        logger.info("=============== Test Start ================");
    }

    @After
    public void cleanUp(){
        logger.info("=============== Test End ================");
        logger.info("");
    }

    @Test
    public void spring(){
        logger.info("Spring Autowired:");
        logger.info("-------------------------------");
        assertNotNull(fakeTable);
        assertNotNull(otherFakeTable);
        logger.info("check");
        logger.info("");
    }

    /***
     * Testing Spring connection to Database with Autowired.
     * Testing FakeTable connection with OtherFakeTable.
     */
    @Test
    public void database() {
        Long numberOfEntries = 5L;
        String name = "Nr";
        String nameOther = "Other";

        logger.info("Clean Database:");
        logger.info("-------------------------------");
        fakeTable.deleteAll();
        otherFakeTable.deleteAll();
        logger.info("check");
        logger.info("");

        logger.info("Create and Save Data:");
        logger.info("-------------------------------");
        for (Long i = 0L; i < numberOfEntries; i++) {
            logger.info(
                    fakeTable.save(new FakeTableEntry(name + i,
                            otherFakeTable.save(new OtherFakeTableEntry(nameOther + name + i))))
                            .toString());
        }
        logger.info("");

        logger.info("Read and Check:");
        logger.info("-------------------------------");
        logger.info("Entry number FakeTable:");
        assertEquals(java.util.Optional.ofNullable(fakeTable.count()).orElse(0L), numberOfEntries);
        logger.info("check");
        logger.info("Entry number OtherFakeTable:");
        assertEquals(java.util.Optional.ofNullable(otherFakeTable.count()).orElse(0L), numberOfEntries);
        logger.info("check");
        logger.info("Entry Attributes:");
        for (FakeTableEntry entry : fakeTable.findAll()) {
            assertEquals(nameOther + entry.name, entry.other.name);
            logger.info(entry.toString());
        }
        logger.info("check");
        logger.info("");
    }
}
