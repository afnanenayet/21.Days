package edu.dartmouth.cs.a21days.controllers;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import edu.dartmouth.cs.a21days.utilities.Globals;

/**
 * Receives intent and initializes a notification at a specific time
 */
public class NotificationPublisher extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(Globals.NOTIFICATION);
        int id = intent.getIntExtra(Globals.NOTIFICATION_HABIT_ID, 0);
        notificationManager.notify(id, notification);
    }
}
