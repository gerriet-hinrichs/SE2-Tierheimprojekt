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
package de.stuff42.se2tierheimprojekt.services;

import java.util.List;

import de.stuff42.se2tierheimprojekt.datatypes.AnswerInterface;
import de.stuff42.se2tierheimprojekt.datatypes.QuestionInterface;

//TODO
//Check if the name is ok
public interface AnswerService {
 
  /**
   * Returns a List with all answers of the question with the specified ID
   * @param questionID ID of the question
   * @return List with all answers of the question or null if the question doesn't exist
   */
  public List<AnswerInterface> getAnswers(long questionID);
  
  /**
   * Creates a new answer in the database
   * @param data to be created answer
   * @return true if answer was successfully created if not false
   */
  public boolean create(QuestionInterface data);
  
  /**
   * Updates an existing answer in the database, which is equal to data
   * @param data 
   * @return true if update was successful
   */
  public boolean update(QuestionInterface data);
  
  /**
   * Deletes the answer with the ID from the database
   * @param id the id of the to be deleted question
   * @return true if answer was successfully deleted
   */
  public boolean delete(long id);
}
