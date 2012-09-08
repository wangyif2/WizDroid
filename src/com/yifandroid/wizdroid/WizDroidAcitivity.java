package com.yifandroid.wizdroid;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;
import com.google.inject.Inject;
import com.yifandroid.wizdroid.WizLog.WizLogger;

public class WizDroidAcitivity extends Activity {

    @Inject
    private WizLogger wizLogger;
    @Inject
    private WizOnAudioFocusChangeListener listener;
    @Inject
    private AudioManager audioManager;
    @Inject
    private AlarmManager alarmManager;
    @Inject
    private ComponentName componentName;

    private Context mContext;

    //The frequency that we request for Audio Focus
    private static final int WIZFREQUENCY = 30000;

    ImageView wizOn, wizOff;
    ToggleButton wizToggleButton;
    Animation animationSlideInLeft, animationSlideOutRight;

    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean toggleState) {
            wizToggleButton.setEnabled(false);

            transitionAnimation(toggleState);
            requestMediaButtonReceiver(toggleState);
            WizMediaActionReceiver.toggleBlocking(WizDroidAcitivity.this.mContext, toggleState);

            wizToggleButton.setEnabled(true);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiz_droid_acitivity);

        WizdroidApplication.getInjector().injectMembers(this);
        init();
        getAudioFocus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        wizToggleButton.setOnCheckedChangeListener(null);
        wizToggleButton.setChecked(WizMediaActionReceiver.isBlocking(this) == 1);
        wizToggleButton.setOnCheckedChangeListener(this.mOnCheckedChangeListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wizOff.clearAnimation();
        wizOn.clearAnimation();
    }

    private void getAudioFocus() {
        int result = audioManager.requestAudioFocus(listener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            wizLogger.debug("AUDIOFOCUS_REQUEST_GRANTED");
        }
    }

    private void init() {
        wizToggleButton = (ToggleButton) findViewById(R.id.toggleButton1);
        wizOff = (ImageView) findViewById(R.id.wizdroid_off);
        wizOn = (ImageView) findViewById(R.id.wizdroid_on);
        mContext = this;

        prepAnimation();
        transitionAnimation(WizMediaActionReceiver.isBlocking(this) == 1);

        wizLogger.debug("We are Magical");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        audioManager.unregisterMediaButtonEventReceiver(componentName);
    }

    private void requestMediaButtonReceiver(boolean block) {
        if (!block) {
            Intent i = new Intent(this, WizAlarmReceiver.class);
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
            alarmManager.cancel(pi);
        } else {
            Intent i = new Intent(this, WizAlarmReceiver.class);
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), WIZFREQUENCY, pi);
        }
    }

    private void transitionAnimation(boolean block) {
        if (!block) {
            //off transition
            wizOn.startAnimation(animationSlideOutRight);
            wizOn.setVisibility(View.INVISIBLE);

            wizOff.startAnimation(animationSlideInLeft);
            wizOff.setVisibility(View.VISIBLE);
        } else {
            //on transition
            wizOff.startAnimation(animationSlideOutRight);
            wizOff.setVisibility(View.INVISIBLE);

            wizOn.startAnimation(animationSlideInLeft);
            wizOn.setVisibility(View.VISIBLE);
        }
    }

    private void prepAnimation() {
        animationSlideInLeft = AnimationUtils.loadAnimation(this,
                android.R.anim.slide_in_left);
        animationSlideOutRight = AnimationUtils.loadAnimation(this,
                android.R.anim.slide_out_right);
        animationSlideInLeft.setDuration(1000);
        animationSlideOutRight.setDuration(1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_wiz_droid_acitivity, menu);
        return true;
    }
}
