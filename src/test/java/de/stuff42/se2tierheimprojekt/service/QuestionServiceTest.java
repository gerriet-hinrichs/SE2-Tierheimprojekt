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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {Application.class}, initializers = {TestApplicationInitializer.class})
@Transactional
public class QuestionServiceTest {


    @Autowired private QuestionService questionService;
    @Autowired private DatabaseSetupService databaseSetupService;
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

    //@Test
    public void getFirstWithAnswers() {
        logger.info("getFirstWithAnswers");
        QuestionModel methodReturnValue = questionService.getFirstWithAnswers();
        logger.info("methodReturnValue: " + methodReturnValue.toString());
        assertNotNull(methodReturnValue);
    }

    //@Test
    public void getByIDWithAnswers() {
        logger.info("getByIDWithAnswers");
        QuestionModel methodReturnValue = questionService.getByIDWithAnswers(0L);
        logger.info("methodReturnValue: " + methodReturnValue.toString());
        assertNotNull(methodReturnValue);
    }

    //@Test
    public void getNextforAnswer() {
        logger.info("getNextForAnswer");
        QuestionModel methodReturnValue = questionService.getNextForAnswer(0L, 0L);
        logger.info("methodReturnValue: " + methodReturnValue.toString());
        assertNotNull(methodReturnValue);
    }

    //@Test
    public void getList() {
        logger.info("getList");
        List<QuestionModel> methodReturnValue = questionService.getList();
        logger.info("methodReturnValue: " + methodReturnValue.toString());
        assertNotNull(methodReturnValue);
        logger.info("ListContent: ");
        assertFalse(methodReturnValue.isEmpty());
        for (QuestionModel entry : methodReturnValue) {
            logger.info("  EntryContent: " + entry.toString());
            assertNotNull(entry);
        }
    }

    //@Test
    public void getAnswersForQuestion() {
        logger.info("getAnswersForQuestion");
        List<AnswerModel> methodReturnValue = questionService.getAnswersForQuestion(0);
        logger.info("methodReturnValue: " + methodReturnValue.toString());
        assertNotNull(methodReturnValue);
        logger.info("ListContent: ");
        assertFalse(methodReturnValue.isEmpty());
        for (AnswerModel entry : methodReturnValue) {
            logger.info("  EntryContent: " + entry.toString());
            assertNotNull(entry);
        }
    }

    @Test
    public void evaluateQuestionnaire(){
        logger.info("evaluateQuestionnaire");
        logger.info("Setup Database");
        databaseSetupService.clean();
        databaseSetupService.addQuestionWithAnswers("DummyQuestion, all or nothing?",
                new AnswerContent("Return nothing",
                        new HashSet<>(Arrays.asList(AnimalType.values())),
                        new HashSet<>(Arrays.asList(AnimalSize.values())),
                        new HashSet<>(Arrays.asList(AnimalCost.values())),
                        new HashSet<>(Arrays.asList(AnimalCareTyp.values())),
                        new HashSet<>(Arrays.asList(AnimalGardenSpace.values()))),
                new AnswerContent("Return all",
                        null,
                        null,
                        null,
                        null,
                        null)
        );
        databaseSetupService.addAnimal("DummyBunny", "DummyRace", AnimalSex.MALE, AnimalAge.MATURE, AnimalSpace.MEDIUM,
                AnimalType.BUNNY, AnimalSize.MEDIUM, AnimalCost.MEDIUM , AnimalCareTyp.NONE, AnimalGardenSpace.NONE);
        databaseSetupService.addAnimal("DummyCat", "DummyCat", AnimalSex.MALE, AnimalAge.MATURE, AnimalSpace.MEDIUM,
                AnimalType.CAT, AnimalSize.MEDIUM, AnimalCost.MEDIUM , AnimalCareTyp.NONE, AnimalGardenSpace.NONE);

        logger.info("Get dummy question");
        QuestionModel dummyQuestion = questionService.getFirstWithAnswers();
        assertNotNull(dummyQuestion);
        logger.info(dummyQuestion.text);

        logger.info("Get answers in dummy question");
        AnswerModel dummyAnswerNothing = dummyQuestion.answers.get(0);
        AnswerModel dummyAnswerAll = dummyQuestion.answers.get(1);
        assertNotNull(dummyAnswerNothing);
        assertNotNull(dummyAnswerAll);
        logger.info(dummyAnswerNothing.text);
        logger.info(dummyAnswerAll.text);

        logger.info("Create maps to evaluate from question and answers");
        Map<Long, List<Long>> mapNothing = new HashMap<>();
        List<Long> listNothing = new ArrayList<>();
        listNothing.add(dummyAnswerNothing.id);
        mapNothing.put(dummyQuestion.id, listNothing);

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

        logger.info("Result with answers");
        ResultModel resultAll = questionService.evaluateQuestionnaire(mapAll);
        assertNotNull(resultAll);
        assertNotNull(resultAll.foundAnimals);
        assertFalse(resultAll.foundAnimals.isEmpty());

        logger.info("Print Result");
        for (AnimalModel entry : resultAll.foundAnimals) {
            assertNotNull(entry);
            logger.info(entry.name);
        }
    }
}
