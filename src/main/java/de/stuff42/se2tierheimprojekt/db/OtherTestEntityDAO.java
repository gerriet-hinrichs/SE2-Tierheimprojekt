package de.stuff42.se2tierheimprojekt.db;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface OtherTestEntityDAO extends CrudRepository<OtherTestEntity, Long> {

}
