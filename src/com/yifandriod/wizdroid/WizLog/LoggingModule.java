package com.yifandriod.wizdroid.WizLog;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * Created by IntelliJ IDEA.
 * User: ywang
 * Date: 21/07/12
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoggingModule extends AbstractModule{
    @Override
    protected void configure() {
        bind(WizLogger.class).in(Singleton.class);
        bind(LogHandler.class).toProvider(LogHandlerProvider.class).in(Singleton.class);
    }
}
