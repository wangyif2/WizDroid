package com.yifandroid.wizdroid;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

    public static int isBlocking(Context paramContext) {
        return paramContext.getPackageManager().getComponentEnabledSetting(BLOCKER);
    }

    public static void toggleBlocking(Context paramContext, boolean toggleState) {
        PackageManager localPackageManager = paramContext.getPackageManager();
        if (toggleState == true)
            localPackageManager.setComponentEnabledSetting(BLOCKER, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, 1);
        else
            localPackageManager.setComponentEnabledSetting(BLOCKER, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, 1);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        WizdroidApplication.getInjector().injectMembers(this);
        wizLogger.debug("Received media button action, aborting broadcast");
        abortBroadcast();
    }
}
