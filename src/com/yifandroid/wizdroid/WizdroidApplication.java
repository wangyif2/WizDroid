package com.yifandroid.wizdroid;

import android.app.Application;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Created by IntelliJ IDEA.
 * User: ywang
 * Date: 24/07/12
 * Time: 3:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class WizdroidApplication extends Application {
    public static Injector injector;

    @Override
    public void onCreate() {
        super.onCreate();
        createInjector();
    }

    private void createInjector() {
        if (injector == null){
            GuiceModule guiceModule = new GuiceModule(this);
            injector = Guice.createInjector(guiceModule);
        }
    }

    public static Injector getInjector() {
        return injector;
    }
}
