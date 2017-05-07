package de.stuff42.se2tierheimprojekt.services;

//TODO
//Check if the name is ok
public interface EvaluationServiceInterface {
  //TODO
  //Check if the ID is really demanded or if a question object has to be returned
  /**
   * Returns the next question for the given answer in the questionnaire
   * @param questionID ID of the answered question
   * @param answerID ID of the answer of the last question
   * @return ID of the next question
   */
  public long getNextforAnswer(long questionID, long answerID);
}
