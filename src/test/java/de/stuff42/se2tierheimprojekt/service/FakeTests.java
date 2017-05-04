package de.stuff42.se2tierheimprojekt.service;


import de.stuff42.se2tierheimprojekt.Application;
import de.stuff42.se2tierheimprojekt.configuration.TestApplicationInitializer;
import de.stuff42.se2tierheimprojekt.db.FakeTableEntry;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {Application.class}, initializers = {TestApplicationInitializer.class})
@Transactional
public class FakeTests {

    @Autowired
    private FakeService fakeService;

    /***
     * Testing:
     * MockService -> FakeTableEntry -> OtherFakeTableEntry
     * Adds a string into FakeTableEntry and the same reverted string into OtherFakeTableEntry.
     */
    @Test
    public void fakeServiceTest() {

        Logger logger = LoggerFactory.getLogger(this.getClass());
        String name = "Test";
        String nameReverse = StringUtils.reverse(name);

        logger.info("Use FakeService:");
        logger.info("-------------------------------");
        FakeTableEntry entry = fakeService.add(name);
        logger.info("check");
        logger.info("");

        logger.info("Check Entry:");
        logger.info("-------------------------------");
        assertThat(entry.name).isEqualTo(name);
        assertThat(entry.other.name).isEqualTo(nameReverse);
        logger.info("check");
        logger.info("");
    }
}
