package com.yifandriod.wizdroid;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.ComponentName;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.SystemClock;
import com.google.inject.Inject;
import com.yifandriod.wizdroid.WizLog.WizLogger;

public class WizDroidActivity extends Activity {
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
<<<<<<< HEAD:src/com/yifandriod/wizdroid/WizDroidActivity.java
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent i = new Intent(this, WizAlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 300, pi);
=======
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), ALARM_PERIOD, WizdroidApplication.getPi());
>>>>>>> fc11c2447682aed7c1f8cb80b6c42b1d185cc15b:src/com/yifandriod/wizdroid/WizDroid.java
    }

    private void removeScheduleRequestMediaButtonReceiver() {
        alarmManager.cancel(WizdroidApplication.getPi());
    }
}
