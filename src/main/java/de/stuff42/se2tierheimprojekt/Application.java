package de.stuff42.se2tierheimprojekt;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import de.stuff42.utils.PathUtils;
import de.stuff42.utils.UtilsConfig;

/**
 * Application entry point. This class also gives access to root context configuration.
 */
@SpringBootApplication
public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        UtilsConfig.setRelevantPackages("de.stuff42.se2tierheimprojekt");
        SpringApplication application = new SpringApplication(Application.class);
        Properties properties = new Properties();

        // database settings
        new DatabaseConfiguration().updateConfigurationProperties(properties);

        // start application
        application.setDefaultProperties(properties);
        context = application.run(args);
        logger.info("Working directory: {}", PathUtils.getApplicationRoot().toString());
    }

    public static void stop() {
        context.stop();
    }
}
