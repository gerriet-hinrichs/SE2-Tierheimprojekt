/*
 * Utilities for use in various applications.
 * Copyright (C) 2017
 *     Adrian Bostelmann <Adrian.Bostelmann@HAW-Hamburg.de>,
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
package de.stuff42.utils.logging;

import de.stuff42.utils.UtilsConfig;

import org.jetbrains.annotations.NotNull;

/**
 * Utility class for logging related stuff.
 */
public class LoggingUtils {

    /**
     * Hide the default constructor since this class only contains static
     * methods.
     */
    private LoggingUtils() {
        // hide the default constructor
    }

    /**
     * Generates a simplified name from the given canonical name.
     *
     * @param name Canonical object name.
     *
     * @return Simplified name.
     */
    @NotNull
    public static String getSimplifiedName(@NotNull String name) {

        // for relevant packages we simply remove the prefix
        for (String packageName : UtilsConfig.getRelevantPackages()) {
            if (name.startsWith(packageName)) {
                return name.substring(packageName.length());
            }
        }

        // java standard library names start with java or javax
        if (name.startsWith("java.") || name.startsWith("javax.")) {
            return name.replaceAll("^([^.]*)\\.(.*?\\.)?([^.]*)$", "<$1>.$3");
        }

        // for other (library) names we assume to have the pattern:
        // {TLD}.{libraryName/providerName}.{...?}.{Class}
        // and we generate a simplified name:
        // <{libraryName/providerName}>.{Class}
        return name.replaceAll("^[^.]*\\.([^.]*)\\.(.*?\\.)?([^.]*)$", "<$1>.$3");
    }

    /**
     * Generates a simplified name from the given canonical name.
     *
     * @param name   Canonical object name.
     * @param prefix Prefix string.
     *
     * @return Simplified name.
     */
    @NotNull
    public static String getSimplifiedName(@NotNull String name, @NotNull String prefix) {
        return prefix + "." + getSimplifiedName(name);
    }
}
