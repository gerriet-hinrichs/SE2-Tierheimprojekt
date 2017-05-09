/*
 * Application to help putting pets from animal shelter across.
 * Copyright (C) 2017
 *     Felix Koch <felix.koch@haw-hamburg.de>,
 *     Kristian Exss <Kristian.Exss@HAW-Hamburg.de>,
 *     Adrian Bostelmann <Adrian.Bostelmann@HAW-Hamburg.de>,
 *     Karsten Boehringer <Karsten.Boehringer@HAW-Hamburg.de>,
 *     Gehui Xu <Gehui.Xu@HAW-Hamburg.de>,
 *     Gerriet Hinrichs <gerriet.hinrichs@haw-hamburg.de>.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
