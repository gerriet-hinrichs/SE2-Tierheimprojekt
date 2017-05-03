package de.stuff42.se2tierheimprojekt.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class MockTests {

    /***
     * Testing only a single mock service.
     */
    @Test
    public void mockServiceTest() {

        // Given
        MockService mockService = new MockService();

        // When
        String greeting = mockService.sayHello("Duke");

        // Then
        assertThat(greeting).isEqualTo("Hello World, Duke");
    }
}
