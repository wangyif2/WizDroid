package com.yifandroid.wizdroids;

import android.content.ComponentName;
import android.media.AudioManager;
import com.google.inject.Inject;
import com.yifandroid.wizdroids.WizLog.WizLogger;

/**
 * User: ywang
 * Date: 23/07/12
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
        wizLogger.debug("AudioFocusChanged");

        if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
            wizLogger.debug("AUDIOFOCUS_LOSS_TRANSIENT");
        }
        else if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
            wizLogger.debug("AUDIOFOCUS_GAIN");
        }
        else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
            wizLogger.debug("AUDIOFOCUS_LOSS");
        }

        audioManager.registerMediaButtonEventReceiver(componentName);
    }
}
