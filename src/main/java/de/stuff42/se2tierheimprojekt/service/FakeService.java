package de.stuff42.se2tierheimprojekt.service;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.stuff42.se2tierheimprojekt.db.OtherFakeTableEntry;
import de.stuff42.se2tierheimprojekt.db.OtherFakeTable;
import de.stuff42.se2tierheimprojekt.db.FakeTableEntry;
import de.stuff42.se2tierheimprojekt.db.FakeTable;

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
