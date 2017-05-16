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


import de.stuff42.se2tierheimprojekt.db.FakeTable;
import de.stuff42.se2tierheimprojekt.db.FakeTableEntry;
import de.stuff42.se2tierheimprojekt.db.OtherFakeTable;
import de.stuff42.se2tierheimprojekt.db.OtherFakeTableEntry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FakeService extends BaseService {

    @Autowired
    private FakeTable fakeTable;

    @Autowired
    private OtherFakeTable otherFakeTable;

    public Iterable<FakeTableEntry> getList() {
        return fakeTable.findAll();
    }

    public FakeTableEntry add(String text) {
        OtherFakeTableEntry otherEntity = new OtherFakeTableEntry(StringUtils.reverse(text));

        FakeTableEntry entity = new FakeTableEntry(text, otherEntity);

        otherFakeTable.save(otherEntity);
        fakeTable.save(entity);

        return entity;
    }
}
