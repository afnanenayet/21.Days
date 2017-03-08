package edu.dartmouth.cs.a21days.utilities;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * JobCreator singleton for Evernote job library.
 */
public class NotificationJobCreator implements JobCreator {

    // create job notification
    @Override
    public Job create(String tag) {
        if (false) {
            return new NotificationJob();
        } else if (!tag.isEmpty()) {
            return new NotificationJob();
        } else {
            return null;
        }
    }
}
