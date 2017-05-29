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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import de.stuff42.se2tierheimprojekt.data.AnimalType;

@Entity
public class AnimalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    // Animal
    // <""> or <attributeText>
    @NotNull
    public String name;

    @NotNull
    public String race;

    @NotNull
    public String sex;

    @NotNull
    public String age;

    @NotNull
    public String requiredSpace;

    // Search properties
    @NotNull
    public AnimalType animalType;

    // small, medium, huge
    @NotNull
    public String animalSize;

    // cheap, medium, expensive
    @NotNull
    public String cost;

    // true, false
    public boolean needCare;

    // true, false
    public boolean garden;

    public AnimalEntity() {
        // no-args constructor required by JPA spec
        // it shouldn't be used directly
    }

    public AnimalEntity(@NotNull String name, @NotNull String race, @NotNull String sex, @NotNull String age, @NotNull String requiredSpace,
                        @NotNull AnimalType animalType, @NotNull String animalSize, @NotNull String cost, boolean needCare, boolean garden) {
        // TODO: check for not null
        this.name = name;
        this.race = race;
        this.sex = sex;
        this.age = age;
        this.requiredSpace = requiredSpace;
        this.animalType = animalType;
        this.animalSize = animalSize;
        this.cost = cost;
        this.needCare = needCare;
        this.garden = garden;
    }
}
