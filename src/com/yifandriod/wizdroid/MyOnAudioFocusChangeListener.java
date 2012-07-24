package com.yifandriod.wizdroid;

import android.content.ComponentName;
import android.media.AudioManager;
import com.yifandriod.wizdroid.WizLog.WizLogger;

/**
* Created by IntelliJ IDEA.
* User: ywang
* Date: 23/07/12
* Time: 10:31 AM
* To change this template use File | Settings | File Templates.
*/
class MyOnAudioFocusChangeListener implements AudioManager.OnAudioFocusChangeListener {
    private AudioManager audioManager;
    private ComponentName componentName;
    private WizLogger wizLogger;

    MyOnAudioFocusChangeListener(AudioManager audioManager, ComponentName componentName, WizLogger wizLogger) {
        this.audioManager = audioManager;
        this.componentName = componentName;
        this.wizLogger = wizLogger;
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        wizLogger.info("AudioFocusChanged");
        if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
            wizLogger.info("AUDIOFOCUS_LOSS_TRANSIENT");
//                    am.registerMediaButtonEventReceiver(componentName);
        }
        else if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
            wizLogger.info("AUDIOFOCUS_GAIN");
            audioManager.registerMediaButtonEventReceiver(componentName);
        }
        else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
            wizLogger.info("AUDIOFOCUS_LOSS");
            int result = audioManager.requestAudioFocus(this,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
            wizLogger.info(result);
            audioManager.registerMediaButtonEventReceiver(componentName);
//                    am.abandonAudioFocus(afChangeListener);
            // Stop playback
        }
    }
}
