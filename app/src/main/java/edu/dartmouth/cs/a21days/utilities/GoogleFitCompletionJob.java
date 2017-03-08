package edu.dartmouth.cs.a21days.utilities;

import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;

/**
 * Periodically checks Google Fit to see if user has completed a habit linked with Google Fit.
 */
public class GoogleFitCompletionJob extends Job {
    // debugging tag
    public static final String TAG = "GoogleFitCompletionJob";

    /**
     * Updates habits and corresponding UI
     */
    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        PersistableBundleCompat extras = params.getExtras();
        String userId = extras.getString(Globals.USER_ID_KEY, "");

        // check if user ID exists
        if (!userId.isEmpty()) {
            Log.d(TAG, "Running job");
            // create a task to check completion
            GoogleFitCompletionTask task = new GoogleFitCompletionTask(Globals.userId);

            try {
                task.setHabitCompleted();
            } catch (Exception e) {
                Log.e(TAG, "Task failed");
                e.printStackTrace();
            }

            return Result.SUCCESS;
        } else
            return Result.FAILURE;
    }

    /**
     * Starts a periodic job to check Google fit completion
     * @param userId The ID string for the user
     * @param intervalLength The length of the interval between checks in milliseconds
     * @return The ID of the job
     */
    public static int startJob(String userId, long intervalLength) {
        PersistableBundleCompat params = new PersistableBundleCompat();

        params.putString(Globals.USER_ID_KEY, userId);

        return new JobRequest.Builder(TAG)
                .setPeriodic(intervalLength)
                .setExtras(params)
                .build()
                .schedule();
    }

    /**
     * Cancels all GoogleFitCompletionJobs
     */
    public static void cancelJob() {
        JobManager.instance().cancelAllForTag(TAG);
    }

    /**
     * Cancels a particular job with this ID
     * @param id the ID of the job
     */
    public static void cancelJob(int id) {
        JobManager.instance().cancel(id);
    }
}
