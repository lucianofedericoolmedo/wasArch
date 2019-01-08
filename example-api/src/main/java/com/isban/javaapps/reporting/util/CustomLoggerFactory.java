package com.isban.javaapps.reporting.util;

import java.util.logging.Logger;

public class CustomLoggerFactory {

    public static <T> Logger getLogger (T loggingObject) {
        return Logger.getLogger(loggingObject.getClass().getName());
    }

    public static <T> Logger getLogger (String loggingPath) {
        return Logger.getLogger(loggingPath);
    }

}
