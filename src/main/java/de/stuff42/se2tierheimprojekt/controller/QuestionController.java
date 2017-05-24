package de.stuff42.se2tierheimprojekt.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.stuff42.se2tierheimprojekt.model.rest.AnswerModel;
import de.stuff42.se2tierheimprojekt.model.rest.QuestionModel;
import de.stuff42.se2tierheimprojekt.service.QuestionService;

@RestController
public class QuestionController extends BaseController<QuestionService> {
  
  @Autowired
  public QuestionController(QuestionService serviceInstance) {
    super(serviceInstance);
  }
  

  /**
   * Returns the first question of the questionnaire with all of its answers.
   *
   * @return Returns the first question or null if there isn't a first question.
   */
  @RequestMapping (value="/Question/first", method=RequestMethod.GET)
  public QuestionModel getFirstWithAnswers() {
    return service.getFirstWithAnswers();
  }

  /**
   * Returns the question with the ID and all of its answers.
   *
   * @param id the ID of the wanted question.
   *
   * @return the question with the ID or null if there is no question with this ID.
   */
  @RequestMapping (value="/Question/{ID}", method=RequestMethod.GET)
  public QuestionModel getByIDWithAnswers(@PathVariable("ID") int id) {
    logger.info("Get Question "+id);  
    return service.getByIDWithAnswers(id);
  }

  /**
   * Returns the next question for the given answer in the questionnaire.
   *
   * @param questionId ID of the answered question.
   * @param answerId   ID of the answer of the last question.
   *
   * @return Next question
   */
  @RequestMapping (value="/Question/answer", method=RequestMethod.POST)
  public QuestionModel getNextforAnswer(int questionId, int answerId) {
    return service.getNextforAnswer(questionId, answerId);
  }

  /**
   * Returns a list with all questions of the questionnaire.
   *
   * @return List with all questions.
   */
  @RequestMapping (value="/Questions", method=RequestMethod.GET)
  public List<QuestionModel> getList() {
      return service.getList();
  }

  /**
   * Returns a List with all answers of the question with the specified ID
   *
   * @param questionId ID of the question
   *
   * @return List with all answers of the question or null if the question doesn't exist
   */
  @RequestMapping (value="Answers/{QuestionID}", method=RequestMethod.GET)
  public List<AnswerModel> getAnswersForQuestion(@PathVariable("QuestionID") int questionId) {
      return service.getAnswersForQuestion(questionId);
  }

}
