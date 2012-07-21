package com.yifandriod.wizdroid.WizLog;

import com.google.inject.Provider;

/**
 * Created by IntelliJ IDEA.
 * User: ywang
 * Date: 21/07/12
 * Time: 4:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class LogHandlerProvider implements Provider<LogHandler> {
    @Override
    public LogHandler get() {
        return new LogHandler(LogLevel.DEBUG, LogFormatter.newInstance(), LogHandler.TAG);
    }
}
