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

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnimalDAO extends CrudRepository<AnimalEntity, Long> {
/*
    @Query( "SELECT animal " +
            "FROM AnimalEntity animal " +
            "WHERE " +
            "animal.animalType IN :animalType AND " +
            "animal.size IN :ssize AND " +
            "animal.cost IN :cost AND " +
            "animal.needCare = :needCare AND " +
            "animal.garden = :garden " +
            "ORDER BY animal.id")
    List<AnimalEntity> getFittingAnimals(@Param("animalType") List<String> animalType,
                                         @Param("ssize") List<String> size,
                                         @Param("cost") List<String> cost,
                                         @Param("needCare") Boolean needCare,
                                         @Param("garden") Boolean garden);
                                         */

    @Query( "SELECT animal FROM AnimalEntity animal WHERE animal.animalType = :animalType ORDER BY animal.id")
    List<AnimalEntity> getFittingAnimals(@Param("animalType") List<String> animalType,
                                         @Param("ssize") List<String> size,
                                         @Param("cost") List<String> cost,
                                         @Param("needCare") Boolean needCare,
                                         @Param("garden") Boolean garden);
}
