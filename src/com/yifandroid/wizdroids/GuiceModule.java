package com.yifandroid.wizdroids;

import android.app.AlarmManager;
import android.content.ComponentName;
import android.content.Context;
import android.media.AudioManager;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.yifandroid.wizdroids.WizLog.LogHandler;
import com.yifandroid.wizdroids.WizLog.LogHandlerProvider;
import com.yifandroid.wizdroids.WizLog.WizLogger;

/**
 * Created by IntelliJ IDEA.
 * User: ywang
 * Date: 21/07/12
 * Time: 4:47 PM
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
        bind(ComponentName.class).toInstance(new ComponentName(context.getPackageName(), WizMediaActionReceiver.class.getName()));
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

        bind(WizOnAudioFocusChangeListener.class);
        bind(WizMediaActionReceiver.class);
        bind(WizAlarmReceiver.class);
        bind(WizWidget.class);
    }
}
