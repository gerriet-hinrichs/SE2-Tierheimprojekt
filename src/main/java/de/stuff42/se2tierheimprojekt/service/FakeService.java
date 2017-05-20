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


import de.stuff42.se2tierheimprojekt.entity.FakeDAO;
import de.stuff42.se2tierheimprojekt.entity.FakeEntry;
import de.stuff42.se2tierheimprojekt.entity.OtherFakeDAO;
import de.stuff42.se2tierheimprojekt.entity.OtherFakeEntry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FakeService extends BaseService {

    @Autowired
    private FakeDAO fakeTable;

    @Autowired
    private OtherFakeDAO otherFakeDAO;

    public Iterable<FakeEntry> getList() {
        return fakeTable.findAll();
    }

    public FakeEntry add(String text) {
        OtherFakeEntry otherEntity = new OtherFakeEntry(StringUtils.reverse(text));

        FakeEntry entity = new FakeEntry(text, otherEntity);

        otherFakeDAO.save(otherEntity);
        fakeTable.save(entity);

        return entity;
    }
}
