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
package de.stuff42.apigenerator;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import de.stuff42.utils.SystemConfig;

/**
 * Class for generator utilities.
 */
public class Utilities {

    /**
     * Build version from system ini.
     */
    private static final String BUILD_VERSION = SystemConfig.get("BUILD", "Version", String.class, "SNAPSHOT");

    /**
     * Indentation base string.
     */
    private static final String INDENTATION_BASE = "    ";

    /**
     * Calculates a safe identifier for use in paths and code.
     * This method adds the build version to calculation to get different results for every build version.
     * The idea is to prevent outside use of internal stuff (where the calculated identifier is used).
     *
     * @param longName Long name to calculate identifier from.
     *
     * @return Calculated identifier.
     */
    @NotNull
    public static String getIdentifier(@NotNull String longName) {
        return "_" + Integer.toString((longName + "_" + BUILD_VERSION).hashCode(), 16);
    }

    /**
     * Calculates an indentation string for the given level.
     *
     * @param level Indentation level.
     *
     * @return Indentation string.
     */
    @Contract(pure = true)
    public static String getIndentationString(int level) {
        String result = "";
        for (int i = 0; i < level; i++) {
            result += INDENTATION_BASE;
        }
        return result;
    }
}
