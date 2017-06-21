/*
 * Application to help putting pets from animal shelter across.
 * Copyright (C) 2017
 *     Felix Koch <code>felix.koch@haw-hamburg.de</code>,
 *     Kristian Exss <code>Kristian.Exss@HAW-Hamburg.de</code>,
 *     Adrian Bostelmann <code>Adrian.Bostelmann@HAW-Hamburg.de</code>,
 *     Karsten Boehringer <code>Karsten.Boehringer@HAW-Hamburg.de</code>,
 *     Gehui Xu <code>Gehui.Xu@HAW-Hamburg.de</code>,
 *     Gerriet Hinrichs <code>gerriet.hinrichs@haw-hamburg.de</code>.
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
import de.stuff42.se2tierheimprojekt.data.AnimalGardenSpace;
import de.stuff42.se2tierheimprojekt.data.AnimalSex;

import org.jetbrains.annotations.NotNull;

/**
 * An single Animal with his attributes.
 */
public class AnimalModel {

    @NotNull
    public Long id;

    @NotNull
    public String name;

    @NotNull
    public String race;

    @NotNull
    public AnimalSex sex;

    public AnimalAge age;

    @NotNull
    public AnimalGardenSpace requiredSpace;

    public String picturePath = "";

    /**
     * An single Animal.
     * Empty String or Text.
     *
     * @param name          <code>""</code> or <code>attributeText</code>.
     * @param race          <code>""</code> or <code>attributeText</code>.
     * @param sex           <code>""</code> or <code>attributeText</code>.
     * @param age           <code>""</code> or <code>attributeText</code>.
     * @param requiredSpace <code>""</code> or <code>attributeText</code>.
     */
    public AnimalModel(@NotNull Long id, @NotNull String name, @NotNull String race, @NotNull AnimalSex sex, AnimalAge age, @NotNull AnimalGardenSpace requiredSpace, @NotNull String picturePath) {
        this.id = id;
        this.name = name;
        this.race = race;
        this.sex = sex;
        this.age = age;
        this.requiredSpace = requiredSpace;
        this.picturePath = picturePath;
    }

    /**
     * Default constructor needed due to JSON de-serializing
     */
    public AnimalModel() {
    }
}
