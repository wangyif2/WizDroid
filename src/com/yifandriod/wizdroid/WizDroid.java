package com.yifandriod.wizdroid;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.ComponentName;
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
    private AudioManager audioManager;
    @Inject
    private ComponentName componentName;
    @Inject
    private AlarmManager alarmManager;

    //2 minutes
    final long ALARM_PERIOD = 120000;



    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        WizdroidApplication.getInjector().injectMembers(this);

        //Get Audio Focus onCreate
        requestAudioFocus();

        //Schedule Periodic request for MediaButtonReceiver
        scheduleRequestMediaButtonReceiver();
    }

    private void requestAudioFocus() {
        int result = audioManager.requestAudioFocus(listener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            wizLogger.info("AUDIOFOCUS_REQUEST_GRANTED");
        }
    }

    private void scheduleRequestMediaButtonReceiver() {
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), ALARM_PERIOD, WizdroidApplication.getPi());
    }

    private void removeScheduleRequestMediaButtonReceiver() {
        alarmManager.cancel(WizdroidApplication.getPi());
    }
}
