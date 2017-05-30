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
package de.stuff42.utils.exception;

import java.util.Collection;
import java.util.function.Function;

import de.stuff42.utils.UtilsConfig;
import de.stuff42.utils.logging.LoggingUtils;

import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import org.jetbrains.annotations.NotNull;

/**
 * Helper utility class that prints exceptions with filtered stacktrace.
 */
public class ExceptionPrinter {

    /**
     * Line separator.
     */
    private static final String LINE_SEPARATOR = System.lineSeparator();

    /**
     * Hide the default constructor since this class only contains static
     * methods.
     */
    private ExceptionPrinter() {
        // hide the default constructor
    }

    /**
     * Prints the given throwable into a string.
     * <p>
     * Uses {@link IndentationType#TRACE_ONLY} for indentation.
     *
     * @param throwable Throwable to be printed.
     *
     * @return Printed throwable.
     */
    @NotNull
    public static String printThrowable(@NotNull Throwable throwable) {
        return printThrowable(throwable, IndentationType.TRACE_ONLY);
    }

    /**
     * Prints the given throwable into a string.
     *
     * @param throwable Throwable to be printed.
     * @param indent    Type of indentation.
     *
     * @return Printed throwable.
     */
    @NotNull
    public static String printThrowable(@NotNull Throwable throwable, @NotNull IndentationType indent) {
        StringBuilder sb = new StringBuilder();
        appendThrowable(
                sb,
                throwable,
                x -> x.getClass().getCanonicalName(),
                Throwable::getLocalizedMessage,
                Throwable::getStackTrace,
                x -> x,
                Throwable::getCause,
                indent
        );
        return sb.toString();
    }

    /**
     * Prints the given throwable proxy into a string.
     * <p>
     * Uses {@link IndentationType#TRACE_ONLY} for indentation.
     *
     * @param proxy Proxy to be printed.
     *
     * @return Printed proxy.
     */
    @NotNull
    public static String printThrowable(@NotNull IThrowableProxy proxy) {
        return printThrowable(proxy, IndentationType.TRACE_ONLY);
    }

    /**
     * Prints the given throwable proxy into a string.
     *
     * @param proxy  Proxy to be printed.
     * @param indent Type of indentation.
     *
     * @return Printed proxy.
     */
    @NotNull
    public static String printThrowable(@NotNull IThrowableProxy proxy, @NotNull IndentationType indent) {
        StringBuilder sb = new StringBuilder();
        appendThrowable(
                sb,
                proxy,
                IThrowableProxy::getClassName,
                IThrowableProxy::getMessage,
                IThrowableProxy::getStackTraceElementProxyArray,
                StackTraceElementProxy::getStackTraceElement,
                IThrowableProxy::getCause,
                indent
        );
        return sb.toString();
    }

    /**
     * Appends the given (proxied) throwable into the {@link StringBuilder}.
     *
     * @param sb                        String builder instance.
     * @param throwable                 Throwable (may be proxied).
     * @param classNameSelector         Selector to get the class name from throwable.
     * @param messageSelector           Selector to get the message from throwable.
     * @param stacktraceSelector        Selector to get the (proxied) stacktrace element array from throwable.
     * @param stackTraceElementSelector Selector to get stacktrace element from proxied stacktrace one.
     * @param causeSelector             Selector for the cause of the throwable.
     * @param indentationType           Type of indentation.
     * @param <T>                       Maybe proxied throwable type.
     * @param <E>                       Maybe proxied trace element type.
     */
    private static <T, E> void appendThrowable(@NotNull StringBuilder sb, @NotNull T throwable,
                                               @NotNull Function<T, String> classNameSelector,
                                               @NotNull Function<T, String> messageSelector,
                                               @NotNull Function<T, E[]> stacktraceSelector,
                                               @NotNull Function<E, StackTraceElement> stackTraceElementSelector,
                                               @NotNull Function<T, T> causeSelector,
                                               @NotNull IndentationType indentationType) {
        boolean isCause = false;
        T current = throwable;
        while (current != null) {
            sb.append(indentationType.messageIndent);
            if (isCause) {
                sb.append("Caused by: ");
            }
            isCause = true;

            // add simplified name
            sb.append(LoggingUtils.getSimplifiedName(classNameSelector.apply(current)));
            sb.append(": ");

            // add message
            sb.append(messageSelector.apply(current));
            sb.append(LINE_SEPARATOR);

            // add trace
            appendStacktraceArray(sb, stacktraceSelector.apply(current), stackTraceElementSelector, indentationType);

            // move to next element
            current = causeSelector.apply(current);
        }
    }

    /**
     * Appends the given stacktrace array into the {@link StringBuilder}.
     *
     * @param sb                        String builder instance.
     * @param stackTrace                Stacktrace array of (proxied) elements.
     * @param stackTraceElementSelector Selector to get the actual stacktrace element from proxied one.
     * @param indentationType           Type of indentation.
     * @param <T>                       Proxied stacktrace element type.
     */
    private static <T> void appendStacktraceArray(@NotNull StringBuilder sb, @NotNull T[] stackTrace,
                                                  @NotNull Function<T, StackTraceElement> stackTraceElementSelector,
                                                  @NotNull IndentationType indentationType) {
        Collection<String> filters = UtilsConfig.getRelevantPackages();
        int skipped = 0;

        StackTraceElement prev = null;
        for (int index = 0; index < stackTrace.length; index++) {
            final StackTraceElement current = stackTraceElementSelector.apply(stackTrace[index]);
            boolean skip = true;
            final String className = current.getClassName();

            // check if we should show the current element
            for (final String filter : filters) {
                if (className.startsWith(filter)) {
                    skip = false;
                    break;
                }
            }
            if (skip) {
                skipped++;
            } else {
                if (skipped > 0) {
                    if (skipped > 1) {

                        // we print the last skipped element so we hand over: skipped - 1
                        appendStacktraceSkippedElements(sb, skipped - 1, indentationType);
                    }
                    skipped = 0;

                    // append last skipped element (at index: index - 1)
                    appendStacktraceElement(sb, prev, index - 1, indentationType);
                }

                // append current (not skipped) element
                appendStacktraceElement(sb, current, index, indentationType);
            }

            prev = current;
        }
    }

    /**
     * Appends a stacktrace line indicating skipped elements.
     *
     * @param sb              String builder instance.
     * @param skipped         Number of skipped elements.
     * @param indentationType Type of indentation.
     */
    private static void appendStacktraceSkippedElements(@NotNull StringBuilder sb, int skipped,
                                                        @NotNull IndentationType indentationType) {
        sb.append(indentationType.traceIndent);
        sb.append("[...] ");
        sb.append(skipped);
        sb.append(" element");
        if (skipped > 1) {
            sb.append('s');
        }
        sb.append(" skipped.");
        sb.append(LINE_SEPARATOR);
    }

    /**
     * Appends a stacktrace line for the given stacktrace element.
     *
     * @param sb              String builder instance.
     * @param element         Stacktrace element.
     * @param index           Index of the stacktrace element.
     * @param indentationType Type of indentation.
     */
    private static void appendStacktraceElement(@NotNull StringBuilder sb, @NotNull StackTraceElement element,
                                                int index, @NotNull IndentationType indentationType) {
        sb.append(indentationType.traceIndent);
        sb.append('#');
        sb.append(index);
        sb.append(": ");
        sb.append(element.getClassName());
        sb.append("::");
        sb.append(element.getMethodName());
        sb.append(" (");
        sb.append(element.getFileName());
        sb.append(" line ");
        sb.append(element.getLineNumber());
        sb.append(')');
        sb.append(LINE_SEPARATOR);
    }

    /**
     * Printing indentation types.
     */
    public enum IndentationType {

        /**
         * Perform no indentation at all.
         */
        NONE("", ""),

        /**
         * Only indent trace with a tab.
         */
        TRACE_ONLY("", "    "),

        /**
         * Indent both: Trace with two tabs and message with a single tab.
         */
        BOTH("    ", "        ");

        /**
         * Trace indentation string.
         */
        final String messageIndent;

        /**
         * Message indentation string.
         */
        final String traceIndent;

        /**
         * Creates element with the specified indentation values.
         *
         * @param messageIndent Message indentation string.
         * @param traceIndent   Trace indentation string.
         */
        IndentationType(String messageIndent, String traceIndent) {
            this.messageIndent = messageIndent;
            this.traceIndent = traceIndent;
        }
    }
}
