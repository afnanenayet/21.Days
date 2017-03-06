package edu.dartmouth.cs.a21days.utilities;

import android.app.Application;
import android.content.Context;

/**
 * Provides method to access application context externally.
 */
public class ApplicationContext extends Application {
    private static Context mContext;

    public void onCreate() {
        super.onCreate();
        ApplicationContext.mContext = getApplicationContext();
    }

    /**
     * Getter for application context
     * @return Context for application
     */
    public static Context getContext() {
        return ApplicationContext.mContext;
    }
}
