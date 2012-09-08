package com.yifandroid.wizdroid;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

import javax.inject.Inject;

/**
 * Created by IntelliJ IDEA.
 * User: ywang
 * Date: 24/07/12
 * Time: 4:02 PM
 */
public class WizAlarmReceiver extends BroadcastReceiver {
    @Inject
    AudioManager audioManager;
    @Inject
    ComponentName componentName;

    @Override
    public void onReceive(Context context, Intent intent) {
        WizdroidApplication.getInjector().injectMembers(this);
        audioManager.registerMediaButtonEventReceiver(componentName);
    }
}
