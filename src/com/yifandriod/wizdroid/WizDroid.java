package com.yifandriod.wizdroid;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.yifandriod.wizdroid.WizLog.WizLogger;

public class WizDroid extends Activity {
    @Inject
    private WizLogger wizLogger;

    @Inject
    private AudioManager.OnAudioFocusChangeListener listener;

    private Context mContext;
    private AudioManager am;
    private ComponentName componentName;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        GuiceModule guiceModule = new GuiceModule(this);

        Injector injector = Guice.createInjector(guiceModule);

        injector.injectMembers(this);
//        this.wizLogger = injector.getInstance(WizLogger.class);
//        injector.injectMembers(this);
        wizLogger.info("We are in main onCreate");


        int result = am.requestAudioFocus(listener, AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
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
