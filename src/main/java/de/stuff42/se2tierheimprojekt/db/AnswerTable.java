package de.stuff42.se2tierheimprojekt.db;

import org.springframework.data.repository.CrudRepository;

public interface AnswerTable extends CrudRepository<AnswerEntry, Long> {
}
