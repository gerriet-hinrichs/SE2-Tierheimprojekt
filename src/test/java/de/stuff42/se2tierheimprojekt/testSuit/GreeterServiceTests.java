package de.stuff42.se2tierheimprojekt.testSuit;

import static org.assertj.core.api.Assertions.assertThat;

import de.stuff42.se2tierheimprojekt.db.*;
import de.stuff42.utils.SystemConfig;
import org.junit.Test;
import org.slf4j.*;

public class GreeterServiceTests {

    @Test
    public void sayHello_whenInvokedWithDuke_thenSaysHelloWorldDuke() {

        // Given
        GreeterService greeterService = new GreeterService();

        // When
        String greeting = greeterService.sayHello("Duke");

        // Then
        assertThat(greeting).isEqualTo("Hello World, Duke");

    }

}
