package com.yifandroid.wizdroid;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.RemoteViews;
import com.google.inject.Inject;
import com.yifandroid.wizdroid.WizLog.WizLogger;

/**
 * Created with IntelliJ IDEA.
 * User: robert
 * Date: 04/01/13
 * Time: 2:24 PM
 */
public class WizWidget extends AppWidgetProvider {
    @Inject
    private WizLogger wizLogger;

    private static ComponentName wizName;
    private static final String toggleAction = "com.yifandroid.wizdroid.TOGGLE";
    private static final ComponentName BLOCKER = new ComponentName("com.yifandroid.wizdroid", "com.yifandroid.wizdroid.WizMediaActionReceiver");

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        PackageManager localPackageManager = context.getPackageManager();
        boolean toggleState = localPackageManager.getComponentEnabledSetting(BLOCKER) == 1;
        AppWidgetManager.getInstance(context).updateAppWidget(wizName, buildUpdate(context, toggleState));

        if (intent.getAction().equals(toggleAction)) {
            WizWidget.updateWidget(context,!toggleState);
            WizMediaActionReceiver.toggleBlocking(context, !toggleState);
        }
    }

    public static void updateWidget(Context context, boolean toggleState) {
        RemoteViews updateViews = buildUpdate(context, toggleState);
        AppWidgetManager.getInstance(context).updateAppWidget(wizName, updateViews);
    }

    private static RemoteViews buildUpdate(Context context, boolean toggleState) {
        wizName = new ComponentName(context.getPackageName(), WizWidget.class.getName());
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.wiz_widget_layout);

        Intent toggleIntent = new Intent(context, WizWidget.class);
        toggleIntent.setAction(toggleAction);
        PendingIntent pendingToggleIntent = PendingIntent.getBroadcast(context, 0, toggleIntent, 0);
        updateViews.setOnClickPendingIntent(R.id.wiz_widget_frame, pendingToggleIntent);

        if (toggleState)
            updateViews.setImageViewResource(R.id.wiz_widget_img, R.drawable.wizdroid);
        else
            updateViews.setImageViewResource(R.id.wiz_widget_img, R.drawable.ic_launcher);
        return updateViews;
    }
}
