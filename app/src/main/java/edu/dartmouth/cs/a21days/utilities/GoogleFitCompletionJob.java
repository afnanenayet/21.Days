package edu.dartmouth.cs.a21days.utilities;

import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

/**
 * Created by aenayet on 3/8/17.
 */

/**
 * Periodically checks Google Fit to see if user has completed habit
 */
public class GoogleFitCompletionJob extends Job {
    public static final String TAG = "GoogleFitCompletionJob";

    /**
     * Updates habits and corresponding UI
     */
    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        Log.d(TAG, "Running job");
        GoogleFitCompletionTask task = new GoogleFitCompletionTask();
        task.setHabitCompleted();
        task.updateUiData();
        return null;
    }

    /**
     * Starts a periodic job to check Google fit completion
     * @param intervalLength The length of the interval between checks in milliseconds
     * @return The ID of the job
     */
    public static int startJob(long intervalLength) {
        return new JobRequest.Builder(TAG)
                .setPeriodic(intervalLength)
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
