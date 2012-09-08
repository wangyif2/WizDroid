package com.yifandroid.wizdroid.WizLog;

import android.util.Log;
import com.google.inject.Inject;

/**
 * Created by IntelliJ IDEA.
 * User: ywang
 * Date: 21/07/12
 * Time: 4:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class LogHandler {

    public static final String TAG = "WizDroid";
    private final String logTag;
    private final LogFormatter logFormatter;
    private volatile LogLevel level;

    @Inject
    public LogHandler(LogLevel level, LogFormatter logFormatter, String tag) {
        this.logFormatter = logFormatter;
        this.level = level;
        logTag = tag;
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    protected void handleDebug(Object message) {
        Log.d(logTag, logFormatter.format(getLevel(), message, null));
    }

    protected void handleInfo(Object message) {
        Log.i(logTag, logFormatter.format(getLevel(), message, null));
    }

    protected void handleWarning(Object message) {
        Log.w(logTag, logFormatter.format(getLevel(), message, null));
    }

    protected void handleError(Object message, Throwable throwable) {
        Log.e(logTag, logFormatter.format(getLevel(), message, throwable));
    }

    protected void handleFatal(Object message, Throwable throwable) {
        Log.wtf(logTag, logFormatter.format(getLevel(), message, throwable));
    }

    public void log(LogLevel alevel, Object message, Throwable throwable) {
        if (alevel.getLevel() >= level.getLevel()) {
            switch (alevel) {
                case DEBUG:
                    handleDebug(message);
                    break;

                case INFO:
                    handleInfo(message);
                    break;

                case WARNING:
                    handleWarning(message);
                    break;

                case ERROR:
                    handleError(message, throwable);
                    break;

                case FATAL:
                    handleFatal(message, throwable);
                    break;
            }
        }
    }
}
