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

import java.util.ArrayList;
import java.util.List;

import de.stuff42.se2tierheimprojekt.entity.AnimalEntity;

import org.jetbrains.annotations.NotNull;

/**
 * Creates a return from Service/Database towards Frontend.
 */
public class ResultModel {

    /**
     * List of all results from request.
     */
    @NotNull
    public List<AnimalModel> foundAnimals;

    /**
     * All result Animals.
     *
     * @param foundAnimals list of AnimalEntity from Database.
     */
    public ResultModel(@NotNull List<AnimalEntity> foundAnimals) {
        List<AnimalModel> animals = new ArrayList<>();

        for (AnimalEntity entry : foundAnimals) {
            animals.add(new AnimalModel(entry.id, entry.name, entry.race, entry.sex, entry.age, entry.requiredSpace, entry.picturePath));
        }

        this.foundAnimals = animals;
    }

    /**
     * Default constructor needed due to JSON de-serializing
     */
    public ResultModel() {}
}
