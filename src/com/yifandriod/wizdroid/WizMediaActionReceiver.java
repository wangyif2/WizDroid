package com.yifandriod.wizdroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.inject.Inject;
import com.yifandriod.wizdroid.WizLog.WizLogger;

/**
 * Created by IntelliJ IDEA.
 * User: ywang
 * Date: 21/07/12
 * Time: 3:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class WizMediaActionReceiver extends BroadcastReceiver {

    @Inject
    private WizLogger wizLogger;

    @Override
    public void onReceive(Context context, Intent intent) {
        WizdroidApplication.getInjector().injectMembers(this);

        wizLogger.info("Received media button action");
        abortBroadcast();
    }
}
