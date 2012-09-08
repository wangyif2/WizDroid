package com.yifandriod.wizdroid;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import com.yifandriod.wizdroid.WizLog.WizLogger;

import javax.inject.Inject;

/**
 * Created by IntelliJ IDEA.
 * User: ywang
 * Date: 24/07/12
 * Time: 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class WizAlarmReceiver extends BroadcastReceiver {
    @Inject
    WizLogger wizLogger;
    @Inject
    AudioManager am;
    @Inject
    ComponentName componentName;

    @Override
    public void onReceive(Context context, Intent intent) {
        WizdroidApplication.getInjector().injectMembers(this);
        am.registerMediaButtonEventReceiver(componentName);
    }
}
