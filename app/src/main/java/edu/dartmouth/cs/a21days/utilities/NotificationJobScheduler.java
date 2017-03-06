package edu.dartmouth.cs.a21days.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.media.RingtoneManager;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import edu.dartmouth.cs.a21days.controllers.MainActivity;

/**
 * Created by aenayet on 3/6/17.
 */

/**
 * The job to run when the timing has been scheduled
 */
public class NotificationJobScheduler extends Job {
    public static final String TAG = "notif_job_sched";

    /**
     * Set up notification from available data
     */
    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        PersistableBundleCompat bundle = params.getExtras();
        Context context = ApplicationContext.getContext();

        // Because we are breaking the static context rule, this may be null
        if (context != null) {
            // Creating notification
            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);

            Intent intent = new Intent(context, MainActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    intent, 0);

            Notification notification = new Notification.Builder(context)
                    .setContentTitle("Don't forget!")
                    .setContentText("You have a habit to maintain!")
                    .setContentIntent(pendingIntent)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setVibrate(new long[] { 500, 500, 500, 500})
                    .build();

            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(0, notification);

            return Result.SUCCESS;
        } else {
            return Result.FAILURE;
        }
    }

    /**
     * Schedule when notifications will go off
     */
    public static void scheduleJob(int hour, int minutes) {
        // 1 AM - 6 AM, ignore seconds
        long timeMs = TimeUnit.MINUTES.toMillis(60 - minutes)
                + TimeUnit.HOURS.toMillis((24 - hour) % 24);

        new JobRequest.Builder(TAG)
                .setExact(timeMs)
                .build()
                .schedule();
    }
}
