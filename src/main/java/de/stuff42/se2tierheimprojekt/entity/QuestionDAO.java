package de.stuff42.se2tierheimprojekt.entity;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface QuestionDAO extends CrudRepository<QuestionEntity, Long> {

    @Query("select q from QuestionEntity q order by q.sortOrder")
    QuestionEntity getFirstQuestion();

    @Query("select q from QuestionEntity q order by q.sortOrder")
    List<QuestionEntity> getSortedList();

    @Query("select q from QuestionEntity q where q.sortOrder > :lastQuestionSortOrder order by q.sortOrder")
    QuestionEntity getNextQuestion(@Param("lastQuestionSortOrder") int lastQuestionSortOrder);
}
