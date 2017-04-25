package de.stuff42.se2tierheimprojekt.configuration;

import java.util.Properties;

import de.stuff42.utils.PathUtils;
import de.stuff42.utils.UtilsConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertiesPropertySource;

/**
 * Application initializer that performs required operations.
 * This includes specifying the correct database config.
 */
public class ApplicationInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        UtilsConfig.setRelevantPackages("de.stuff42.se2tierheimprojekt");
        Properties properties = new Properties();

        // database settings
        new DatabaseConfig().updateConfigurationProperties(properties);

        applicationContext.getEnvironment().getPropertySources().addFirst(new PropertiesPropertySource("databaseConfiguration", properties));

        logger.info("Working directory: {}", PathUtils.getApplicationRoot().toString());
    }
}
