package com.yifandriod.wizdroid.WizLog;

import com.google.inject.Inject;
import org.jetbrains.annotations.Nullable;

/**
 * Created by IntelliJ IDEA.
 * User: ywang
 * Date: 21/07/12
 * Time: 3:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class WizLogger {
    private final LogHandler logHandler;

    @Inject
    public WizLogger(LogHandler logHandler) {
        this.logHandler = logHandler;
    }

    public void debug(Object message) {
        logAdb(LogLevel.DEBUG, message, null);
    }

    public void debug(String format, Object... params) {
        String message = String.format(format, params);
        logAdb(LogLevel.DEBUG, message, null);
    }

    public void info(Object message) {
        logAdb(LogLevel.INFO, message, null);
    }

    public void info(String format, Object... params) {
        String message = String.format(format, params);
        logAdb(LogLevel.INFO, message, (Throwable) null);
    }

    public void warn(Object message) {
        logAdb(LogLevel.WARNING, message, null);
    }

    public void warn(String format, Object... params) {
        String message = String.format(format, params);
        logAdb(LogLevel.WARNING, message, (Throwable) null);
    }

    public void error(String format, Object... params) {
        String message = String.format(format, params);
        logAdb(LogLevel.ERROR, message, (Throwable) null);
    }

    public void error(Object message, Throwable throwable) {
        logAdb(LogLevel.ERROR, message, throwable);
    }

    public void fatal(Object message) {
        logAdb(LogLevel.FATAL, message, null);
    }

    private void logAdb(LogLevel level, Object message, @Nullable Throwable throwable) {
        if (logHandler != null) {
            logHandler.log(level, message, throwable);
        }
    }
}
