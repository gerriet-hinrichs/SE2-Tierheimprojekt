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

import de.stuff42.se2tierheimprojekt.data.*;

import org.jetbrains.annotations.NotNull;

@Entity
public class AnimalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    // Animal
    @NotNull
    public String name = "";

    @NotNull
    public String race = "";

    @NotNull
    public AnimalSex sex = AnimalSex.FEMALE;

    @NotNull
    public AnimalAge age = AnimalAge.YOUNG;

    @NotNull
    public AnimalSpace requiredSpace = AnimalSpace.HUGE;

    // Search properties
    @NotNull
    public AnimalType animalType = AnimalType.BIRD;

    @NotNull
    public AnimalSize animalSize = AnimalSize.HUGE;

    @NotNull
    public AnimalCost animalCost;

    @NotNull
    public AnimalCareTyp needCare;

    @NotNull
    public AnimalGardenSpace garden;
    
    @NotNull
    public String picturePath = "";

    public AnimalEntity() {
        // no-args constructor required by JPA spec
        // it shouldn't be used directly
    }

    public AnimalEntity(@NotNull String name, @NotNull String race, @NotNull AnimalSex sex, @NotNull AnimalAge age, @NotNull AnimalSpace requiredSpace,
                        @NotNull AnimalType animalType, @NotNull AnimalSize animalSize, @NotNull AnimalCost animalCost, @NotNull AnimalCareTyp needCare, @NotNull AnimalGardenSpace garden, @NotNull String picturePath) {
        this.name = name;
        this.race = race;
        this.sex = sex;
        this.age = age;
        this.requiredSpace = requiredSpace;
        this.animalType = animalType;
        this.animalSize = animalSize;
        this.animalCost = animalCost;
        this.needCare = needCare;
        this.garden = garden;
        this.picturePath = picturePath;
    }
}
