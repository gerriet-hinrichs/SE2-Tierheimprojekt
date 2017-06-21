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

import de.stuff42.se2tierheimprojekt.Application;
import de.stuff42.se2tierheimprojekt.configuration.TestApplicationInitializer;
import de.stuff42.se2tierheimprojekt.data.*;
import de.stuff42.se2tierheimprojekt.model.AnswerContent;
import de.stuff42.se2tierheimprojekt.model.rest.AnimalModel;
import de.stuff42.se2tierheimprojekt.model.rest.AnswerModel;
import de.stuff42.se2tierheimprojekt.model.rest.QuestionModel;
import de.stuff42.se2tierheimprojekt.model.rest.ResultModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {Application.class}, initializers = {TestApplicationInitializer.class})
@Transactional
public class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private DatabaseSetupService databaseSetupService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before
    public void before() {
        logger.info("--------------- Start Test ---------------");
    }

    @After
    public void after() {
        logger.info("--------------- End Test ---------------");
        logger.info("");
    }

    @Test
    public void questionConnection() {
        logger.info("questionConnection");
        assertNotNull(questionService);
    }

    @Test
    public void setupConnection() {
        logger.info("setupConnection");
        assertNotNull(databaseSetupService);
    }

    @Test
    public void getFirstWithAnswers() {
        logger.info("getFirstWithAnswers");

        logger.info("Setup Database");
        databaseSetupService.clean();
        databaseSetupService.addQuestionWithAnswers("Dummy question one", AnswerType.CHECKBOX,
                new AnswerContent("Dummy answer one, from Question one.",
                        null, null, null, null, null),
                new AnswerContent("Dummy answer two, from Question one.",
                        null, null, null, null, null));
        databaseSetupService.addQuestionWithAnswers("Dummy question two", AnswerType.CHECKBOX,
                new AnswerContent("Dummy answer one, from Question two.",
                        null, null, null, null, null),
                new AnswerContent("Dummy answer two, from Question two.",
                        null, null, null, null, null));

        logger.info("Get first Question with Answers");
        QuestionModel methodReturnValue = questionService.getFirstWithAnswers();
        assertNotNull(methodReturnValue);
        assertEquals("Dummy question one", methodReturnValue.text);
        logger.info("Question: " + methodReturnValue.text);

        assertNotNull(methodReturnValue.answers);
        assertEquals("Dummy answer one, from Question one.", methodReturnValue.answers.get(0).text);
        assertEquals("Dummy answer two, from Question one.", methodReturnValue.answers.get(1).text);
        logger.info("Answer one: " + methodReturnValue.answers.get(0).text);
        logger.info("Answer two: " + methodReturnValue.answers.get(1).text);
    }

    //@Test
    public void getByIDWithAnswers() {
        //TODO: Missing
        logger.info("getByIDWithAnswers");
        QuestionModel methodReturnValue = questionService.getByIDWithAnswers(0L);
        logger.info("methodReturnValue: " + methodReturnValue.toString());
        assertNotNull(methodReturnValue);
    }

    @Test
    public void getList() {
        logger.info("getList");

        logger.info("Setup Database");
        databaseSetupService.clean();
        databaseSetupService.addQuestionWithAnswers("Dummy question one", AnswerType.CHECKBOX,
                new AnswerContent("Dummy answer one, from Question one.",
                        null, null, null, null, null),
                new AnswerContent("Dummy answer two, from Question one.",
                        null, null, null, null, null));
        databaseSetupService.addQuestionWithAnswers("Dummy question two", AnswerType.CHECKBOX,
                new AnswerContent("Dummy answer one, from Question two.",
                        null, null, null, null, null),
                new AnswerContent("Dummy answer two, from Question two.",
                        null, null, null, null, null));
        databaseSetupService.addQuestionWithAnswers("Dummy question tree", AnswerType.CHECKBOX,
                new AnswerContent("Dummy answer one, from Question tree.",
                        null, null, null, null, null),
                new AnswerContent("Dummy answer two, from Question tree.",
                        null, null, null, null, null));

        logger.info("Get all questions");
        List<QuestionModel>  returnList = questionService.getList();
        assertNotNull(returnList);
        for(QuestionModel question : returnList){
            assertNotNull(question);
            logger.info("Question: " + question.text);
        }

        logger.info("Check number of returned quests");
        assertEquals(3, returnList.size());
    }

    //@Test
    public void getAnswersForQuestion() {
        logger.info("getAnswersForQuestion");

        logger.info("Setup Database");
        databaseSetupService.clean();
        databaseSetupService.addQuestionWithAnswers("Dummy question one", AnswerType.CHECKBOX,
                new AnswerContent("Dummy answer one, from Question one.",
                        null, null, null, null, null),
                new AnswerContent("Dummy answer two, from Question one.",
                        null, null, null, null, null));
        databaseSetupService.addQuestionWithAnswers("Dummy question two", AnswerType.CHECKBOX,
                new AnswerContent("Dummy answer one, from Question two.",
                        null, null, null, null, null),
                new AnswerContent("Dummy answer two, from Question two.",
                        null, null, null, null, null));
        databaseSetupService.addQuestionWithAnswers("Dummy question tree", AnswerType.CHECKBOX,
                new AnswerContent("Dummy answer one, from Question tree.",
                        null, null, null, null, null),
                new AnswerContent("Dummy answer two, from Question tree.",
                        null, null, null, null, null));

        logger.info("Get all questions");
        List<QuestionModel>  returnList = questionService.getList();
        assertNotNull(returnList);
        for(QuestionModel question : returnList){
            assertNotNull(question);
            logger.info("Question: " + question.text);
        }

        //TODO: Missing
        /*
        logger.info("Get dummy questions");
        for(QuestionModel question : returnList){
            logger.info(questionService.getAnswersForQuestion(question.id).toString());
            logger.info(question.answers.toString());
            assertEquals(questionService.getAnswersForQuestion(question.id), question.answers);
        }
        */
    }

    @Test
    public void evaluateQuestionnaire() {
        logger.info("evaluateQuestionnaire");
        // TODO: negativ checks, maybe split checks
        logger.info("Setup Database");
        databaseSetupService.clean();
        databaseSetupService.addQuestionWithAnswers("Dummy question, all or nothing?", AnswerType.CHECKBOX,
                new AnswerContent("Dummy answer, return nothing.",
                        new HashSet<>(Arrays.asList(AnimalType.values())),
                        new HashSet<>(Arrays.asList(AnimalSize.values())),
                        new HashSet<>(Arrays.asList(AnimalCost.values())),
                        new HashSet<>(Arrays.asList(AnimalCareTyp.values())),
                        new HashSet<>(Arrays.asList(AnimalGardenSpace.values()))),
                new AnswerContent("Dummy answer, return half.",
                        new HashSet<>(Collections.singletonList(AnimalType.CAT)),
                        null,
                        null,
                        null,
                        null),
                new AnswerContent("Dummy answer, return all.",
                        null,
                        null,
                        null,
                        null,
                        null)
        );
        databaseSetupService.addAnimal("DummyBunny", "DummyRace", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.MEDIUM,
                AnimalType.BUNNY, AnimalSize.MEDIUM, AnimalCost.MEDIUM, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, "");
        databaseSetupService.addAnimal("DummyCat", "DummyCat", AnimalSex.MALE, AnimalAge.MATURE, AnimalGardenSpace.MEDIUM,
                AnimalType.CAT, AnimalSize.MEDIUM, AnimalCost.MEDIUM, AnimalCareTyp.NONE, AnimalGardenSpace.NONE, "");

        logger.info("Get dummy question");
        QuestionModel dummyQuestion = questionService.getFirstWithAnswers();
        assertNotNull(dummyQuestion);
        logger.info(dummyQuestion.text);

        logger.info("Get answers in dummy question");
        AnswerModel dummyAnswerNothing = dummyQuestion.answers.get(0);
        AnswerModel dummyAnswerHalf = dummyQuestion.answers.get(1);
        AnswerModel dummyAnswerAll = dummyQuestion.answers.get(2);
        assertNotNull(dummyAnswerNothing);
        assertNotNull(dummyAnswerHalf);
        assertNotNull(dummyAnswerAll);
        logger.info(dummyAnswerNothing.text);
        logger.info(dummyAnswerHalf.text);
        logger.info(dummyAnswerAll.text);

        logger.info("Create maps to evaluate from question and answers");
        Map<Long, List<Long>> mapNothing = new HashMap<>();
        List<Long> listNothing = new ArrayList<>();
        listNothing.add(dummyAnswerNothing.id);
        mapNothing.put(dummyQuestion.id, listNothing);

        Map<Long, List<Long>> mapHalf = new HashMap<>();
        List<Long> listHalf = new ArrayList<>();
        listHalf.add(dummyAnswerHalf.id);
        listHalf.add(dummyAnswerNothing.id);
        mapHalf.put(dummyQuestion.id, listHalf);

        Map<Long, List<Long>> mapAll = new HashMap<>();
        List<Long> listAll = new ArrayList<>();
        listAll.add(dummyAnswerAll.id);
        mapAll.put(dummyQuestion.id, listAll);

        logger.info("GetResults");
        logger.info("Result with no answers");
        ResultModel resultNothing = questionService.evaluateQuestionnaire(mapNothing);
        assertNotNull(resultNothing);
        assertNotNull(resultNothing.foundAnimals);
        assertTrue(resultNothing.foundAnimals.isEmpty());

        logger.info("Result with half answers");
        ResultModel resultHalf = questionService.evaluateQuestionnaire(mapHalf);
        assertNotNull(resultHalf);
        assertNotNull(resultHalf.foundAnimals);
        assertEquals(1, resultHalf.foundAnimals.size());

        logger.info("Print half Result");
        for (AnimalModel entry : resultHalf.foundAnimals) {
            assertNotNull(entry);
            logger.info(entry.name);
        }

        logger.info("Result with answers");
        ResultModel resultAll = questionService.evaluateQuestionnaire(mapAll);
        assertNotNull(resultAll);
        assertNotNull(resultAll.foundAnimals);
        assertEquals(2, resultAll.foundAnimals.size());

        logger.info("Print Result");
        for (AnimalModel entry : resultAll.foundAnimals) {
            assertNotNull(entry);
            logger.info(entry.name);
        }
    }
}
