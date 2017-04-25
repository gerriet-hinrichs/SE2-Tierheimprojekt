package de.stuff42.se2tierheimprojekt.configuration;

import org.springframework.context.ConfigurableApplicationContext;

public class TestApplicationInitializer extends ApplicationInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        DatabaseConfig.configurationPrefix = "Test";
        super.initialize(applicationContext);
    }
}
