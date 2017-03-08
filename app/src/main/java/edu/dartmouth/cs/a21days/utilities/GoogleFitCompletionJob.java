package edu.dartmouth.cs.a21days.utilities;

import android.app.job.JobScheduler;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

/**
 * Created by aenayet on 3/8/17.
 */

public class GoogleFitCompletionJob extends Job {
    public static String TAG = "GoogleFitCompletionJob";
    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        return null;
    }

    public static int scheduleJob() {
        return new JobRequest.Builder(GoogleFitCompletionJob.TAG)
                .build()
                .schedule();
    }
}
