package de.stuff42.se2tierheimprojekt;

import java.util.Properties;

import de.stuff42.utils.PathUtils;
import de.stuff42.utils.UtilsConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Application entry point. This class also gives access to root context configuration.
 */
@SpringBootApplication
@ComponentScan
public class Application {

    /**
     * Application class logger.
     */
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    /**
     * Active application context.
     */
    private static ConfigurableApplicationContext context;

    /**
     * Configuration prefix to be used when loading database configuration.
     * This field is set within the test application from static initializer.
     */
    static String configurationPrefix = "";

    /**
     * Application startup main class.
     *
     * @param args Application arguments.
     */
    public static void main(String[] args) {

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(" >>> MAIN CALLED");
        System.out.println();
        System.out.println();
        System.out.println();

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

    /**
     * Stops the currently active application context.
     */
    public static void stop() {
        context.stop();
    }
}
