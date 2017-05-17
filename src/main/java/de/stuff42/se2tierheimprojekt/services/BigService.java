package de.stuff42.se2tierheimprojekt.services;

import de.stuff42.se2tierheimprojekt.db.AnswerTable;
import de.stuff42.se2tierheimprojekt.db.QuestionTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BigService implements BigServiceI{

    @Autowired
    private QuestionTable questionTable;

    @Autowired
    private AnswerTable answerTable;

    //------------- Questions ------------
    @Override
    public QuestionTable getFirstWithAnswers() {
        return null;
    }

    @Override
    public QuestionTable getByIDWithAnswers(long id) {
        return null;
    }

    @Override
    public List<QuestionTable> getList() {
        return null;
    }

    @Override
    public boolean createQuestion(QuestionTable data) {
        return false;
    }

    @Override
    public boolean updateQuestion(QuestionTable data) {
        return false;
    }

    @Override
    public boolean deleteQuestion(long id) {
        return false;
    }

    //------------- Answers ------------
    @Override
    public List<AnswerTable> getAnswers(long questionID) {
        return null;
    }

    @Override
    public boolean createAnswer(AnswerTable data) {
        return false;
    }

    @Override
    public boolean updateAnswer(AnswerTable data) {
        return false;
    }

    @Override
    public boolean deleteAnswer(long id) {
        return false;
    }

    //------------- Evaluation ------------
    @Override
    public long getNextforAnswer(long questionID, long answerID) {
        return 0;
    }
}
