package de.stuff42.se2tierheimprojekt.db;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface FakeTable extends CrudRepository<FakeTableEntry, Long> {

}
