package de.stuff42.utils.logging;

import ch.qos.logback.classic.pattern.ThrowableHandlingConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;

import de.stuff42.utils.exception.ExceptionPrinter;
import de.stuff42.utils.exception.ExceptionPrinter.IndentationType;

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
