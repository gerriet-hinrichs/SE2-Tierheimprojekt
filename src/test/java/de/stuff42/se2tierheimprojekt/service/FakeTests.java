package de.stuff42.se2tierheimprojekt.service;


import de.stuff42.se2tierheimprojekt.Application;
import de.stuff42.se2tierheimprojekt.configuration.TestApplicationInitializer;
import de.stuff42.se2tierheimprojekt.db.FakeTableEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
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
// @TransactionConfiguration(defaultRollback = true)
public class FakeTests {

    @Autowired
    FakeService mockService;

    /***
     * Testing:
     * MockService -> FakeTableEntry -> OtherFakeTableEntry
     * Adds a string into FakeTableEntry and the same reverted string into OtherFakeTableEntry.
     */
    @Test
    public void fakeServiceTest() {

        FakeTableEntry entry = mockService.add("Test");

        assertThat(entry.name).isEqualTo("Test");
        assertThat(entry.other.name).isEqualTo("tseT");
    }
}
