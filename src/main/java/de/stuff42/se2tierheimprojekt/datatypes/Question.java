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
package de.stuff42.se2tierheimprojekt.datatypes;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Question implements QuestionI, Comparable {

    private final int sortOrder;
    private final String questionText;
    private final AnswerI[] answers;

    /**
     * Creates a question with all answers.
     * Answers will be created automated.
     * @param sortOrder SortId  > 0.
     * @param questionText Text of questions.
     * @param answersText Array of all answers text, first position = first answer ... , must have at least one.
     * @throws IllegalArgumentException if constructor arguments are illegal.
     */
    public Question(int sortOrder, String questionText, String[] answersText) throws IllegalArgumentException{
        if(sortOrder <= 0 || questionText == null || answersText == null){
            throw new IllegalArgumentException();
        }

        this.sortOrder = sortOrder;
        this.questionText = questionText;
        answers = new Answer[answersText.length];

        for (int i = 0; i < answersText.length; i++) {
            if(answersText[i] != null){
                throw new IllegalArgumentException();
            }
            answers[i] = new Answer(i+1, answersText[i], sortOrder);
        }
    }

    /**
     * Creates a question with all answers.
     * @param sortOrder SortId  > 0.
     * @param questionText Text of questions.
     * @param answers Array of all answers text, first position = answer sortOrder 1 ... , must have at least one.
     * @throws IllegalArgumentException if constructor arguments are illegal.
     */
    public Question(int sortOrder, String questionText, AnswerI[] answers) throws IllegalArgumentException{
        if(sortOrder <= 0 || questionText == null || answers == null){
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < answers.length; i++) {
            if(answers[i] != null || sortOrder != answers[i].getQuestionSortOrder() || answers[i].getSortOrder() != i ){
                throw new IllegalArgumentException();
            }
        }

        this.sortOrder = sortOrder;
        this.questionText = questionText;
        this.answers = answers;
    }

    @Override
    public int getSortOrder() {
        return sortOrder;
    }

    @Override
    public String getQuestionText() {
        return questionText;
    }

    @Override
    public AnswerI[] getAnswerObjects() {
        return answers;
    }

    @Override
    public String[] getAnswerText() {
        String[] answerTextArray = new String[answers.length];
        for (int i = 0; i < answers.length; i++) {
            answerTextArray[i] = answers[i].getAnswerText();
        }
        return answerTextArray;
    }

    @Override
    public int compareTo(@NotNull Object other) {
        Question otherQuestion = (Question)other;

        if(this.sortOrder > otherQuestion.sortOrder){
            return 1;
        }
        if(this.sortOrder < otherQuestion.sortOrder){
            return -1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (sortOrder != question.sortOrder) return false;
        if (!questionText.equals(question.questionText)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(answers, question.answers);
    }

    @Override
    public int hashCode() {
        int result = sortOrder;
        result = 31 * result + questionText.hashCode();
        result = 31 * result + Arrays.hashCode(answers);
        return result;
    }

    @Override
    public String toString() {
        return String.format(
                "<%s>[SortOrder=%d, QuestionText =%s, AnswersText=%s]",
                this.getClass().getSimpleName(), sortOrder, questionText, Arrays.toString(getAnswerText()));
    }
}
