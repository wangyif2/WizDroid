package com.yifandriod.wizdroid;

import android.app.AlarmManager;
import android.content.ComponentName;
import android.content.Context;
import android.media.AudioManager;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.yifandriod.wizdroid.WizLog.LogHandler;
import com.yifandriod.wizdroid.WizLog.LogHandlerProvider;
import com.yifandriod.wizdroid.WizLog.WizLogger;

/**
 * Created by IntelliJ IDEA.
 * User: ywang
 * Date: 21/07/12
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class GuiceModule extends AbstractModule{

    private Context context;

    public GuiceModule(Context context) {
        this.context = context;
    }

    @Override
    protected void configure() {
        bind(WizLogger.class).in(Singleton.class);
        bind(LogHandler.class).toProvider(LogHandlerProvider.class).in(Singleton.class);
        bind(ComponentName.class).toInstance(new ComponentName(context.getPackageName(), RemoteControlReceiver.class.getName()));
        bind(AudioManager.class).toProvider(new Provider<AudioManager>() {
            @Override
            public AudioManager get() {
                return (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            }
        });

        bind(AlarmManager.class).toProvider(new Provider<AlarmManager>() {
            @Override
            public AlarmManager get() {
                return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            }
        });

        bind(MyOnAudioFocusChangeListener.class);
        bind(RemoteControlReceiver.class);
        bind(OnAlarmReceiver.class);
    }
}
