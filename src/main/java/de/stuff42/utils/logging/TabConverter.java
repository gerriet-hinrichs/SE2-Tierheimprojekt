package de.stuff42.utils.logging;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * Converter that provides the tab character for use in log output.
 */
public class TabConverter extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {
        return "\t";
    }
}
