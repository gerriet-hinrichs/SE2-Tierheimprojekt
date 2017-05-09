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
package de.stuff42.se2tierheimprojekt.service;

import java.time.OffsetDateTime;

import de.stuff42.utils.SystemConfig;

import org.springframework.stereotype.Service;

/**
 * App start service to provide basic information that is required on app start.
 */
@Service
public class AppStartService extends BaseService {

    public String getBuildVersion() {
        return SystemConfig.get("BUILD", "Version", String.class, "UNKNOWN");
    }

    public OffsetDateTime getBuildTime() {
        return OffsetDateTime.parse(SystemConfig.get(
                "BUILD",
                "Timestamp",
                String.class,
                "1970-01-01T00:00:00.000+00:00"
        ));
    }

    public Boolean isDebug() {
        return SystemConfig.get("BUILD", "Debug", Boolean.class, false);
    }
}
