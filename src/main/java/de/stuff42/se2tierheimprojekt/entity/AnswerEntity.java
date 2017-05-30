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
package de.stuff42.se2tierheimprojekt.entity;

import java.util.Set;
import javax.persistence.*;

import de.stuff42.se2tierheimprojekt.data.AnimalCost;
import de.stuff42.se2tierheimprojekt.data.AnimalSize;
import de.stuff42.se2tierheimprojekt.data.AnimalType;

import org.jetbrains.annotations.NotNull;

@Entity
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public int sortOrder;

    public String text;

    @ManyToOne
    @JoinColumn
    public QuestionEntity question;

    // Search properties
    @ElementCollection
    public Set<AnimalType> animalType;

    @ElementCollection
    public Set<AnimalSize> animalSize;

    @ElementCollection
    public Set<AnimalCost> cost;

    public boolean needCare;

    public boolean garden;

    protected AnswerEntity() {
        // no-args constructor required by JPA spec
        // this one is protected since it shouldn't be used directly
    }

    /**
     * Creates an answer for a Question.
     * And adds attributes for evaluate.
     *
     * @param sortOrder  for sorting answers.
     * @param text       text of answer.
     * @param question   the question from the answer.
     * @param animalType Null or properties, that are sorted out by evaluating.
     * @param animalSize Null or properties, that are sorted out by evaluating.
     * @param cost       Null or properties, that are sorted out by evaluating.
     * @param needCare   true to be sorted out.
     * @param garden     true to be sorted out.
     */
    public AnswerEntity(int sortOrder, String text, QuestionEntity question,
                        @NotNull Set<AnimalType> animalType, @NotNull Set<AnimalSize> animalSize, @NotNull Set<AnimalCost> cost, boolean needCare, boolean garden) {
        this.animalType = animalType;
        this.animalSize = animalSize;
        this.cost = cost;
        this.needCare = needCare;
        this.garden = garden;
        this.sortOrder = sortOrder;
        this.text = text;
        this.question = question;
    }
}
