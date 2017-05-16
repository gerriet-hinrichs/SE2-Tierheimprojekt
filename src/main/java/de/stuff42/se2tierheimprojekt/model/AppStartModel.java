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

import java.time.format.DateTimeFormatter;

import de.stuff42.se2tierheimprojekt.service.AppStartService;

public class AppStartModel {

    private final AppStartService service;

    public AppStartModel(AppStartService service) {
        this.service = service;
    }

    public String getBuildVersionJS() {

        // escape quotes in string
        return "'" + service.getBuildVersion().replaceAll("'", "\\'") + "'";
    }

    public String getBuildTimeJS() {

        // create date object from time
        return "new Date('" + service.getBuildTime().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) + "')";
    }

    public Boolean getIsDebug() {
        return service.isDebug();
    }

    public String getDebugJS() {

        // properly stringify
        return String.valueOf(service.isDebug());
    }
}
