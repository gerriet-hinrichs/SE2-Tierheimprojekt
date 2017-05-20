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

import java.util.Arrays;
import java.util.Iterator;

import de.stuff42.se2tierheimprojekt.datatypes.Answer;
import de.stuff42.se2tierheimprojekt.datatypes.AnswerI;
import de.stuff42.se2tierheimprojekt.datatypes.Question;
import de.stuff42.se2tierheimprojekt.datatypes.QuestionI;
import de.stuff42.se2tierheimprojekt.entity.AnswerDAO;
import de.stuff42.se2tierheimprojekt.entity.QuestionEntity;
import de.stuff42.se2tierheimprojekt.entity.QuestionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService implements QuestionServiceI {

  @Autowired
  private AnswerDAO answerDAO;

  @Autowired
  private QuestionDAO questionDAO;

  @Override
  public QuestionI getFirstWithAnswers() {
      return getByIDWithAnswers(1);
  }

  @Override
  public QuestionI getByIDWithAnswers(int sortOrder) {
      // Find Question
      Iterator<QuestionEntity> iterator = questionDAO.findAll().iterator();
      QuestionEntity questionEntity = null;
      boolean found = false;
      while(iterator.hasNext() && !found){
          questionEntity = iterator.next();
          if(questionEntity.sortOrder == sortOrder){
              found = true;
          }
      }

      // If no Question found
      if(questionEntity == null){
          return null;
      }
      return new Question(questionEntity.sortOrder, questionEntity.text, getAnswersFromQuestionEntry(questionEntity));
  }

  @Override
  public QuestionI[] getList() {
      QuestionI[] questions = new QuestionI[(int) questionDAO.count()];
      Iterator<QuestionEntity> iterator = questionDAO.findAll().iterator();
      QuestionEntity questionEntity = null;

      int i = 0;
      while(iterator.hasNext()){
          questionEntity = iterator.next();
          questions[i] = new Question(questionEntity.sortOrder, questionEntity.text, getAnswersFromQuestionEntry(questionEntity));
          i++;
      }

      // If no Question found
      if(questionEntity == null){
          return null;
      }
      return questions;
  }

  @Override
  public QuestionI getNextforAnswer(int questionSortOrder, int answerSortOrder) {
      // TODO: Wo sollen die Informationen hie zu stehen, in der Antort in der DB oder woanders(entscheidungslogik)?
      return null;
  }

  @Override
  public AnswerI[] getAnswers(int questionSortOrder) {
      QuestionI question = getByIDWithAnswers(questionSortOrder);
      // If no Question found
      if(question == null){
          return null;
      }
      AnswerI[] answers = question.getAnswerObjects();
      Arrays.sort(answers);
      return answers;
  }

  private Answer[] getAnswersFromQuestionEntry(QuestionEntity questionEntity){/*
      // Get answers from Question
      AnswerEntry[] answerEntry = questionEntry.answers;
      Answer[] answers = new Answer[answerEntry.length];
      for (int i = 0; i < answerEntry.length; i++) {
          answers[i] = new Answer(answerEntry[i].sortOrder, answerEntry[i].text, answerEntry[i].question.sortOrder);
      }
      // Sort Questions
      Arrays.sort(answers);
      return answers;*/ return null;
  }
}
