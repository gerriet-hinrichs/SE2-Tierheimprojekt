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
package de.stuff42.se2tierheimprojekt.controller.test;

import de.stuff42.apigenerator.annotation.GenerateClientApi;
import de.stuff42.se2tierheimprojekt.controller.BaseController;
import de.stuff42.se2tierheimprojekt.entity.FakeEntity;
import de.stuff42.se2tierheimprojekt.service.FakeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@GenerateClientApi
public class TestController extends BaseController<FakeService> {

    /**
     * Creates instance for the given service.
     *
     * @param serviceInstance Service instance.
     */
    @Autowired
    public TestController(FakeService serviceInstance) {
        super(serviceInstance);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public Iterable<FakeEntity> getList() {
        return service.getList();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/test/{text}")
    public FakeEntity add(@PathVariable String text) {
        logger.info(text);
        return service.add(text);
    }
}
