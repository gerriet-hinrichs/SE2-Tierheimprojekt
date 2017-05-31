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
package de.stuff42.se2tierheimprojekt.model.rest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import de.stuff42.se2tierheimprojekt.entity.AnswerEntity;
import de.stuff42.se2tierheimprojekt.entity.QuestionEntity;

import org.jetbrains.annotations.NotNull;

public class QuestionModel {

    /**
     * DB id from question.
     */
    @NotNull
    public Long id;

    /**
     * Question text.
     */
    public String text;

    /**
     * Sorting order.
     */
    public int sortOrder;

    /**
     * Matching answers.
     */
    public List<AnswerModel> answers;

    /**
     * Creates rest model from database entity.
     *
     * @param entity Database entity.
     */
    public QuestionModel(QuestionEntity entity) {
        this(entity, true);
    }

    /**
     * Creates rest model from database entity.
     *
     * @param entity         Database entity.
     * @param includeAnswers Flag if answers should be included.
     */
    public QuestionModel(QuestionEntity entity, boolean includeAnswers) {
        this.id = entity.id;
        this.text = entity.text;
        this.sortOrder = entity.sortOrder;

        if (includeAnswers) {
            // load answer model list
            answers = new ArrayList<>(entity.answers.size());
            for (AnswerEntity answerEntity : entity.answers) {
                answers.add(new AnswerModel(answerEntity));
            }

            // sort answers
            answers.sort(Comparator.comparingInt(a -> a.sortOrder));
        }
    }

    /**
     * Default constructor needed due to JSON de-serializing
     */
    public QuestionModel() {}
}
