package com.yifandriod.wizdroid;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.yifandriod.wizdroid.WizLog.LoggingModule;
import com.yifandriod.wizdroid.WizLog.WizLogger;

public class Main extends Activity {
    @Inject
    private WizLogger wizLogger;

    private AudioManager am;
    private Context mContext;
    private ComponentName componentName;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.mContext = this;
        this.am = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        this.componentName = new ComponentName(getPackageName(), RemoteControlReceiver.class.getName());

        LoggingModule loggingModule = new LoggingModule();
        Injector injector = Guice.createInjector(loggingModule);
        this.wizLogger = injector.getInstance(WizLogger.class);
//        injector.injectMembers(this);
        wizLogger.info("We are in main onCreate");

        AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                wizLogger.info("AudioFocusChanged");
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                    wizLogger.info("AUDIOFOCUS_LOSS_TRANSIENT");
//                    am.registerMediaButtonEventReceiver(componentName);
                }
                else if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
                    wizLogger.info("AUDIOFOCUS_GAIN");
                    am.registerMediaButtonEventReceiver(componentName);
                }
                else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                    wizLogger.info("AUDIOFOCUS_LOSS");
                    int result = am.requestAudioFocus(this,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
                    wizLogger.info(result);
                    am.registerMediaButtonEventReceiver(componentName);
//                    am.abandonAudioFocus(afChangeListener);
                    // Stop playback
                }
            }
        };


        // Request audio focus for playback

        int result = am.requestAudioFocus(afChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
        {
            wizLogger.info("AUDIOFOCUS_REQUEST_GRANTED");
//            am.unregisterMediaButtonEventReceiver(componentName);
            // Start playback.
        }

        am.registerMediaButtonEventReceiver(componentName);
    }




    @Override
    protected void onStop() {
        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
        wizLogger.info("unregisterMediaButtonEventReceiver onStop");
//        am.unregisterMediaButtonEventReceiver(componentName);
    }
}
