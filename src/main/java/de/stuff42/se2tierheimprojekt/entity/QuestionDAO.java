/*
 * Application to help putting pets from animal shelter across.
 * Copyright (C) 2017
 *     Felix Koch <felix.koch@haw-hamburg.de>,
 *     Kristian Exss <Kristian.Exss@HAW-Hamburg.de>,
 *     Adrian Bostelmann <Adrian.Bostelmann@HAW-Hamburg.de>,
 *     Karsten Boehringer <Karsten.Boehringer@HAW-Hamburg.de>,
 *     Gehui Xu <Gehui.Xu@HAW-Hamburg.de>,
 *     Gerriet Hinrichs <gerriet.hinrichs@haw-hamburg.de>.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.stuff42.se2tierheimprojekt.entity;

import java.util.List;
import java.util.Set;

import de.stuff42.se2tierheimprojekt.data.*;
import de.stuff42.se2tierheimprojekt.model.EvaluationResult;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface QuestionDAO extends CrudRepository<QuestionEntity, Long> {

    @Query("SELECT q FROM QuestionEntity q ORDER BY q.sortOrder")
    Page<QuestionEntity> getFirstQuestions(Pageable pageable);

    default QuestionEntity getFirstQuestion() {
        List<QuestionEntity> result = getFirstQuestions(new PageRequest(0, 1)).getContent();
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @Query("SELECT q FROM QuestionEntity q ORDER BY q.sortOrder")
    List<QuestionEntity> getSortedList();

    @Query("SELECT DISTINCT question FROM QuestionEntity question" +
            " JOIN question.answers answer" +
            " WHERE question.id NOT IN :questionIds" +
            " AND (EXISTS (SELECT 1 FROM answer.animalType animalType WHERE animalType IN :animalType)" +
            " OR EXISTS (SELECT 1 FROM answer.animalSize animalSize WHERE animalSize IN :animalSize)" +
            " OR EXISTS (SELECT 1 FROM answer.cost cost WHERE cost IN :cost)" +
            " OR EXISTS (SELECT 1 FROM answer.needCare needCare WHERE needCare IN :needCare)" +
            " OR EXISTS (SELECT 1 FROM answer.garden garden WHERE garden IN :garden))" +
            " ORDER BY question.sortOrder")
    Page<QuestionEntity> getNextQuestions(
            @Param("questionIds") Set<Long> questionIds,
            @Param("animalType") List<AnimalType> animalType,
            @Param("animalSize") List<AnimalSize> animalSize,
            @Param("cost") List<AnimalCost> cost,
            @Param("needCare") List<AnimalCareTyp> needCare,
            @Param("garden") List<AnimalGardenSpace> garden,
            Pageable pageable);

    default QuestionEntity getNextQuestion(Set<Long> questionIds, EvaluationResult evaluationResult) {
        List<QuestionEntity> result = getNextQuestions(
                questionIds,
                evaluationResult.animalTypes,
                evaluationResult.animalSizes,
                evaluationResult.animalCosts,
                evaluationResult.animalCareTypes,
                evaluationResult.animalGardenSpaces,
                new PageRequest(0, 1)).getContent();
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }
}
