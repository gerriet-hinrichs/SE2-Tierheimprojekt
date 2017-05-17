package de.stuff42.se2tierheimprojekt.services;

import de.stuff42.se2tierheimprojekt.db.AnswerTable;
import de.stuff42.se2tierheimprojekt.db.QuestionTable;

import java.util.List;

public interface BigServiceI {

    //------------- Questions ------------

    /**
     * Returns the first question of the questionnaire
     * with all of its answers.
     * @return Returns the first question or null if there isn't a first question
     */
    public QuestionTable getFirstWithAnswers();

    /**
     * Returns the question with the ID and all of its answers
     * @param id the ID of the wanted question
     * @return the question with the ID or null if there is no question with this ID
     */
    public QuestionTable getByIDWithAnswers(long id);

    /**
     * Returns a list with all questions of the questionnaire
     * @return List with all questions
     */
    public List<QuestionTable> getList();

    /**
     * Creates a new question in the database
     * @param data to be created question
     * @return true if question was successfully created if not false
     */
    public boolean createQuestion(QuestionTable data);

    /**
     * Updates an existing question in the database, which is equal to data
     * @param data
     * @return true if update was successful
     */
    public boolean updateQuestion(QuestionTable data);

    /**
     * Deletes the question with the ID from the database
     * @param id the id of the to be deleted question
     * @return true if question was successfully deleted
     */
    public boolean deleteQuestion(long id);

    //------------- Answers ------------

    /**
            * Returns a List with all answers of the question with the specified ID
   * @param questionID ID of the question
   * @return List with all answers of the question or null if the question doesn't exist
            */
    public List<AnswerTable> getAnswers(long questionID);

    /**
     * Creates a new answer in the database
     * @param data to be created answer
     * @return true if answer was successfully created if not false
     */
    public boolean createAnswer(AnswerTable data);

    /**
     * Updates an existing answer in the database, which is equal to data
     * @param data
     * @return true if update was successful
     */
    public boolean updateAnswer(AnswerTable data);

    /**
     * Deletes the answer with the ID from the database
     * @param id the id of the to be deleted question
     * @return true if answer was successfully deleted
     */
    public boolean deleteAnswer(long id);

    //------------- Evaluation ------------

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
