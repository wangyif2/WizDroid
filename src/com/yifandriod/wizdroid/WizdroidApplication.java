package com.yifandriod.wizdroid;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
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
    public static boolean blockingState;
    public static PendingIntent pi;

    @Override
    public void onCreate() {
        super.onCreate();

        createInjector();
        createPendingIntent();

        blockingState = false;
    }

    private void createPendingIntent() {
        if (pi ==null){
            Intent i = new Intent(this, OnAlarmReceiver.class);
            pi = PendingIntent.getBroadcast(this, 0, i, 0);
        }
    }

    public static PendingIntent getPi(){
        return pi;
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
