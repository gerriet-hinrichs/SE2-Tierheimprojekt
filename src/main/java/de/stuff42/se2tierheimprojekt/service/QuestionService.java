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
import java.util.stream.Collectors;

import de.stuff42.se2tierheimprojekt.data.*;
import de.stuff42.se2tierheimprojekt.entity.*;
import de.stuff42.se2tierheimprojekt.model.rest.AnswerModel;
import de.stuff42.se2tierheimprojekt.model.rest.QuestionModel;
import de.stuff42.se2tierheimprojekt.model.rest.ResultModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EnumType;

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
     * @return Next question
     */
    public QuestionModel getNextForAnswer(long questionId, long answerId) {

        // TODO: Load next question based on answer and not only on sort order.

        QuestionEntity lastQuestionEntity = questionDAO.findOne(questionId);
        if (lastQuestionEntity == null) {
            return null;
        }

        QuestionEntity nextQuestionEntity = questionDAO.getNextQuestion(lastQuestionEntity.sortOrder);
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
     * Gets all answers of the Questionnaire and returns the Evaluation.
     *
     * @param answers All selected answers of the questionnaire
     * @return Evaluation result.
     */
    public ResultModel evaluateQuestionnaire(Map<Long, List<Long>> answers) {
        //Create and fill Result Lists
        List<AnimalType> animalType = new LinkedList<>();
        animalType.addAll(Arrays.asList(AnimalType.values()));
        List<AnimalCost> cost = new LinkedList<>();
        cost.addAll(Arrays.asList(AnimalCost.values()));
        List<AnimalSize> size = new LinkedList<>();
        size.addAll(Arrays.asList(AnimalSize.values()));
        List<AnimalGardenSpace> garden = new LinkedList<>();
        garden.addAll(Arrays.asList(AnimalGardenSpace.values()));
        List<AnimalCareTyp> needSpecialCare = new LinkedList<>();
        needSpecialCare.addAll(Arrays.asList(AnimalCareTyp.values()));

            //Get all answers
        for (Entry<Long, List<Long>> entry : answers.entrySet()) {

            if(entry.getValue().size() == 1){
                AnswerEntity answerEntry = answerDAO.findOne(entry.getValue().get(0));
                    // Evaluate
                if (answerEntry.animalType != null) { animalType.removeAll(answerEntry.animalType); }
                if (answerEntry.cost       != null) { cost.removeAll(answerEntry.cost); }
                if (answerEntry.animalSize != null) { size.removeAll(answerEntry.animalSize); }
                if (answerEntry.garden     != null) { garden.removeAll(answerEntry.garden); }
                if (answerEntry.needCare   != null) { needSpecialCare.removeAll(answerEntry.needCare); }

            }else if(entry.getValue().size() > 1){
                // TODO: Buttontype check if different possibil
                Set<AnswerEntity> answerList = new HashSet<>((Collection<? extends AnswerEntity>) answerDAO.findAll(entry.getValue()));
                    // Evaluate
                // if (all AnswerEntities.EnumSet are not null)
                //     remove allEnumTyp.values from
                //         (get) all shared values from answers
                if (answerList.stream().allMatch(ele -> ele.animalType != null)){
                    animalType.removeAll(
                            evaluateHelper(
                                    AnimalType.values(),
                                    answerList.stream().map(ele -> ele.animalType).collect(Collectors.toList()))
                    );
                }
                if (answerList.stream().allMatch(ele -> ele.cost != null)){
                    cost.removeAll(
                            evaluateHelper(
                                    AnimalCost.values(),
                                    answerList.stream().map(ele -> ele.cost).collect(Collectors.toList()))
                    );
                }
                if (answerList.stream().allMatch(ele -> ele.animalSize != null)){
                    size.removeAll(
                            evaluateHelper(
                                    AnimalSize.values(),
                                    answerList.stream().map(ele -> ele.animalSize).collect(Collectors.toList()))
                    );
                }
                if (answerList.stream().allMatch(ele -> ele.garden != null)){
                    garden.removeAll(
                            evaluateHelper(
                                    AnimalGardenSpace.values(),
                                    answerList.stream().map(ele -> ele.garden).collect(Collectors.toList()))
                    );
                }
                if (answerList.stream().allMatch(ele -> ele.needCare != null)){
                    needSpecialCare.removeAll(
                            evaluateHelper(
                                    AnimalCareTyp.values(),
                                    answerList.stream().map(ele -> ele.needCare).collect(Collectors.toList()))
                    );
                }
            }else{
                logger.error("EvaluateQuestionnaire, Question without answers! " + questionDAO.findOne(entry.getKey()).toString());
            }
        }

        if (animalType.isEmpty() || cost.isEmpty() || size.isEmpty() || garden.isEmpty() || needSpecialCare.isEmpty()) {
            return new ResultModel(new LinkedList<>());
        }
        return new ResultModel(animalDAO.getFittingAnimals(animalType, size, cost, needSpecialCare, garden));
    }

    //TODO: ADD DOC!! && with interface?
    private <T> Set<T> evaluateHelper(T[] enumValues, List<Set<T>> attributeSets){
        Set<T> result = new HashSet<>(Arrays.asList(enumValues));
        for (Set<T> answer : attributeSets){
            result.retainAll(answer);
        }
        return result;
    }
}
