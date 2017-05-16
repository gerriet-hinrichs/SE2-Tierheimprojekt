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

import de.stuff42.utils.exception.ExceptionPrinter;
import de.stuff42.utils.exception.ExceptionPrinter.IndentationType;

import ch.qos.logback.classic.pattern.ThrowableHandlingConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;

/**
 * Throwable converter that performs stacktrace simplification.
 */
public class SimplifiedThrowableProxyConverter extends ThrowableHandlingConverter {

    /**
     * Converts the exception from the given event into a string with simplified trace.
     * <p>
     * Uses {@link IndentationType#BOTH} for indentation.
     *
     * @param event Logging event.
     *
     * @return Printed exception with simplified trace.
     *
     * @see ExceptionPrinter#printThrowable(IThrowableProxy, IndentationType)
     */
    @Override
    public String convert(ILoggingEvent event) {
        IThrowableProxy proxy = event.getThrowableProxy();

        // early exit: no throwable within event
        if (proxy == null) {
            return "";
        }

        // use exception printer otherwise
        return ExceptionPrinter.printThrowable(proxy, IndentationType.BOTH);
    }
}
