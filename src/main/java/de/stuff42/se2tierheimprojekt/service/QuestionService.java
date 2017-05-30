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

import de.stuff42.se2tierheimprojekt.data.AnimalCost;
import de.stuff42.se2tierheimprojekt.data.AnimalSize;
import de.stuff42.se2tierheimprojekt.data.AnimalType;
import de.stuff42.se2tierheimprojekt.entity.*;
import de.stuff42.se2tierheimprojekt.model.rest.AnswerModel;
import de.stuff42.se2tierheimprojekt.model.rest.QuestionModel;
import de.stuff42.se2tierheimprojekt.model.rest.ResultModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * Returns the next question for the given answer in the questionnaire.
     *
     * @param questionId ID of the answered question.
     * @param answerId   ID of the answer of the last question.
     *
     * @return Next question
     */
    public QuestionModel getNextforAnswer(long questionId, long answerId) {

        // TODO: Load next question based on answer and not only on sort order.

        QuestionEntity lastQuestionEntity = questionDAO.findOne(questionId);
        if (lastQuestionEntity == null) {
            return null;
        }
        QuestionEntity nextQuestionEntity = null;
        try {
            //TODO: fit that with an other way inside QuestionDAO
            nextQuestionEntity = questionDAO.getNextQuestion(lastQuestionEntity.sortOrder);
        }catch(IndexOutOfBoundsException e){}
        if (nextQuestionEntity == null) {
            return null;
        }

        return new QuestionModel(nextQuestionEntity);
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
     * Gets all answers of the Questionaire and returns the Evaluation.
     *
     * @param answers All selected answers of the questionaire
     *
     * @return
     */
    public ResultModel evaluateQuestionaire(Map<Long, List<Long>> answers) {
        List<AnswerEntity> answerList = new LinkedList<>();

        //Get all answers
        Iterator<Entry<Long, List<Long>>> iterator = answers.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<Long, List<Long>> entry = iterator.next();
            long questionId = entry.getKey();
            List<Long> answerIds = entry.getValue();
            Iterator<Long> answerIdIterator = answerIds.iterator();
            while (answerIdIterator.hasNext()) {
                long answerId = answerIdIterator.next();
                answerList.add(answerDAO.getAnswer(questionId, answerId));
            }
        }

        //Create and fill Result Lists
        List<AnimalType> animalType = new LinkedList<>();
        animalType.addAll(Arrays.asList(AnimalType.values()));
        List<AnimalCost> cost = new LinkedList<>();
        cost.addAll(Arrays.asList(AnimalCost.values()));
        List<AnimalSize> size = new LinkedList<>();
        size.addAll(Arrays.asList(AnimalSize.values()));
        boolean garden = true;
        boolean needSpecialCare = true;

        //Remove results
        Iterator<AnswerEntity> answerIterator = answerList.iterator();
        while (answerIterator.hasNext()) {
            AnswerEntity answer = answerIterator.next();

            //TODO Just marks likely error source
            animalType.removeAll(answer.animalType);
            cost.removeAll(answer.cost);
            size.removeAll(answer.animalSize);

            if (answer.garden) {
                garden = false;
            }
            if (answer.needCare) {
                needSpecialCare = false;
            }
        }

        return new ResultModel(animalDAO.getFittingAnimals(animalType, size, cost, needSpecialCare, garden));
    }
}
