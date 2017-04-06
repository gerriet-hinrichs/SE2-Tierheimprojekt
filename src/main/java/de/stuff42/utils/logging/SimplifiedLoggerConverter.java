package de.stuff42.utils.logging;

import java.util.HashMap;
import java.util.Map;

import ch.qos.logback.classic.pattern.NamedConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * Logger converter that performs logger name simplification.
 */
public class SimplifiedLoggerConverter extends NamedConverter {

    /**
     * Map for already known simplified names. Used for performance optimization.
     */
    private static Map<String, String> simplifiedNames = new HashMap<>();

    /**
     * Returns the simplified logger name.
     *
     * @param event Logging event.
     *
     * @return Simplified name.
     *
     * @see LoggingUtils#getSimplifiedName(String)
     */
    protected String getFullyQualifiedName(ILoggingEvent event) {
        return simplifiedNames.computeIfAbsent(event.getLoggerName(), LoggingUtils::getSimplifiedName);
    }

}
