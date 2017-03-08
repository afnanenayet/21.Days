package edu.dartmouth.cs.a21days.utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.field.MillisDurationField;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The job to run when the timing has been scheduled.
 */
public class NotificationJob extends Job {
    // tag for debugging
    static final String DEBUG_TAG = "NotificationJob";

    /**
     * Sends a notification when scheduled
     */
    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        Log.d(DEBUG_TAG, "Running job");

        PersistableBundleCompat bundle = params.getExtras();
        Context context = ApplicationContext.getContext();
        String title = bundle.getString(Globals.NOTIFICATION_TITLE_KEY, "");
        String message = bundle.getString(Globals.NOTIFICATION_MESSAGE_KEY, "");
        String id = bundle.getString(Globals.NOTIFICATION_HABIT_ID, "");

        // Scheduling next job for following week
        NotificationJob.scheduleJob(id, title, message, Globals.dayInMs * 7);

        // Because we are breaking the static context rule, this may be null
        if (context != null) {
            new NotificationJobTask().sendNotification(context, title, message);
            return Result.SUCCESS;
        } else {
            return Result.FAILURE;
        }
    }

    /**
     * Schedules when a notification will go off and the contents of the notification
     * @param habitId The ID for the habit
     * @param title The title text for the notification
     * @param message The message text for the notification
     * @param hour The hour at which the notification should go off in 24 hour format
     *             (ex: if 1:30 PM, then the hour is 13)
     * @param minutes The minute at which the notification should go off (ex: if 1:30 PM, then the
     *                minute is 30)
     * @param days A boolean array of which days this notification will repeat
     *
     * @return A list of Job ID(s) for the jobs that have been scheduled
     */
    public static ArrayList<Integer> scheduleJob(String habitId, String title, String message,
                                                 int hour, int minutes, boolean[] days) {
        Log.d(DEBUG_TAG, "Scheduling job");

        // Initializing time objects
        DateTime currentTime = DateTime.now();

        // Need to initialize as now first to get correct date
        DateTime targetTime = DateTime.now();
        targetTime = targetTime
                .withHourOfDay(hour)
                .withMinuteOfHour(minutes);

        // Calculating time offset for millis
        long targetMillis = targetTime.getMillis();
        long currentMillis = currentTime.getMillis();
        long timeOffsetMillis;
        boolean setDayBack = currentMillis < targetMillis;

        // If the target time is less than the current time for today, then we must add a full day
        // in milliseconds as longs cannot be negative. Then we must set the alarm back by a day
        // beceause we added a full day in milliseconds
        if (currentMillis < targetMillis) {
            timeOffsetMillis = targetMillis - currentMillis;
        } else {
            timeOffsetMillis = Globals.dayInMs + targetMillis - currentMillis;
        }

        // Pass arguments for notification
        PersistableBundleCompat params = new PersistableBundleCompat();
        params.putString(Globals.NOTIFICATION_TITLE_KEY, title);
        params.putString(Globals.NOTIFICATION_MESSAGE_KEY, message);
        params.putInt(Globals.NOTIFICATION_HOUR_KEY, hour);
        params.putInt(Globals.NOTIFICATION_MINUTE_KEY, minutes);
        params.putIntArray(Globals.NOTIFICATION_DAY_ARRAY_KEY,
                HabitUtility.booleanToIntArray(days));
        params.putString(Globals.NOTIFICATION_HABIT_ID, habitId);

        // Holds the Ids of the jobs that have been scheduled
        ArrayList<Integer> jobIds = new ArrayList<>();

        // loop through boolean array
        for (int i = 0; i < days.length; i++) {
            // If we must set the day back, subtract from the index but use modulus
            int effective_index = setDayBack ? (i + days.length - 1) % days.length : i;

            // If this day is valid then schedule a job
            if (days[effective_index]) {
                long offsetMs = HabitUtility.daysOffset(effective_index);

                Log.d(DEBUG_TAG, "Days offset: " + Long.toString(offsetMs));
                Log.d(DEBUG_TAG, "Time offset: " + Long.toString(timeOffsetMillis));

                // build the job
                int jobId = new JobRequest.Builder(habitId)
                        .setExact(timeOffsetMillis + offsetMs)
                        .setExtras(params)
                        .build()
                        .schedule();
                jobIds.add(jobId);
            }
        }

        return jobIds;
    }

    /**
     * Schedule a notification with only an offset
     * @param habitId The ID string for the habit as it is in firebase
     * @param title The title text for the notification
     * @param message The message text for the notification
     * @param offset the offset of the job, in milliseconds
     * @return The ID of the job
     */
    public static int scheduleJob(String habitId, String title, String message, long offset) {
        Log.d(DEBUG_TAG, "Scheduling job from previous job");

        // Pass arguments for notification
        PersistableBundleCompat params = new PersistableBundleCompat();
        params.putString(Globals.NOTIFICATION_TITLE_KEY, title);
        params.putString(Globals.NOTIFICATION_MESSAGE_KEY, message);
        params.putString(Globals.NOTIFICATION_HABIT_ID, habitId);

        return new JobRequest.Builder(habitId)
                .setExact(offset)
                .setExtras(params)
                .build()
                .schedule();
    }
    /**
     * Batch cancel a set of jobs pertaining to a particular habit
     * @param habitId The ID of the habit
     */
    public static void cancelJob(String habitId) {
        JobManager.instance().cancelAllForTag(habitId);
    }
}
