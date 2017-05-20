package de.stuff42.se2tierheimprojekt.entity;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AnswerDAO extends CrudRepository<AnswerEntity, Long> {

    @Query("select a from AnswerEntity a where a.question.id = :questionId order by a.sortOrder")
    List<AnswerEntity> getSortedListForQuestion(@Param("questionId") long questionId);
}
