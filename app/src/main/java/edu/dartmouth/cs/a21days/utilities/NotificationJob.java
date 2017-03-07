package edu.dartmouth.cs.a21days.utilities;

import android.content.Context;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;

import java.util.concurrent.TimeUnit;

/**
 * The job to run when the timing has been scheduled.
 */
public class NotificationJob extends Job {
    // tag for debugging
    private static final String TAG = "notif_job_sched";

    /**
     * Sends a notification when job is scheduled
     */
    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        PersistableBundleCompat bundle = params.getExtras();
        Context context = ApplicationContext.getContext();

        // Because we are breaking the static context rule, this may be null
        if (context != null) {
            new NotificationJobTask().sendNotification(context);
            return Result.SUCCESS;
        } else {
            return Result.FAILURE;
        }
    }

    /**
     * Schedules when a notification will go off and the contents of the notification
     * @param title The title text for the notification
     * @param message The message text for the notification
     * @param hour The hour at which the notification should go off in 24 hour format
     *             (ex: if 1:30 PM, then the hour is 13)
     * @param minutes The minute at which the notification should go off (ex: if 1:30 PM, then the
     *                minute is 30)
     * @param days A boolean array of which days this notification will repeat
     */
    public static void scheduleJob(String title, String message,
                                   int hour, int minutes, boolean[] days) {

        // Getting time from now in milliseconds
        long timeMs = TimeUnit.MINUTES.toMillis(60 - minutes)
                + TimeUnit.HOURS.toMillis((24 - hour) % 24);

        PersistableBundleCompat params = new PersistableBundleCompat();
        params.putString(Globals.NOTIFICATION_TITLE_KEY, title);
        params.putString(Globals.NOTIFICATION_MESSAGE_KEY, message);

        // loop through boolean array
        for (int i = 0; i < days.length; i++) {
            // If this day is valid then schedule a job
            if (days[i]) {
                long offsetMs = HabitUtility.daysOffset(i);
                // build the job
                int j = new JobRequest.Builder(TAG)
                        .setExact(timeMs + offsetMs)
                        .setExtras(params)
                        .setPeriodic(Globals.dayInMs * 7)
                        .build()
                        .schedule();
            }
        }
    }
}
