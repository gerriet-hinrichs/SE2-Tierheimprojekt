package de.stuff42.se2tierheimprojekt.services;

import java.util.List;

import de.stuff42.se2tierheimprojekt.datatypes.AnswerInterface;
import de.stuff42.se2tierheimprojekt.datatypes.QuestionInterface;

//TODO
//Check if the name is ok
public interface AnswerServiceInterface {
 
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
