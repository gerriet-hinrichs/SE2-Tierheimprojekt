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
