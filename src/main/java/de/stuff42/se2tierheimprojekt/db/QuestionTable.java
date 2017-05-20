package de.stuff42.se2tierheimprojekt.db;

import org.springframework.data.repository.CrudRepository;

public interface QuestionTable extends CrudRepository<QuestionEntry, Long> {
}
