package de.stuff42.se2tierheimprojekt.db;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

/**
 * Created by Felix Koch on 01.05.2017.
 */
public interface DummyRepositoryI extends CrudRepository<DummyDataSet, Long> {

    List<DummyDataSet> findByName(String name);

}
