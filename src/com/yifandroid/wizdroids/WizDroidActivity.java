package com.yifandroid.wizdroids;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.ComponentName;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;
import com.google.inject.Inject;
import com.yifandroid.wizdroids.WizLog.WizLogger;

public class WizDroidActivity extends Activity {

    @Inject
    private WizLogger wizLogger;
    @Inject
    private static WizOnAudioFocusChangeListener listener;
    @Inject
    private AudioManager audioManager;
    @Inject
    private AlarmManager alarmManager;
    @Inject
    private ComponentName componentName;

    private static Context mContext;

    static ImageView wizOn;
    static ImageView wizOff;
    ToggleButton wizToggleButton;
    static Animation animationSlideInLeft;
    static Animation animationSlideOutRight;

    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean toggleState) {
            wizToggleButton.setEnabled(false);

            transitionAnimation(toggleState);
            WizMediaActionReceiver.toggleBlocking(mContext, toggleState);
            WizWidget.updateWidget(mContext, toggleState);

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
        wizToggleButton.setChecked(WizMediaActionReceiver.isBlocking(this));
        transitionAnimation(WizMediaActionReceiver.isBlocking(this));
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
        transitionAnimation(WizMediaActionReceiver.isBlocking(this));

        wizLogger.debug("We are Magical");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        audioManager.unregisterMediaButtonEventReceiver(componentName);
    }

    public static void transitionAnimation(boolean block) {
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
