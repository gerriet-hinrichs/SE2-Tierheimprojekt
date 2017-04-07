package de.stuff42.se2tierheimprojekt.service.test;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.stuff42.se2tierheimprojekt.db.OtherTestEntity;
import de.stuff42.se2tierheimprojekt.db.OtherTestEntityDAO;
import de.stuff42.se2tierheimprojekt.db.TestEntity;
import de.stuff42.se2tierheimprojekt.db.TestEntityDAO;
import de.stuff42.se2tierheimprojekt.service.BaseService;

@Service
public class TestService extends BaseService {

    @Autowired
    private TestEntityDAO testEntityDAO;

    @Autowired
    private OtherTestEntityDAO otherTestEntityDAO;

    public Iterable<TestEntity> getList() {
        return testEntityDAO.findAll();
    }

    public TestEntity add(String text) {
        OtherTestEntity other = new OtherTestEntity();
        other.name = StringUtils.reverse(text);

        TestEntity entity = new TestEntity();
        entity.name = text;
        entity.other = other;

        otherTestEntityDAO.save(other);
        testEntityDAO.save(entity);

        return entity;
    }
}
