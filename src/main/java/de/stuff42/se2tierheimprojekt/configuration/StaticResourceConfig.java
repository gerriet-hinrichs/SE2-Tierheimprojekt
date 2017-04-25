package de.stuff42.se2tierheimprojekt.configuration;

import de.stuff42.utils.PathUtils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Resource handler for static files.
 */
@Configuration
public class StaticResourceConfig extends WebMvcConfigurerAdapter {

    /**
     * Url of static files on the server.
     */
    private static final String STATIC_FILES_URL = "/static";

    /**
     * Path to static files on disk.
     */
    private static final String STATIC_FILES_PATH = "/static-files";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(STATIC_FILES_URL + "/**").addResourceLocations(
                "file:" + PathUtils.getApplicationRoot().toString() + STATIC_FILES_PATH + "/"
        );
    }
}