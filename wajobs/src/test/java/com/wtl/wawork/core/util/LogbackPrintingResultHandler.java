package com.wtl.wawork.core.util;

import org.slf4j.Logger;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.PrintingResultHandler;
import org.springframework.util.CollectionUtils;

/**
 * A {@link PrintingResultHandler} that writes to a provided {@link Logger}
 */
public class LogbackPrintingResultHandler extends PrintingResultHandler {

    /**
     * Factory method for creating a {@link PrintingResultHandler} that prints
     * to the provided {@link Logger}. Based on {@link MockMvcResultHandlers}.
     * 
     * @param log
     *            the {@link Logger}
     */
    public static ResultHandler log(Logger log) {
        return new LogbackPrintingResultHandler(log);
    }

    private LogbackPrintingResultHandler(final Logger log) {
        super(new ResultValuePrinter() {

            @Override
            public void printHeading(String heading) {
                if (log.isDebugEnabled()) {
                    log.debug("\n");
                    log.debug(String.format("%20s:", heading));
                }
            }

            @Override
            public void printValue(String label, Object value) {
                if (log.isDebugEnabled()) {
                    if (value != null && value.getClass().isArray()) {
                        value = CollectionUtils.arrayToList(value);
                    }
                    log.debug(String.format("%20s = %s", label, value));
                }
            }
        });

    }
}