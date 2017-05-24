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

import de.stuff42.se2tierheimprojekt.entity.AnswerDAO;
import de.stuff42.se2tierheimprojekt.entity.AnswerEntity;
import de.stuff42.se2tierheimprojekt.entity.QuestionDAO;
import de.stuff42.se2tierheimprojekt.entity.QuestionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DataBaseFillService extends BaseService {

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private AnswerDAO answerDAO;

    /**
     *
     * @param questionSortOrder SortOrder of Question.
     * @param questionText Test of Question.
     * @param answers Integer = AnswerSortOrder, String = AnswerText.
     */
    public void addQuestionWithAnswers(int questionSortOrder, String questionText, Map<Integer ,String> answers){
        // TODO: safety checks
        QuestionEntity question = new QuestionEntity( questionSortOrder, questionText);
        questionDAO.save(question);
        for (Map.Entry<Integer, String> entry : answers.entrySet()) {
            AnswerEntity answer = new AnswerEntity( entry.getKey(), entry.getValue(), question);
            answerDAO.save(answer);
        }
    }
    /*
    public void addQuestion(int sortOrder, String text){
        QuestionEntity entry = new QuestionEntity( sortOrder, text);
        questionDAO.save(entry);
    }

    public void addAnswer(int answerSortOrder, String answerText, int questionSortOrder, String questionText){
        // TODO: if Question exists add answer
        AnswerEntity entry = new AnswerEntity( answerSortOrder, answerText, new QuestionEntity( questionSortOrder, questionText));
        answerDAO.save(entry);
    }
    */
}
