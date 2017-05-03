package de.stuff42.se2tierheimprojekt.IntegrationTests;

import de.stuff42.se2tierheimprojekt.Application;
import de.stuff42.se2tierheimprojekt.configuration.TestApplicationInitializer;
import de.stuff42.se2tierheimprojekt.db.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {Application.class}, initializers = {TestApplicationInitializer.class})
@Transactional
// @TransactionConfiguration(defaultRollback = true)
public class SpringDatabase {

    @Autowired
    private FakeTable fakeTable;

    @Autowired
    private OtherFakeTable otherFakeTable;

    /***
     * Testing Spring connection to Database with Autowired.
     * Testing FakeTable connection with OtherFakeTable.
     */
    @Test
    public void fakeTableTests() {

        Logger logger = LoggerFactory.getLogger(this.getClass());

        logger.info("Save Data:");
        logger.info("-------------------------------");
        new FakeTableEntry("Nr1",new OtherFakeTableEntry("OtherNr1"));
        new FakeTableEntry("Nr2",new OtherFakeTableEntry("OtherNr2"));
        new FakeTableEntry("Nr3",new OtherFakeTableEntry("OtherNr3"));
        logger.info("");

        logger.info("Read all FakeTable:");
        logger.info("-------------------------------");
        for (FakeTableEntry entry : fakeTable.findAll()) {
            logger.info(entry.toString());
        }//TODO: Ergebnisse checken!

        logger.info("Read all OtherFakeTable:");
        logger.info("-------------------------------");
        for (OtherFakeTableEntry entry : otherFakeTable.findAll()) {
            logger.info(entry.toString());
        }//TODO: Ergebnisse checken!
    }
}
