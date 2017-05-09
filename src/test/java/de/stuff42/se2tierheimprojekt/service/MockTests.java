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
package de.stuff42.se2tierheimprojekt.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.assertj.core.api.Assertions.assertThat;

public class MockTests {

    /***
     * Testing only a single mock service.
     */
    @Test
    public void mockServiceTest() {

        Logger logger = LoggerFactory.getLogger(this.getClass());

        logger.info("Create Object:");
        logger.info("-------------------------------");
        MockService mockService = new MockService();
        logger.info("");

        logger.info("Call Service function:");
        logger.info("-------------------------------");
        String greeting = mockService.sayHello("Duke");
        logger.info("");

        logger.info("Check returned value:");
        logger.info("-------------------------------");
        assertThat(greeting).isEqualTo("Hello World, Duke");
        logger.info("");
    }
}
