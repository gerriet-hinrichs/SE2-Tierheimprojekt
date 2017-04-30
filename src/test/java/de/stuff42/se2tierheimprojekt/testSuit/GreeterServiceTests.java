package de.stuff42.se2tierheimprojekt.testSuit;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

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

    @Test
    public void databaseManipulatorTest() {

        DatabaseManipulator manipulator = new DatabaseManipulator();

        manipulator.cleanDb();

        manipulator.fillDb();

        manipulator.cleanDb();

    }

}
