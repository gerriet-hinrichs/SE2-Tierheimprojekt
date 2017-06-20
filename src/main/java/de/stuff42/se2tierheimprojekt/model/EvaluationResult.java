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
package de.stuff42.se2tierheimprojekt.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.stuff42.se2tierheimprojekt.data.*;
import de.stuff42.se2tierheimprojekt.entity.AnswerEntity;

/**
 * Evaluation result class.
 */
public class EvaluationResult {

    /**
     * Animal types.
     */
    public List<AnimalType> animalTypes = new ArrayList<>();

    /**
     * Animal costs.
     */
    public List<AnimalCost> animalCosts = new ArrayList<>();

    /**
     * Animal sizes.
     */
    public List<AnimalSize> animalSizes = new ArrayList<>();

    /**
     * Animal garden space values.
     */
    public List<AnimalGardenSpace> animalGardenSpaces = new ArrayList<>();

    /**
     * Animal care types.
     */
    public List<AnimalCareTyp> animalCareTypes = new ArrayList<>();

    /**
     * Creates new empty (or completely filled) evaluation result.
     *
     * @param fillWithData Flag if properties should be filled with all existing options.
     */
    public EvaluationResult(boolean fillWithData) {
        if (fillWithData) {

            // the list returned from Arrays.asList is not modifiable, so we need this here
            animalTypes.addAll(Arrays.asList(AnimalType.values()));
            animalCosts.addAll(Arrays.asList(AnimalCost.values()));
            animalSizes.addAll(Arrays.asList(AnimalSize.values()));
            animalGardenSpaces.addAll(Arrays.asList(AnimalGardenSpace.values()));
            animalCareTypes.addAll(Arrays.asList(AnimalCareTyp.values()));
        }
    }

    /**
     * Adds the given answer to this result.
     * This operation performs a union on its own and the answer's properties.
     *
     * @param answer Answer to be added.
     */
    public void add(AnswerEntity answer) {
        if (answer.animalType != null) {
            animalTypes.addAll(answer.animalType);
        }

        if (answer.cost != null) {
            animalCosts.addAll(answer.cost);
        }

        if (answer.animalSize != null) {
            animalSizes.addAll(answer.animalSize);
        }

        if (answer.garden != null) {
            animalGardenSpaces.addAll(answer.garden);
        }

        if (answer.needCare != null) {
            animalCareTypes.addAll(answer.needCare);
        }
    }

    /**
     * Adds the given answer to this result.a
     * This operation performs an intersection on its onw and the answer's properties.
     *
     * @param answer Answer this result will be intersected with.
     */
    public void intersect(AnswerEntity answer) {
        if (answer.animalType != null) {
            animalTypes.retainAll(answer.animalType);
        }

        if (answer.cost != null) {
            animalCosts.retainAll(answer.cost);
        }

        if (answer.animalSize != null) {
            animalSizes.retainAll(answer.animalSize);
        }

        if (answer.garden != null) {
            animalGardenSpaces.retainAll(answer.garden);
        }

        if (answer.needCare != null) {
            animalCareTypes.retainAll(answer.needCare);
        }
    }

    /**
     * Removes all properties found within the given answer.
     *
     * @param answer Answer instance.
     */
    public void remove(AnswerEntity answer) {
        if (answer.animalType != null) {
            animalTypes.removeAll(answer.animalType);
        }

        if (answer.cost != null) {
            animalCosts.removeAll(answer.cost);
        }

        if (answer.animalSize != null) {
            animalSizes.removeAll(answer.animalSize);
        }

        if (answer.garden != null) {
            animalGardenSpaces.removeAll(answer.garden);
        }

        if (answer.needCare != null) {
            animalCareTypes.removeAll(answer.needCare);
        }
    }

    /**
     * Removes all properties found within the given evaluation result.
     *
     * @param other Evaluation result instance.
     */
    public void remove(EvaluationResult other) {
        animalTypes.removeAll(other.animalTypes);
        animalCosts.removeAll(other.animalCosts);
        animalSizes.removeAll(other.animalSizes);
        animalGardenSpaces.removeAll(other.animalGardenSpaces);
        animalCareTypes.removeAll(other.animalCareTypes);
    }

    /**
     * Returns if this result is empty.
     * Returns <code>false</code> if any of its property sets is empty.
     *
     * @return If this evaluation result is empty.
     */
    public boolean isEmpty() {
        return animalTypes.isEmpty()
                || animalCosts.isEmpty()
                || animalSizes.isEmpty()
                || animalGardenSpaces.isEmpty()
                || animalCareTypes.isEmpty();
    }
}
