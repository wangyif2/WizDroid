package com.yifandroid.wizdroid;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import com.google.inject.Inject;
import com.yifandroid.wizdroid.WizLog.WizLogger;

/**
 * Created by IntelliJ IDEA.
 * User: ywang
 * Date: 21/07/12
 * Time: 3:00 PM
 */
public class WizMediaActionReceiver extends BroadcastReceiver {
    @Inject
    private WizLogger wizLogger;
    @Inject
    private static final ComponentName BLOCKER = new ComponentName("com.yifandroid.wizdroid", "com.yifandroid.wizdroid.WizMediaActionReceiver");

    //The frequency that we request for Audio Focus
    private static final int WIZFREQUENCY = 30000;


    public static int isBlocking(Context paramContext) {
        return paramContext.getPackageManager().getComponentEnabledSetting(BLOCKER);
    }

    public static void toggleBlocking(Context paramContext, boolean toggleState) {
        AlarmManager alarmManager = (AlarmManager) paramContext.getSystemService(Context.ALARM_SERVICE);
        PackageManager localPackageManager = paramContext.getPackageManager();

        if (toggleState) {
            Intent i = new Intent(paramContext, WizAlarmReceiver.class);
            PendingIntent pi = PendingIntent.getBroadcast(paramContext, 0, i, 0);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), WIZFREQUENCY, pi);

            localPackageManager.setComponentEnabledSetting(BLOCKER, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, 1);
        } else {
            Intent i = new Intent(paramContext, WizAlarmReceiver.class);
            PendingIntent pi = PendingIntent.getBroadcast(paramContext, 0, i, 0);
            alarmManager.cancel(pi);

            localPackageManager.setComponentEnabledSetting(BLOCKER, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, 1);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        WizdroidApplication.getInjector().injectMembers(this);
        wizLogger.debug("Received media button action, aborting broadcast");
        abortBroadcast();
    }
}
