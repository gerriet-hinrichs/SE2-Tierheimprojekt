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

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {Application.class}, initializers = {TestApplicationInitializer.class})
@Transactional
public class QuestionServiceTest {

    @Autowired
    private DatabaseSetupService databaseSetupService;

    @Autowired
    private QuestionService questionService;

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
        assertNotNull(questionService);
    }

    @Test
    public void setupConnection() {
        assertNotNull(databaseSetupService);
    }

    @Test
    public void superBasicTest() {
        logger.info("Beginn");

        logger.info("DatabaseAction");
        databaseSetupService.clean();
        databaseSetupService.setup();

        logger.info("Get First Question");
        Map<Long, List<Long>> answers = new HashMap<>();
        QuestionModel qm = questionService.getFirstWithAnswers();
        AnswerModel am;
        boolean end = false;

        logger.info("Question/Answer Loop");
        while (!end) {
            logger.info(qm.text);
            logger.info(qm.answers.get(qm.answers.size() - 1).text);

            am = qm.answers.get(qm.answers.size() - 1);
            Long qId = qm.id;
            Long aId = am.id;
            answers.put(qId, make(aId));
            qm = questionService.getNextForAnswer(qId, aId);
            if (qm == null) {
                end = true;
            }
        }

        logger.info("GetResult");
        ResultModel model = questionService.evaluateQuestionnaire(answers);

        logger.info("Print Result");
        for (AnimalModel entry : model.foundAnimals) {
            logger.info(entry.name);
        }
    }

    private List<Long> make(Long... listContend) {
        List<Long> list = new LinkedList<>();
        Collections.addAll(list, listContend);
        return list;
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
}
