package edu.dartmouth.cs.a21days.utilities;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * Created by aenayet on 3/6/17.
 */

/**
 * JobCreator singleton for Evernote job library
 */
public class NotificationJobCreator implements JobCreator {

    @Override
    public Job create(String tag) {
        switch(tag) {
            case NotificationJobScheduler.TAG:
                return new NotificationJobScheduler();
            default:
                return null;
        }
    }
}
