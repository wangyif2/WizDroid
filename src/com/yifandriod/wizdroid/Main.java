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

public class Main extends Activity
{
    @Inject
    private WizLogger wizLogger;

    private AudioManager am;
    private Context mContext;
    private ComponentName componentName;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
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

        am.registerMediaButtonEventReceiver(componentName);
    }

    @Override
    protected void onStop() {
        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
        am.unregisterMediaButtonEventReceiver(componentName);
    }
}
