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
