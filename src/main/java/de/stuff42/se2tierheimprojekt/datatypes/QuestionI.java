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

/**
 * This class stores an question and all answers for it.
 */
public interface QuestionI {

    /**
     * Getter for the SortId
     * @return SortOrder.
     */
    int getSortOrder();

    /**
     * Getter for the text of the question.
     * @return QuestionText.
     */
    String getQuestionText();

    /**
     * Gives a array over all answers from these question.
     * The answers are sorted by there SortOrder. (Lowest first)
     * @return All answers objects of these question.
     */
    AnswerI[] getAnswerObjects();

    /**
     * Gives a array over all answers from these question.
     * The answers are sorted by there SortOrder. (Lowest first)
     * @return All answers strings of these question.
     */
    String[] getAnswerText();

}
