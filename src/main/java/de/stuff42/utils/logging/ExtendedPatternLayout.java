package de.stuff42.utils.logging;

import java.util.HashMap;
import java.util.Map;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.pattern.EnsureExceptionHandling;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.pattern.PatternLayoutBase;

/**
 * Extends the flexible pattern layout by more custom fields.
 */
public class ExtendedPatternLayout extends PatternLayoutBase<ILoggingEvent> {

    /**
     * Converter map (we need a custom one here to keep the original pattern layout as it is).
     */
    private static final Map<String, String> converterMap = new HashMap<String, String>();

    static {

        // copy existing entries
        converterMap.putAll(PatternLayout.defaultConverterMap);

        // tab
        converterMap.put("tab", TabConverter.class.getName());

        // simplified logger name converter
        converterMap.put("simpleLo", SimplifiedLoggerConverter.class.getName());
        converterMap.put("simpleLogger", SimplifiedLoggerConverter.class.getName());
        converterMap.put("simpleC", SimplifiedLoggerConverter.class.getName());

        // simplified throwable proxy converter
        converterMap.put("simpleEx", SimplifiedThrowableProxyConverter.class.getName());
        converterMap.put("simpleException", SimplifiedThrowableProxyConverter.class.getName());
        converterMap.put("simpleThrowable", SimplifiedThrowableProxyConverter.class.getName());
    }

    /**
     * Ensures we have exception handling.
     */
    public ExtendedPatternLayout() {

        // always add exception handling
        this.postCompileProcessor = new EnsureExceptionHandling();
    }

    @Override
    public Map<String, String> getDefaultConverterMap() {
        return converterMap;
    }

    @Override
    protected String getPresentationHeaderPrefix() {
        return "#logback custom (de.stuff42.utils.logging.ExtendedPatternLayout) pattern: ";
    }

    @Override
    public String doLayout(ILoggingEvent event) {
        if (!isStarted()) {
            return CoreConstants.EMPTY_STRING;
        }
        return writeLoopOnConverters(event);
    }
}
