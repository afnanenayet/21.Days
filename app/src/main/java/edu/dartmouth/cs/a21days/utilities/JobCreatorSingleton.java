package edu.dartmouth.cs.a21days.utilities;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * JobCreator singleton for Evernote job library.
 */
public class JobCreatorSingleton implements JobCreator {

    // create job notification
    @Override
    public Job create(String tag) {
        if (tag.equals(GoogleFitCompletionJob.TAG)) {
            return new GoogleFitCompletionJob();
        } else if (!tag.isEmpty()) {
            return new NotificationJob();
        } else {
            return null;
        }
    }
}
