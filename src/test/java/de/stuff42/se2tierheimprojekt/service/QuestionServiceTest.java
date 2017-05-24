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

import de.stuff42.se2tierheimprojekt.Application;
import de.stuff42.se2tierheimprojekt.configuration.TestApplicationInitializer;
import de.stuff42.se2tierheimprojekt.model.rest.AnswerModel;
import de.stuff42.se2tierheimprojekt.model.rest.QuestionModel;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {Application.class}, initializers = {TestApplicationInitializer.class})
@Transactional
public class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeClass
    public static void setUpClass(){
        // TODO: Connect to Database (Maybe don't need to make a new Application)
        // TODO: Fill Database with Dummy's
    }

    @AfterClass
    public static void cleanUpClass(){
        // TODO: Clean Database (if Transactional don't makes it)
    }

    @Before
    public void before(){
        logger.info("--------------- Start Test ---------------");
    }

    @After
    public void after(){
        logger.info("--------------- End Test ---------------");
        logger.info("");
    }

    @Test
    public void getFirstWithAnswers() {
        logger.info("getFirstWithAnswers");
        QuestionModel methodReturnValue = questionService.getFirstWithAnswers();
        logger.info("methodReturnValue: " + methodReturnValue.toString());
        assertNotNull(methodReturnValue);
    }

    @Test
    public void getByIDWithAnswers() {
        logger.info("getByIDWithAnswers");
        QuestionModel methodReturnValue = questionService.getByIDWithAnswers(0L);
        logger.info("methodReturnValue: " + methodReturnValue.toString());
        assertNotNull(methodReturnValue);
    }

    @Test
    public void getNextforAnswer() {
        logger.info("getNextforAnswer");
        QuestionModel methodReturnValue = questionService.getNextforAnswer(0L, 0L);
        logger.info("methodReturnValue: " + methodReturnValue.toString());
        assertNotNull(methodReturnValue);
    }

    @Test
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

    @Test
    public void getAnswersForQuestion() {
        logger.info("getAnswersForQuestion");
        List<AnswerModel> methodReturnValue = questionService.getAnswersForQuestion( 0);
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