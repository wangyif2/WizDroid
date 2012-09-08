package com.yifandriod.wizdroid;

import android.content.ComponentName;
import android.media.AudioManager;
import com.google.inject.Inject;
import com.yifandriod.wizdroid.WizLog.WizLogger;

/**
* Created by IntelliJ IDEA.
* User: ywang
* Date: 23/07/12
* Time: 10:31 AM
* To change this template use File | Settings | File Templates.
*/
public class WizOnAudioFocusChangeListener implements AudioManager.OnAudioFocusChangeListener {
    private AudioManager audioManager;
    private ComponentName componentName;
    private WizLogger wizLogger;

    @Inject
    WizOnAudioFocusChangeListener(AudioManager audioManager, ComponentName componentName, WizLogger wizLogger) {
        this.audioManager = audioManager;
        this.componentName = componentName;
        this.wizLogger = wizLogger;
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        wizLogger.info("AudioFocusChanged");

        if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
            wizLogger.info("AUDIOFOCUS_LOSS_TRANSIENT");
        }
        else if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
            wizLogger.info("AUDIOFOCUS_GAIN");
        }
        else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
            wizLogger.info("AUDIOFOCUS_LOSS");
        }

        audioManager.registerMediaButtonEventReceiver(componentName);
    }
}
