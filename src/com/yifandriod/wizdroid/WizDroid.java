package com.yifandriod.wizdroid;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.SystemClock;
import com.google.inject.Inject;
import com.yifandriod.wizdroid.WizLog.WizLogger;

public class WizDroid extends Activity {
    @Inject
    private WizLogger wizLogger;
    @Inject
    private MyOnAudioFocusChangeListener listener;
    @Inject
    private AudioManager am;
    @Inject
    private ComponentName componentName;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        WizdroidApplication.getInjector().injectMembers(this);

        wizLogger.info("We are in main onCreate");

        int result = am.requestAudioFocus(listener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            wizLogger.info("AUDIOFOCUS_REQUEST_GRANTED");
        }

        scheduleRequestMediaButtonReceiver();
    }

    private void scheduleRequestMediaButtonReceiver() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent i = new Intent(this, OnAlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 300, pi);
    }


}
