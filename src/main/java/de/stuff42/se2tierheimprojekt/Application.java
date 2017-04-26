package de.stuff42.se2tierheimprojekt;

import de.stuff42.se2tierheimprojekt.configuration.ApplicationInitializer;
import de.stuff42.utils.UtilsConfig;

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
     * Active application context.
     */
    private static ConfigurableApplicationContext context;

    /**
     * Application startup main class.
     *
     * @param args Application arguments.
     */
    public static void main(String[] args) {
        UtilsConfig.setRelevantPackages("de.stuff42.se2tierheimprojekt");
        SpringApplication application = new SpringApplication(Application.class);
        application.addInitializers(new ApplicationInitializer());
        context = application.run(args);
    }

    /**
     * Stops the currently active application context.
     */
    public static void stop() {
        context.stop();
    }
}
