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

import java.util.List;

import de.stuff42.se2tierheimprojekt.datatypes.AnswerI;
import de.stuff42.se2tierheimprojekt.datatypes.QuestionI;

public interface QuestionServiceI {
  /**
   * Returns the first question of the questionnaire
   * with all of its answers.
   * @return Returns the first question or null if there isn't a first question
   */
  public QuestionI getFirstWithAnswers();
  
  /**
   * Returns the question with the ID and all of its answers
   * @param sortOrder the ID of the wanted question
   * @return the question with the ID or null if there is no question with this ID
   */
  public QuestionI getByIDWithAnswers(int sortOrder);
  
  /**
   * Returns a list with all questions of the questionnaire
   * @return List with all questions
   */
  public QuestionI[] getList();
  
  /**
   * Returns the next question for the given answer in the questionnaire
   * @param questionSortOrder ID of the answered question
   * @param answerSortOrder ID of the answer of the last question
   * @return ID of the next question
   */
  public QuestionI getNextforAnswer(int questionSortOrder, int answerSortOrder);
  
  /**
   * Returns a List with all answers of the question with the specified ID
   * @param questionSortOrder ID of the question
   * @return List with all answers of the question or null if the question doesn't exist
   */
  public AnswerI[] getAnswers(int questionSortOrder);

}
