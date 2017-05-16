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
