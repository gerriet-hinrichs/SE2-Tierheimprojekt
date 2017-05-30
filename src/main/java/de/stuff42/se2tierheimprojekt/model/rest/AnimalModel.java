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

import de.stuff42.se2tierheimprojekt.data.AnimalAge;
import de.stuff42.se2tierheimprojekt.data.AnimalSex;
import de.stuff42.se2tierheimprojekt.data.AnimalSpace;

import org.jetbrains.annotations.NotNull;

/**
 * An single Animal with his attributes.
 */
public class AnimalModel {

    @NotNull
    public String name;

    @NotNull
    public String race;

    @NotNull
    public AnimalSex sex;

    public AnimalAge age;

    @NotNull
    public AnimalSpace requiredSpace;

    /**
     * An single Animal.
     * Empty String or Text.
     * @param name <""> or <attributeText>.
     * @param race <""> or <attributeText>.
     * @param sex <""> or <attributeText>.
     * @param age <""> or <attributeText>.
     * @param requiredSpace <""> or <attributeText>.
     */
    public AnimalModel(@NotNull String name, @NotNull String race, @NotNull AnimalSex sex, AnimalAge age, @NotNull AnimalSpace requiredSpace) {
        this.name = name;
        this.race = race;
        this.sex = sex;
        this.age = age;
        this.requiredSpace = requiredSpace;
    }
}
