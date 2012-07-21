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
public class RemoteControlReceiver extends BroadcastReceiver {

    @Inject
    private WizLogger wizLogger;

    @Override
    public void onReceive(Context context, Intent intent) {

        android.util.Log.i("WizDroid", "Received media button action");
        //To change body of implemented methods use File | Settings | File Templates.

//        if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
//            KeyEvent event = (KeyEvent)intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
//            if (KeyEvent.KEYCODE_MEDIA_PLAY == event.getKeyCode()) {
//
////                wizLogger.info("test test madafaka");
//                android.util.Log.i("WizDroid", "android log");
//
//                abortBroadcast();
//            }
//        }
    }
}
