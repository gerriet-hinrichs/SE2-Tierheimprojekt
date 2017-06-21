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
package de.stuff42.se2tierheimprojekt.controller;

import de.stuff42.se2tierheimprojekt.service.DatabaseSetupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for database setup.
 */
@Controller
public class DatabaseSetupController extends BaseController<DatabaseSetupService> {

    /**
     * Creates instance for the given service.
     *
     * @param serviceInstance Service instance.
     */
    @Autowired
    public DatabaseSetupController(DatabaseSetupService serviceInstance) {
        super(serviceInstance);
    }

    @RequestMapping(value = "/setup/clear", method = RequestMethod.GET)
    @ResponseBody
    public String clean() {
        service.clean();
        return "Performed database clean.";
    }

    @RequestMapping(value = "/setup/fill", method = RequestMethod.GET)
    @ResponseBody
    public String fill() {
        service.setup();
        return "Filled database.";
    }

    @RequestMapping(value = "/setup", method = RequestMethod.GET)
    @ResponseBody
    public String setup() {
        return this.clean() + '\n' + this.fill();
    }
}
