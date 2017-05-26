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
package de.stuff42.se2tierheimprojekt.model.rest;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Question from Frontend towards Service/Database.
 */
public class EvaluationModel {

    /**
     * One or more animalType that possible for search.
     */
    @NotNull
    public List<String> animalType;

    /**
     * One or more size that possible for search.
     */
    @NotNull
    public List<String> size;

    /**
     * One or more cost that possible for search.
     */
    @NotNull
    public List<String> cost;

    /**
     * Need Care or not.
     */
    @NotNull
    public Boolean needCare;

    /**
     * Need garden or not.
     */
    @NotNull
    public Boolean garden;

    /**
     * Creates a Request
     * Spelling is important!
     * @param animalType dog, cat, bird, fish, reptile, hamster, bunny, guineaPig, mouse. (One or More)
     * @param size small, medium, huge. (One or More)
     * @param cost cheap, medium, expensive. (One or More)
     * @param needCare true, false.
     * @param garden true, false.
     */
    public EvaluationModel(@NotNull List<String> animalType, @NotNull List<String> size, @NotNull List<String> cost, @NotNull Boolean needCare, @NotNull Boolean garden) {
        this.animalType = animalType;
        this.size = size;
        this.cost = cost;
        this.needCare = needCare;
        this.garden = garden;
    }
}
