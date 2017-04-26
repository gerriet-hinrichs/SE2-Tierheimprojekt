package de.stuff42.se2tierheimprojekt.testSuit;

import de.stuff42.se2tierheimprojekt.Application;
import de.stuff42.se2tierheimprojekt.configuration.TestApplicationInitializer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {Application.class}, initializers = {TestApplicationInitializer.class})
public class GreeterServiceIntegrationTests {

    @Autowired
    private GreeterService greeterService;

    @Test
    public void sayHello_whenInvokedWithDuke_thenSaysHelloWorldDuke() {

        // When
        String greeting = greeterService.sayHello("Duke");

        // Then
        assertThat(greeting).isEqualTo("Hello World, Duke");

    }

}
