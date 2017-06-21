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
package de.stuff42.se2tierheimprojekt.service;

import java.util.*;
import java.util.Map.Entry;

import de.stuff42.se2tierheimprojekt.entity.*;
import de.stuff42.se2tierheimprojekt.model.EvaluationResult;
import de.stuff42.se2tierheimprojekt.model.rest.AnswerModel;
import de.stuff42.se2tierheimprojekt.model.rest.QuestionModel;
import de.stuff42.se2tierheimprojekt.model.rest.ResultModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class QuestionService extends BaseService {

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private AnswerDAO answerDAO;

    @Autowired
    private AnimalDAO animalDAO;

    /**
     * Returns the first question of the questionnaire with all of its answers.
     *
     * @return Returns the first question or null if there isn't a first question.
     */
    public QuestionModel getFirstWithAnswers() {
        QuestionEntity questionEntity = questionDAO.getFirstQuestion();
        if (questionEntity == null) {
            return null;
        }
        return new QuestionModel(questionEntity);
    }

    /**
     * Returns the question with the ID and all of its answers.
     *
     * @param id the ID of the wanted question.
     *
     * @return the question with the ID or null if there is no question with this ID.
     */
    public QuestionModel getByIDWithAnswers(long id) {
        QuestionEntity questionEntity = questionDAO.findOne(id);
        if (questionEntity == null) {
            return null;
        }
        return new QuestionModel(questionEntity);
    }

    /**
     * Returns a list with all questions of the questionnaire.
     *
     * @return List with all questions.
     */
    public List<QuestionModel> getList() {
        List<QuestionEntity> questionEntities = questionDAO.getSortedList();

        List<QuestionModel> questionModels = new ArrayList<>(questionEntities.size());
        for (QuestionEntity questionEntity : questionEntities) {
            questionModels.add(new QuestionModel(questionEntity, false));
        }

        return questionModels;
    }

    /**
     * Returns a List with all answers of the question with the specified ID
     *
     * @param questionId ID of the question
     *
     * @return List with all answers of the question or null if the question doesn't exist
     */
    public List<AnswerModel> getAnswersForQuestion(long questionId) {
        List<AnswerEntity> answerEntities = answerDAO.getSortedListForQuestion(questionId);

        List<AnswerModel> answerModels = new ArrayList<>(answerEntities.size());
        for (AnswerEntity questionEntity : answerEntities) {
            answerModels.add(new AnswerModel(questionEntity));
        }

        return answerModels;
    }

    /**
     * Returns the next question for the given answers.
     *
     * @param answers All selected answers of the questionnaire.
     *
     * @return The next question.
     */
    public QuestionModel getNextForAnswers(Map<Long, List<Long>> answers) {

        // special case: empty set: return first question
        if (answers.isEmpty()) {
            return getFirstWithAnswers();
        }

        // evaluate input
        EvaluationResult evaluationResult = evaluate(answers);

        // only query database if we can get a result
        if (evaluationResult.isEmpty()) {
            return null;
        }

        // load next question
        QuestionEntity nextQuestion = questionDAO.getNextQuestion(answers.keySet(), evaluationResult);

        // if there is no next question, return null
        if (nextQuestion == null) {
            return null;
        }

        return new QuestionModel(nextQuestion);
    }

    /**
     * Gets all answers of the Questionnaire and returns the Evaluation.
     *
     * @param answers All selected answers of the questionnaire
     *
     * @return Evaluation result.
     */
    public ResultModel evaluateQuestionnaire(Map<Long, List<Long>> answers) {

        // evaluate input
        EvaluationResult evaluationResult = evaluate(answers);

        // only query database if it's required
        if (evaluationResult.isEmpty()) {
            return new ResultModel(new LinkedList<>());
        }
        return new ResultModel(animalDAO.getFittingAnimals(evaluationResult));
    }

    /**
     * Performs evaluation on the given answers and returns the evaluation result.
     *
     * @param answers All selected answers of the questionnaire.
     *
     * @return Evaluation result.
     */
    private EvaluationResult evaluate(Map<Long, List<Long>> answers) {

        // create result model with all properties
        EvaluationResult evaluationResult = new EvaluationResult(true);

        // handle all questions
        for (Entry<Long, List<Long>> entry : answers.entrySet()) {

            // extract answer ids
            List<Long> answerIds = entry.getValue();

            // check if we have at least one answer
            if (answerIds.isEmpty()) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
            }

            // load question (and ensure it's valid)
            QuestionEntity questionEntity = questionDAO.findOne(entry.getKey());
            if (questionEntity == null) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
            }

            // check how answers should be handled
            switch (questionEntity.answerType) {

                // multi answer (remove only properties that are excluded by all given answers)
                case CHECKBOX:

                    // create partial result without data
                    EvaluationResult partialEvaluationResult = new EvaluationResult(false);

                    // iterate over all answers
                    boolean first = true;
                    for (Long answerId : entry.getValue()) {

                        // load selected answer (and ensure it's valid)
                        AnswerEntity answerEntry = answerDAO.findOne(answerId);
                        if (answerEntry == null) {
                            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
                        }

                        if (first) {

                            // handle first given answer differently (add all stuff to model)
                            partialEvaluationResult.add(answerEntry);
                            first = false;
                        } else {

                            // we want to have the intersection of possible answers
                            partialEvaluationResult.intersect(answerEntry);
                        }
                    }

                    // evaluate partial result
                    evaluationResult.remove(partialEvaluationResult);

                    break;

                // single answer
                case RADIO_BUTTON:
                case SLIDER:

                    // ensure only one answer is given
                    if (answerIds.size() != 1) {
                        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
                    }

                    // load selected answer (and ensure it's valid)
                    AnswerEntity answerEntry = answerDAO.findOne(answerIds.get(0));
                    if (answerEntry == null) {
                        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
                    }

                    // evaluate answer
                    evaluationResult.remove(answerEntry);
                    break;
            }
        }

        // return the evaluation result
        return evaluationResult;
    }
}
