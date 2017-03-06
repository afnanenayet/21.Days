package edu.dartmouth.cs.a21days.utilities;

/**
 * Created by aenayet on 3/6/17.
 */

import android.app.Notification;
import android.content.Context;
import android.content.Intent;

import edu.dartmouth.cs.a21days.controllers.NotificationPublisher;

/**
 * Helper class to create scheduled notifications at certain times
 */
public class NotificationUtility {
    private Context mContext;

    public NotificationUtility(Context context) {
        this.mContext = context;
    }

    public void scheduleNotification(String id, Notification notification) {

        // Setting up notification intent
        Intent intent = new Intent(mContext, NotificationPublisher.class);
        intent.putExtra(Globals.NOTIFICATION_HABIT_ID, id);
        intent.putExtra(Globals.NOTIFICATION, notification);

        // Setting up alarm to schedule notification
        
    }

    public void cancelNotification() {
    }
}
