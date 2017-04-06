package de.stuff42.utils.logging;

import org.jetbrains.annotations.NotNull;

import de.stuff42.utils.UtilsConfig;

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
