package de.stuff42.se2tierheimprojekt.entity;

import org.springframework.data.repository.CrudRepository;

public interface QuestionDAO extends CrudRepository<QuestionEntry, Long> {
}
