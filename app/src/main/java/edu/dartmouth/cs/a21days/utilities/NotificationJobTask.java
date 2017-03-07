package edu.dartmouth.cs.a21days.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.annotation.WorkerThread;

import edu.dartmouth.cs.a21days.controllers.MainActivity;

/**
 * Class to handle logic for notification job.
 */
public class NotificationJobTask {

    /**
     * Sends notification with intent in a worker thread
     * @param context System context to get notification manager
     */
    @WorkerThread
    public void sendNotification(Context context) {
        // Creating notification
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        // set up intent so clicking on notification opens app
        Intent intent = new Intent(context, MainActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intent, 0);

        // build notification
        Notification notification = new Notification.Builder(context)
                .setContentTitle("Don't forget!")
                .setContentText("You have a habit to maintain!")
                .setContentIntent(pendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[] {500, 500, 500, 500})
                .build();

        // clears notification when it is clicked
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);
    }
}
