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
package de.stuff42.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.jetbrains.annotations.NotNull;

/**
 * Configuration class for utility functions.
 */
public class UtilsConfig {

    /**
     * Contains packages that are relevant for utility stuff, e.g. stacktrace simplification or logger name generation.
     */
    @NotNull
    private static Collection<String> relevantPackages = Collections.emptyList();

    /**
     * Hide the default constructor since this class only contains static
     * methods.
     */
    private UtilsConfig() {
        // hide the default constructor
    }

    /**
     * Returns a unmodifiable collection containing relevant package names.
     *
     * @return Relevant package names.
     */
    @NotNull
    public static Collection<String> getRelevantPackages() {
        return relevantPackages;
    }

    /**
     * Sets package name filters for stack trace simplification.
     *
     * @param relevantPackages Package names that are relevant for utility stuff, e.g. stacktrace simplification or
     *                         logger name generation.
     */
    public static void setRelevantPackages(@NotNull final String... relevantPackages) {

        // add ending dot (if not present) to only match exact packages
        for (int i = 0; i < relevantPackages.length; i++) {
            if (!relevantPackages[i].endsWith(".")) {
                relevantPackages[i] += ".";
            }
        }

        // set names (as unmodifiable collection)
        UtilsConfig.relevantPackages = Collections.unmodifiableCollection(Arrays.asList(relevantPackages));
    }
}
