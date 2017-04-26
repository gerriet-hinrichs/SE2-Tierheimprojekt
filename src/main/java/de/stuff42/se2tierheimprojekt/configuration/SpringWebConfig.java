package de.stuff42.se2tierheimprojekt.configuration;

import de.stuff42.utils.SystemConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@EnableWebMvc
@Configuration
@ComponentScan({"de.stuff42.se2tierheimprojekt"})
public class SpringWebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("/resources/templates/");
        return configurer;
    }

    @Bean
    public FreeMarkerViewResolver viewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();

        // disable caching when in debug mode
        resolver.setCache(!SystemConfig.get("BUILD", "Debug", Boolean.class, true));
        resolver.setPrefix("");
        resolver.setSuffix(".ftl");
        return resolver;
    }
}
