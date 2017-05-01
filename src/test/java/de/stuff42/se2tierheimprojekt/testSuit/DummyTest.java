package de.stuff42.se2tierheimprojekt.testSuit;

import de.stuff42.se2tierheimprojekt.Application;
import de.stuff42.se2tierheimprojekt.configuration.TestApplicationInitializer;
import de.stuff42.se2tierheimprojekt.db.DummyDataSet;
import de.stuff42.se2tierheimprojekt.db.DummyRepositoryI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Felix Koch on 01.05.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {Application.class}, initializers = {TestApplicationInitializer.class})
//@Transactional
//@TransactionConfiguration(defaultRollback = true)
public class DummyTest {

    @Autowired
    private DummyRepositoryI dummyRepo;

    @Test
    public void databaseDummyTest() {

        Logger logger = LoggerFactory.getLogger(Application.class);

        logger.info("Delate all:");
        logger.info("-------------------------------");
        dummyRepo.deleteAll();
        logger.info("");

        logger.info("Save Data:");
        logger.info("-------------------------------");
        dummyRepo.save(new DummyDataSet("Nr1"));
        dummyRepo.save(new DummyDataSet("Nr2"));
        dummyRepo.save(new DummyDataSet("Nr3"));
        logger.info("");

        logger.info("Read Data:");
        logger.info("-------------------------------");
        for (DummyDataSet dummy : dummyRepo.findAll()) {
            logger.info(dummy.toString());
        }
        logger.info("");

        logger.info("Customer found with id 1L:");
        logger.info("--------------------------------");
        DummyDataSet dummy1 = dummyRepo.findOne(1L);
        logger.info(dummy1.toString());
        logger.info("");

        logger.info("DummyDataSet found with findByName('name'):");
        logger.info("--------------------------------------------");
        for (DummyDataSet dummy : dummyRepo.findByName("Nr2")) {
            logger.info(dummy.toString());
        }
        logger.info("");

    }
}
