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

import java.util.Arrays;

public class Question implements QuestionI{

    private final int sortOrder;
    private final String questionText;
    private final AnswerI[] answers;

    /**
     * Creates a question with all answers.
     * @param sortOrder SortId  > 0.
     * @param questionText Text of questions.
     * @param answers Array of all answers, needs to be sorted by lowest sortOrder firs.
     * @throws IllegalArgumentException if constructor arguments are illegal.
     */
    public Question(int sortOrder, String questionText, AnswerI[] answers) throws IllegalArgumentException{
        if(sortOrder <= 0 || questionText == null || answers == null){
            throw new IllegalArgumentException();
        }

        this.sortOrder = sortOrder;
        this.questionText = questionText;
        this.answers = answers;

        if (answers.length > 1) {
            for (int i = 1; i < answers.length; i++) {
                if (answers[i - 1].getSortOrder() >= answers[i].getSortOrder()) {
                    throw new IllegalArgumentException();
                }
            }
        }
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
    public int hashCode() { return sortOrder; }

    @Override
    public String toString() {
        return String.format(
                "<%s>[SortOrder=%d, QuestionText =%s, AnswersText=%s]",
                this.getClass().getSimpleName(), sortOrder, questionText, Arrays.toString(getAnswerText()));
    }
}
