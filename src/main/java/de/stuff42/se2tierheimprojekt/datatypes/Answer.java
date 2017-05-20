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

public class Answer implements AnswerI{

    private final int sortOrder;
    private final String answerText;

    /**
     * Creates a answer.
     * @param sortOrder SortId  > 0.
     * @param answerText Text of questions.
     * @throws IllegalArgumentException if constructor arguments are illegal.
     */
    public Answer(int sortOrder, String answerText) throws IllegalArgumentException{
        if(sortOrder <= 0 || answerText == null){
            throw new IllegalArgumentException();
        }

        this.sortOrder = sortOrder;
        this.answerText = answerText;
    }

    @Override
    public int getSortOrder() {
        return sortOrder;
    }

    @Override
    public String getAnswerText() {
        return answerText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (sortOrder != answer.sortOrder) return false;
        return answerText.equals(answer.answerText);
    }

    @Override
    public int hashCode() {
        return sortOrder;
    }

    @Override
    public String toString() {
        return String.format(
                "<%s>[SortOrder=%d, AnswerText=%s]",
                this.getClass().getSimpleName(), sortOrder, answerText);
    }
}
